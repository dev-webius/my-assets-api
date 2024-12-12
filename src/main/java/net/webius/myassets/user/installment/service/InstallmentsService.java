package net.webius.myassets.user.installment.service;

import lombok.RequiredArgsConstructor;
import net.webius.myassets.global.auth.entity.UserEntity;
import net.webius.myassets.user.asset.service.AssetsService;
import net.webius.myassets.user.installment.dto.InstallmentApplicationReq;
import net.webius.myassets.user.installment.entity.AssetInstallmentExtraEntity;
import net.webius.myassets.user.installment.repository.AssetInstallmentExtraRepository;
import net.webius.myassets.util.DateUtils;
import net.webius.myassets.global.auth.service.UserEntityUserDetailsService;
import net.webius.myassets.user.installment.domain.AssetInstallment;
import net.webius.myassets.user.installment.dto.InstallmentCreationReq;
import net.webius.myassets.user.installment.dto.InstallmentDetailRes;
import net.webius.myassets.user.installment.dto.InstallmentsRes;
import net.webius.myassets.user.installment.entity.AssetInstallmentEntity;
import net.webius.myassets.user.asset.exception.AssetNotFoundException;
import net.webius.myassets.user.installment.exception.InstallmentNotFoundException;
import net.webius.myassets.user.installment.repository.AssetInstallmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class InstallmentsService {
    private final AssetInstallmentRepository assetInstallmentRepository;
    private final UserEntityUserDetailsService userEntityUserDetailsService;
    private final AssetsService assetsService;
    private final AssetInstallmentExtraRepository assetInstallmentExtraRepository;

    @Transactional
    public URI addInstallment(InstallmentCreationReq installmentCreationReq) throws AssetNotFoundException {
        var userEntity = userEntityUserDetailsService.getUserDetails().getUserEntity();
        var assetEntity = assetsService.findAssetByAssetId(installmentCreationReq.getAssetId(), userEntity);

        var paidAt = installmentCreationReq.getPaidAt(); // NOTE: LocalDate, LocalDateTime 고민
        var beginAt = paidAt.plusMonths(1).withDayOfMonth(1); // 다음달 1일로 초기화
        var endAt = beginAt.plusMonths(installmentCreationReq.getTotalMonth());

        var assetInstallmentExtraEntity = new AssetInstallmentExtraEntity();
        assetInstallmentExtraEntity.setMemo(installmentCreationReq.getMemo());
        assetInstallmentExtraEntity = assetInstallmentExtraRepository.save(assetInstallmentExtraEntity);

        // NOTE: null 요소는 현재 사용하지 않음
        var assetInstallmentEntity = new AssetInstallmentEntity();
        assetInstallmentEntity.setAsset(assetEntity);
        assetInstallmentEntity.setName(installmentCreationReq.getName());
        assetInstallmentEntity.setAmount(installmentCreationReq.getAmount());
        assetInstallmentEntity.setAssetInstallmentProvider(null);
        assetInstallmentEntity.setAssetInstallmentProviderCode(null);
        assetInstallmentEntity.setTotalMonth(installmentCreationReq.getTotalMonth());
        assetInstallmentEntity.setFee(0L);
        assetInstallmentEntity.setIsInterestFree(installmentCreationReq.getIsInterestFee());
        assetInstallmentEntity.setPaidAt(installmentCreationReq.getPaidAt());
        assetInstallmentEntity.setBeginAt(beginAt);
        assetInstallmentEntity.setEndAt(endAt);
        assetInstallmentEntity.setExtra(assetInstallmentExtraEntity);

        assetInstallmentRepository.save(assetInstallmentEntity);

        return UriComponentsBuilder
                .fromUriString("/v1/installments/{id}")
                .build(assetInstallmentEntity.getUuid());
    }

    @Transactional(readOnly = true)
    public InstallmentsRes getInstallments() {
        var userEntity = userEntityUserDetailsService.getUserDetails().getUserEntity();

        var totalCount = assetInstallmentRepository.countByAsset_User_Id(userEntity.getId());
        var assetInstallmentEntities = assetInstallmentRepository.findAllByAsset_User_Id(userEntity.getId());

        var response = new InstallmentsRes();
        response.setTotalCount(totalCount);
        response.setInstallments(AssetInstallment.fromEntities(assetInstallmentEntities));

        return response;
    }

    @Transactional(readOnly = true)
    public InstallmentDetailRes getInstallment(UUID installmentId) throws InstallmentNotFoundException {
        var userEntity = userEntityUserDetailsService.getUserDetails().getUserEntity();
        var assetInstallmentEntity = findInstallmentById(installmentId, userEntity);

        var currentMonth = DateUtils.getCurrentMonth(assetInstallmentEntity.getBeginAt(), assetInstallmentEntity.getEndAt());

        var response = new InstallmentDetailRes();
        response.setName(assetInstallmentEntity.getName());
        response.setAmount(assetInstallmentEntity.getAmount());
        response.setCurrentMonth(currentMonth);
        response.setTotalMonth(assetInstallmentEntity.getTotalMonth());
        response.setFee(assetInstallmentEntity.getFee());
        response.setIsInterestFree(assetInstallmentEntity.getIsInterestFree());
        response.setMemo(assetInstallmentEntity.getExtra().getMemo());
        response.setPaidAt(assetInstallmentEntity.getPaidAt());
        response.setBeginAt(assetInstallmentEntity.getBeginAt());
        response.setEndAt(assetInstallmentEntity.getEndAt());

        return response;
    }

    @Transactional
    public void applyInstallment(UUID installmentId, InstallmentApplicationReq installmentApplicationReq) throws InstallmentNotFoundException {
        var userEntity = userEntityUserDetailsService.getUserDetails().getUserEntity();
        var assetInstallmentEntity = findInstallmentById(installmentId, userEntity);

        assetInstallmentEntity.getExtra().setMemo(installmentApplicationReq.getMemo());

        assetInstallmentRepository.save(assetInstallmentEntity);
    }

    @Transactional
    public void deleteInstallment(UUID installmentId) throws InstallmentNotFoundException {
        var userEntity = userEntityUserDetailsService.getUserDetails().getUserEntity();
        var assetInstallmentEntity = findInstallmentById(installmentId, userEntity);

        assetInstallmentRepository.delete(assetInstallmentEntity);
    }

    public AssetInstallmentEntity findInstallmentById(UUID installmentId, UserEntity userEntity) throws InstallmentNotFoundException {
        return assetInstallmentRepository.findByUuidAndAsset_User_Id(installmentId, userEntity.getId())
                .orElseThrow(() -> new InstallmentNotFoundException("Installment: " + installmentId + ", User: " + userEntity.getUsername()));
    }
}

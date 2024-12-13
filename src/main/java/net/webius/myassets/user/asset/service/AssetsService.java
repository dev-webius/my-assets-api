package net.webius.myassets.user.asset.service;

import lombok.RequiredArgsConstructor;
import net.webius.myassets.global.auth.entity.UserEntity;
import net.webius.myassets.global.auth.service.UserEntityUserDetailsService;
import net.webius.myassets.user.asset.domain.Asset;
import net.webius.myassets.user.asset.domain.AssetProvider;
import net.webius.myassets.user.asset.dto.*;
import net.webius.myassets.user.asset.entity.AssetEntity;
import net.webius.myassets.user.asset.entity.AssetProviderEntity;
import net.webius.myassets.user.asset.entity.AssetStatisticsEntity;
import net.webius.myassets.user.asset.exception.AssetAliasAlreadyUsingException;
import net.webius.myassets.user.asset.exception.AssetNotFoundException;
import net.webius.myassets.user.asset.exception.AssetProviderNotFoundException;
import net.webius.myassets.user.asset.repository.AssetProviderRepository;
import net.webius.myassets.user.asset.repository.AssetRepository;
import net.webius.myassets.user.asset.repository.AssetStatisticsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class AssetsService {
    private final UserEntityUserDetailsService userEntityUserDetailsService;
    private final AssetRepository assetRepository;
    private final AssetProviderRepository assetProviderRepository;
    private final AssetStatisticsRepository assetStatisticsRepository;

    @Transactional(readOnly = true)
    public AssetDetailRes getAsset(UUID assetId) {
        var userEntity = userEntityUserDetailsService.getUserDetails().getUserEntity();

        var assetEntity = findAssetByAssetId(assetId, userEntity);

        var response = new AssetDetailRes();
        response.setType(assetEntity.getType());
        response.setProvider(AssetProvider.fromEntity(assetEntity.getAssetProvider()));
        response.setAmount(assetEntity.getAmount());
        response.setAlias(assetEntity.getAlias());
        response.setMemo(assetEntity.getMemo());
        response.setUpdatedAt(assetEntity.getUpdatedAt());

        return response;
    }

    @Transactional(readOnly = true)
    public AssetsRes getAssets() {
        var userEntity = userEntityUserDetailsService.getUserDetails().getUserEntity();

        var totalCount = assetRepository.countAllByUser_Uuid(userEntity.getUuid());
        var assetEntities = assetRepository.findAllByUser_Uuid(userEntity.getUuid());

        var response = new AssetsRes();
        response.setTotalCount(totalCount);
        response.setAssets(Asset.fromEntities(assetEntities));

        return response;
    }

    @Transactional
    public URI createAsset(AssetCreationReq assetCreationReq) throws AssetAliasAlreadyUsingException, AssetProviderNotFoundException, URISyntaxException {
        var userEntity = userEntityUserDetailsService.getUserDetails().getUserEntity();

        // 별칭 중복 검증
        verifyAssetAliasAlreadyExists(assetCreationReq.getAlias());

        var assetProviderEntity = findProviderByProviderCode(assetCreationReq.getProviderCode());

        AssetEntity assetEntity = new AssetEntity();
        assetEntity.setUser(userEntity);
        assetEntity.setType(assetCreationReq.getType());
        assetEntity.setAssetProvider(assetProviderEntity);
        assetEntity.setAssetProviderCode(assetCreationReq.getProviderCode());
        assetEntity.setAlias(assetCreationReq.getAlias());
        assetEntity.setMemo(assetCreationReq.getMemo());

        assetRepository.save(assetEntity);

        return UriComponentsBuilder
                .fromUriString("/v1/assets/{id}")
                .build(assetEntity.getUuid());
    }

    @Transactional
    public void applyAsset(UUID id, AssetApplicationReq assetApplicationReq) throws AssetNotFoundException, AssetAliasAlreadyUsingException, AssetProviderNotFoundException {
        var userEntity = userEntityUserDetailsService.getUserDetails().getUserEntity();
        var assetEntity = findAssetByAssetId(id, userEntity);

        // Alias 변경 검토
        if (!assetEntity.getAlias().equals(assetApplicationReq.getAlias())) {
            verifyAssetAliasAlreadyExists(assetApplicationReq.getAlias());
            assetEntity.setAlias(assetApplicationReq.getAlias());
        }

        // Provider Code 변경 검토
        if (!assetEntity.getAssetProviderCode().equals(assetApplicationReq.getProviderCode())) {
            var assetProviderEntity = findProviderByProviderCode(assetApplicationReq.getProviderCode());
            assetEntity.setAssetProvider(assetProviderEntity);
            assetEntity.setAssetProviderCode(assetProviderEntity.getCode());
        }

        assetEntity.setMemo(assetApplicationReq.getMemo());
        assetRepository.save(assetEntity);
    }

    @Transactional
    public void deleteAsset(UUID id) throws AssetNotFoundException {
        var userEntity = userEntityUserDetailsService.getUserDetails().getUserEntity();
        var assetEntity = findAssetByAssetId(id, userEntity);

        assetRepository.delete(assetEntity);
    }

    @Transactional(readOnly = true)
    public AssetCreationOptionsRes getAssetCreationOptions() {
        var assetProviderEntities = assetProviderRepository.findAll();

        var response = new AssetCreationOptionsRes();
        response.setProviders(AssetProvider.fromEntities(assetProviderEntities));

        return response;
    }

    @Transactional
    public void updateAssetUsage(UUID id, AssetUsageReq assetUsageReq) throws AssetNotFoundException {
        var userEntity = userEntityUserDetailsService.getUserDetails().getUserEntity();
        var assetEntity = findAssetByAssetId(id, userEntity);

        var assetStatisticsEntity = new AssetStatisticsEntity();
        assetStatisticsEntity.setAssetId(assetEntity.getId());
        assetStatisticsEntity.setAmount(assetUsageReq.getAmount());
        assetStatisticsEntity.setDateAt(assetUsageReq.getDateAt());
        assetStatisticsRepository.save(assetStatisticsEntity);

        assetEntity.setAmount(assetUsageReq.getAmount());
        assetRepository.save(assetEntity);
    }

    public void verifyAssetAliasAlreadyExists(String alias) throws AssetAliasAlreadyUsingException {
        if (assetRepository.existsByAlias(alias)) {
            throw new AssetAliasAlreadyUsingException(alias);
        }
    }

    public AssetEntity findAssetByAssetId(UUID assetId, UserEntity userEntity) throws AssetNotFoundException {
        return assetRepository.findByUuidAndUser_Id(assetId, userEntity.getId())
                .orElseThrow(() -> new AssetNotFoundException("Asset: " + assetId + ", User: " + userEntity.getUsername()));
    }

    public AssetProviderEntity findProviderByProviderCode(String providerCode) throws AssetProviderNotFoundException {
        return assetProviderRepository.findByCode(providerCode)
                .orElseThrow(() -> new AssetProviderNotFoundException(providerCode));
    }
}

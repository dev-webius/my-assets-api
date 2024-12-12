package net.webius.myassets.user.installment.domain;

import lombok.Getter;
import lombok.Setter;
import net.webius.myassets.user.installment.entity.AssetInstallmentEntity;
import net.webius.myassets.util.DateUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter @Setter
public class AssetInstallment {
    private UUID id;
    private String name;
    private Long amount;
    private Long currentMonth;
    private Long totalMonth;
    private Long fee;
    private Boolean isInterestFree;

    public static AssetInstallment fromEntity(AssetInstallmentEntity entity) {
        AssetInstallment assetInstallment = new AssetInstallment();
        assetInstallment.setId(entity.getUuid());
        assetInstallment.setName(entity.getName());
        assetInstallment.setAmount(entity.getAmount());
        assetInstallment.setCurrentMonth(DateUtils.getCurrentMonth(entity.getBeginAt(), entity.getEndAt()));
        assetInstallment.setTotalMonth(entity.getTotalMonth());
        assetInstallment.setFee(entity.getFee());
        assetInstallment.setIsInterestFree(entity.getIsInterestFree());
        return assetInstallment;
    }

    public static List<AssetInstallment> fromEntities(List<AssetInstallmentEntity> entities) {
        return entities.stream().map(AssetInstallment::fromEntity).collect(Collectors.toList());
    }
}

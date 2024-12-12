package net.webius.myassets.user.installment.dto;

import lombok.Getter;
import lombok.Setter;
import net.webius.myassets.user.installment.domain.AssetInstallment;

import java.util.List;

@Getter @Setter
public class InstallmentsRes {
    private Long totalCount;
    private List<AssetInstallment> installments;
}

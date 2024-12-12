package net.webius.myassets.user.installment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "asset_installment_extra")
@Getter @Setter
public class AssetInstallmentExtraEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PrimaryKeyJoinColumn @OneToOne
    private AssetInstallmentEntity assetInstallment;

    @Column(nullable = false)
    private String memo;
}

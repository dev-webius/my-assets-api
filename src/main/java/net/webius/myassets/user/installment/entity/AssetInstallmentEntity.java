package net.webius.myassets.user.installment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.webius.myassets.user.asset.entity.AssetEntity;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "asset_installment")
@Getter @Setter
public class AssetInstallmentEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID uuid = UUID.randomUUID();

    @ManyToOne
    private AssetEntity asset;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Long amount;

    @JoinColumn(nullable = true) @ManyToOne
    private AssetInstallmentProviderEntity assetInstallmentProvider;

    @Column(nullable = true, length = 20)
    private String assetInstallmentProviderCode;

    @Column(nullable = false)
    private Long totalMonth;

    @Column(nullable = false)
    private Long fee = 0L;

    @Column(nullable = false)
    private Boolean isInterestFree;

    @OneToOne
    private AssetInstallmentExtraEntity extra;

    @Column(nullable = false)
    private LocalDate paidAt;

    @Column(nullable = false)
    private LocalDate beginAt;

    @Column(nullable = false)
    private LocalDate endAt;
}

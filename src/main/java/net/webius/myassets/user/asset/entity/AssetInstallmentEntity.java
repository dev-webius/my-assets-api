package net.webius.myassets.user.asset.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "asset_installment")
@Getter @Setter
public class AssetInstallmentEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID uuid = UUID.randomUUID();

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Long amount;

    @JoinColumn(nullable = false) @ManyToOne
    private AssetInstallmentProviderEntity assetInstallmentProvider;

    @Column(nullable = false, length = 20)
    private String assetInstallmentProviderCode;

    @Column(nullable = false)
    private Integer totalMonth;

    @Column(nullable = false)
    private Integer fee;

    @Column(nullable = false)
    private Boolean isInterestFree;

    @Column(nullable = false)
    private LocalDateTime paidAt;

    @Column(nullable = false)
    private LocalDateTime beginAt;

    @Column(nullable = false)
    private LocalDateTime endAt;
}

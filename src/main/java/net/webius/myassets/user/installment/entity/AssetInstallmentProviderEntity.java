package net.webius.myassets.user.installment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.webius.myassets.user.asset.entity.AssetProviderEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity(name = "asset_installment_provider")
@Getter @Setter
public class AssetInstallmentProviderEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String code;

    @JoinColumn(nullable = false) @ManyToOne
    private AssetProviderEntity assetProvider;

    @Column(nullable = false, length = 20)
    private String assetProviderCode;

    @Column(nullable = false)
    private Long totalMonth;

    @Column(nullable = false)
    private Long fee;

    @Column(nullable = false) @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false) @UpdateTimestamp
    private LocalDateTime updatedAt;
}

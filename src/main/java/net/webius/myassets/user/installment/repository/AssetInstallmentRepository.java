package net.webius.myassets.user.installment.repository;

import net.webius.myassets.user.installment.entity.AssetInstallmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssetInstallmentRepository extends JpaRepository<AssetInstallmentEntity, Long> {
    Long countByAsset_User_Id(Long userId);
    List<AssetInstallmentEntity> findAllByAsset_User_Id(Long userId);
    Optional<AssetInstallmentEntity> findByUuidAndAsset_User_Id(UUID installmentId, Long userId);
}

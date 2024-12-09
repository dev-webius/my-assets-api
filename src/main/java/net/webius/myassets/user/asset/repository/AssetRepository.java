package net.webius.myassets.user.asset.repository;

import net.webius.myassets.user.asset.entity.AssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssetRepository extends JpaRepository<AssetEntity, Long> {
    long countAllByUser_Uuid(UUID uuid);
    List<AssetEntity> findAllByUser_Uuid(UUID userUuid);

    Optional<AssetEntity> findByUuidAndUser_Id(UUID assetUuid, Long userId);
    boolean existsByAlias(String alias);
}

package net.webius.myassets.user.asset.repository;

import net.webius.myassets.user.asset.entity.AssetEntity;
import net.webius.myassets.user.asset.entity.AssetProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssetProviderRepository extends JpaRepository<AssetProviderEntity, Long> {
    Optional<AssetProviderEntity> findByCode(String code);
}

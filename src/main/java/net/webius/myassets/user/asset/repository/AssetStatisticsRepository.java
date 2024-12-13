package net.webius.myassets.user.asset.repository;

import net.webius.myassets.user.asset.entity.AssetStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetStatisticsRepository extends JpaRepository<AssetStatisticsEntity, Long> {
}

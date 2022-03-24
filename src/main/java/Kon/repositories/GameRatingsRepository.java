package Kon.repositories;

import Kon.models.gameRatings.database.GameRatingsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRatingsRepository extends JpaRepository<GameRatingsModel, Integer> {
}

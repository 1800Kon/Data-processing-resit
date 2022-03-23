package Kon.repositories;

import Kon.models.videogameSales.database.VideogameSalesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideogameSalesRepository extends JpaRepository<VideogameSalesModel, Integer> {
}

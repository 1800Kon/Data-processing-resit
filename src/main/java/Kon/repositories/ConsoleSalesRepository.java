package Kon.repositories;

import Kon.models.consoleSales.database.ConsoleSalesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsoleSalesRepository extends JpaRepository<ConsoleSalesModel, Long> {

}

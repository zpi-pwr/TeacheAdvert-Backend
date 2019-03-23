package ppztw.AdvertBoard.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ppztw.AdvertBoard.Model.Subcategory;

import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    Optional<Subcategory> findBySubcategoryName(String subcategory_name);

    Boolean existsBySubcategoryName(String subcategory_name);
}
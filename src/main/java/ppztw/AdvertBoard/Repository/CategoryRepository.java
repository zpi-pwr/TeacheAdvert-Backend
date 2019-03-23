package ppztw.AdvertBoard.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ppztw.AdvertBoard.Model.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryName(String category_name);

    Boolean existsByCategoryName(String category_name);
}
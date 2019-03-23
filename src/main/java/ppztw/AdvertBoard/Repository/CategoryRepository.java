package ppztw.AdvertBoard.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ppztw.AdvertBoard.Model.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategory_Name(String category_name);

    Boolean existsByCategory_Name(String category_name);
}
package ppztw.AdvertBoard.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ppztw.AdvertBoard.Model.CategoryInfo;

@Repository
public interface CategoryInfoRepository extends JpaRepository<CategoryInfo, Long> {
}

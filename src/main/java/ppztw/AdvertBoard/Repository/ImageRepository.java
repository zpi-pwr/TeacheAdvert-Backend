package ppztw.AdvertBoard.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ppztw.AdvertBoard.Model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}

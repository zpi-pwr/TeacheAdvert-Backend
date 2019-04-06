package ppztw.AdvertBoard.Repository.Advert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ppztw.AdvertBoard.Model.Advert.Tag;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

}

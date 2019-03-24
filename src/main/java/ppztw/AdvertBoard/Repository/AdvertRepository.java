package ppztw.AdvertBoard.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ppztw.AdvertBoard.Model.Advert;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, Long> {

}

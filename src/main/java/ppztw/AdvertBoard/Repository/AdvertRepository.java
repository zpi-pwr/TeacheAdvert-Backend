package ppztw.AdvertBoard.Repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ppztw.AdvertBoard.Model.Advert;

@Repository
public interface AdvertRepository extends PagingAndSortingRepository<Advert, Long> {

}

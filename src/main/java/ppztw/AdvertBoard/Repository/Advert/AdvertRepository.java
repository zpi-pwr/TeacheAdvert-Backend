package ppztw.AdvertBoard.Repository.Advert;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ppztw.AdvertBoard.Model.Advert.Advert;

@Repository
public interface AdvertRepository extends PagingAndSortingRepository<Advert, Long> {

}

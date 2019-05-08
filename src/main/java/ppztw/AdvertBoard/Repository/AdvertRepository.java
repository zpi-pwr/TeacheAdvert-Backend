package ppztw.AdvertBoard.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ppztw.AdvertBoard.Model.Advert;

import java.util.List;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, Long> {

    @Query("SELECT a FROM #{#entityName} a JOIN a.tags t" +
            " WHERE t.name IN :tags AND" +
            " a.status IN (0, 1)" +
            " AND a.title LIKE CONCAT('%',:title,'%')")
    Page<Advert> findAllByTitleLikeAndTagsIn(Pageable pageable,
                                             @Param("title") String title,
                                             @Param("tags") List<String> tags);


    @Query("SELECT a FROM #{#entityName} a WHERE a.status IN (0, 1)" +
            " AND a.title LIKE CONCAT('%',:title,'%')")
    Page<Advert> findAllByTitleLike(Pageable pageable,
                                    @Param("title") String title);




}

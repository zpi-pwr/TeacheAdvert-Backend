package ppztw.AdvertBoard.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ppztw.AdvertBoard.Model.Profile;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUserId(Long userId);

    Page<Profile> findAll(Pageable pageable);

    @Query("SELECT p FROM #{#entityName} p WHERE p.visibleName LIKE CONCAT('%',:substr,'%')")
    Page<Profile> findAllByVisibleNameLike(@Param("substr") String nameContains, Pageable pageable);
}

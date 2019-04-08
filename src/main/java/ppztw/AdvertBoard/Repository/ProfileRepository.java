package ppztw.AdvertBoard.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ppztw.AdvertBoard.Model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}

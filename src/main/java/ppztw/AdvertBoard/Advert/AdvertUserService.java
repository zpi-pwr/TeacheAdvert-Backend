package ppztw.AdvertBoard.Advert;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppztw.AdvertBoard.Model.Advert;
import ppztw.AdvertBoard.Model.User;
import ppztw.AdvertBoard.Repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdvertUserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<Advert> findAdvert(User user, Long id) {

        List<Advert> adverts = user.getAdverts();
        Advert advert = null;
        for (Advert adv : adverts)
            if (adv.getId().equals(id)) {
                advert = adv;
                break;
            }

        return Optional.ofNullable(advert);
    }
}

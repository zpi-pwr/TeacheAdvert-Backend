package ppztw.AdvertBoard.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ppztw.AdvertBoard.Exception.ResourceNotFoundException;
import ppztw.AdvertBoard.Model.Profile;
import ppztw.AdvertBoard.Model.User;
import ppztw.AdvertBoard.Payload.ProfileInfo;
import ppztw.AdvertBoard.Repository.ProfileRepository;
import ppztw.AdvertBoard.Repository.UserRepository;
import ppztw.AdvertBoard.Security.UserPrincipal;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Transactional
    public void processProfile(UserPrincipal userPrincipal, ProfileInfo profileInfo) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        Profile profile = !profileRepository.findByUserId(userPrincipal.getId()).isPresent() ? new Profile() : user.getProfile();
        profile.setUser(user);
        profile.setContactMail(profileInfo.getContactMail());
        profile.setFirstName(profileInfo.getFirstName());
        profile.setLastName(profileInfo.getLastName());
        profile.setTelephoneNumber(profileInfo.getTelephoneNumber());
        profile.setVisibleName(profileInfo.getVisibleName());
        user.setProfile(profile);

        userRepository.save(user);
        profileRepository.save(profile);
    }

}

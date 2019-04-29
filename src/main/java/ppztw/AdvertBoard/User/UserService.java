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

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

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

        Map<Long, Double> categoryEntries = profileInfo.getCategoryEntries();
        if (categoryEntries != null) {
            Map<Long, Double> userCategoryEntries = user.getCategoryEntries();
            if (userCategoryEntries != null) {
                for (Map.Entry<Long, Double> entry : categoryEntries.entrySet()) {
                    Long catId = entry.getKey();
                    Double val = entry.getValue();
                    if (userCategoryEntries.containsKey(catId)) {
                        Double oldVal = userCategoryEntries.get(catId);
                        userCategoryEntries.put(catId, oldVal + val);
                    }
                }
                user.setCategoryEntries(userCategoryEntries);
            } else
                user.setCategoryEntries(categoryEntries);
        }
        userRepository.save(user);
        profileRepository.save(profile);
    }

    public void addCategoryEntry(Long categoryId, User user, Double val) {
        Map<Long, Double> categoryEntries = user.getCategoryEntries();
        if (categoryEntries == null) {
            categoryEntries = new HashMap<Long, Double>();
            categoryEntries.put(categoryId, val);
        } else {
            if (categoryEntries.containsKey(categoryId))
                val = val + categoryEntries.get(categoryId);
            categoryEntries.put(categoryId, val);

        }
        user.setCategoryEntries(categoryEntries);
        userRepository.save(user);
    }
}

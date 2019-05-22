package ppztw.AdvertBoard.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import ppztw.AdvertBoard.Exception.ResourceNotFoundException;
import ppztw.AdvertBoard.Model.Profile;
import ppztw.AdvertBoard.Model.ProfileRating;
import ppztw.AdvertBoard.Model.User;
import ppztw.AdvertBoard.Payload.ProfileInfo;
import ppztw.AdvertBoard.Repository.ProfileRatingRepository;
import ppztw.AdvertBoard.Repository.UserRepository;
import ppztw.AdvertBoard.Security.UserPrincipal;
import ppztw.AdvertBoard.View.User.ProfileSummaryView;
import ppztw.AdvertBoard.View.User.ProfileView;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRatingRepository profileRatingRepository;


    @Transactional
    public void processProfile(UserPrincipal userPrincipal, ProfileInfo profileInfo) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        Profile profile = user.getProfile() == null ? new Profile() : user.getProfile();
        profile.setContactMail(profileInfo.getContactMail());
        profile.setFirstName(profileInfo.getFirstName());
        profile.setLastName(profileInfo.getLastName());
        profile.setTelephoneNumber(profileInfo.getTelephoneNumber());
        profile.setVisibleName(profileInfo.getVisibleName());
        user.setProfile(profile);
        userRepository.save(user);
    }


    public Page<ProfileSummaryView> getAllProfileSummaryViews(Pageable pageable,
                                                              @RequestParam(required = false) String nameContains) {

        Page<User> users;
        if (nameContains != null && !nameContains.isEmpty())
            users = userRepository.findAllByProfileVisibleNameLike(nameContains, pageable);
        else
            users = userRepository.findAll(pageable);

        List<ProfileSummaryView> profileSummaryViewList = new ArrayList<>();
        for (User user : users)
            profileSummaryViewList.add(new ProfileSummaryView(user));
        return new PageImpl<>(profileSummaryViewList, pageable, users.getTotalElements());
    }


    public ProfileView getProfileView(Long profileId) {
        User user = userRepository.findByProfileId(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", profileId));

        Double rating = getProfileRating(user.getProfile().getId());
        Integer ratingCount = getProfileRatingCount(user.getProfile().getId());


        return new ProfileView(user, rating, ratingCount);
    }


    public void rateProfile(Long userId, Long ratedProfileId, Integer rating) {
        User user = findById(userId);
        ProfileRating profileRating = profileRatingRepository.
                findByRatingIdAndRatedId(user.getProfile().getId(), ratedProfileId).orElse(new ProfileRating());

        profileRating.setRating(rating.doubleValue());
        profileRating.setRatingId(user.getProfile().getId());
        profileRating.setRatedId(ratedProfileId);
        profileRatingRepository.save(profileRating);

    }

    public Double getProfileRating(Long profileId) {
        return profileRatingRepository.getProfileRating(profileId);
    }

    public Integer getProfileRatingCount(Long profileId) {
        return profileRatingRepository.getProfileRatingCount(profileId);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userId));
    }

}

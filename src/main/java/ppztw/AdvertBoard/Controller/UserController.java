package ppztw.AdvertBoard.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ppztw.AdvertBoard.Exception.ResourceNotFoundException;
import ppztw.AdvertBoard.Model.Profile;
import ppztw.AdvertBoard.Model.User;
import ppztw.AdvertBoard.Payload.ApiResponse;
import ppztw.AdvertBoard.Payload.ProfileInfo;
import ppztw.AdvertBoard.Repository.ProfileRepository;
import ppztw.AdvertBoard.Repository.UserRepository;
import ppztw.AdvertBoard.Security.CurrentUser;
import ppztw.AdvertBoard.Security.UserPrincipal;
import ppztw.AdvertBoard.User.UserService;
import ppztw.AdvertBoard.View.User.ProfileSummaryView;
import ppztw.AdvertBoard.View.User.ProfileView;
import ppztw.AdvertBoard.View.User.UserView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserView getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        return new UserView(user);

    }

    @PostMapping("user/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> setProfile(@CurrentUser UserPrincipal userPrincipal,
                                        @RequestBody ProfileInfo profileInfo) {
        userService.processProfile(userPrincipal, profileInfo);
        return ResponseEntity.ok(new ApiResponse(true, "Profile set successfully!"));
    }

    @GetMapping("/user/all")
    @PreAuthorize("permitAll()")
    public Page<ProfileSummaryView> getAllUsers(Pageable pageable,
                                                @RequestParam(required = false) String nameContains) {

        Page<Profile> profiles;
        if (nameContains != null && !nameContains.isEmpty())
            profiles = profileRepository.findAllByVisibleNameLike(nameContains, pageable);
        else
            profiles = profileRepository.findAll(pageable);

        List<ProfileSummaryView> profileSummaryViewList = new ArrayList<>();
        for (Profile profile : profiles)
            profileSummaryViewList.add(new ProfileSummaryView(profile));
        return new PageImpl<>(profileSummaryViewList, pageable, profiles.getTotalElements());
    }


    @GetMapping("/user/get")
    @PreAuthorize("permitAll()")
    public ProfileView getProfile(@Valid Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", profileId));

        return new ProfileView(profile);
    }
}
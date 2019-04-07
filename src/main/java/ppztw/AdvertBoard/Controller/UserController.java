package ppztw.AdvertBoard.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import ppztw.AdvertBoard.Util.PageUtils;
import ppztw.AdvertBoard.View.User.ProfileSummaryView;
import ppztw.AdvertBoard.View.User.ProfileView;
import ppztw.AdvertBoard.View.User.UserView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        PageUtils<ProfileSummaryView> pageUtils = new PageUtils<>();

        List<Profile> profiles = profileRepository.findAll();
        List<ProfileSummaryView> profileViews = new ArrayList<>();
        for (Profile profile : profiles)
            profileViews.add(new ProfileSummaryView(profile));
        if (nameContains != null)
            profileViews = profileViews.stream()
                    .filter(profile -> profile.getVisibleName().contains(nameContains))
                    .collect(Collectors.toList());
        return pageUtils.getPage(profileViews, pageable);
    }


    @GetMapping("/user/get")
    @PreAuthorize("permitAll()")
    public ProfileView getProfile(@Valid Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", profileId));

        return new ProfileView(profile);
    }
}
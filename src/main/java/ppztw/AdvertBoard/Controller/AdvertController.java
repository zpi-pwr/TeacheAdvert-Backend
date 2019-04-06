package ppztw.AdvertBoard.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ppztw.AdvertBoard.Advert.AdvertUserService;
import ppztw.AdvertBoard.Exception.ResourceNotFoundException;
import ppztw.AdvertBoard.Model.Advert;
import ppztw.AdvertBoard.Model.User;
import ppztw.AdvertBoard.Payload.Advert.CreateAdvertRequest;
import ppztw.AdvertBoard.Payload.Advert.EditAdvertRequest;
import ppztw.AdvertBoard.Payload.ApiResponse;
import ppztw.AdvertBoard.Repository.*;
import ppztw.AdvertBoard.Security.CurrentUser;
import ppztw.AdvertBoard.Security.UserPrincipal;
import ppztw.AdvertBoard.View.Advert.AdvertDetailsView;

import javax.validation.Valid;

@RestController
@RequestMapping("/advert")
public class AdvertController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AdvertUserService advertUserService;

    @Autowired
    private AdvertRepository advertRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CategoryInfoRepository categoryInfoRepository;

    @PostMapping(value = "/add")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public ResponseEntity<?> addAdvert(@CurrentUser UserPrincipal userPrincipal,
                                       @Valid @RequestBody CreateAdvertRequest createAdvertRequest) {

        advertUserService.addAdvert(userPrincipal, createAdvertRequest);
        return ResponseEntity.ok(new ApiResponse(true, "Added new advert"));
    }

    @PostMapping(value = "/edit")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public ResponseEntity<?> editAdvert(@CurrentUser UserPrincipal userPrincipal,
                                        @Valid @RequestPart EditAdvertRequest editAdvertRequest) {

        advertUserService.editAdvert(userPrincipal, editAdvertRequest);
        return ResponseEntity.ok(new ApiResponse(true, "Advert edited successfully"));
    }

    @PostMapping("remove")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> removeAdvert(@CurrentUser UserPrincipal userPrincipal,
                                          @RequestParam Long id) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        Advert advert = advertUserService.findAdvert(user, id).orElseThrow(() ->
                new ResourceNotFoundException("Advert", "id", id));

        advert.setStatus(Advert.Status.ARCHIVED);
        userRepository.save(user);
        return ResponseEntity.ok(new ApiResponse(true, "Advert removed."));
    }

    @GetMapping("get")
    @PreAuthorize("permitAll()")
    public AdvertDetailsView getAdvert(@RequestParam Long id) {
        Advert advert = advertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Advert", "id", id));

        return new AdvertDetailsView(advert);
    }
}

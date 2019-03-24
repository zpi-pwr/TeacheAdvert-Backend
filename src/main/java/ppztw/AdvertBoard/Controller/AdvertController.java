package ppztw.AdvertBoard.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ppztw.AdvertBoard.Advert.AdvertUserService;
import ppztw.AdvertBoard.Exception.ResourceNotFoundException;
import ppztw.AdvertBoard.Model.*;
import ppztw.AdvertBoard.Payload.Advert.CreateAdvertRequest;
import ppztw.AdvertBoard.Payload.Advert.EditAdvertRequest;
import ppztw.AdvertBoard.Payload.ApiResponse;
import ppztw.AdvertBoard.Repository.AdvertRepository;
import ppztw.AdvertBoard.Repository.SubcategoryRepository;
import ppztw.AdvertBoard.Repository.UserRepository;
import ppztw.AdvertBoard.Security.CurrentUser;
import ppztw.AdvertBoard.Security.UserPrincipal;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/advert")
public class AdvertController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private AdvertUserService advertUserService;

    @Autowired
    private AdvertRepository advertRepository;

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addAdvert(@CurrentUser UserPrincipal userPrincipal,
                                       @Valid @RequestBody CreateAdvertRequest createAdvertRequest) {

        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userPrincipal.getId()));


        List<Advert> advertList = user.getAdverts();
        if (advertList == null)
            advertList = new ArrayList<Advert>();

        Subcategory subcategory = subcategoryRepository.findBySubcategoryName(
                createAdvertRequest.getSubcategory()).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Subcategory", "name", createAdvertRequest.getSubcategory()));
        Advert advert = new Advert(
                createAdvertRequest.getTitle(),
                createAdvertRequest.getTags(),
                createAdvertRequest.getDescription(),
                createAdvertRequest.getImgUrls(), subcategory,
                user);
        advertList.add(advert);
        user.setAdverts(advertList);
        subcategory.addAdvert(advert);

        userRepository.save(user);
        subcategoryRepository.save(subcategory);

        return ResponseEntity.ok(new ApiResponse(true, "Added new advert"));
    }

    @PostMapping("/edit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> editAdvert(@CurrentUser UserPrincipal userPrincipal,
                                        @RequestBody EditAdvertRequest editAdvertRequest) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        Advert advert = advertUserService.findAdvert(user, editAdvertRequest.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Advert", "id", editAdvertRequest.getId()));


        if (editAdvertRequest.getImgUrls() != null) {
            List<ImgUrl> imgUrls = new ArrayList<>();
            for (String imgUrl : editAdvertRequest.getImgUrls())
                imgUrls.add(new ImgUrl(imgUrl));
            advert.setImgUrls(imgUrls);
        }

        if (editAdvertRequest.getTags() != null) {
            List<Tag> tags = new ArrayList<>();
            for (String tag : editAdvertRequest.getTags())
                tags.add(new Tag(tag));
            advert.setTags(tags);
        }

        if (editAdvertRequest.getTitle() != null)
            advert.setTitle(editAdvertRequest.getTitle());

        if (editAdvertRequest.getDescription() != null)
            advert.setDescription(editAdvertRequest.getDescription());

        userRepository.save(user);
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

    @GetMapping("all")
    @PreAuthorize("permitAll()")
    public List<Advert> getAllAdverts() {
        return advertRepository.findAll();
    }

    @GetMapping("get")
    @PreAuthorize("permitAll()")
    public Advert getAdvert(@RequestParam Long id) {
        return advertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Advert", "id", id));
    }
}

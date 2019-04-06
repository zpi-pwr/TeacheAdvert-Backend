package ppztw.AdvertBoard.Controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ppztw.AdvertBoard.Advert.AdvertUserService;
import ppztw.AdvertBoard.Exception.ResourceNotFoundException;
import ppztw.AdvertBoard.Model.*;
import ppztw.AdvertBoard.Payload.Advert.CreateAdvertRequest;
import ppztw.AdvertBoard.Payload.Advert.EditAdvertRequest;
import ppztw.AdvertBoard.Payload.Advert.ImagePayload;
import ppztw.AdvertBoard.Payload.ApiResponse;
import ppztw.AdvertBoard.Repository.*;
import ppztw.AdvertBoard.Security.CurrentUser;
import ppztw.AdvertBoard.Security.UserPrincipal;
import ppztw.AdvertBoard.View.Advert.AdvertDetailsView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        List<Advert> advertList = user.getAdverts();
        if (advertList == null)
            advertList = new ArrayList<Advert>();

        Category subcategory = categoryRepository.findById(
                createAdvertRequest.getCategory()).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Category", "id", createAdvertRequest.getCategory()));


        List<Tag> tags = new ArrayList<>();
        if (createAdvertRequest.getTags() != null)
            for (String tag : createAdvertRequest.getTags()) {
                Tag tempTag = (tagRepository.findByName(tag).orElseGet(() -> new Tag(tag)));
                tags.add(tempTag);
            }

        Image img = null;
        String extension = "";
        ImagePayload imagePayload = createAdvertRequest.getImage();

        if (imagePayload != null) {
            if (imagePayload.getType().equals("image/png")) {
                img = new Image(imagePayload.getName(),
                        Base64.decodeBase64(imagePayload.getBase64()));
                    imageRepository.save(img);
            }
        }

        Map<Long, String> additionalInfo = createAdvertRequest.getAdditionalInfo();
        List<AdvertInfo> advertInfos = new ArrayList<>();
        if (additionalInfo != null) {
            for (Map.Entry<Long, String> entry : additionalInfo.entrySet()) {
                CategoryInfo info = categoryInfoRepository.findById(entry.getKey())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "CategoryInfo", "id", entry.getKey()));
                advertInfos.add(new AdvertInfo(info, entry.getValue()));
            }
        }


        Advert advert = new Advert(
                createAdvertRequest.getTitle(),
                tags,
                createAdvertRequest.getDescription(),
                img, subcategory,
                user, advertInfos);
        advertList.add(advert);
        user.setAdverts(advertList);
        subcategory.addAdvert(advert);

        userRepository.save(user);
        categoryRepository.save(subcategory);

        return ResponseEntity.ok(new ApiResponse(true, "Added new advert"));
    }

    @PostMapping(value = "/edit")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public ResponseEntity<?> editAdvert(@CurrentUser UserPrincipal userPrincipal,
                                        @Valid @RequestPart EditAdvertRequest editAdvertRequest) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        Advert advert = advertUserService.findAdvert(user, editAdvertRequest.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Advert", "id", editAdvertRequest.getId()));


        Image img = null;
        String extension = "";
        ImagePayload imagePayload = editAdvertRequest.getImage();

        if (imagePayload != null) {
            if (imagePayload.getType().equals("image/png")) {
                img = new Image(imagePayload.getName(),
                        Base64.decodeBase64(imagePayload.getBase64()));
                imageRepository.save(img);
                advert.setImage(img);
            }
        }
        if (editAdvertRequest.getTags() != null) {
            List<Tag> tags = new ArrayList<>();
            for (String tag : editAdvertRequest.getTags()) {
                Tag tempTag = (tagRepository.findByName(tag).orElseGet(() -> new Tag(tag)));
                tags.add(tempTag);
            }
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

    @GetMapping("get")
    @PreAuthorize("permitAll()")
    public AdvertDetailsView getAdvert(@RequestParam Long id) {
        Advert advert = advertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Advert", "id", id));

        return new AdvertDetailsView(advert);
    }
}

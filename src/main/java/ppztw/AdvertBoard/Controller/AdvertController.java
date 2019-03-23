package ppztw.AdvertBoard.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ppztw.AdvertBoard.Exception.ResourceNotFoundException;
import ppztw.AdvertBoard.Model.Advert;
import ppztw.AdvertBoard.Model.User;
import ppztw.AdvertBoard.Payload.ApiResponse;
import ppztw.AdvertBoard.Payload.CreateAdvertRequest;
import ppztw.AdvertBoard.Repository.UserRepository;
import ppztw.AdvertBoard.Security.CurrentUser;
import ppztw.AdvertBoard.Security.UserPrincipal;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/advert")
public class AdvertController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addAdvert(@CurrentUser UserPrincipal userPrincipal,
                                       @Valid @RequestBody CreateAdvertRequest createAdvertRequest) {

        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userPrincipal.getId()));


        List<Advert> advertList = user.getAdverts();
        if (advertList == null)
            advertList = new ArrayList<Advert>();
        Advert advert = new Advert(
                createAdvertRequest.getTitle(),
                createAdvertRequest.getTags(),
                createAdvertRequest.getDescription(),
                createAdvertRequest.getImgUrls());
        advertList.add(advert);
        user.setAdverts(advertList);

        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse(true, "Added new advert"));
    }
}

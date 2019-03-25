package ppztw.AdvertBoard.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ppztw.AdvertBoard.Exception.ResourceNotFoundException;
import ppztw.AdvertBoard.Model.Image;
import ppztw.AdvertBoard.Repository.ImageRepository;

@RestController
@RequestMapping("/image")
@PreAuthorize("permitAll()")
public class ImageController {
    @Autowired
    private ImageRepository imageRepository;

    @ResponseBody
    @GetMapping(value = "/get")
    public ResponseEntity<?> getImage(@RequestParam Long id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        Image image = imageRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Image", "id", id));
        Resource resource = new ByteArrayResource(image.getPic());
        return new ResponseEntity<>(resource, httpHeaders, HttpStatus.OK);
    }

}


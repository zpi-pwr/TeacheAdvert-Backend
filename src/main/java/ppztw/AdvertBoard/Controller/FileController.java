package ppztw.AdvertBoard.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ppztw.AdvertBoard.Files.FileService;
import ppztw.AdvertBoard.Payload.ApiResponse;
import ppztw.AdvertBoard.Security.CurrentUser;
import ppztw.AdvertBoard.Security.UserPrincipal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.MalformedURLException;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/images")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveMultipartFile(
            @CurrentUser UserPrincipal userPrincipal,
            @NotNull @NotEmpty String conversationId,
            @NotNull MultipartFile image) {

        String response = fileService.saveFile(conversationId, image);

        return ResponseEntity.ok(new ApiResponse(true, response));
    }

    @GetMapping(value = "/images", produces = {
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_GIF_VALUE,
            MediaType.IMAGE_JPEG_VALUE
    })
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getMultipartFile(
            @NotNull @NotEmpty String path) {

        ResponseEntity<?> response;

        try {
            response = ResponseEntity.ok(fileService.loadFile(path));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            response = ResponseEntity.ok(new ApiResponse(false, e.toString()));
        }

        return response;
    }

}

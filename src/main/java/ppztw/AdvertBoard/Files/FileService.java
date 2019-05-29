package ppztw.AdvertBoard.Files;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ppztw.AdvertBoard.Exception.IncorrectFileException;
import ppztw.AdvertBoard.Exception.ResourceNotFoundException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileService {

    private Date date;

    private List<String> allowedFileExtensions;

    public FileService() {
        date = new Date();

        allowedFileExtensions = new ArrayList<>(
                Arrays.asList(
                        "jpeg",
                        "jpg",
                        "png",
                        "gif")
        );
    }

    public String saveFile(String folderName, MultipartFile file) {
        String filePath = "";

        if (file != null) {
            try {
                filePath = saveFileToDisk(folderName, file);
            } catch (Exception e) {
                throw new IncorrectFileException(file.getOriginalFilename(), e);
            }
        }

        return filePath;
    }

    public Resource loadFile(String path) throws MalformedURLException {
        Path filePath = Paths.get(path);
        Resource resource = new UrlResource(filePath.toUri());

        if(resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            return new UrlResource((Paths.get("./images/default.jpg").toUri()));
        }
    }

    private String saveFileToDisk(String folderName, MultipartFile file) throws Exception {
        String destinationFolder = String.format("./images/%s", folderName);
        String targetFileName = getHashedFileName(Objects.requireNonNull(file.getOriginalFilename()));

        Path path = Paths.get(String.format("%s/%s", destinationFolder, targetFileName));

        byte[] bytes = file.getBytes();

        Files.createDirectories(path.getParent());
        Files.createFile(path);
        Files.write(path, bytes);

        return path.toString();
    }

    private String getHashedFileName(String fileName) throws Exception {
        String[] fileNameParts = fileName.split("\\.");

        String newFileName = Arrays.stream(fileNameParts, 0, fileNameParts.length - 1)
                                .collect(Collectors.joining("."));

        String extension = fileNameParts[fileNameParts.length - 1];

        if(!allowedFileExtensions.contains(extension)) {
            throw new Exception(String.format("Incorrect extension: %s is not in %s", extension, allowedFileExtensions.toString()));
        }

        return String.format("%s%s.%s", date.getTime(), newFileName.hashCode(), extension);
    }
}

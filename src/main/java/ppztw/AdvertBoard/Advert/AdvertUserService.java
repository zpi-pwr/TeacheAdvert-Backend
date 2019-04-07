package ppztw.AdvertBoard.Advert;


import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppztw.AdvertBoard.Exception.BadRequestException;
import ppztw.AdvertBoard.Exception.ResourceNotFoundException;
import ppztw.AdvertBoard.Model.Advert.*;
import ppztw.AdvertBoard.Model.User;
import ppztw.AdvertBoard.Payload.Advert.CreateAdvertRequest;
import ppztw.AdvertBoard.Payload.Advert.EditAdvertRequest;
import ppztw.AdvertBoard.Payload.Advert.ImagePayload;
import ppztw.AdvertBoard.Repository.Advert.AdvertRepository;
import ppztw.AdvertBoard.Repository.Advert.CategoryInfoRepository;
import ppztw.AdvertBoard.Repository.Advert.CategoryRepository;
import ppztw.AdvertBoard.Repository.Advert.TagRepository;
import ppztw.AdvertBoard.Repository.UserRepository;
import ppztw.AdvertBoard.Security.UserPrincipal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AdvertUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CategoryInfoRepository categoryInfoRepository;

    @Autowired
    private AdvertRepository advertRepository;

    private Optional<Advert> findAdvert(Long userId, Long id) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userId));
        List<Advert> adverts = user.getAdverts();
        Advert advert = null;
        for (Advert adv : adverts)
            if (adv.getId().equals(id)) {
                advert = adv;
                break;
            }

        return Optional.ofNullable(advert);
    }

    public void addAdvert(UserPrincipal userPrincipal, CreateAdvertRequest request) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        Category category = categoryRepository.findById(request.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id",
                        request.getCategory()));

        List<Advert> advertList = user.getAdverts() == null ? new ArrayList<Advert>() : user.getAdverts();
        Advert advert = addNewAdvert(request.getTitle(), request.getTags(), request.getDescription(),
                request.getImage(), category, user, request.getAdditionalInfo());
        advertList.add(advert);
        user.setAdverts(advertList);
        category.addAdvert(advert);

        userRepository.save(user);
        categoryRepository.save(category);

    }

    public void editAdvert(UserPrincipal userPrincipal, EditAdvertRequest request) {
        Advert advert = findAdvert(userPrincipal.getId(), request.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Advert", "id", request.getId()));

        advert.setTitle(request.getTitle() == null ? advert.getTitle() : request.getTitle());
        advert.setTags(request.getTags() == null ? advert.getTags() : processTags(request.getTags()));
        advert.setDescription(request.getDescription() == null ?
                advert.getDescription() : request.getDescription());
        advert.setImage(request.getImage() == null ? advert.getImage() :
                processImage(request.getImage()));
        advert.setAdditionalInfo(request.getAdditionalInfo() == null ?
                advert.getAdditionalInfo() :
                processAdvertInfo(advert.getSubcategory(), request.getAdditionalInfo()));

        advert.setStatus(Advert.Status.EDITED);
        advertRepository.save(advert);
    }

    private Advert addNewAdvert(String title, List<String> tagNames, String description,
                                ImagePayload imagePayload, Category category,
                                User user,
                                Map<Long, String> additionalInfo) {

        List<Tag> tags = processTags(tagNames);
        Image img = processImage(imagePayload);
        List<AdvertInfo> advertInfos = processAdvertInfo(category, additionalInfo);

        return advertRepository
                .save(new Advert(title, tags, description, img, category, user, advertInfos));
    }


    private List<Tag> processTags(List<String> tags) {
        List<Tag> tagList = null;
        if (tags != null) {
            tagList = new ArrayList<>();
            for (String tag : tags) {
                Tag tempTag = (tagRepository.findByName(tag).orElseGet(() -> new Tag(tag)));
                tagList.add(tempTag);

            }
        }
        return tagList;
    }

    private List<AdvertInfo> processAdvertInfo(Category category,
                                               Map<Long, String> advertInfo) {
        List<AdvertInfo> advertInfos = null;
        if (advertInfo != null) {
            advertInfos = new ArrayList<>();

            List<CategoryInfo> infoList = category.getInfoList();
            List<Long> idList = new ArrayList<>();

            for (CategoryInfo categoryInfo : infoList)
                idList.add(categoryInfo.getId());

            for (Map.Entry<Long, String> entry : advertInfo.entrySet()) {
                if (!idList.contains(entry.getKey()))
                    throw new BadRequestException("This category doesn't contain such additional info!");
                CategoryInfo info = categoryInfoRepository.findById(entry.getKey())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "CategoryInfo", "id", entry.getKey()));
                advertInfos.add(new AdvertInfo(info, entry.getValue()));
            }
        }
        return advertInfos;
    }

    private Image processImage(ImagePayload imagePayload) {
        return imagePayload != null ? new Image(imagePayload.getName(),
                Base64.decodeBase64(imagePayload.getBase64())) : null;
    }
}

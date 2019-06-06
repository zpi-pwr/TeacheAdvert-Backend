package ppztw.AdvertBoard.Advert;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppztw.AdvertBoard.Exception.ResourceNotFoundException;
import ppztw.AdvertBoard.Model.Advert;
import ppztw.AdvertBoard.Model.Tag;
import ppztw.AdvertBoard.Model.User;
import ppztw.AdvertBoard.Payload.Advert.CreateAdvertRequest;
import ppztw.AdvertBoard.Payload.Advert.EditAdvertRequest;
import ppztw.AdvertBoard.Repository.AdvertRepository;
import ppztw.AdvertBoard.Repository.TagRepository;
import ppztw.AdvertBoard.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdvertUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private AdvertRepository advertRepository;

    public Optional<Advert> findAdvert(Long userId, Long id) {
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

    public void addAdvert(Long userId, CreateAdvertRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userId));
        List<Advert> advertList = user.getAdverts() == null ? new ArrayList<Advert>() : user.getAdverts();
        Advert advert = addNewAdvert(request.getTitle(), request.getTags(), request.getDescription(), user,
                request.getConversationId());
        advertList.add(advert);
        user.setAdverts(advertList);
        userRepository.save(user);

    }

    public void editAdvert(Long userId, EditAdvertRequest request) {
        Advert advert = findAdvert(userId, request.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Advert", "id", request.getId()));

        advert.setTitle(request.getTitle() == null ? advert.getTitle() : request.getTitle());
        advert.setTags(request.getTags() == null ? advert.getTags() : processTags(request.getTags()));
        advert.setDescription(request.getDescription() == null ?
                advert.getDescription() : request.getDescription());

        advert.setStatus(Advert.Status.EDITED);
        advertRepository.save(advert);
    }


    private Advert addNewAdvert(String title, List<String> tagNames, String description, User user, String conversationId) {
        List<Tag> tags = processTags(tagNames);
        return advertRepository.save(new Advert(title, tags, description, user, conversationId));
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

}

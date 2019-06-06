package ppztw.AdvertBoard.Advert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ppztw.AdvertBoard.Model.Advert;
import ppztw.AdvertBoard.Repository.AdvertRepository;
import ppztw.AdvertBoard.View.Advert.AdvertSummaryView;
import ppztw.AdvertBoard.View.Advert.AdvertDetailsView;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdvertService {

    @Autowired
    AdvertRepository advertRepository;


    public Page<AdvertDetailsView> getPage(Pageable pageable, String titleContains, List<String> tags) {
        Page<AdvertDetailsView> adverts;
        if (titleContains == null)
            titleContains = "";
        if (tags == null || tags.isEmpty())
            adverts = advertToSummaryView(advertRepository.findAllByTitleLike(pageable, titleContains));
        else
            adverts = advertToSummaryView(
                    advertRepository.findAllByTitleLikeAndTagsIn(pageable, titleContains, tags));
        return adverts;
    }

    private Page<AdvertDetailsView> advertToSummaryView(Page<Advert> adverts) {
        List<AdvertDetailsView> advertSummaryViews = new ArrayList<>();
        for (Advert advert : adverts)
            advertSummaryViews.add(new AdvertDetailsView(advert));
        return new PageImpl<>(advertSummaryViews, adverts.getPageable(), adverts.getTotalElements());
    }

}

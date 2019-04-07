package ppztw.AdvertBoard;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import ppztw.AdvertBoard.Advert.AdvertUserService;
import ppztw.AdvertBoard.Exception.ResourceNotFoundException;
import ppztw.AdvertBoard.Model.Advert.Advert;
import ppztw.AdvertBoard.Model.Advert.Category;
import ppztw.AdvertBoard.Model.User;
import ppztw.AdvertBoard.Repository.Advert.AdvertRepository;
import ppztw.AdvertBoard.Repository.Advert.CategoryInfoRepository;
import ppztw.AdvertBoard.Repository.Advert.CategoryRepository;
import ppztw.AdvertBoard.Repository.Advert.TagRepository;
import ppztw.AdvertBoard.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class AdvertUserServiceIntegrationTests {
    @Autowired
    private AdvertUserService advertUserService;
    @MockBean
    private AdvertRepository advertRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private TagRepository tagRepository;
    @MockBean
    private CategoryInfoRepository categoryInfoRepository;

    @Before
    public void setUp() {
        User user = new User();
        user.setId(0L);

        Advert advert = new Advert();
        advert.setId(0L);

        Category category = new Category();
        category.setId(0L);

        advert.setUser(user);
        advert.setSubcategory(category);
        List<Advert> advertList = new ArrayList<>();
        advertList.add(advert);

        user.setAdverts(advertList);
        category.setAdverts(advertList);


        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(advertRepository.findById(advert.getId())).thenReturn(Optional.of(advert));
        Mockito.when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

    }

    @Test
    public void whenValidUserAndAdvertId_thenAdvertShouldBeFound() {
        Long userId = 0L;
        Long advertId = 0L;

        Optional<Advert> advertOptional = advertUserService.findAdvert(userId, advertId);
        Advert advert = advertOptional.get();
        assertThat(advert).isNotNull();
        assertThat(advert.getId()).isEqualTo(advertId);
        assertThat(advert.getUser().getId()).isEqualTo(userId);
    }

    @Test
    public void whenValidUserInvalidAdvertId_thenNullValueShouldBeFound() {
        Long userId = 0L;
        Long advertId = 1L;
        assertThat(advertUserService.findAdvert(userId, advertId)).isEmpty();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenInvalidUserId_thenResourceNotFoundExceptionShouldBeThrown() {
        Long userId = 1L;
        Long advertId = 0L;
        advertUserService.findAdvert(userId, advertId);
    }

    @TestConfiguration
    static class AdvertUserServiceTestContextConfiguration {

        @Bean
        public AdvertUserService userService() {
            return new AdvertUserService();
        }
    }

}

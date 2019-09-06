package ru.pchurzin.votesystem.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.pchurzin.votesystem.model.Restaurant;
import ru.pchurzin.votesystem.service.VoteSystemService;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(RestaurantControllerTest.class)
@EnableWebMvc
class RestaurantControllerTest {

    @Test
    void shouldReturnEmptyRestaurants() throws Exception {
        VoteSystemService service = Mockito.mock(VoteSystemService.class);
        RestaurantController controller = new RestaurantController(service);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(getRequestBuilder(HttpMethod.GET, ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldReturnNonEmptyRestaurants() throws Exception {
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId(100);
        restaurant1.setTitle("title1");
        Restaurant restaurant2 = new Restaurant(restaurant1);
        restaurant2.setId(101);
        restaurant2.setTitle("title2");

        VoteSystemService service = Mockito.mock(VoteSystemService.class);
        Mockito.when(service.findAllRestaurants()).thenReturn(Arrays.asList(restaurant1, restaurant2));

        RestaurantController controller = new RestaurantController(service);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(getRequestBuilder(HttpMethod.GET, ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(100)))
                .andExpect(jsonPath("$[1].id", is(101)));
    }

    @Test
    void shouldReturnExistingRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(100);
        restaurant.setTitle("title1");

        VoteSystemService service = Mockito.mock(VoteSystemService.class);
        Mockito.when(service.findRestaurantById(100)).thenReturn(Optional.of(restaurant));

        RestaurantController controller = new RestaurantController(service);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(getRequestBuilder(HttpMethod.GET, "/100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(100)))
                .andExpect(jsonPath("$.title", is("title1")));
    }

    @Test
    void shouldReturnNotFoundForNonExistingRestaurant() throws Exception {
        VoteSystemService service = Mockito.mock(VoteSystemService.class);
        Mockito.when(service.findRestaurantById(100)).thenReturn(Optional.empty());
        RestaurantController controller = new RestaurantController(service);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(getRequestBuilder(HttpMethod.GET, "/100"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void createNewRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(100);
        restaurant.setTitle("title1");
        Restaurant nullIdRestaurant = new Restaurant(restaurant).setId(null);

        VoteSystemService service = Mockito.mock(VoteSystemService.class);
        Mockito.when(service.saveRestaurant(nullIdRestaurant)).thenReturn(Optional.of(restaurant));

        RestaurantController controller = new RestaurantController(service);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(getRequestBuilder(HttpMethod.POST, "")
                .content(new ObjectMapper().writeValueAsString(restaurant)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(100)))
                .andExpect(jsonPath("$.title", is("title1")));

        mockMvc.perform(getRequestBuilder(HttpMethod.POST, "")
                .content(new ObjectMapper().writeValueAsString(nullIdRestaurant)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(100)))
                .andExpect(jsonPath("$.title", is("title1")));
    }

    @Test
    void updateRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant()
                .setId(100)
                .setTitle("title1");

        VoteSystemService service = Mockito.mock(VoteSystemService.class);
        Mockito.when(service.saveRestaurant(restaurant)).thenReturn(Optional.of(restaurant));

        RestaurantController controller = new RestaurantController(service);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(getRequestBuilder(HttpMethod.PUT, "/100")
                .content(new ObjectMapper().writeValueAsString(restaurant)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateNonExistingRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant()
                .setId(100)
                .setTitle("title1");

        VoteSystemService service = Mockito.mock(VoteSystemService.class);

        RestaurantController controller = new RestaurantController(service);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(getRequestBuilder(HttpMethod.PUT, "/100")
                .content(new ObjectMapper().writeValueAsString(restaurant)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void updateRestaurantWithWrongId() throws Exception {
        Restaurant restaurant = new Restaurant()
                .setId(100)
                .setTitle("title1");

        VoteSystemService service = Mockito.mock(VoteSystemService.class);

        RestaurantController controller = new RestaurantController(service);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(getRequestBuilder(HttpMethod.PUT, "/200")
                .content(new ObjectMapper().writeValueAsString(restaurant)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteRestaurant() throws Exception {
        VoteSystemService service = Mockito.mock(VoteSystemService.class);
        Mockito.when(service.removeRestaurantById(200)).thenReturn(true);

        RestaurantController controller = new RestaurantController(service);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(getRequestBuilder(HttpMethod.DELETE, "/200"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteNonExistingRestaurant() throws Exception {
        VoteSystemService service = Mockito.mock(VoteSystemService.class);

        RestaurantController controller = new RestaurantController(service);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(getRequestBuilder(HttpMethod.DELETE, "/200"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private MockHttpServletRequestBuilder getRequestBuilder(HttpMethod method, String url) {
        return MockMvcRequestBuilders.request(method, RestaurantController.REST_URL + url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8");
    }
}
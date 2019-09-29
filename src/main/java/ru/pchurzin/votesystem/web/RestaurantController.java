package ru.pchurzin.votesystem.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.pchurzin.votesystem.model.MenuItem;
import ru.pchurzin.votesystem.model.Restaurant;
import ru.pchurzin.votesystem.model.Vote;
import ru.pchurzin.votesystem.service.VoteSystemService;

import java.net.URI;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(path = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    final static String REST_URL = "/restaurants";

    private final VoteSystemService service;

    @Autowired
    public RestaurantController(VoteSystemService service) {
        this.service = service;
    }

    @GetMapping
    Collection<Restaurant> findAllRestaurants() {
        return service.findAllRestaurants();
    }

    @GetMapping("/{id}")
    ResponseEntity<Restaurant> findRestaurantById(@PathVariable int id) {
        Optional<Restaurant> restaurant = service.findRestaurantById(id);
        return restaurant.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        restaurant.setId(null);
        Optional<Restaurant> newRestaurant = service.saveRestaurant(restaurant);
        if (!newRestaurant.isPresent()) {
            throw new RuntimeException();
        }

        Restaurant r = newRestaurant.get();
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(r.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(r);
    }

    @PutMapping("/{id}")
    ResponseEntity<Restaurant> updateRestaurant(@RequestBody Restaurant restaurant, @PathVariable int id) {
        if (restaurant.getId() == null || id != restaurant.getId()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Restaurant> savedRestaurant = service.saveRestaurant(restaurant);
        if (!savedRestaurant.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteRestaurant(@PathVariable int id) {
        if (service.removeRestaurantById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}" + MenuItemController.REST_URL)
    Collection<MenuItem> getRestaurantMenuItems(@PathVariable("id") int restaurantId) {
        return service.findAllMenuItemsByRestaurantId(restaurantId);
    }

    @PostMapping("/{id}" + MenuItemController.REST_URL)
    ResponseEntity<MenuItem> createMenuItem(@PathVariable("id") int restaurantId, @RequestBody MenuItem menuItem) {
        menuItem.setId(null);
        menuItem.setRestaurantId(restaurantId);
        Optional<MenuItem> newMenuItem = service.saveMenuItem(menuItem);
        if (!newMenuItem.isPresent()) {
            throw new RuntimeException();
        }

        MenuItem item = newMenuItem.get();
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(MenuItemController.REST_URL + "/{id}")
                .buildAndExpand(item.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(item);
    }

    @PostMapping("/{id}" + VoteController.REST_URL)
    ResponseEntity<Vote> voteForRestaurant(@PathVariable("id") int restaurantId) throws Exception {
        Vote vote = new Vote(9999, restaurantId, LocalDate.now());//todo: getCurrentUser
        Optional<Vote> newVote = service.saveVote(vote);
        if (!newVote.isPresent()) {
            throw new RuntimeException();
        }

        Vote savedVote = newVote.get();
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(MenuItemController.REST_URL + "/{id}")
                .buildAndExpand(savedVote.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(savedVote);
    }
}

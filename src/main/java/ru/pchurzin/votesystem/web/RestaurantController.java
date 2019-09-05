package ru.pchurzin.votesystem.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pchurzin.votesystem.model.Restaurant;
import ru.pchurzin.votesystem.service.VoteSystemService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/restaurants", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

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
        Restaurant restaurant = service.findRestaurantById(id);
        if (restaurant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        restaurant.setId(null);
        Restaurant newRestaurant = service.saveRestaurant(restaurant);
        return new ResponseEntity<>(newRestaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<Restaurant> updateRestaurant(@RequestBody Restaurant restaurant, @PathVariable int id) {
        if (id != restaurant.getId()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Restaurant updatedRestaurant = service.saveRestaurant(restaurant);
        return new ResponseEntity<>(updatedRestaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRestaurant(@PathVariable int id) {
        service.removeRestaurantById(id);
    }
}

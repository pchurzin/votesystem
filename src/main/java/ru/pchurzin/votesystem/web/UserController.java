package ru.pchurzin.votesystem.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.pchurzin.votesystem.model.User;
import ru.pchurzin.votesystem.service.VoteSystemService;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(path = UserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    final static String REST_URL = "/users";

    private final VoteSystemService service;

    @Autowired
    public UserController(VoteSystemService service) {
        this.service = service;
    }

    @GetMapping
    Collection<User> findAllUsers() {
        return service.findAllUsers();
    }

    @GetMapping("/{id}")
    ResponseEntity<User> findUserById(@PathVariable int id) {
        Optional<User> user = service.findUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    ResponseEntity<User> createUser(@RequestBody User user) {
        user.setId(null);
        Optional<User> newUser = service.saveUser(user);
        if (!newUser.isPresent()) {
            throw new RuntimeException();
        }

        User r = newUser.get();
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(r.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(r);
    }

    @PutMapping("/{id}")
    ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable int id) {
        if (user.getId() == null || id != user.getId()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<User> savedUser = service.saveUser(user);
        if (!savedUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable int id) {
        if (service.removeUserById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}


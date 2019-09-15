package ru.pchurzin.votesystem.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pchurzin.votesystem.model.MenuItem;
import ru.pchurzin.votesystem.service.VoteSystemService;

import java.util.Optional;

@RestController
@RequestMapping(path = MenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class MenuItemController {
    static final String REST_URL = "/menu";

    private final VoteSystemService service;

    @Autowired
    public MenuItemController(VoteSystemService service) {
        this.service = service;
    }

    @GetMapping(path = "/{id}")
    ResponseEntity<MenuItem> findMenuItemById(@PathVariable("id") int id) {
        Optional<MenuItem> menuItem = service.findMenuItemById(id);
        return menuItem.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    ResponseEntity<MenuItem> updateMenuItem(@RequestBody MenuItem menuItem, @PathVariable int id) {
        if (menuItem.getId() == null || id != menuItem.getId()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<MenuItem> savedMenuItem = service.saveMenuItem(menuItem);
        if (!savedMenuItem.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteMenuItem(@PathVariable int id) {
        if (service.removeMenuItemById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

package ru.pchurzin.votesystem.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pchurzin.votesystem.model.Vote;
import ru.pchurzin.votesystem.service.VoteSystemService;

import java.util.Optional;

@RestController
@RequestMapping(path = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    static final String REST_URL = "/votes";

    private final VoteSystemService service;

    @Autowired
    public VoteController(VoteSystemService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    ResponseEntity<Vote> findVoteById(@PathVariable("id") int id) {
        Optional<Vote> vote = service.findVoteById(id);
        return vote.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteVote(@PathVariable int id) {
        if (service.removeVoteById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}

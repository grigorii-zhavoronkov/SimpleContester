package ru.stray27.scontester.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stray27.scontester.entities.Sender;
import ru.stray27.scontester.repositories.SenderRepository;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/sender/")
public class SenderController {

    @Autowired
    private SenderRepository repository;

    @RequestMapping(value = "restoreUID", method = RequestMethod.POST)
    public ResponseEntity<Sender> restoreUIDByName(@RequestBody Sender sender) {
        try {
            sender = repository.findByName(sender.getName()).orElseThrow();
            sender.setAttempts(null);
            return new ResponseEntity<>(sender, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "checkUID", method = RequestMethod.POST)
    public ResponseEntity<?> checkUID(@RequestBody Sender sender) {
        try {
            repository.findByUID(sender.getUID()).orElseThrow();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

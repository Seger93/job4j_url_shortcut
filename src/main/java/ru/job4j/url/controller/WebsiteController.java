package ru.job4j.url.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.url.model.Website;
import ru.job4j.url.service.SimpleWebsiteService;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/website")
@RestController
public class WebsiteController {
    private final SimpleWebsiteService websiteService;

    @GetMapping("/all")
    public ResponseEntity<List<Website>> findAll() {
        List<Website> allPersons = this.websiteService.findAll();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(allPersons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Website> findById(@PathVariable int id) {
        var website = this.websiteService.findById(id);
        if (website.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Не найден такой Id");
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(website.get());
    }

    @PostMapping("/create")
    public ResponseEntity<Website> create(@RequestBody @Valid Website website) {
        var optionalWebsite = this.websiteService.save(website);
        return new ResponseEntity<>(optionalWebsite.get(), HttpStatus.CREATED);
    }
}
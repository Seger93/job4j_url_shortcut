package ru.job4j.url.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.url.dto.DtoUrlStatistics;
import ru.job4j.url.dto.DtoWebsite;
import ru.job4j.url.model.Shortcuts;
import ru.job4j.url.model.Website;
import ru.job4j.url.service.SimpleShortcutsService;
import ru.job4j.url.service.SimpleWebsiteService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/website")
@RestController
public class WebsiteController {
    private final SimpleWebsiteService websiteService;
    private final SimpleShortcutsService shortcutsService;

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

    @PostMapping("/registration")
    public ResponseEntity<DtoWebsite> create(@RequestBody @Valid Website website) {
        if (websiteService.findByLogin(website.getLogin()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Пользователь с таким логином уже зарегистрирован");
        }
        var dtoWebsite = websiteService.checkBooleanDto(website);
        if (!dtoWebsite.get().isSite()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(dtoWebsite.get());
        }
        this.websiteService.save(website);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dtoWebsite.get());
    }

    @PostMapping("/convert")
    public ResponseEntity<String> convert(@RequestBody @Valid Shortcuts shortcuts) {
        if (shortcutsService.findByUrlName(shortcuts.getUrlName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Такой URL уже существует");
        }
        shortcutsService.save(shortcuts);
        String codeMessage = "code: " + shortcuts.getUniqueCode();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(codeMessage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (websiteService.delete(id)) {
            return ResponseEntity.ok().build();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Не удален по такому ID");
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<DtoUrlStatistics>> urlStata() {
        var stat = shortcutsService.getUrlStatistics();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(stat);
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code, HttpServletResponse response) {
        var sho = shortcutsService.findByUnique(code);
        if (sho.isPresent()) {
            response.setHeader("Location", sho.get().getUrlName());
            return ResponseEntity.status(HttpStatus.FOUND).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
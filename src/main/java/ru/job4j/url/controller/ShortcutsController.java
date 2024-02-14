package ru.job4j.url.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.url.dto.DtoUrlStatistics;
import ru.job4j.url.model.Shortcuts;
import ru.job4j.url.service.SimpleShortcutsService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RequestMapping("/shortcut")
@RestController
public class ShortcutsController {

    private final SimpleShortcutsService shortcutsService;

    @PostMapping("/convert")
    public ResponseEntity<Object> convert(@RequestBody @Valid Shortcuts shortcuts) {
        shortcutsService.save(shortcuts);
        Map<String, String> response = new HashMap<>();
        response.put("code ", shortcuts.getUniqueCode());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<DtoUrlStatistics>> urlStata() {
        var stat = shortcutsService.getUrlStatistics();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(stat);
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<Object> redirect(@PathVariable String code) {
        Map<String, String> response = new HashMap<>();
        response.put("HTTP CODE - 302 REDIRECT: ",
                shortcutsService.findByUniqueAndIncrementCount(code).get().getUrlName());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
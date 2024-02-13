package ru.job4j.url.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.url.dto.DtoUrlStatistics;
import ru.job4j.url.model.Shortcuts;
import ru.job4j.url.service.SimpleShortcutsService;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/shortcut")
@RestController
public class ShortcutsController {

    private final SimpleShortcutsService shortcutsService;

    @PostMapping("/convert")
    public ResponseEntity<String> convert(@RequestBody @Valid Shortcuts shortcuts) {
        shortcutsService.save(shortcuts);
        String codeMessage = "code: " + shortcuts.getUniqueCode();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(codeMessage);
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<DtoUrlStatistics>> urlStata() {
        var stat = shortcutsService.getUrlStatistics();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(stat);
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<String> redirect(@PathVariable String code) {
        var sho = shortcutsService.findByUniqueAndIncrementCount(code);
        String codeMessage = "HTTP CODE - 302 REDIRECT: " + sho.get().getUrlName();
        return ResponseEntity.ok().body(codeMessage);

    }
}
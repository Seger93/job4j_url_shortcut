package ru.job4j.url.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.url.dto.DtoUrlStatistics;
import ru.job4j.url.model.Shortcuts;
import ru.job4j.url.repository.ShortcutsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class SimpleShortcutsService implements ShortcutsService {
    private final ShortcutsRepository shortcutsRepository;

    public Optional<Shortcuts> findByUniqueAndIncrementCount(String code)  {
        var shortcut = shortcutsRepository.findByUniqueCode(code);
        if (shortcut.isEmpty()) {
            return Optional.empty();
        }
        shortcutsRepository.incrementCountByCode(code);
        return shortcut;
    }

    @Override
    public Optional<Shortcuts> findByUniqueCode(String code) {
        return shortcutsRepository.findByUniqueCode(code);
    }

    @Override
    public Optional<Shortcuts> save(Shortcuts url) {
        url.setUniqueCode(generateCode(url.getUrlName()));
        return Optional.of(shortcutsRepository.save(url));
    }

    @Override
    public Optional<Shortcuts> findById(Integer id) {
        return shortcutsRepository.findById(id);
    }

    @Override
    public List<Shortcuts> findAll() {
        return shortcutsRepository.findAll();
    }

    @Override
    public Optional<Shortcuts> findByUrlName(String urlName) {
        return shortcutsRepository.findByUrlName(urlName);
    }

    private String generateCode(String url) {
        return UUID.randomUUID().toString().substring(0, 7);
    }

    public List<DtoUrlStatistics> getUrlStatistics() {
        var stat = shortcutsRepository.findAll();
        List<DtoUrlStatistics> list = new ArrayList<>();
        stat.forEach(shortcuts -> {
            DtoUrlStatistics dto = new DtoUrlStatistics();
            dto.setUrl(shortcuts.getUrlName());
            dto.setTotal(shortcuts.getCount());
            list.add(dto);
        });
        return list;
    }
}
package ru.job4j.url.service;

import ru.job4j.url.model.Shortcuts;

import java.util.List;
import java.util.Optional;

public interface ShortcutsService {

    Optional<Shortcuts> findByUniqueCode(String code);

    Optional<Shortcuts> save(Shortcuts url);

    Optional<Shortcuts> findById(Integer id);

    List<Shortcuts> findAll();
}

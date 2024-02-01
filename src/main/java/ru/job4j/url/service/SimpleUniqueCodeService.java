package ru.job4j.url.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.url.model.UniqueCode;
import ru.job4j.url.model.Url;
import ru.job4j.url.repository.UniqueCodeRepository;
import ru.job4j.url.repository.UrlRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleUniqueCodeService  {
    private final UniqueCodeRepository uniqueCodeRepository;

    public Optional<UniqueCode> save(UniqueCode uniqueCode) {
        if (uniqueCodeRepository.findById(uniqueCode.getId()).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(uniqueCodeRepository.save(uniqueCode));
    }
}
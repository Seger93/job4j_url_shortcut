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
    private final ShortcutsRepository urlRepository;

    @Override
    public Optional<Shortcuts> findByUniqueCode(String code) {
        return urlRepository.findByUniqueCode(code);
    }

    @Override
    public Optional<Shortcuts> save(Shortcuts url) {
        if (urlRepository.findById(url.getId()).isPresent()) {
            return Optional.empty();
        }
        url.setUniqueCode(generateCode(url.getUrlName()));
        return Optional.of(urlRepository.save(url));
    }

    @Override
    public Optional<Shortcuts> findById(Integer id) {
        return urlRepository.findById(id);
    }

    @Override
    public List<Shortcuts> findAll() {
        return urlRepository.findAll();
    }

    private String generateCode(String url) {
        do {
            url = UUID.randomUUID().toString().substring(0, 7);
        } while (urlRepository.findByUniqueCode(url).isPresent());
        return url;
    }

    public List<DtoUrlStatistics> getAllUrlStatistics() {
        List<Shortcuts> urls = urlRepository.findAll();
        List<DtoUrlStatistics> urlStatisticsList = new ArrayList<>();

        for (Shortcuts url : urls) {
            DtoUrlStatistics urlStatistics = new DtoUrlStatistics();
            urlStatistics.setUrl(url.getUrlName());
            urlStatistics.setCount(url.getCount());
            urlStatisticsList.add(urlStatistics);
        }
        return urlStatisticsList;
    }
}
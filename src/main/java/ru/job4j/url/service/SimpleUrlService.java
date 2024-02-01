package ru.job4j.url.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.url.dto.DtoUrlStatistics;
import ru.job4j.url.model.UniqueCode;
import ru.job4j.url.model.Url;
import ru.job4j.url.repository.UniqueCodeRepository;
import ru.job4j.url.repository.UrlRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class SimpleUrlService {
    private UrlRepository urlRepository;
    private UniqueCodeRepository uniqueCodeRepository;

    public Optional<Url> save(Url url) {
        if (urlRepository.findById(url.getId()).isPresent()) {
            return Optional.empty();
        }
        var uniqueCode = new UniqueCode();
        uniqueCode.setCode(generateCode(url.getUrl()));
        uniqueCodeRepository.save(uniqueCode);
        url.setUniqueCode(uniqueCode);
        return Optional.of(urlRepository.save(url));
    }

    private String generateCode(String url) {
        String code;
        do {
            code = UUID.randomUUID().toString().substring(0, 7);
        } while (uniqueCodeRepository.findByCode(code).isPresent());
        return code;
    }

    public Optional<Url> findByUniqueCode(String code) {
        var url = urlRepository.findByUniqueCode_Code(code);
        if (url.isPresent()) {
            url.get().setCount(url.get().getCount() + 1);
            urlRepository.save(url.get());
        }
        return url;
    }

    public List<DtoUrlStatistics> getAllUrlStatistics() {
        List<Url> urls = urlRepository.findAll();
        List<DtoUrlStatistics> urlStatisticsList = new ArrayList<>();

        for (Url url : urls) {
            DtoUrlStatistics urlStatistics = new DtoUrlStatistics();
            urlStatistics.setUrl(url.getUrl());
            urlStatistics.setCount(url.getCount());
            urlStatisticsList.add(urlStatistics);
        }
        return urlStatisticsList;
    }
}
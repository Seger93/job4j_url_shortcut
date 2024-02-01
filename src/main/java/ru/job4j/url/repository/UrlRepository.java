package ru.job4j.url.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.url.model.Url;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends CrudRepository<Url, Integer> {

    Optional<Url> findByUniqueCode(String code);

    List<Url> findAll();
}
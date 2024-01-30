package ru.job4j.url.service;

import ru.job4j.url.model.Website;

import java.util.List;
import java.util.Optional;

public interface WebsiteService {

    Optional<Website> findByLogin(String login);

    Optional<Website> save(Website user);

    Optional<Website> findById(Integer id);

    List<Website> findAll();
}
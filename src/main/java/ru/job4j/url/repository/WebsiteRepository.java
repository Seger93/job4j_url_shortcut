package ru.job4j.url.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.url.model.Website;

import java.util.List;
import java.util.Optional;

@Repository
public interface WebsiteRepository extends CrudRepository<Website, Integer> {

    Optional<Website> findByLogin(String login);

    List<Website> findAll();

    Optional<Website> findBySite(String name);
}
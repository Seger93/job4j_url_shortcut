package ru.job4j.url.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.url.model.Shortcuts;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShortcutsRepository extends CrudRepository<Shortcuts, Integer> {

    Optional<Shortcuts> findByUniqueCode(String code);

    List<Shortcuts> findAll();

    Optional<Shortcuts> findByUrlName(String urlName);

}
package ru.job4j.url.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.url.model.Shortcuts;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShortcutsRepository extends CrudRepository<Shortcuts, Integer> {

    Optional<Shortcuts> findByUniqueCode(String code);

    List<Shortcuts> findAll();

    Optional<Shortcuts> findByUrlName(String urlName);

    @Transactional
    @Modifying
    @Query("UPDATE Shortcuts s SET s.count = s.count + 1 WHERE s.uniqueCode = :code")
    void incrementCountByCode(@Param("code") String code);
}
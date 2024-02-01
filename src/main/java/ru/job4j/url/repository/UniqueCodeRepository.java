package ru.job4j.url.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.url.model.UniqueCode;

import java.util.Optional;

public interface UniqueCodeRepository extends CrudRepository<UniqueCode, Integer> {

    Optional<UniqueCode> findByCode(String code);
}
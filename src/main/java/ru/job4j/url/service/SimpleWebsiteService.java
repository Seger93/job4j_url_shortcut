package ru.job4j.url.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.url.dto.DtoWebsite;
import ru.job4j.url.model.Website;
import ru.job4j.url.repository.WebsiteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleWebsiteService implements WebsiteService, UserDetailsService {

    private final WebsiteRepository websiteRepository;

    private final BCryptPasswordEncoder encoder;

    @Override
    public Optional<Website> findByLogin(String login) {
        return websiteRepository.findByLogin(login);
    }

    @Override
    public Optional<Website> save(Website website) {
        if (websiteRepository.findByLogin(website.getLogin()).isPresent()) {
            return Optional.empty();
        }
        website.setPassword(encoder.encode(website.getPassword()));
        website.setLogin(encoder.encode(website.getLogin()));
        return Optional.of(websiteRepository.save(website));
    }

    public Optional<DtoWebsite> checkBooleanDto(Website website) {
        var dtoWebsite = new DtoWebsite();
        var siteCheck = findSiteByName(website.getSite());
        dtoWebsite.setPassword(encoder.encode(website.getPassword()));
        dtoWebsite.setSite(siteCheck);
        dtoWebsite.setLogin(encoder.encode(website.getLogin()));
        return Optional.of(dtoWebsite);
    }


    public boolean findSiteByName(String name) {
        return websiteRepository.findBySite(name).isEmpty();
    }

    public Optional<Website> findById(Integer id) {
        return websiteRepository.findById(id);
    }

    public List<Website> findAll() {
        return websiteRepository.findAll();
    }

    public boolean delete(Integer id) {
        var findWebsite = websiteRepository.findById(id);
        if (findWebsite.isPresent()) {
            websiteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return websiteRepository.findByLogin(username)
                .map(person -> new User(person.getLogin(), person.getPassword(), new ArrayList<>()))
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException(username);
                });
    }
}
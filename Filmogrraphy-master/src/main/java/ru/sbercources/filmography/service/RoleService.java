package ru.sbercources.filmography.service;

import org.springframework.stereotype.Service;
import ru.sbercources.filmography.model.Role;
import ru.sbercources.filmography.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public List<Role> getList() {
        return repository.findAll();
    }

    public Role getOne(Long id) {
        return repository.findById(id).orElseThrow();
    }
}
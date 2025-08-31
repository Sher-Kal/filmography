package ru.sbercources.filmography.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sbercources.filmography.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
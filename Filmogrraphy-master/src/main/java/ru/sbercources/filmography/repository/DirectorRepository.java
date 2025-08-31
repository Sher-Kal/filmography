package ru.sbercources.filmography.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.sbercources.filmography.model.Director;

import java.util.List;
import java.util.Set;

@Repository
public interface DirectorRepository extends GenericRepository<Director> {

    Set<Director> findAllByIdIn(Set<Long> ids);

    Page<Director> findAllByDirectorFIO(Pageable pageable, String directorFIO);
    Page<Director> findAllByDirectorFIOContaining(Pageable pageable, String directorFIO);
}
package ru.sbercources.filmography.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sbercources.filmography.model.User;

import java.util.List;

@Repository
public interface UserRepository extends GenericRepository<User> {

    List<User> findAllByFirstName(String firstName);

    List<User> findAllByFirstNameAndLastName(String firstName, String lastName);

    @Query(nativeQuery = true, value = """
                SELECT * FROM users WHERE created_by =:createdBy
            """)
    List<User> findAllByCreatedBy(@Param(value = "createdBy") String createdBy);

    User findUserByLogin(String login);
}
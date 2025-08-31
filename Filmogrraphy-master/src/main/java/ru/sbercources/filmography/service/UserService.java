package ru.sbercources.filmography.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sbercources.filmography.dto.LoginDto;
import ru.sbercources.filmography.model.Film;
import ru.sbercources.filmography.model.User;
import ru.sbercources.filmography.repository.OrderRepository;
import ru.sbercources.filmography.repository.UserRepository;

import java.util.Set;

@Slf4j
@Service
public class UserService extends GenericService<User> {

    private final OrderRepository orderRepository;
    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;

    protected UserService(OrderRepository orderRepository, UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService) {
        super(repository);
        this.orderRepository = orderRepository;
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
    }

    //Список всех арендованных/купленных фильмов у пользователя
    public Set<Film> getAllFilmsByUserId(Long userId) {
        System.out.println(userId);
        return orderRepository.findAllFilmsByUserId(userId);
    }

    @Override
    public User create(User user) {
        user.setCreatedBy("REGISTRATION");
        user.setRole(roleService.getOne(1L));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public User createManager(User user) {
        user.setCreatedBy("ADMIN");
        user.setRole(roleService.getOne(2L));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public User getByLogin(String login) {
        return repository.findUserByLogin(login);
    }

    public boolean checkPassword(LoginDto loginDto) {
        return bCryptPasswordEncoder.matches(loginDto.getPassword(), getByLogin(loginDto.getLogin()).getPassword());
    }
}
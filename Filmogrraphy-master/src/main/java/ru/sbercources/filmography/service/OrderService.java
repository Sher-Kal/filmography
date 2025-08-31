package ru.sbercources.filmography.service;

import org.springframework.stereotype.Service;
import ru.sbercources.filmography.dto.OrderDto;
import ru.sbercources.filmography.model.Film;
import ru.sbercources.filmography.model.Order;
import ru.sbercources.filmography.model.User;
import ru.sbercources.filmography.repository.FilmRepository;
import ru.sbercources.filmography.repository.OrderRepository;
import ru.sbercources.filmography.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService extends GenericService<Order> {

    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final OrderRepository repository;

    protected OrderService(OrderRepository repository, UserRepository userRepository, FilmRepository filmRepository) {
        super(repository);
        this.userRepository = userRepository;
        this.filmRepository = filmRepository;
        this.repository = repository;
    }

    public Order addOrder(OrderDto orderDto) {
        User user = userRepository.findById(orderDto.getUserId()).orElseThrow();
        Film film = filmRepository.findById(orderDto.getFilmId()).orElseThrow();
        Order order = Order.builder()
                .createdBy("hardcode")
                .createdWhen(LocalDateTime.now())
                .user(user)
                .film(film)
                .rentDate(orderDto.getRentDate())
                .rentPeriod(orderDto.getRentPeriod())
                .isPurchased(orderDto.isPurchased())
                .isDeleted(false)
                .build();

        return create(order);
    }

    public List<Order> getUserOrders(Long userId) {
        return userRepository.findById(userId).orElseThrow().getOrders().stream().toList();
    }
}
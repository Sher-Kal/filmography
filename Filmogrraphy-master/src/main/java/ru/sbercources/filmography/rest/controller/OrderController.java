package ru.sbercources.filmography.rest.controller;

import org.springframework.web.bind.annotation.*;
import ru.sbercources.filmography.dto.OrderDto;
import ru.sbercources.filmography.mapper.OrderMapper;
import ru.sbercources.filmography.model.Order;
import ru.sbercources.filmography.service.OrderService;

import java.util.List;


@RestController
@RequestMapping("/rest/order")
public class OrderController extends GenericController<Order, OrderDto> {

    private final OrderService service;
    private final OrderMapper mapper;

    protected OrderController(OrderService service, OrderMapper mapper) {
        super(service, mapper);
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/add")
    public OrderDto addOrder(@RequestBody OrderDto orderDto) {
        return mapper.toDto(service.addOrder(orderDto));
    }

    @GetMapping("/user-orders/{userId}")
    public List<OrderDto> getUserOrders(@PathVariable Long userId) {
        return mapper.toDtos(service.getUserOrders(userId));
    }
}
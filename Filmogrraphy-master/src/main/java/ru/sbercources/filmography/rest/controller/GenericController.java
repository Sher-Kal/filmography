package ru.sbercources.filmography.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sbercources.filmography.dto.GenericDto;
import ru.sbercources.filmography.mapper.GenericMapper;
import ru.sbercources.filmography.model.GenericModel;
import ru.sbercources.filmography.service.GenericService;

import java.util.List;

@RestController
public abstract class GenericController<T extends GenericModel, N extends GenericDto> {
    private final GenericService<T> service;
    protected final GenericMapper<T, N> mapper;

    protected GenericController(GenericService<T> service, GenericMapper<T, N> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(description = "Получить список всех записей", method = "GetAll")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<N>> getAll() {
        return ResponseEntity.ok().body(service.listAll().stream().map(mapper::toDto).toList());
    }

    @Operation(description = "Получить запись по id", method = "GetOne")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDto(service.getOne(id)));
    }

    @Operation(description = "Создать запись", method = "Create")
    @PostMapping
    public ResponseEntity<N> create(@RequestBody N object) {
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDto(service.create(mapper.toEntity(object))));
    }

    @Operation(description = "Обновить запись по id", method = "Update")
    @PutMapping("/{id}")
    public ResponseEntity<N> update(@RequestBody N object, @PathVariable Long id) {
        object.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDto(service.create(mapper.toEntity(object))));
    }

    @Operation(description = "Удалить запись по id", method = "Delete")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
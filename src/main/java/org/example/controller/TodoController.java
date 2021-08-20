package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.TodoEntity;
import org.example.dto.TodoRequestDto;
import org.example.dto.TodoResponseDto;
import org.example.service.TodoService;
import org.example.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@CrossOrigin
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getAllTodo() {
        List<TodoEntity> entityList = todoService.findAll();
        List<TodoResponseDto> responseList = entityList.stream().map(TodoResponseDto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<TodoResponseDto> getTodo(@PathVariable Long id) {
        TodoEntity entity = todoService.findById(id);
        return new ResponseEntity<>(new TodoResponseDto(entity), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto request) {
        if (Utils.isBlank(request.getTitle()))
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        TodoEntity entity = todoService.create(request);
        return new ResponseEntity<>(new TodoResponseDto(entity), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable Long id,
                                                      @RequestBody TodoRequestDto request) {

        TodoEntity entity = todoService.updateById(id, request);
        return new ResponseEntity<>(new TodoResponseDto(entity), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo() {
        todoService.deleteAll();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAllTodo(@PathVariable Long id) {
        todoService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}

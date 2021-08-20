package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.TodoEntity;
import org.example.dto.TodoRequestDto;
import org.example.repository.TodoEntityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoEntityRepository repository;

    public List<TodoEntity> findAll() {
        return repository.findAll();
    }

    public TodoEntity findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    public TodoEntity create(TodoRequestDto request) {
        if (request.getCompleted() == null)
            request.setCompleted(false);
        if (request.getOrder() == null)
            request.setOrder(0L);
        TodoEntity todoEntity = TodoEntity.builder()
                .title(request.getTitle())
                .todoOrder(request.getOrder())
                .completed(request.getCompleted())
                .build();
        return repository.save(todoEntity);
    }

    public TodoEntity updateById(Long id, TodoRequestDto request) {
        TodoEntity todoEntity = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        if (request.getTitle() != null)
            todoEntity.setTitle(request.getTitle());
        if (request.getOrder() != null)
            todoEntity.setTodoOrder(request.getOrder());
        if (request.getCompleted() != null)
            todoEntity.setCompleted(request.getCompleted());
        return repository.save(todoEntity);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

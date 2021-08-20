package org.example.service;

import org.example.domain.TodoEntity;
import org.example.dto.TodoRequestDto;
import org.example.repository.TodoEntityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoEntityRepository repository;

    @InjectMocks
    private TodoService todoService;

    private TodoEntity oneExpectedEntity(Long id) {
        return TodoEntity.builder()
                .id(id)
                .title("TEST TODO")
                .todoOrder(0L)
                .completed(false)
                .build();
    }

    private List<TodoEntity> manyExpectedEntity() {
        List<TodoEntity> entityList = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            entityList.add(oneExpectedEntity((long) i));
        return entityList;
    }

    @DisplayName("1. ID로 특정 TODO 조회")
    @Test
    void test_1() {
        TodoEntity expectedEntity = oneExpectedEntity(1L);
        given(repository.findById(anyLong())).willReturn(Optional.of(expectedEntity));

        TodoEntity result = todoService.findById(1L);

        assertEquals(expectedEntity.getId(), result.getId());
        assertEquals(expectedEntity.getTitle(), result.getTitle());
        assertEquals(expectedEntity.getTodoOrder(), result.getTodoOrder());
        assertEquals(expectedEntity.getCompleted(), result.getCompleted());
    }

    @DisplayName("2. 없는 ID 조회")
    @Test
    void test_2(){
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        try{
            todoService.findById(1L);
        }catch (ResponseStatusException e) {}
    }

    @DisplayName("3. 전체 TODO 조회(10개)")
    @Test
    void test_3(){
        List<TodoEntity> expectedEntityList = manyExpectedEntity();
        given(repository.findAll()).willReturn(expectedEntityList);

        List<TodoEntity> result = todoService.findAll();

        assertEquals(expectedEntityList.size(), result.size());
    }

    @DisplayName("4. TODO 생성")
    @Test
    void test_4(){
        TodoRequestDto todoRequestDto = TodoRequestDto.builder()
                .title("test")
                .completed(true)
                .build();
        when(repository.save(any(TodoEntity.class))).then(AdditionalAnswers.returnsFirstArg());

        TodoEntity result = todoService.create(todoRequestDto);

        assertEquals("test", result.getTitle());
        assertEquals(true, result.getCompleted());
    }

    @DisplayName("5. TODO 수정")
    @Test
    void test_5(){
        TodoEntity originEntity = oneExpectedEntity(1L);
        TodoRequestDto todoRequestDto = TodoRequestDto.builder()
                .title("test")
                .completed(true)
                .build();

        when(repository.save(any(TodoEntity.class))).then(AdditionalAnswers.returnsFirstArg());
        when(repository.findById(anyLong())).thenReturn(Optional.of(originEntity));

        TodoEntity result = todoService.updateById(1L, todoRequestDto);

        assertEquals("test", result.getTitle());
        assertEquals(true, result.getCompleted());
        assertEquals(originEntity.getId(), result.getId());
        assertEquals(originEntity.getTodoOrder(), result.getTodoOrder());
    }
}
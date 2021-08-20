package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.TodoEntity;
import org.example.dto.TodoRequestDto;
import org.example.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    TodoService todoService;

    private TodoEntity expected;

    @BeforeEach
    void init() {
        expected = TodoEntity.builder()
                .id(1L)
                .title("TEST TODO")
                .todoOrder(0L)
                .completed(false)
                .build();
    }

    @DisplayName("1. 모든 TODO 조회")
    @Test
    void test_1() throws Exception {
        List<TodoEntity> expectedList = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            expectedList.add(mock(TodoEntity.class));

        given(todoService.findAll()).willReturn(expectedList);

        mvc.perform(get("/")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(10));
    }

    @DisplayName("2. 특정 TODO 조회")
    @Test
    void test_2() throws Exception {
        given(todoService.findById(anyLong())).willReturn(expected);

        mvc.perform(get("/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.title").value(expected.getTitle()));
    }

    @DisplayName("3. TODO 생성")
    @Test
    void test_3() throws Exception {
        TodoRequestDto request = TodoRequestDto.builder()
                .title("create")
                .build();
        expected.setTitle("create");

        given(todoService.create(any(TodoRequestDto.class))).willReturn(expected);

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.title").value(expected.getTitle()));
    }

    @DisplayName("4. title 없이 TODO 생성")
    @Test
    void test_4() throws Exception {
        TodoRequestDto request = TodoRequestDto.builder()
                .completed(true)
                .order(0L)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isBadRequest());
    }
}
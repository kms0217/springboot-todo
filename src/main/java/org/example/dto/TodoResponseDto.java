package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.TodoEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoResponseDto {

    private static final String baseUrl = "http://localhost:9092/";
    private Long id;
    private String title;
    private Long order;
    private Boolean completed;
    private String url;

    public TodoResponseDto(TodoEntity todoEntity) {
        id = todoEntity.getId();
        title = todoEntity.getTitle();
        order = todoEntity.getTodoOrder();
        completed = todoEntity.getCompleted();
        url = baseUrl + id;
    }
}

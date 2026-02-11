package com.syariful.todoapps.mapper;

import com.syariful.todoapps.dto.TodoDto;
import com.syariful.todoapps.model.Todo;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {

    public TodoDto todoDto(Todo todo) {
        if (todo == null) {
            return null;
        }

        TodoDto dto = new TodoDto();
        dto.setId(todo.getId());
        dto.setTitle(todo.getTitle());
        dto.setDescription(todo.getDescription());
        dto.setStatus(todo.getStatus());
        dto.setCreatedAt(todo.getCreatedAt());
        dto.setUpdatedAt(todo.getUpdatedAt());
        return dto;
    }

    public Todo toEntity(TodoDto dto) {
        if (dto == null) {
            return null;
        }

        Todo todo = new Todo();
        todo.setId(dto.getId());
        todo.setTitle(dto.getTitle());
        todo.setDescription(dto.getDescription());
        todo.setStatus(dto.getStatus());

        // createdAt and updatedAt are handled by JPA

        return todo;
    }
}

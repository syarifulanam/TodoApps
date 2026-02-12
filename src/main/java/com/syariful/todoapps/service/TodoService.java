package com.syariful.todoapps.service;

import com.syariful.todoapps.dto.TodoDto;
import com.syariful.todoapps.mapper.TodoMapper;
import com.syariful.todoapps.model.Todo;
import com.syariful.todoapps.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {


    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    public void saveTodo(TodoDto todoDto) {
        Todo todo = todoMapper.toEntity(todoDto);
        todoRepository.save(todo);
    }
}

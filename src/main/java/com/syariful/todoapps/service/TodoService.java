package com.syariful.todoapps.service;

import com.syariful.todoapps.dto.TodoDto;
import com.syariful.todoapps.mapper.TodoMapper;
import com.syariful.todoapps.model.Todo;
import com.syariful.todoapps.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoMapper todoMapper;

    public void saveTodo(TodoDto todoDto) {
        Todo todo = todoMapper.toEntity(todoDto);
        todoRepository.save(todo);
    }
}

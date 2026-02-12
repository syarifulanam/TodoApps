package com.syariful.todoapps.service;

import com.syariful.todoapps.dto.TodoDto;
import com.syariful.todoapps.mapper.TodoMapper;
import com.syariful.todoapps.model.Todo;
import com.syariful.todoapps.repository.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {


    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    @Transactional
    public void saveTodo(TodoDto todoDto) {
        Todo todo = todoMapper.toEntity(todoDto);
        todoRepository.save(todo);
    }

    public Page<TodoDto> findAll(Pageable pageable) {
        Page<Todo> todoPage = todoRepository.findAll(pageable);

        List<TodoDto> dtoList = new ArrayList<>();

        for (Todo todo : todoPage.getContent()) {
            dtoList.add(todoMapper.todoDto(todo));
        }

        return new PageImpl<>(
                dtoList,
                pageable,
                todoPage.getTotalElements()
        );
    }
}

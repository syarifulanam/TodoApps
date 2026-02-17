package com.syariful.todoapps.service;

import com.syariful.todoapps.dto.TodoDto;
import com.syariful.todoapps.mapper.TodoMapper;
import com.syariful.todoapps.model.Todo;
import com.syariful.todoapps.repository.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
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
        //log.info("Query Tanpa Pagination:");
        //List<Todo> todoList = todoRepository.findAll();

        //log.info("Query Dengan Pagination:");
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

    @Transactional
    public void deleteById(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new NoSuchElementException("Todo not found with id " + id);
        }
        todoRepository.deleteById(id);
    }

    public TodoDto findById(Long id) {
        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isPresent()) {
            return todoMapper.todoDto(todo.get());
        }
        throw new NoSuchElementException("Todo not found with id: "  + id);
    }

    @Transactional
    public void updateTodo(Long id, TodoDto todoDto) {
//        Optional<Todo> todo = todoRepository.findById(id);
//        if (todo.isPresent()) {
//            todoMapper.updatedEntityFromDto(todoDto, todo.get());
//            todoRepository.save(todo.get());
//            return;
//        }
//        throw new NoSuchElementException("Todo not found with id: " + id);

        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isEmpty()) {
            throw new NoSuchElementException("Todo not found with id: " + id);
        }

        todoMapper.updatedEntityFromDto(todoDto,todo.get());
        todoRepository.save(todo.get());
    }
}

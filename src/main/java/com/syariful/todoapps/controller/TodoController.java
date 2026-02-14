package com.syariful.todoapps.controller;

import com.syariful.todoapps.dto.TodoDto;
import com.syariful.todoapps.model.TodoStatus;
import com.syariful.todoapps.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public String index(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<TodoDto> todoPage = todoService.findAll(pageable);
        model.addAttribute("todos", todoPage);
        return "todos/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("todo", new TodoDto());
        model.addAttribute("statuses", TodoStatus.values());
        return "todos/create";
    }

    @PostMapping("/create")
    public String saveTodo(@Valid @ModelAttribute("todo") TodoDto todoDto, BindingResult result, RedirectAttributes redirecAttributes, Model model) {

        // NOTE : jika ada error. maka balik ke halaman create tapi pakai todo yg saat ini
        if ((result.hasErrors())) {
            model.addAttribute("statuses", TodoStatus.values());
            return "todos/create";
        }

        todoService.saveTodo(todoDto);

        redirecAttributes.addFlashAttribute("message", "Todo Created successfully");
        redirecAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/todos";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        todoService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Todo deleted successfully");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/todos";
    }

    //buka form edit aja
    @GetMapping("/edit/{id}")
    public String editTodoForm(@PathVariable Long id, Model model) {
        TodoDto todoDto = todoService.findById(id);
        model.addAttribute("todo", todoDto);
        model.addAttribute("statuses", TodoStatus.values());
        return "todos/edit";
    }

    //untuk simpan data/update data
    @PostMapping("/edit/{id}")
    public String updateTodo(@PathVariable Long id, @Valid @ModelAttribute("todo") TodoDto todoDto, BindingResult result, RedirectAttributes redirectAttributes, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("statuses", TodoStatus.values());
            return "todos/edit";
        }
        // simpan data yg sudah kita ubah
        todoService.updateTodo(id,todoDto);

        //beri pesan sukses di halaman todos
        redirectAttributes.addFlashAttribute("message", "Todo updated successfully");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/todos";
    }

}

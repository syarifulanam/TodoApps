package com.syariful.todoapps.controller;

import com.syariful.todoapps.dto.TodoDto;
import com.syariful.todoapps.model.TodoStatus;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/todos")
public class TodoController {

    @GetMapping
    public String index() {
        return "todos/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("todo", new TodoDto());
        model.addAttribute("statuses", TodoStatus.values());
        return "todos/create";
    }

    @PostMapping("/create")
    public String saveTodo(
            @Valid @ModelAttribute("todo") TodoDto todoDto,
            BindingResult result,
            RedirectAttributes redirecAttributes,
            Model model) {

        // NOTE : jika ada error. maka balik ke halaman create tapi pakai todo yg saat ini
        if ((result.hasErrors())) {
            model.addAttribute("statuses", TodoStatus.values());
            return "todos/create";
        }
        redirecAttributes.addFlashAttribute("message", "Todo Created successfully");
        redirecAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/todos";
    }
}

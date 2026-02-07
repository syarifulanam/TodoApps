package com.syariful.todoapps.dto;

import com.syariful.todoapps.model.TodoStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class TodoDto {

    private Long id;

    @NotBlank(message = "title is required")
    private String title;

    private String description;

    @NotBlank(message = "status is required")
    private TodoStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

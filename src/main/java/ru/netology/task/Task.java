package ru.netology.task;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private Long id;

    @NotBlank(message = "Описание задачи не может быть пустым")
    private String description;

    private Boolean completed = false;
}
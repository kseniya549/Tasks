package ru.netology.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // ✅ Очищаем перед каждым тестом
    @BeforeEach
    void clearAllTasks() throws Exception {
        mockMvc.perform(delete("/tasks"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreateAndGetTask() throws Exception {
        // Создаём ОДНУ задачу
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Тест\"}"))
                .andExpect(status().isCreated());

        // Проверяем что она ОДНА
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));  // Теперь будет 1!
    }

    @Test
    void testDeleteTask() throws Exception {
        // Создаём задачу
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Удалить\"}"))
                .andExpect(status().isCreated());

        // Удаляем по ID
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNoContent());

        // Проверяем что пусто
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));  // Теперь будет 0!
    }

    @Test
    void testValidation() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}
package ru.netology.task;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {
    private final List<Task> tasks = new CopyOnWriteArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public Task createTask(String description) {
        Task task = new Task(counter.incrementAndGet(), description, false);
        tasks.add(task);
        return task;
    }

    public Task updateTask(Long id, String description, Boolean completed) {
        Task task = tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Задача не найдена"));

        if (description != null) task.setDescription(description);
        if (completed != null) task.setCompleted(completed);

        return task;
    }
    public void deleteTask(Long id) {
        tasks.removeIf(task -> task.getId().equals(id));
    }
    public void deleteAllTasks() {
        tasks.clear();
    }


}
package ru.netology.task;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Long id) {
        super("Задача с ID" + id +  "не найдена");
    }

}

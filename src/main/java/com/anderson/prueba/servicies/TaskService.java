package com.anderson.prueba.servicies;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.anderson.prueba.models.Task;

@Service
public class TaskService {
    private ArrayList<Task> tasks = new ArrayList<>();
    private Long nextId = 1L;

    public TaskService() {
        tasks.add(new Task(nextId++, "Completar ejercicio", false));
        tasks.add(new Task(nextId++, "Revisar código", true));
    }

    public ArrayList<Task> getTasksList() {
        return new ArrayList<>(tasks); // Devuelvo una copia para evitar modificaciones externas
    }

    public Task createTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("La tarea no puede ser nula");
        }
        
        task.setId(nextId++);
        tasks.add(task);
        return task;
    }
}

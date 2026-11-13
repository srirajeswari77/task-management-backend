
package com.devops.task_management_backend.controller;

import com.devops.task_management_backend.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public TaskController() {
        tasks.add(new Task(idCounter.getAndIncrement(), "Learn React", false));
        tasks.add(new Task(idCounter.getAndIncrement(), "Learn Spring Boot", false));
        tasks.add(new Task(idCounter.getAndIncrement(), "Learn Jenkins CI/CD", false));
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return tasks;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task addTask(@RequestBody Task task) {
        Task newTask = new Task(
            idCounter.getAndIncrement(),
            task.getTitle(),
            false
        );

        tasks.add(newTask);
        return newTask;
    }

    @PutMapping("/{id}")
    public Task updateTask(
            @PathVariable Long id,
            @RequestBody Task updatedTask) {

        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                task.setCompleted(updatedTask.isCompleted());
                return task;
            }
        }

        throw new org.springframework.web.server.ResponseStatusException(
            HttpStatus.NOT_FOUND, "Task not found"
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        boolean removed = tasks.removeIf(task -> task.getId().equals(id));

        if (!removed) {
            throw new org.springframework.web.server.ResponseStatusException(
                HttpStatus.NOT_FOUND, "Task not found"
            );
        }
    }
}

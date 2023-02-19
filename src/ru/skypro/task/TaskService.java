package ru.skypro.task;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class TaskService {
    private final Map<Integer, Task> tasks = new HashMap<>();

    public void addTask(Task task) {
        this.tasks.put(task.getId(), task);
    }

    public Collection<Task> getAllTasks() {
        return this.tasks.values();
    }

    public Collection<Task> getTasksForDate(LocalDate date) {
        TreeSet<Task> tasksForDate = new TreeSet<>();
        for (Task task : tasks.values()) {
            if (task.appearsIn(date)) {
                tasksForDate.add(task);
            }
        }
        return tasksForDate;
    }
    public void removeTask(int id) throws TaskNotFoundException{
        if (this.tasks.containsKey(id)){
            this.tasks.remove(id);
        } else {
            throw new TaskNotFoundException("Такой задачи нет");
        }
    }
}

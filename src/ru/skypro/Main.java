package ru.skypro;

import ru.skypro.task.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Scanner;

public class Main {
    private static final TaskService SHEDULE = new TaskService();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d.MM.yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SHEDULE.addTask(new SingleTask("single", "test", LocalDateTime.now().plusHours(1), TaskType.WORK));
        SHEDULE.addTask(new DailyTask("daily", "test", LocalDateTime.now().plusHours(3), TaskType.PERSONAL));
        SHEDULE.addTask(new WeeklyTask("weekly", "test", LocalDateTime.now().plusHours(5), TaskType.WORK));
        SHEDULE.addTask(new MonthlyTask("monthly", "test", LocalDateTime.now().plusHours(7), TaskType.PERSONAL));
        SHEDULE.addTask(new YearlyTask("yearly", "test", LocalDateTime.now().plusHours(9), TaskType.WORK));
        addTask(scanner);
        printTaskForDate(scanner);
    }

    private static void addTask(Scanner scanner) {
        String title = readString("Введите назнавание задачи:", scanner);
        String description = readString("Введите описание задачи:", scanner);
        LocalDateTime taskDate = readDateTime(scanner);
        TaskType taskType = readType(scanner);
        Repeatability repeatability = readRepeatability(scanner);
        Task task = switch (repeatability) {
            case SINGLE -> new SingleTask(title, description, taskDate, taskType);
            case DAILY -> new DailyTask(title, description, taskDate, taskType);
            case WEEKLY -> new WeeklyTask(title, description, taskDate, taskType);
            case MONTHLY -> new MonthlyTask(title, description, taskDate, taskType);
            case YEARLY -> new YearlyTask(title, description, taskDate, taskType);
        };
        SHEDULE.addTask(task);
    }

    private static TaskType readType(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Выберите тип задачи:");
                for (TaskType taskType : TaskType.values()) {
                    System.out.println(taskType.ordinal() + ". " + localizeType(taskType));
                }
                System.out.println("Введите тип:");
                String ordinalLine = scanner.nextLine();
                int ordinal = Integer.parseInt(ordinalLine);
                return TaskType.values()[ordinal];
            } catch (NumberFormatException e) {
                System.out.println("Неверный тип задачи");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Тип задачи не найден");
            }
        }
    }

    private static Repeatability readRepeatability(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Выберите тип повторяемости");
                for (Repeatability repeatability : Repeatability.values()) {
                    System.out.println(repeatability.ordinal() + ". " + localizeRepeatability(repeatability));
                }
                System.out.println("Введите тип:");
                String ordinalLine = scanner.nextLine();
                int ordinal = Integer.parseInt(ordinalLine);
                return Repeatability.values()[ordinal];
            } catch (NumberFormatException e) {
                System.out.println("Неверный тип задачи");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Тип задачи не найден");
            }
        }
    }
    private static LocalDateTime readDateTime(Scanner scanner){
        LocalDate localDate = readDate(scanner);
        LocalTime localTime = readTime(scanner);
        return localDate.atTime(localTime);
    }
    private static String readString(String message, Scanner scanner){
        while (true){
            System.out.println(message);
            String readString = scanner.nextLine();
            if (readString == null || readString.isBlank()){
                System.out.println("Ввведите значение");
            } else {
                return readString;
            }
        }
    }
    private static void removeTasks(Scanner scanner){
        System.out.println("Все задачи:");
        for (Task task : SHEDULE.getAllTasks()){
            System.out.println(task.getId() + task.getTitle() + localizeType(task.getTaskType()) + localizeRepeatability(task.getRepeatabilityType()));
        }
        while (true){
            try {
                System.out.println("Выбрать задачу для удаления");
                String idLine = scanner.nextLine();
                int id = Integer.parseInt(idLine);
                SHEDULE.removeTask(id);
                break;
            } catch (NumberFormatException e){
                System.out.println("Неверный номер задачи");
            } catch (TaskNotFoundException e){
                System.out.println("Такой задачи нет");
            }
        }
    }
    private static void printTaskForDate(Scanner scanner){
        LocalDate localDate = readDate(scanner);
        Collection<Task> tasksForDate = SHEDULE.getTasksForDate(localDate);
        System.out.println("Задачи на: " + localDate.format(DATE_FORMAT));
        for (Task task: tasksForDate){
            System.out.println(localizeType(task.getTaskType()) + task.getTitle() + task.getTaskDateTime().format(TIME_FORMAT) + task.getDescription());
        }
    }
    private static LocalDate readDate(Scanner scanner){
        while (true){
            try {
                System.out.println("Введите дату задачи");
                String dateLine = scanner.nextLine();
                return LocalDate.parse(dateLine, DATE_FORMAT);
            } catch (DateTimeParseException e){
                System.out.println("Некорректная дата");
            }
        }
    }
    private static LocalTime readTime(Scanner scanner){
        while (true){
            try {
                System.out.println("Введите ремя задачи");
                String timeLine = scanner.nextLine();
                return LocalTime.parse(timeLine, TIME_FORMAT);
            } catch (DateTimeParseException e){
                System.out.println("Некорректное время");
            }
        }
    }
    private static String localizeType(TaskType taskType){
        return switch (taskType){
            case WORK -> "Рабочая задача";
            case PERSONAL -> "Личная задача";
        };
    }
    private static String localizeRepeatability(Repeatability repeatability){
        return switch (repeatability){
            case SINGLE -> "Разовая";
            case DAILY -> "Ежедневная";
            case WEEKLY -> "Еженедельная";
            case MONTHLY -> "Ежемесячная";
            case YEARLY -> "Ежегодная";
        };
    }

}
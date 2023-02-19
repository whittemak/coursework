package ru.skypro.second;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class secondTask {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String words = "yourapp the quick brown fox jumps over the lazy dog";
        String[] arr = words.split(" ");
        System.out.println(arr.length);
        System.out.println("TOP 10: ");
        Arrays.stream(words.split(" ")).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream().sorted(java.util.Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(10).forEach(e -> System.out.println(e.getKey() + "-" + e.getValue()));


    }
}

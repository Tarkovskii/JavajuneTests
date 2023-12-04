package org.testOne.second;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestTwo {
    public static void main(String[] args) {

        List<Employee> employees = generatorEmployees();
        Stream<Employee> finEmployee = employees.stream();
        Stream<Employee> salesEmployee = employees.stream();

        /**
         * 2.2 Вывести список всех различных отделов (department) по списку сотрудников
         */
        finEmployee.filter(n -> n.getDepartment().equals("sales")).forEach(employee -> System.out.println(employee));
        System.out.println("-----------------------------------------------------------------------------------");
        salesEmployee.filter(n -> n.getDepartment().equals("finance")).forEach(employee -> System.out.println(employee));
        System.out.println("-----------------------------------------------------------------------------------");


        /**
         * 2.3 Всем сотрудникам, чья зарплата меньше 10_000, повысить зарплату на 20%
         */
        employees.stream().filter(n -> n.getSalary() < 10_000).forEach(n -> n.setSalary(n.getSalary() * 1.2));

        employees.stream().forEach(employee -> System.out.println(employee));

        /**
         *  2.4 * Из списка сотрудников с помощью стрима создать Map<String, List<Employee>> с отделами
         *  и сотрудниками внутри отдела
         */

        Map<String, List<Employee>> employeesByDepartment = employees
                .stream()
                .collect(Collectors.groupingBy(employee -> employee.getDepartment()));

        employeesByDepartment.forEach((department, name) -> System.out.format("department %s: %s \n", department, name));

        /**
         * 2.5 * Из списока сорудников с помощью стрима создать Map<String, Double>
         *     с отделами и средней зарплатой внутри отдела
         */
        //Map<String, Double> averageSalary = employees.stream()
        //        .collect(Collectors.toMap(Employee::getDepartment, Function.identity(),
        //                Double.parseDouble((existing,replacement) -> existing)));
        //System.out.println("----------------------------------------------------------------------------------");

        //System.out.println(averageSalary);


    }

    /**
     * 2.1 Создать список из 10-20 сотрудников
     */
    private static List<Employee> generatorEmployees() {
        return List.of(
                new Employee("Alan", 24, 10_000, "finance"),
                new Employee("Ben", 28, 9_000, "finance"),
                new Employee("Sam", 26, 8_000, "finance"),
                new Employee("Simba", 24, 11_000, "finance"),
                new Employee("Kolya", 25, 5_000, "finance"),
                new Employee("Sara", 30, 4_000, "sales"),
                new Employee("Ivan", 37, 6_000, "sales"),
                new Employee("Nat", 29, 10_000, "sales"),
                new Employee("Robert", 24, 4_000, "sales"),
                new Employee("Nikolas", 24, 9_000, "sales")
        );

    }
}


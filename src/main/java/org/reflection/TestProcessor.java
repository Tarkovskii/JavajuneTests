package org.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestProcessor {
    /**
     * Данный метод находит все void-методы без аргументов в классе и запускает их
     * <p>
     * Для запуска создаётся тестовый объект с помощью конструктора без аргументов
     */
    public static void runTest(Class<?> testClass) {
        final Constructor<?> declaredConstructor;
        try {
            declaredConstructor = testClass.getDeclaredConstructor();
            //declaredConstructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Для класса \"" + testClass.getName() + "\"не найдено конструкторов без аргументов");
        }
        final Object testObj;
        try {
            testObj = declaredConstructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Не удалось создать объект класса \"" + testClass.getName() + "\"");
        }


        List<Method> methods = new ArrayList<>();
        List<Method> beforeAfterEach = new ArrayList<>();
        for (Method method : testClass.getMethods()) {
            if (method.isAnnotationPresent(Test.class) && !method.isAnnotationPresent(Skip.class)) {
                checkTestMethod(method);
                methods.add(method);
            }
            if (method.isAnnotationPresent(BeforeEach.class) | method.isAnnotationPresent(AfterEach.class)) {
                checkTestMethod(method);
                beforeAfterEach.add(method);
            }
        }


        List <Method> sortMethods = sortingTestByOrder(methods);
        for (Method method:sortMethods) {
            System.out.println(method);
        }

        System.out.println("====================================================");



        if (beforeAfterEach.size() != 0) {
            runBeforeAfterEachTest(methods, beforeAfterEach, testObj);
        } else {
            methods.forEach(it -> runTest(it, testObj));
        }

    }


    private static void checkTestMethod(Method method) {
        if (!method.getReturnType().isAssignableFrom(void.class) || method.getParameterCount() != 0) {
            throw new IllegalArgumentException("Метод \"" + method.getName() + "\"должен быть void и не иметь аргументов");
        }
    }

    private static void runTest(Method testMethod, Object testObject) {
        try {
            testMethod.invoke(testObject);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Не удалось запустить тестовый метод \"" + testMethod.getName() + "\"");
        }
    }

    /**
     * 1. Добавить аннотации BeforeEach, AfterEach,
     * которые ставятся над методами void без аругментов и запускаются ДО и ПОСЛЕ всех тестов соответственно.
     * @param testMethods List с основными Тестами
     * @param beforeAfterEach List с BeforeEach and AfterEach методами
     * @param testObj Object
     */
    private static void runBeforeAfterEachTest(List<Method> testMethods, List<Method> beforeAfterEach, Object testObj) {
        int i = 0;
        while (testMethods.size() > i) {
            for (Method beforeEach : beforeAfterEach)
                if (beforeEach.isAnnotationPresent(BeforeEach.class)) {
                    try {
                        beforeEach.invoke(testObj);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException("Ошибка BeforeEach");
                    }
                }
            try {
                testMethods.get(i).invoke(testObj);

            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Ошибка основного Теста");
            }

            for (Method afterEach : beforeAfterEach) {
                if (afterEach.isAnnotationPresent(AfterEach.class)) {
                    try {
                        afterEach.invoke(testObj);
                    } catch (IllegalAccessException | InvocationTargetException exception) {
                        throw new RuntimeException("Ошибка AfterEach");
                    }
                }
            }
            i++;
        }
    }

    /**
     * 2. В аннотацию Test добавить параметр order() со значением 0 по умолчанию.
     * Необходимо при запуске тестов фильтровать тесты по этому параметру (от меньшего к большему).
     * @param testMethod Неотсортированный список тестов
     * @return отсортированный список тестов
     */
    public static List<Method> sortingTestByOrder(List<Method> testMethod){
        return testMethod.stream().sorted(Comparator.comparing(it-> it.getAnnotation(Test.class).order())).toList();

    }

}

package org.reflection;

public class Homework {

    /**
     * Написать свою систему запуска тестов
     */
    /**
     * Написать нечто, запускающее тесты
     */
    public static void main(String[] args) {
        TestProcessor.runTest(MyTest.class);
    }

    static class MyTest {
        @BeforeEach
        public void beforeEachTest(){
            System.out.println("BeforeEach called");
        }

        @AfterEach
        public void afterEachTest(){
            System.out.println("AfterEach called");
        }

        @Test(order = 0)
        public void firstTest(){
            System.out.println("firstTest запущен");
        }

        @Test(order = -4)
        /**
         * 3.* Добавить аннотацию @Skip, которую можно ставить над тест-методами. Если она стоит - то тест не запускается.
         */
        @Skip
        public void secondTest(){
            System.out.println("secondTest заущен");
        }

        @Test(order = 1)
        public void thirdTest(){
            System.out.println("thirdTest заущен");
        }

        @Test(order = 3)
        public void fourthTest(){
            System.out.println("fourthTest заущен");
        }



    }
}

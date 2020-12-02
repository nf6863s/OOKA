package TestClass;

import Interfaces.StartingClass;
import Interfaces.StoppingClass;

public class TestDummy {
    private String name;

    public TestDummy() {
        this.name = "tester";
    }

    @StartingClass
    public void test() {
        this.name = name;
        System.out.println("Starting the Testclass with the name " + this.name);
    }

    @StoppingClass
    public void stopTest() {
        System.out.println("Stopping the Testclass with the name " + this.name);
    }

    public static void main(String[] args) {
        System.out.println("Test main");
    }
}

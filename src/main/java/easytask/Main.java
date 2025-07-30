package easytask;

/**
 * Main entry point of the EasyTask application.
 * Runs manual tests through the TestHarness class.
 * @author Houde Yu
 */
public class Main {
    public static void main(String[] args) {
        TestHarness tester = new TestHarness();

        tester.testClassHierarchy(); // 测试继承和多态
        tester.testInterface();      // 测试接口和多态
    }
}


import tests.TestRunner;
import webserver.WebServer;

import java.io.IOException;
import java.util.Scanner;

public class Launcher {

    private static WebServer webServer = new WebServer(1337);

    public static void main(String[] args) throws IOException {
        webServer.initialize();
        webServer.start();
    }

    private static void runTests() {
        TestRunner testRunner = new TestRunner();
        testRunner.runTests();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press enter to exit.");
        scanner.nextLine();
    }
}

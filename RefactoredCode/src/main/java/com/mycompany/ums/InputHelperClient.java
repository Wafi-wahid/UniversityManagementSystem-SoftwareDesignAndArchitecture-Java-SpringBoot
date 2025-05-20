package com.mycompany.ums;

import java.util.Scanner;

/**
 * Interface segregates input prompting behavior (ISP).
 */
interface InputPrompter {
    String prompt(String promptMessage);
}

/**
 * Abstract base class for input prompters.
 * Can be extended for various input sources or validation (OCP).
 */
abstract class BaseInputPrompter implements InputPrompter {
    protected final Scanner scanner;

    protected BaseInputPrompter(Scanner scanner) {
        if (scanner == null) {
            throw new IllegalArgumentException("Scanner cannot be null");
        }
        this.scanner = scanner;
    }
}

/**
 * Concrete input prompter implementation from console using Scanner.
 * Respects LSP since it can be substituted wherever InputPrompter is expected.
 */
class ConsoleInputPrompter extends BaseInputPrompter {

    public ConsoleInputPrompter(Scanner scanner) {
        super(scanner);
    }

    @Override
    public String prompt(String promptMessage) {
        String input;
        do {
            System.out.print(promptMessage);
            input = scanner.nextLine().trim();
        } while (input.isEmpty());
        return input;
    }
}

/**
 * Client class depends on abstraction InputPrompter (DIP).
 */
public class InputHelperClient {
    private final InputPrompter prompter;

    public InputHelperClient(InputPrompter prompter) {
        if (prompter == null) {
            throw new IllegalArgumentException("InputPrompter cannot be null");
        }
        this.prompter = prompter;
    }

    public String getNonEmptyInput(String promptMessage) {
        return prompter.prompt(promptMessage);
    }

    // Demo main
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InputPrompter prompter = new ConsoleInputPrompter(scanner);
        InputHelperClient client = new InputHelperClient(prompter);

        String name = client.getNonEmptyInput("Enter your name: ");
        System.out.println("Hello, " + name + "!");
    }
}

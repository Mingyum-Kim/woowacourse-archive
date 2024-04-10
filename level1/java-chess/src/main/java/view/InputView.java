package view;

import java.util.Scanner;
import view.command.CommandDto;
import view.command.SeparatedCommandInput;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);
    public static final String MOVE_POSITION_REGEX_FORMAT = "^[a-h][1-8]$";

    public CommandDto inputCommand() {
        return new CommandDto(SeparatedCommandInput.from(scanner.nextLine()));
    }
}

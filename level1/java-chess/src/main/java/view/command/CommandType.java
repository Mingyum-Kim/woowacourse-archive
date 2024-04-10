package view.command;

import java.util.Arrays;

public enum CommandType {
    START("start", 0),
    END("end", 0),
    MOVE("move", 2),
    STATUS("status", 0),
    CONTINUE("continue", 0),
    ;

    private final String command;
    private final int supplementsCount;

    CommandType(final String command, final int supplementsCount) {
        this.command = command;
        this.supplementsCount = supplementsCount;
    }

    public static CommandType from(final SeparatedCommandInput separatedCommandInput) {
        return Arrays.stream(CommandType.values())
                .filter(type -> type.command.equals(separatedCommandInput.getPrefix()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 게임 명령어입니다."));
    }

    public int getSupplementsCount() {
        return supplementsCount;
    }
}

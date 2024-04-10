package view.command;

import java.util.ArrayList;
import java.util.List;

public class CommandDto {
    private final CommandType commandType;
    private final List<String> supplements;

    public CommandDto(final SeparatedCommandInput separatedCommandInput) {
        this.commandType = CommandType.from(separatedCommandInput);
        this.supplements = new ArrayList<>(separatedCommandInput.getSupplements());
    }

    public boolean isInvalidSupplementSize() {
        return supplements.size() != commandType.getSupplementsCount();
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public List<String> getSupplements() {
        return supplements;
    }
}

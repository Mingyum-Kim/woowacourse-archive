package controller.command;

import service.ChessGameService;
import view.OutputView;

public interface CommandExecutor {
    void execute(ChessGameService chessGameService, OutputView outputView);
}

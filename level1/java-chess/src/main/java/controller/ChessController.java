package controller;

import controller.command.Command;
import service.ChessGameService;
import view.InputView;
import view.OutputView;
import view.command.CommandDto;

public class ChessController {
    private final InputView inputView;
    private final OutputView outputView;
    private final ChessGameService chessGameService;

    public ChessController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.chessGameService = new ChessGameService();
    }

    public void runChessGame() {
        outputView.printStartMessage();
        while (chessGameService.isContinuing()) {
            inputCommandAndExecute();
        }
    }

    public void inputCommandAndExecute() {
        try {
            CommandDto commandDto = inputView.inputCommand();
            Command command = Command.from(commandDto);
            command.execute(commandDto, chessGameService, outputView);
        } catch (final Exception exception) {
            outputView.printErrorMessage(exception.getMessage());
            chessGameService.endGame();
        }
    }
}

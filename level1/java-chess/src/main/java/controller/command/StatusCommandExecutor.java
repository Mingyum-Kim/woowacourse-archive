package controller.command;

import domain.GameResult;
import service.ChessGameService;
import view.OutputView;
import view.command.CommandDto;

public class StatusCommandExecutor implements CommandExecutor {
    public StatusCommandExecutor(final CommandDto commandDto) {
        if (commandDto.isInvalidSupplementSize()) {
            throw new IllegalArgumentException("[ERROR] 결과 조회 명령어를 올바르게 입력해주세요.");
        }
    }

    @Override
    public void execute(
            final ChessGameService chessGameService,
            final OutputView outputView
    ) {
        GameResult gameResult = chessGameService.status();
        outputView.printGameResult(gameResult);
    }
}

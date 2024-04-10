package controller.command;

import static controller.constants.GameState.CHECKMATE;
import static view.InputView.MOVE_POSITION_REGEX_FORMAT;

import controller.dto.MoveResult;
import domain.position.File;
import domain.position.Position;
import domain.position.Rank;
import java.util.List;
import java.util.regex.Pattern;
import service.ChessGameService;
import view.OutputView;
import view.command.CommandDto;

public class MoveCommandExecutor implements CommandExecutor {
    private static final Pattern POSITION_INPUT_PATTERN = Pattern.compile(MOVE_POSITION_REGEX_FORMAT);
    private static final int SOURCE_SUPPLEMENT_INDEX = 0;
    private static final int TARGET_SUPPLEMENT_INDEX = 1;
    private static final int FILE_INDEX_OF_POSITION = 0;
    private static final int RANK_INDEX_OF_POSITION = 1;

    private final Position source;
    private final Position target;

    public MoveCommandExecutor(final CommandDto commandDto) {
        if (commandDto.isInvalidSupplementSize()) {
            throw new IllegalArgumentException("[ERROR] 게임 이동 명령어를 올바르게 입력해주세요.");
        }
        List<String> supplements = commandDto.getSupplements();
        this.source = convertToPosition(supplements.get(SOURCE_SUPPLEMENT_INDEX));
        this.target = convertToPosition(supplements.get(TARGET_SUPPLEMENT_INDEX));
    }

    private Position convertToPosition(final String coordinate) {
        validateInvalidCoordinate(coordinate);
        File file = new File(coordinate.charAt(FILE_INDEX_OF_POSITION));
        Rank rank = new Rank(Character.getNumericValue(coordinate.charAt(RANK_INDEX_OF_POSITION)));
        return new Position(file, rank);
    }

    private void validateInvalidCoordinate(String coordinate) {
        if (isInvalidCoordinate(coordinate)) {
            throw new IllegalArgumentException("[ERROR] 게임 이동 명령어를 올바르게 입력해주세요.");
        }
    }

    private boolean isInvalidCoordinate(String coordinate) {
        return !POSITION_INPUT_PATTERN.matcher(coordinate).matches();
    }

    @Override
    public void execute(
            final ChessGameService chessGameService,
            final OutputView outputView
    ) {
        MoveResult moveResult = chessGameService.move(source, target);

        outputView.printChessBoard(moveResult.chessGameStatus().chessBoard());
        if (moveResult.gameState() == CHECKMATE) {
            outputView.printCheckmateColor(moveResult.movedPiece().getColor());
        }
    }
}

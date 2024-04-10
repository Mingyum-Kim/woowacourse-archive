package repository.mapper;

import domain.piece.piecerole.Bishop;
import domain.piece.piecerole.BlackPawn;
import domain.piece.piecerole.King;
import domain.piece.piecerole.Knight;
import domain.piece.piecerole.PieceRole;
import domain.piece.piecerole.Queen;
import domain.piece.piecerole.Rook;
import domain.piece.piecerole.WhitePawn;
import java.util.Arrays;

public enum PieceRoleMapper {
    KING("King", King.create()),
    QUEEN("Queen", Queen.create()),
    KNIGHT("Knight", Knight.create()),
    ROOK("Rook", Rook.create()),
    BISHOP("Bishop", Bishop.create()),
    BLACK_PAWN("BlackPawn", BlackPawn.create()),
    WHITE_PAWN("WhitePawn", WhitePawn.create()),
    ;

    private final String fieldName;
    private final PieceRole pieceRole;

    PieceRoleMapper(final String fieldName, final PieceRole pieceRole) {
        this.fieldName = fieldName;
        this.pieceRole = pieceRole;
    }

    public static PieceRole getPieceRoleByName(final String name) {
        return Arrays.stream(PieceRoleMapper.values())
                .filter(element -> element.fieldName.equals(name))
                .findFirst()
                .orElseThrow((() -> new IllegalArgumentException("[ERROR] PieceRole 객체를 생성할 수 없습니다.")))
                .pieceRole;
    }
}

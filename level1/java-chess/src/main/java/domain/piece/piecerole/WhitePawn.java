package domain.piece.piecerole;

import domain.game.Direction;
import domain.game.Movable;
import domain.piece.Piece;
import domain.position.Position;
import domain.position.Rank;
import java.util.List;

public class WhitePawn extends Pawn {
    private WhitePawn(final List<Movable> movables) {
        super(movables);
    }

    public static WhitePawn create() {
        List<Movable> routes = List.of(
                new Movable(ORIGINAL_MAX_MOVEMENT, Direction.NORTH),
                new Movable(ORIGINAL_MAX_MOVEMENT, Direction.NORTH_EAST),
                new Movable(ORIGINAL_MAX_MOVEMENT, Direction.NORTH_WEST)
        );
        return new WhitePawn(routes);
    }

    @Override
    protected List<Movable> generateCurrentMovable(final Position source) {
        if (isStartPosition(source)) {
            return List.of(
                    new Movable(INITIAL_MAX_MOVEMENT, Direction.NORTH),
                    new Movable(ORIGINAL_MAX_MOVEMENT, Direction.NORTH_EAST),
                    new Movable(ORIGINAL_MAX_MOVEMENT, Direction.NORTH_WEST)
            );
        }
        return routes;
    }

    @Override
    public boolean isStartPosition(final Position source) {
        return source.isEqualRank(new Rank(2));
    }

    @Override
    protected boolean hasSameColorPawnOnCurrentFile(
            final Position current,
            final Position position,
            final Piece piece
    ) {
        if (position.equals(current)) {
            return false;
        }
        return position.hasEqualFilePosition(current) && piece.equalPieceRole(this);
    }
}

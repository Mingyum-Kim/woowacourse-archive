package domain.game;

import static domain.piece.Color.BLACK;
import static domain.piece.Color.WHITE;

import domain.piece.Color;
import domain.piece.Piece;
import java.util.Objects;

public class Turn {
    private Color color;

    public Turn(final Color color) {
        this.color = color;
    }

    public void changeTurn() {
        if (color == BLACK) {
            this.color = WHITE;
            return;
        }
        this.color = BLACK;
    }

    public boolean isNotTurn(final Piece sourcePiece) {
        return color != sourcePiece.getColor();
    }

    public String getName() {
        return color.name();
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Turn turn = (Turn) o;
        return color == turn.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}

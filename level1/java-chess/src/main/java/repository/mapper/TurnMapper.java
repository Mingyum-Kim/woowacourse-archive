package repository.mapper;

import domain.game.Turn;
import domain.piece.Color;
import java.util.Arrays;

public enum TurnMapper {
    BLACK("BLACK", new Turn(Color.BLACK)),
    WHITE("WHITE", new Turn(Color.WHITE)),
    ;
    private final String name;
    private final Turn turn;

    TurnMapper(final String name, final Turn turn) {
        this.name = name;
        this.turn = turn;
    }

    public static Turn getTurnByName(final String name) {
        return Arrays.stream(TurnMapper.values())
                .filter(element -> element.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] Turn 객체를 생성할 수 없습니다."))
                .turn;
    }
}

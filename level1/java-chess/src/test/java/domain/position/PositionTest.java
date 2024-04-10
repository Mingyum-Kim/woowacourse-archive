package domain.position;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Vector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionTest {

    @DisplayName("위치를 생성한다")
    @Test
    void generatePosition() {
        Position position = new Position(new File('b'), new Rank(1));
        assertThat(position).isEqualTo(new Position(new File('b'), new Rank(1)));
    }

    @DisplayName("차이를 계산한다.")
    @Test
    void subtractPosition() {
        Position source = new Position(new File('b'), new Rank(1));
        Position target = new Position(new File('c'), new Rank(2));

        assertThat(source.generateVectorToTargetPosition(target)).isEqualTo(new Vector(-1, -1));
    }

    @DisplayName("file 정보를 문자열로 반환한다.")
    @Test
    void fileName() {
        Position position = new Position(new File('b'), new Rank(1));
        assertThat(position.fileName()).isEqualTo("b");
    }

    @DisplayName("rank 정보를 문자열로 반환한다.")
    @Test
    void rankName() {
        Position position = new Position(new File('b'), new Rank(1));
        assertThat(position.rankName()).isEqualTo("1");
    }
}

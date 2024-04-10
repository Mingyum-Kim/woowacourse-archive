package domain.game;

import static fixture.PositionFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.*;

class DirectionTest {

    @DisplayName("(b,1) -> (c,2)로 이동하면, 북동 방향이다.")
    @Test
    void moveToNorthEast() {
        Direction findDirection = Direction.findDirection(B1, C2);
        assertThat(findDirection).isEqualTo(Direction.NORTH_EAST);
    }

    @DisplayName("(b,1) -> (c, 3)로 이동하면, UP_RIGHT 방향이다.")
    @Test
    void moveToUpRight() {
        Direction findDirection = Direction.findDirection(B1, C3);
        assertThat(findDirection).isEqualTo(Direction.UP_RIGHT);
    }

    @DisplayName("(c,4) -> (f,1)로 이동하면, 남동 방향이다.")
    @Test
    void moveToSouthEast() {
        Direction findDirection = Direction.findDirection(C4, F1);
        assertThat(findDirection).isEqualTo(Direction.SOUTH_EAST);
    }
}

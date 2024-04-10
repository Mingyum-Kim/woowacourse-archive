package repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.generator.ConnectionGenerator;

class ConnectionGeneratorTest {
    @DisplayName("드라이버 연결 테스트")
    @Test
    void connectionTest() {
        final Connection connection = ConnectionGenerator.getConnection();
        assertThat(connection).isNotNull();
    }
}

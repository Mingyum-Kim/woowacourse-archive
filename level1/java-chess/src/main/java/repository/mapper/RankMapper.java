package repository.mapper;

import domain.position.Rank;

public final class RankMapper {
    private RankMapper() {
    }

    public static Rank getRankByName(final String name) {
        try {
            return new Rank(Integer.parseInt(name));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("[ERROR] Rank 객체를 생성할 수 없습니다.");
        }
    }
}

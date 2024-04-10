package repository.mapper;

import domain.piece.Color;
import java.util.Arrays;

public enum ColorMapper {
    BLACK("BLACK", Color.BLACK),
    WHITE("WHITE", Color.WHITE),
    ;

    private final String fieldName;
    private final Color color;

    ColorMapper(final String fieldName, final Color color) {
        this.fieldName = fieldName;
        this.color = color;
    }

    public static Color getColorByName(final String name) {
        return Arrays.stream(ColorMapper.values())
                .filter(element -> element.fieldName.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] Color를 조회할 수 없습니다."))
                .color;
    }
}

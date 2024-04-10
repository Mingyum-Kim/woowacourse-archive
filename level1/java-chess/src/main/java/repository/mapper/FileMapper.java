package repository.mapper;

import domain.position.File;

public final class FileMapper {
    private FileMapper() {
    }

    public static File getFileByName(final String name) {
        if (name.length() != 1) {
            throw new IllegalArgumentException("[ERROR] File 객체를 생성할 수 없습니다.");
        }
        return new File(name.charAt(0));
    }
}

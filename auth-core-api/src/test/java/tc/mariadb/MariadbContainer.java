package tc.mariadb;

import org.testcontainers.containers.MariaDBContainer;

/**
 * 공용으로 사용하기 위해서 MariaDBContainer를 확장하여 사용합니다.
 */
public class MariadbContainer extends MariaDBContainer<MariadbContainer> {
    private static final String IMAGE_VERSION = "mariadb:10.5";
    private static MariadbContainer container;

    private MariadbContainer() {
        super(IMAGE_VERSION);
        withInitScript("sql/init.sql");

    }

    public static MariadbContainer getInstance() {
        if (container == null) {
            container = new MariadbContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        // 컨테이너가 시작된 후에 시스템 속성을 설정합니다.
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}

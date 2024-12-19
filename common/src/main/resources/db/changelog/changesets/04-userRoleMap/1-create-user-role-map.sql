-- liquibase formatted sql

-- changeset manjul.tamang:1
-- preconditions onFail:CONTINUE onError:HALT
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = (SELECT DATABASE()) AND table_name = 'user_role_map'
CREATE TABLE IF NOT EXISTS user_role_map
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    version     BIGINT                NULL,
    user_id BIGINT                NOT NULL,
    role_id     BIGINT                NOT NULL,
    CONSTRAINT pk_user_role_map PRIMARY KEY (id),
    CONSTRAINT fk_user_role_map_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_user_role_map_role FOREIGN KEY (role_id) REFERENCES roles (id)
    );
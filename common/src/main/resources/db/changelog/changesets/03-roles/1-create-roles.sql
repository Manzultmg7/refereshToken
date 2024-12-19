-- liquibase formatted sql

-- changeset manjul.tamang:1
-- preconditions onFail:CONTINUE onError:HALT
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = (SELECT DATABASE()) AND table_name = 'roles'
CREATE TABLE IF NOT EXISTS roles
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    version       BIGINT                NOT NULL,
    name          VARCHAR(255)          NOT NULL
    );
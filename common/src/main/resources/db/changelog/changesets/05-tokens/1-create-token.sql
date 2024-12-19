-- liquibase formatted sql

-- changeset manjul.tamang:1
-- preconditions onFail:CONTINUE orError:HALT
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'tokens'
CREATE TABLE IF NOT EXISTS tokens (
    id              BIGINT AUTO_INCREMENT   PRIMARY KEY NOT NULL,
    version         BIGINT                  NOT NULL,
    access_token    VARCHAR(255)            NOT NULL,
    refresh_token   VARCHAR(255)            NOT NULL,
    logged_out      BOOLEAN                 NOT NULL,
    user_id         BIGINT                  NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
    );
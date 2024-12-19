-- liquibase formatted sql
--changeset manjul.tamang:1

--preconditions onFail:CONTINUE onError:HALT
CREATE TABLE IF NOT EXISTS `users`
(
    id                              BIGINT AUTO_INCREMENT NOT NULL,
    version                         BIGINT                NOT NULL,
    full_name                       VARCHAR(255)          NOT NULL,
    password                        VARCHAR(255)          NULL,
    username                        VARCHAR(255)          NULL,
    email                           VARCHAR(255)          NOT NULL,
    phone_number                    VARCHAR(255)          NOT NULL,
    address                         VARCHAR(255)          NULL,
    status_id                       BIGINT                NOT NULL,
    registered_date                 datetime              NULL,
    password_changed_date           datetime              NULL,
    last_logged_in_time             datetime              NULL,
    wrong_password_attempt_count    INT                   NULL,
    profile_picture_name            VARCHAR(255)          NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
    );


--changeset manjul.tamang:2
--precondition-on-fail:MARK_RAN
--preconditions
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.table_constraints WHERE constraint_schema = (SELECT DATABASE()) AND table_name = 'users' AND constraint_name = 'uc_user_email'
ALTER TABLE users
    ADD CONSTRAINT uc_user_email UNIQUE (email);

--changeset manjul.tamang:3
--precondition-on-fail:MARK_RAN
--preconditions
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.table_constraints WHERE constraint_schema = (SELECT DATABASE()) AND table_name = 'users' AND constraint_name = 'uc_user_phone_number'
ALTER TABLE users
    ADD CONSTRAINT uc_user_phone_number UNIQUE (phone_number);

--changeset manjul.tamang:4
--precondition-on-fail:MARK_RAN
--preconditions
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.table_constraints WHERE constraint_schema = (SELECT DATABASE()) AND table_name = 'users' AND constraint_name = 'FK_USER_ON_STATUS'
ALTER TABLE users
    ADD CONSTRAINT FK_USER_ON_STATUS FOREIGN KEY (status_id) REFERENCES status (id);

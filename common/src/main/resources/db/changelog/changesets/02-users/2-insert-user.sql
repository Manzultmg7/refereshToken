-- liquibase formatted sql
--changeset manjul.tamang:1

--preconditions onFail:CONTINUE onError:HALT
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM users where email='manjul.tamang7@gmail.com' OR phone_number='9803060765'
INSERT INTO `users` (full_name, password, username,email, phone_number,address, status_id, wrong_password_attempt_count,version)
VALUES
    ('Manjul Tamang', '$2a$12$ohszArwmIDp3RjFuP7z6SO09QAYe3cARZyD6mmJ7XsP5NOmjr6p6i', 'manjultmg777', 'manjul.tamang7@gmail.com', '9803060765', 'bhaktapur',1,0, 0);
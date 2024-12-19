-- liquibase formatted sql

-- changeset manjul.tamang:1
-- preconditions onFail:CONTINUE onError:HALT
-- precondition-sql-check expectedResult:1 SELECT COUNT(*) FROM users WHERE username = 'manzultmg777'
INSERT INTO user_role_map (user_id, role_id)
SELECT
    (SELECT id FROM users WHERE username = 'manzultmg777') AS user_id,
    r.id AS role_id
FROM roles r
WHERE r.id NOT IN
      (SELECT role_id FROM user_role_map WHERE user_id = (SELECT id FROM users WHERE username = 'manzultmg777'));

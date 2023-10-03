CREATE SEQUENCE IF NOT EXISTS users_id_seq AS INTEGER START WITH 1;
CREATE SEQUENCE IF NOT EXISTS dict_status_id_seq AS INTEGER START WITH 1;
CREATE SEQUENCE IF NOT EXISTS tasks_id_seq AS INTEGER START WITH 1;

CREATE TABLE IF NOT EXISTS "user"
(
    id       INTEGER PRIMARY KEY UNIQUE DEFAULT nextval('users_id_seq'),
    login    VARCHAR(255) NOT NULL UNIQUE,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password TEXT
);
ALTER SEQUENCE IF EXISTS users_id_seq owned by "user".id;

CREATE TABLE IF NOT EXISTS task
(
    id               INTEGER PRIMARY KEY UNIQUE DEFAULT nextval('tasks_id_seq'),
    code             UUID UNIQUE,
    name             VARCHAR(255) NOT NULL,
    status           VARCHAR(255) NOT NULL,
    responsible_user VARCHAR(255),
    CONSTRAINT responsible_user FOREIGN KEY (responsible_user) REFERENCES "user" (login)
);
ALTER SEQUENCE IF EXISTS tasks_id_seq owned by task.id;

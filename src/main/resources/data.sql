INSERT INTO roles (id, name) VALUES (1, 'ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'USER');

INSERT INTO users (id, user_login, user_password, user_email, role_id) VALUES (1, 'admin', 'admin', 'admin@mail.ru', 1);
INSERT INTO users (id, user_login, user_password, user_email, role_id) VALUES (2, 'user', 'user', 'user@mail.ru', 2);
INSERT INTO users (id, user_login, user_password, user_email, role_id) VALUES (3, 'test', 'test', 'test@mail.ru', 2);


-- admin = $2a$10$ma9p/4/c/SEhGQB8JGmFeuEAoFALLgMCuKZZ0xIK2N3IjDO7c71xa
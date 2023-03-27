insert into regular_user (id, email, password, name, surname, salt, status, failed_attempts, locked_until, verified, role) values
                        (nextval('users_id_gen'), 'peki@gmail.com', '$2a$10$8TWonhaYGbjZ1C69pQwB0uWBOANl1FCwz0wxH9z2LsKXIhTM1hUay', 'Pera', 'Peric', '123', 4, 0, null, true, 0);
insert into admin (id, email, password, name, surname, salt, status, failed_attempts, locked_until, verified, role) values
    (nextval('users_id_gen'), 'admin@gmail.com', '$2a$10$8TWonhaYGbjZ1C69pQwB0uWBOANl1FCwz0wxH9z2LsKXIhTM1hUay', 'Admin', 'Admin', '123', 0, 0, null, true, 2);


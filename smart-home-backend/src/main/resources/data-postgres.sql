insert into regular_user (id, email, password, name, surname, salt, status, failed_attempts, locked_until, verified, role) values
                        (nextval('users_id_gen'), 'peki@maildrop.cc', '$2a$10$8TWonhaYGbjZ1C69pQwB0uWBOANl1FCwz0wxH9z2LsKXIhTM1hUay', 'Pera', 'Peric', '123', 4, 0, null, true, 0),
                        (nextval('users_id_gen'), 'miki@maildrop.c', '$2a$10$8TWonhaYGbjZ1C69pQwB0uWBOANl1FCwz0wxH9z2LsKXIhTM1hUay', 'Miki', 'Mikic', '123', 4, 0, null, true, 0);
insert into admin (id, email, password, name, surname, salt, status, failed_attempts, locked_until, verified, role) values
    (nextval('users_id_gen'), 'admin@gmail.com', '$2a$10$8TWonhaYGbjZ1C69pQwB0uWBOANl1FCwz0wxH9z2LsKXIhTM1hUay', 'Admin', 'Admin', '123', 0, 0, null, true, 2);

--  INSERT INTO csr (user_id, common_name, organization_unit, organization, city, state, country, status) VALUES
--      (1, 'www.example.com', 'IT', 'Example Company', 'San Francisco', 'CA', 'US', 0);

-- INSERT INTO cancel_certificate(alias, reason, most_recent) values
--     ('peki@gmail.com', 'neki razlog', true);
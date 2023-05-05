insert into regular_user (id, email, password, name, surname, salt, status, failed_attempts, locked_until, verified, role, pin) values
                        (nextval('users_id_gen'), 'peki@maildrop.cc', '$2y$10$uwgoYpON2hx80Xpfgn4.O.j0Pys.uATCE2gQu3BNr/DwC8qn6G9am', 'Pera', 'Peric', '123', 4, 0, null, true, 0, null),
                        (nextval('users_id_gen'), 'miki@maildrop.c', '$2y$10$uwgoYpON2hx80Xpfgn4.O.j0Pys.uATCE2gQu3BNr/DwC8qn6G9am', 'Miki', 'Mikic', '123', 4, 0, null, true, 0, null);
insert into admin (id, email, password, name, surname, salt, status, failed_attempts, locked_until, verified, role) values
    (nextval('users_id_gen'), 'admin@gmail.com', '$2y$10$uwgoYpON2hx80Xpfgn4.O.j0Pys.uATCE2gQu3BNr/DwC8qn6G9am', 'Admin', 'Admin', '123', 0, 0, null, true, 1);

insert into regular_user (id, email, password, name, surname, salt, status, failed_attempts, locked_until, verified, role, pin) values
        (nextval('users_id_gen'), 'pop@gmail.com', '$2y$10$uwgoYpON2hx80Xpfgn4.O.j0Pys.uATCE2gQu3BNr/DwC8qn6G9am', 'Pop', 'Popovic', '123', 0, 0, null, true, 0, null),
        (nextval('users_id_gen'), 'anas@gmail.com', '$2y$10$uwgoYpON2hx80Xpfgn4.O.j0Pys.uATCE2gQu3BNr/DwC8qn6G9am', 'Anastasija', 'Samcovic', '123', 0, 0, null, true, 0, null),
        (nextval('users_id_gen'), 'srdjan@gmail.com', '$2y$10$uwgoYpON2hx80Xpfgn4.O.j0Pys.uATCE2gQu3BNr/DwC8qn6G9am', 'Srdjan', 'Djuric', '123', 0, 0, null, true, 0, null);

insert into real_estate(city, name, sq_meters, street, street_num, owner_id) values
    ('Novi Sad', 'Popova vila', 200, 'Maksima Gorkog', 12, 4),
    ('Kraljevo', 'Anastasijina krcma', 150, 'Kraljvacka', 2, 5),
    ('Kraljevo', 'Anastasijina krcma 2', 80, 'Kraljvacka', 15, 5);

insert into real_estate_tenant(real_estate_id, user_id) values
    (1, 6),
    (1, 5),
    (2, 6);

--  INSERT INTO csr (user_id, common_name, organization_unit, organization, city, state, country, status) VALUES
--      (1, 'www.example.com', 'IT', 'Example Company', 'San Francisco', 'CA', 'US', 0);

-- INSERT INTO cancel_certificate(alias, reason, most_recent) values
--     ('peki@gmail.com', 'neki razlog', true);
insert into role (role_name) values ('ROLE_TENANT'),
                               ('ROLE_OWNER'),
                               ('ROLE_ADMIN');

insert into privilege (privilege_name) values
                                 ('INCREMENT_FAILED_ATTEMPTS'),
                                 ('GET_ALIASES'),
                                 ('GET_CERTIFICATE_BY_ALIAS'),
                                 ('CREATE_CERTIFICATE'),
                                 ('CANCEL_CERTIFICATE'),
                                 ('CREATE_CSR'),
                                 ('GET_PENDING_CSRS'),
                                 ('REJECT_CSR'),
                                 ('GET_REAL_ESTATE'),
                                 ('FILTER_REAL_ESTATE'),
                                 ('CREATE_REAL_ESTATE'),
                                 ('EDIT_REAL_ESTATE'),
                                 ('EDIT_OWNER_REAL_ESTATE'),
                                 ('EDIT_TENANTS_REAL_ESTATE'),
                                 ('DELETE_REAL_ESTATE'),
                                 ('GET_ACTIVE_REGULARS');

insert into role_privilege (role_id, privilege_id) values
                                                    (3,2),
                                                    (3,3),
                                                    (3,4),
                                                    (3,5),
                                                    (2,6),
                                                    (1,6),
                                                    (3,7),
                                                    (3,8),
                                                    (3,9),
                                                    (1,9),
                                                    (2,9),
                                                    (3,10),
                                                    (2,10),
                                                    (1,10),
                                                    (3,11),
                                                    (3,12),
                                                    (3,13),
                                                    (3,14),
                                                    (3,15),
                                                    (3,16);

insert into regular_user (id, email, password, name, surname, salt, status, failed_attempts, locked_until, verified, role_id, pin) values
                        (nextval('users_id_gen'), 'peki@maildrop.cc', '$2y$10$uwgoYpON2hx80Xpfgn4.O.j0Pys.uATCE2gQu3BNr/DwC8qn6G9am', 'Pera', 'Peric', '123', 4, 0, null, true, 1, null),
                        (nextval('users_id_gen'), 'miki@maildrop.c', '$2y$10$uwgoYpON2hx80Xpfgn4.O.j0Pys.uATCE2gQu3BNr/DwC8qn6G9am', 'Miki', 'Mikic', '123', 4, 0, null, true, 2, null);
insert into admin (id, email, password, name, surname, salt, status, failed_attempts, locked_until, verified, role_id) values
    (nextval('users_id_gen'), 'admin@gmail.com', '$2y$10$uwgoYpON2hx80Xpfgn4.O.j0Pys.uATCE2gQu3BNr/DwC8qn6G9am', 'Admin', 'Admin', '123', 0, 0, null, true, 3);

insert into regular_user (id, email, password, name, surname, salt, status, failed_attempts, locked_until, verified, role_id, pin) values
        (nextval('users_id_gen'), 'pop@gmail.com', '$2y$10$uwgoYpON2hx80Xpfgn4.O.j0Pys.uATCE2gQu3BNr/DwC8qn6G9am', 'Pop', 'Popovic', '123', 0, 0, null, true, 2, null),
        (nextval('users_id_gen'), 'anas@gmail.com', '$2y$10$uwgoYpON2hx80Xpfgn4.O.j0Pys.uATCE2gQu3BNr/DwC8qn6G9am', 'Anastasija', 'Samcovic', '123', 0, 0, null, true, 2, null),
        (nextval('users_id_gen'), 'srdjan@gmail.com', '$2y$10$uwgoYpON2hx80Xpfgn4.O.j0Pys.uATCE2gQu3BNr/DwC8qn6G9am', 'Srdjan', 'Djuric', '123', 0, 0, null, true, 1, null);

insert into real_estate(city, name, sq_meters, street, street_num, owner_id) values
    ('Novi Sad', 'Popova vila', 200, 'Maksima Gorkog', 12, 4),
    ('Kraljevo', 'Anastasijina krcma', 150, 'Kraljevacka', 2, 5),
    ('Kraljevo', 'Anastasijina krcma 2', 80, 'Kraljevacka', 15, 5);

insert into real_estate_tenant(real_estate_id, user_id) values
    (1, 6),
    (1, 5),
    (2, 6);

--  INSERT INTO csr (user_id, common_name, organization_unit, organization, city, state, country, status) VALUES
--      (1, 'www.example.com', 'IT', 'Example Company', 'San Francisco', 'CA', 'US', 0);

-- INSERT INTO cancel_certificate(alias, reason, most_recent) values
--     ('peki@gmail.com', 'neki razlog', true);
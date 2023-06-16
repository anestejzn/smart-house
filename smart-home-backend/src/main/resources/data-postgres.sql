-- sifra1234A2@

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
                             ('GET_REAL_ESTATE'),           --9
                             ('FILTER_OWNER_REAL_ESTATES'), --10
                             ('CREATE_REAL_ESTATE'),        --11
                             ('EDIT_REAL_ESTATE'),          --12
                             ('EDIT_OWNERSHIP_REAL_ESTATE'),--13
                             ('EDIT_TENANTS_REAL_ESTATE'),  --14
                             ('DELETE_REAL_ESTATE'),        --15
                             ('GET_ACTIVE_OWNERS'),         --16
                             ('GET_ALL_OWNERS'),            --17
                             ('FILTER_TENANT_REAL_ESTATES'),--18
                             ('GET_ACTIVE_TENANTS'),        --19
                             ('READ_LOGS'),                 --20
                             ('FILTER_LOGS'),               --21
                             ('GET_ALL_ALARMS'),            --22
                             ('FILTER_USERS'),              --23
                             ('BLOCK_USER'),                --24
                             ('UNBLOCK_USER'),              --25
                             ('READ_DEVICES'),              --26
                             ('DELETE_DEVICE'),             --27
                             ('CREATE_DEVICES'),            --28
                             ('EDIT_DEVICES'),              --29
                             ('GET_FILTERED_ALARMS'),       --30
                             ('GET_REPORT'),                --31
                             ('SAVE_RULE'),                 --32
                             ('GET_RULES'),                 --33
                             ('READ_DEVICE_MESSAGES');      --34

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
                                                    (3,11),
                                                    (3,12),
                                                    (3,13),
                                                    (2,14),
                                                    (3,15),
                                                    (3,16),
                                                    (3,17),
                                                    (1,18),
                                                    (2,19),
                                                    (3,19),
                                                    (3,20),
                                                    (3,21),
                                                    (3,22),
                                                    (3,23),
                                                    (3,24),
                                                    (3,23),
                                                    (3,24),
                                                    (3,25),
                                                    (3,26),
                                                    (1,26),
                                                    (2,26),
                                                    (3,27),
                                                    (3,28),
                                                    (3,29),
                                                    (1,30),
                                                    (2,30),
                                                    (3,30),
                                                    (2,31),
                                                    (3,32),
                                                    (3,33),
                                                    (1,34),
                                                    (2,34);


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

insert into device(device_type, name, filter_regex, period_read, real_estate_id, photo_path, last_read) values
    (0, 'Samsung Camera 100x', '\bmovement\b', 5, 2, 'camera.png', null), --m kao stavio za movement za kameru, nmp sta da stavim
    (0, 'Samsung NighCamera 120x', '\bmovement\b', 5, 2, 'camera.png', null),
    (2, 'Bosch TS 1', '[40-60]c', 10, 2, '\blow\b', null),                -- c kao celsius za temperaturu
    (1, 'Bosch Smart AirConditioner', '\bhigh\b', 5, 1,'air-conditioner.png', null);

insert into message(message_text, date_time, device_id, device_type) values
    ('Room temperature is at optimal level.', '2023-06-16 15:00', 3, 2),
    ('No movement.', '2023-06-16 15:00', 1, 0),
    ('No signal.', '2023-06-16 15:08', 1, 0);

insert into alarm(message, device_id, date_time, admin_only) values
    ('Camera has detected movement.', 1, '2023-05-01', false),
    ('Camera has detected movement.', 1, '2023-06-01', false),
    ('Temperature is too high.', 3, '2023-06-02', false),
    ('Temperature is too high.', 3, '2023-06-10', false),
    ('Temperature too low.', 4, '2023-06-02', false);

insert into rule(regex_pattern, device_type) values
    ('detected', 0),
    ('critical low', 2),
    ('critical high', 2);
--  INSERT INTO csr (user_id, common_name, organization_unit, organization, city, state, country, status) VALUES
--      (1, 'www.example.com', 'IT', 'Example Company', 'San Francisco', 'CA', 'US', 0);

-- INSERT INTO cancel_certificate(alias, reason, most_recent) values
--     ('peki@gmail.com', 'neki razlog', true);
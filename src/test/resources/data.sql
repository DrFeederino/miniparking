DROP TABLE IF EXISTS drivers;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS offices;
DROP TABLE IF EXISTS spots;
DROP TABLE IF EXISTS offices_drivers;

CREATE TABLE offices
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    office_title VARCHAR(255) NOT NULL
);

INSERT INTO offices (office_title)
VALUES ('firstOffice'),
       ('secondOffice');


CREATE TABLE locations
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    capacity       bigint       NOT NULL,
    location_title VARCHAR(255) NOT NULL,
    office_id      BIGINT
);

INSERT INTO locations (capacity, location_title, office_id)
VALUES (22, 'location1', 1),
       (50, 'location2', 1),
       (70, 'location3', 2);

CREATE TABLE spots
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    spot_title  VARCHAR(255) NOT NULL,
    location_id BIGINT
);

INSERT INTO spots (spot_title, location_id)
VALUES ('1', 1),
       ('2', 1),
       ('3', 1),
       ('4', 1),
       ('5', 2),
       ('6', 2),
       ('9', 3);

CREATE TABLE drivers
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    email   VARCHAR(255) NOT NULL,
    name    VARCHAR(255) NOT NULL,
    spot_id BIGINT,
    office_id  BIGINT
);

INSERT INTO drivers (email, name, spot_id, office_id)
VALUES ('test_user@epam.com', 'User', 1, 1),
       ('me_user@maul.ru', 'Me', 5, 1),
       ('mail_test@mail.com', 'Mail', 7, 2);

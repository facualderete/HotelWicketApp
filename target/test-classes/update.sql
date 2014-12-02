CREATE TABLE picture(
  id serial NOT NULL,
  isMain boolean NOT NULL,
  picture bytea,
  CONSTRAINT picture_pkey PRIMARY KEY (id)
) WITH (OIDS=FALSE);
Commit;

CREATE TABLE destination
(
  id serial NOT NULL,
  destination character varying(255),
  details character varying(255),
  picture_id integer,
  CONSTRAINT destination_pkey PRIMARY KEY (id),
  CONSTRAINT fke2febeeb443d7ec FOREIGN KEY (picture_id)
      REFERENCES picture (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
) WITH(OIDS=FALSE);
Commit;

CREATE TABLE hotel(
  id serial NOT NULL,
  accesscounter integer NOT NULL,
  active boolean NOT NULL,
  address character varying(255),
  breakfast boolean NOT NULL,
  category integer NOT NULL,
  city character varying(255),
  name character varying(255),
  phone character varying(255),
  price integer NOT NULL,
  type character varying(255),
  website character varying(255),
  destination_id integer,
  CONSTRAINT hotel_pkey PRIMARY KEY (id),
  CONSTRAINT fk42ad194d298f4ec FOREIGN KEY (destination_id)
      REFERENCES destination (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
) WITH (OIDS=FALSE);
Commit;

CREATE TABLE users(
  id serial NOT NULL,
  admin boolean NOT NULL,
  description character varying(255),
  email character varying(255),
  lastname character varying(255),
  name character varying(255),
  password character varying(255),
  picture_id integer,
  CONSTRAINT users_pkey PRIMARY KEY (id),
  CONSTRAINT fk6a68e08b443d7ec FOREIGN KEY (picture_id)
      REFERENCES picture (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
) WITH (OIDS=FALSE);
Commit;

CREATE TABLE comment(
  id serial NOT NULL,
  commentdate timestamp without time zone,
  companions character varying(255),
  details character varying(255),
  forbidden boolean NOT NULL,
  fromdate timestamp without time zone,
  comfort integer NOT NULL,
  facilities integer NOT NULL,
  hygiene integer NOT NULL,
  location integer NOT NULL,
  price integer NOT NULL,
  service integer NOT NULL,
  reason character varying(255),
  todate timestamp without time zone,
  hotel_id integer,
  user_id integer,
  CONSTRAINT comment_pkey PRIMARY KEY (id),
  CONSTRAINT fk9bde863f5ebf28ac FOREIGN KEY (hotel_id)
      REFERENCES hotel (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk9bde863fb8cb2028 FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
) WITH (OIDS=FALSE);
Commit;

CREATE TABLE users_hotel(
  users_id integer NOT NULL,
  favourites_id integer NOT NULL,
  CONSTRAINT users_hotel_pkey PRIMARY KEY (users_id, favourites_id),
  CONSTRAINT fkf47d1c9d733621b6 FOREIGN KEY (favourites_id)
      REFERENCES hotel (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkf47d1c9db1bbf8cb FOREIGN KEY (users_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT users_hotel_favourites_id_key UNIQUE (favourites_id)
) WITH (OIDS=FALSE);
Commit;

CREATE TABLE hotel_picture(
  hotel_id integer NOT NULL,
  pictures_id integer NOT NULL,
  CONSTRAINT hotel_picture_pkey PRIMARY KEY (hotel_id, pictures_id),
  CONSTRAINT fk9db299335ebf28ac FOREIGN KEY (hotel_id)
      REFERENCES hotel (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk9db29933c5c03255 FOREIGN KEY (pictures_id)
      REFERENCES picture (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT hotel_picture_pictures_id_key UNIQUE (pictures_id)
) WITH (OIDS=FALSE);
Commit;

INSERT INTO destination(destination) VALUES ('Capital Federal');
INSERT INTO destination(destination) VALUES ('Palermo');
INSERT INTO destination(destination) VALUES ('Microcentro');
INSERT INTO destination(destination) VALUES ('San Miguel de Tucuman');
INSERT INTO destination(destination) VALUES ('Monserrat');
INSERT INTO destination(destination) VALUES ('San Telmo');
INSERT INTO destination(destination) VALUES ('Puerto Madero');
Commit;

INSERT INTO "hotel" (accesscounter, active, address, breakfast, category, city, name, phone, price, type, website, destination_id)
SELECT 0, true, address, true, category, city, name, phone, price, type, website, 1
FROM "Hotel"
ORDER BY id;
Commit;

UPDATE hotel
   SET destination_id = (
    SELECT id FROM destination WHERE destination.destination = hotel.city);
Commit;

ALTER TABLE hotel DROP COLUMN city;
Commit;

INSERT INTO "users" (admin, name, lastname, description, email, password)
SELECT false, name, lastname, description, email, password
FROM "User"
ORDER BY id;
Commit;

INSERT INTO "comment" (fromdate, todate, commentdate, reason, companions, details, hotel_id, user_id, forbidden, hygiene, facilities, service, location, price, comfort)
SELECT from_date, to_date, comment_date, reason, companion, details, hotel_id, user_id, false, hygiene, facilities, service, location, 0, 0
FROM "Comment"
ORDER BY id;
Commit;

DROP TABLE "Comment";
DROP TABLE "Hotel";
DROP TABLE "User";
Commit;

ALTER TABLE hotel OWNER TO paw;
ALTER TABLE users OWNER TO paw;
ALTER TABLE comment OWNER TO paw;
ALTER TABLE users_hotel OWNER TO paw;
ALTER TABLE picture OWNER TO paw;
ALTER TABLE hotel_picture OWNER TO paw;
ALTER TABLE destination OWNER TO paw;
--Se corrige el error que tiene postgresql al devolver un tipo bytea... Que ardan en el infierno!!
ALTER DATABASE paw2 SET bytea_output TO 'escape';

Commit;
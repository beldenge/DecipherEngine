-- Database: "Zodiac"

-- DROP DATABASE "Zodiac";

CREATE DATABASE "Zodiac"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'English, United States'
       LC_CTYPE = 'English, United States'
       CONNECTION LIMIT = -1;

	   
-- Table: parts_of_speech

-- DROP TABLE parts_of_speech;

CREATE TABLE parts_of_speech
(
  word character varying NOT NULL,
  part_of_speech character(1) NOT NULL,
  frequency_weight smallint DEFAULT 1,
  CONSTRAINT pk_word_pos PRIMARY KEY (word, part_of_speech)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE parts_of_speech OWNER TO postgres;
	   

-- Table: cipher

-- DROP TABLE cipher;

CREATE TABLE cipher
(
  id SERIAL NOT NULL,
  "name" character varying NOT NULL,
  "rows" integer NOT NULL DEFAULT 0,
  columns integer NOT NULL DEFAULT 0,
  CONSTRAINT pk_id PRIMARY KEY (id ),
  CONSTRAINT unique_name UNIQUE (name )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE cipher
  OWNER TO postgres;

INSERT INTO "cipher"("name", "rows", columns) VALUES
('zodiac340', 20, 17);	   


-- Table: ciphertext

-- DROP TABLE ciphertext;

CREATE TABLE ciphertext
(
  id integer NOT NULL,
  "value" character varying NOT NULL,
  cipher_id integer NOT NULL,
  CONSTRAINT pk_id_cipher_id PRIMARY KEY (id , cipher_id ),
  CONSTRAINT fk_cipher_id FOREIGN KEY (cipher_id)
      REFERENCES cipher (id) MATCH SIMPLE
      ON UPDATE  CASCADE ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE ciphertext
  OWNER TO postgres;

INSERT INTO ciphertext(cipher_id, id, "value") VALUES
('1', 1, 'h'),
('1', 2, 'e'),
('1', 3, 'r'),
('1', 4, 'greater'),
('1', 5, 'backp'),
('1', 6, 'backl'),
('1', 7, 'carrot'),
('1', 8, 'v'),
('1', 9, 'p'),
('1', 10, 'backk'),
('1', 11, 'i'),
('1', 12, 'bottomsemi'),
('1', 13, 'l'),
('1', 14, 't'),
('1', 15, 'g'),
('1', 16, 'topsemi'),
('1', 17, 'backd'),
('1', 18, 'n'),
('1', 19, 'backp'),
('1', 20, 'plus'),
('1', 21, 'b'),
('1', 22, 'vertstrike'),
('1', 23, 'fullbox'),
('1', 24, 'o'),
('1', 25, 'lrbox'),
('1', 26, 'd'),
('1', 27, 'w'),
('1', 28, 'y'),
('1', 29, 'dot'),
('1', 30, 'less'),
('1', 31, 'llbox'),
('1', 32, 'k'),
('1', 33, 'backf'),
('1', 34, 'horstrike'),
('1', 35, 'b'),
('1', 36, 'backy'),
('1', 37, 'rightdoti'),
('1', 38, 'backc'),
('1', 39, 'm'),
('1', 40, 'plus'),
('1', 41, 'u'),
('1', 42, 'z'),
('1', 43, 'g'),
('1', 44, 'w'),
('1', 45, 'vertstrike'),
('1', 46, 'horstrike'),
('1', 47, 'l'),
('1', 48, 'fullbox'),
('1', 49, 'zodiac'),
('1', 50, 'h'),
('1', 51, 'j'),
('1', 52, 's'),
('1', 53, 'backp'),
('1', 54, 'backp'),
('1', 55, 'tridot'),
('1', 56, 'carrot'),
('1', 57, 'backl'),
('1', 58, 'fulltri'),
('1', 59, 'llbox'),
('1', 60, 'v'),
('1', 61, 'rightsemi'),
('1', 62, 'backp'),
('1', 63, 'o'),
('1', 64, 'plus'),
('1', 65, 'plus'),
('1', 66, 'r'),
('1', 67, 'k'),
('1', 68, 'topsemi'),
('1', 69, 'box'),
('1', 70, 'tri'),
('1', 71, 'm'),
('1', 72, 'plus'),
('1', 73, 'zodiac'),
('1', 74, 'flipt'),
('1', 75, 'backj'),
('1', 76, 'backd'),
('1', 77, 'i'),
('1', 78, 'fullcircle'),
('1', 79, 'f'),
('1', 80, 'p'),
('1', 81, 'plus'),
('1', 82, 'p'),
('1', 83, 'leftsemi'),
('1', 84, 'backk'),
('1', 85, 'forslash'),
('1', 86, 'backp'),
('1', 87, 'fulltri'),
('1', 88, 'r'),
('1', 89, 'carrot'),
('1', 90, 'f'),
('1', 91, 'backl'),
('1', 92, 'o'),
('1', 93, 'minus'),
('1', 94, 'llbox'),
('1', 95, 'backd'),
('1', 96, 'c'),
('1', 97, 'backk'),
('1', 98, 'f'),
('1', 99, 'greater'),
('1', 100, 'topsemi'),
('1', 101, 'd'),
('1', 102, 'vertstrike'),
('1', 103, 'fullbox'),
('1', 104, 'fullcircle'),
('1', 105, 'plus'),
('1', 106, 'k'),
('1', 107, 'backq'),
('1', 108, 'lrbox'),
('1', 109, 'leftdoti'),
('1', 110, 'topsemi'),
('1', 111, 'u'),
('1', 112, 'backc'),
('1', 113, 'x'),
('1', 114, 'g'),
('1', 115, 'v'),
('1', 116, 'dot'),
('1', 117, 'zodiac'),
('1', 118, 'l'),
('1', 119, 'i'),
('1', 120, 'vertstrike'),
('1', 121, 'g'),
('1', 122, 'topsemi'),
('1', 123, 'j'),
('1', 124, 'backf'),
('1', 125, 'backj'),
('1', 126, 'fullbox'),
('1', 127, 'o'),
('1', 128, 'plus'),
('1', 129, 'box'),
('1', 130, 'n'),
('1', 131, 'y'),
('1', 132, 'zodiac'),
('1', 133, 'plus'),
('1', 134, 'boxdot'),
('1', 135, 'l'),
('1', 136, 'tri'),
('1', 137, 'backd'),
('1', 138, 'less'),
('1', 139, 'm'),
('1', 140, 'plus'),
('1', 141, 'backb'),
('1', 142, 'plus'),
('1', 143, 'z'),
('1', 144, 'r'),
('1', 145, 'topsemi'),
('1', 146, 'f'),
('1', 147, 'b'),
('1', 148, 'backc'),
('1', 149, 'backy'),
('1', 150, 'a'),
('1', 151, 'circledot'),
('1', 152, 'leftsemi'),
('1', 153, 'k'),
('1', 154, 'minus'),
('1', 155, 'zodiac'),
('1', 156, 'backl'),
('1', 157, 'u'),
('1', 158, 'v'),
('1', 159, 'plus'),
('1', 160, 'carrot'),
('1', 161, 'j'),
('1', 162, 'plus'),
('1', 163, 'o'),
('1', 164, 'backp'),
('1', 165, 'tridot'),
('1', 166, 'less'),
('1', 167, 'f'),
('1', 168, 'b'),
('1', 169, 'backy'),
('1', 170, 'minus'),
('1', 171, 'u'),
('1', 172, 'plus'),
('1', 173, 'r'),
('1', 174, 'forslash'),
('1', 175, 'fullcircle'),
('1', 176, 'flipt'),
('1', 177, 'e'),
('1', 178, 'i'),
('1', 179, 'd'),
('1', 180, 'y'),
('1', 181, 'b'),
('1', 182, 'backp'),
('1', 183, 'backb'),
('1', 184, 't'),
('1', 185, 'm'),
('1', 186, 'k'),
('1', 187, 'o'),
('1', 188, 'topsemi'),
('1', 189, 'less'),
('1', 190, 'backc'),
('1', 191, 'backl'),
('1', 192, 'r'),
('1', 193, 'j'),
('1', 194, 'i'),
('1', 195, 'llbox'),
('1', 196, 'fullcircle'),
('1', 197, 't'),
('1', 198, 'leftsemi'),
('1', 199, 'm'),
('1', 200, 'dot'),
('1', 201, 'plus'),
('1', 202, 'p'),
('1', 203, 'b'),
('1', 204, 'f'),
('1', 205, 'zodiac'),
('1', 206, 'circledot'),
('1', 207, 'tri'),
('1', 208, 's'),
('1', 209, 'backy'),
('1', 210, 'fullbox'),
('1', 211, 'plus'),
('1', 212, 'n'),
('1', 213, 'i'),
('1', 214, 'fullcircle'),
('1', 215, 'f'),
('1', 216, 'b'),
('1', 217, 'backc'),
('1', 218, 'vertstrike'),
('1', 219, 'leftdoti'),
('1', 220, 'fulltri'),
('1', 221, 'r'),
('1', 222, 'backl'),
('1', 223, 'g'),
('1', 224, 'f'),
('1', 225, 'n'),
('1', 226, 'carrot'),
('1', 227, 'backf'),
('1', 228, 'fullcircle'),
('1', 229, 'topsemi'),
('1', 230, 'leftsemi'),
('1', 231, 'backb'),
('1', 232, 'dot'),
('1', 233, 'backc'),
('1', 234, 'v'),
('1', 235, 'leftsemi'),
('1', 236, 'flipt'),
('1', 237, 'plus'),
('1', 238, 'plus'),
('1', 239, 'backy'),
('1', 240, 'b'),
('1', 241, 'x'),
('1', 242, 'bottomsemi'),
('1', 243, 'llbox'),
('1', 244, 'rightdoti'),
('1', 245, 'leftsemi'),
('1', 246, 'tri'),
('1', 247, 'c'),
('1', 248, 'e'),
('1', 249, 'greater'),
('1', 250, 'v'),
('1', 251, 'u'),
('1', 252, 'z'),
('1', 253, 'fullcircle'),
('1', 254, 'minus'),
('1', 255, 'plus'),
('1', 256, 'i'),
('1', 257, 'backc'),
('1', 258, 'dot'),
('1', 259, 'rightsemi'),
('1', 260, 'zodiac'),
('1', 261, 'b'),
('1', 262, 'k'),
('1', 263, 'vertstrike'),
('1', 264, 'o'),
('1', 265, 'backp'),
('1', 266, 'carrot'),
('1', 267, 'dot'),
('1', 268, 'backf'),
('1', 269, 'm'),
('1', 270, 'backq'),
('1', 271, 'g'),
('1', 272, 'topsemi'),
('1', 273, 'r'),
('1', 274, 'backc'),
('1', 275, 't'),
('1', 276, 'plus'),
('1', 277, 'l'),
('1', 278, 'bottomsemi'),
('1', 279, 'circledot'),
('1', 280, 'c'),
('1', 281, 'less'),
('1', 282, 'plus'),
('1', 283, 'f'),
('1', 284, 'backl'),
('1', 285, 'w'),
('1', 286, 'b'),
('1', 287, 'i'),
('1', 288, 'horstrike'),
('1', 289, 'l'),
('1', 290, 'plus'),
('1', 291, 'plus'),
('1', 292, 'horstrike'),
('1', 293, 'w'),
('1', 294, 'c'),
('1', 295, 'zodiac'),
('1', 296, 'w'),
('1', 297, 'backc'),
('1', 298, 'p'),
('1', 299, 'o'),
('1', 300, 's'),
('1', 301, 'h'),
('1', 302, 't'),
('1', 303, 'forslash'),
('1', 304, 'vertstrike'),
('1', 305, 'horstrike'),
('1', 306, 'backp'),
('1', 307, 'i'),
('1', 308, 'f'),
('1', 309, 'backk'),
('1', 310, 'backd'),
('1', 311, 'w'),
('1', 312, 'less'),
('1', 313, 'tridot'),
('1', 314, 'flipt'),
('1', 315, 'b'),
('1', 316, 'box'),
('1', 317, 'y'),
('1', 318, 'o'),
('1', 319, 'b'),
('1', 320, 'llbox'),
('1', 321, 'minus'),
('1', 322, 'c'),
('1', 323, 'backc'),
('1', 324, 'greater'),
('1', 325, 'm'),
('1', 326, 'd'),
('1', 327, 'h'),
('1', 328, 'n'),
('1', 329, 'backp'),
('1', 330, 'backk'),
('1', 331, 's'),
('1', 332, 'zodiac'),
('1', 333, 'z'),
('1', 334, 'o'),
('1', 335, 'fulltri'),
('1', 336, 'a'),
('1', 337, 'i'),
('1', 338, 'k'),
('1', 339, 'leftdoti'),
('1', 340, 'plus');

-- Table: plaintext

-- DROP TABLE plaintext;

CREATE TABLE plaintext
(
  ciphertext_id integer NOT NULL,
  value character varying NOT NULL,
  solution_id integer NOT NULL,
  locked boolean DEFAULT false,
  CONSTRAINT pk_solution_id_ciphertext_id PRIMARY KEY (solution_id , ciphertext_id ),
  CONSTRAINT fk_solution_id FOREIGN KEY (solution_id)
      REFERENCES solution (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE plaintext
  OWNER TO postgres;
  
-- Table: solution

-- DROP TABLE solution;

CREATE TABLE solution
(
  id serial NOT NULL,
  confidence integer,
  created_timestamp timestamp without time zone NOT NULL DEFAULT now(),
  cipher_id integer NOT NULL,
  CONSTRAINT pk_solution_id PRIMARY KEY (id ),
  CONSTRAINT fk_cipher_id FOREIGN KEY (cipher_id)
      REFERENCES cipher (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE solution
  OWNER TO postgres;


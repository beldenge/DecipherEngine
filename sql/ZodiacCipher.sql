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
  frequency_weight integer DEFAULT 1,
  CONSTRAINT pk_word_pos PRIMARY KEY (word , part_of_speech )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE parts_of_speech
  OWNER TO postgres;
	   

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

INSERT INTO "cipher"(id, "name", "rows", columns) VALUES
(1, 'zodiac340', 20, 17),
(2, 'zodiac408', 24, 17);	   


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

INSERT INTO ciphertext(cipher_id, id, "value") VALUES
('2', 1, 'tri'),
('2', 2, 'lrbox'),
('2', 3, 'p'),
('2', 4, 'forslash'),
('2', 5, 'z'),
('2', 6, 'forslash'),
('2', 7, 'u'),
('2', 8, 'b'),
('2', 9, 'lrbox'),
('2', 10, 'backk'),
('2', 11, 'o'),
('2', 12, 'r'),
('2', 13, 'pi'),
('2', 14, 'backp'),
('2', 15, 'x'),
('2', 16, 'pi'),
('2', 17, 'b'),
('2', 18, 'w'),
('2', 19, 'v'),
('2', 20, 'plus'),
('2', 21, 'backe'),
('2', 22, 'g'),
('2', 23, 'y'),
('2', 24, 'f'),
('2', 25, 'circledot'),
('2', 26, 'tri'),
('2', 27, 'h'),
('2', 28, 'p'),
('2', 29, 'boxdot'),
('2', 30, 'k'),
('2', 31, 'anchor'),
('2', 32, 'backq'),
('2', 33, 'y'),
('2', 34, 'backe'),
('2', 35, 'm'),
('2', 36, 'j'),
('2', 37, 'y'),
('2', 38, 'carrot'),
('2', 39, 'u'),
('2', 40, 'i'),
('2', 41, 'backk'),
('2', 42, 'tridot'),
('2', 43, 'backq'),
('2', 44, 't'),
('2', 45, 'flipt'),
('2', 46, 'n'),
('2', 47, 'q'),
('2', 48, 'y'),
('2', 49, 'd'),
('2', 50, 'fullcircle'),
('2', 51, 'horstrike'),
('2', 52, 's'),
('2', 53, 'vertstrike'),
('2', 54, 'forslash'),
('2', 55, 'tri'),
('2', 56, 'fullbox'),
('2', 57, 'b'),
('2', 58, 'p'),
('2', 59, 'o'),
('2', 60, 'r'),
('2', 61, 'a'),
('2', 62, 'u'),
('2', 63, 'lrbox'),
('2', 64, 'backf'),
('2', 65, 'r'),
('2', 66, 'backl'),
('2', 67, 'backq'),
('2', 68, 'e'),
('2', 69, 'backk'),
('2', 70, 'carrot'),
('2', 71, 'l'),
('2', 72, 'm'),
('2', 73, 'z'),
('2', 74, 'j'),
('2', 75, 'backd'),
('2', 76, 'backr'),
('2', 77, 'backslash'),
('2', 78, 'backp'),
('2', 79, 'f'),
('2', 80, 'h'),
('2', 81, 'v'),
('2', 82, 'w'),
('2', 83, 'backe'),
('2', 84, 'fulltri'),
('2', 85, 'y'),
('2', 86, 'boxdot'),
('2', 87, 'plus'),
('2', 88, 'backq'),
('2', 89, 'g'),
('2', 90, 'd'),
('2', 91, 'tri'),
('2', 92, 'k'),
('2', 93, 'i'),
('2', 94, 'horstrike'),
('2', 95, 'circledot'),
('2', 96, 'backq'),
('2', 97, 'x'),
('2', 98, 'fulltri'),
('2', 99, 'fullcircle'),
('2', 100, 'zodiac'),
('2', 101, 's'),
('2', 102, 'vertstrike'),
('2', 103, 'r'),
('2', 104, 'n'),
('2', 105, 'flipt'),
('2', 106, 'anchor'),
('2', 107, 'y'),
('2', 108, 'e'),
('2', 109, 'backl'),
('2', 110, 'o'),
('2', 111, 'fulltri'),
('2', 112, 'backq'),
('2', 113, 'g'),
('2', 114, 'b'),
('2', 115, 't'),
('2', 116, 'q'),
('2', 117, 's'),
('2', 118, 'fullbox'),
('2', 119, 'b'),
('2', 120, 'l'),
('2', 121, 'backd'),
('2', 122, 'forslash'),
('2', 123, 'p'),
('2', 124, 'fullbox'),
('2', 125, 'b'),
('2', 126, 'boxdot'),
('2', 127, 'x'),
('2', 128, 'backq'),
('2', 129, 'e'),
('2', 130, 'h'),
('2', 131, 'm'),
('2', 132, 'u'),
('2', 133, 'carrot'),
('2', 134, 'r'),
('2', 135, 'r'),
('2', 136, 'backk'),
('2', 137, 'backc'),
('2', 138, 'z'),
('2', 139, 'k'),
('2', 140, 'backq'),
('2', 141, 'backp'),
('2', 142, 'i'),
('2', 143, 'horstrike'),
('2', 144, 'w'),
('2', 145, 'backq'),
('2', 146, 'anchor'),
('2', 147, 'fulltri'),
('2', 148, 'fullcircle'),
('2', 149, 'l'),
('2', 150, 'm'),
('2', 151, 'backr'),
('2', 152, 'tri'),
('2', 153, 'fullbox'),
('2', 154, 'b'),
('2', 155, 'p'),
('2', 156, 'd'),
('2', 157, 'r'),
('2', 158, 'plus'),
('2', 159, 'backj'),
('2', 160, 'pi'),
('2', 161, 'circledot'),
('2', 162, 'backslash'),
('2', 163, 'n'),
('2', 164, 'vertstrike'),
('2', 165, 'backe'),
('2', 166, 'e'),
('2', 167, 'u'),
('2', 168, 'h'),
('2', 169, 'backk'),
('2', 170, 'f'),
('2', 171, 'z'),
('2', 172, 'backc'),
('2', 173, 'backp'),
('2', 174, 'o'),
('2', 175, 'v'),
('2', 176, 'w'),
('2', 177, 'i'),
('2', 178, 'fullcircle'),
('2', 179, 'plus'),
('2', 180, 'flipt'),
('2', 181, 'l'),
('2', 182, 'horstrike'),
('2', 183, 'backl'),
('2', 184, 'carrot'),
('2', 185, 'r'),
('2', 186, 'circledot'),
('2', 187, 'h'),
('2', 188, 'i'),
('2', 189, 'tri'),
('2', 190, 'd'),
('2', 191, 'r'),
('2', 192, 'box'),
('2', 193, 't'),
('2', 194, 'y'),
('2', 195, 'backr'),
('2', 196, 'backslash'),
('2', 197, 'backd'),
('2', 198, 'backe'),
('2', 199, 'forslash'),
('2', 200, 'boxdot'),
('2', 201, 'x'),
('2', 202, 'j'),
('2', 203, 'q'),
('2', 204, 'a'),
('2', 205, 'p'),
('2', 206, 'fullcircle'),
('2', 207, 'm'),
('2', 208, 'fulltri'),
('2', 209, 'r'),
('2', 210, 'u'),
('2', 211, 'flipt'),
('2', 212, 'lrbox'),
('2', 213, 'l'),
('2', 214, 'horstrike'),
('2', 215, 'n'),
('2', 216, 'v'),
('2', 217, 'e'),
('2', 218, 'k'),
('2', 219, 'h'),
('2', 220, 'pi'),
('2', 221, 'g'),
('2', 222, 'backr'),
('2', 223, 'i'),
('2', 224, 'anchor'),
('2', 225, 'j'),
('2', 226, 'backk'),
('2', 227, 'fullcircle'),
('2', 228, 'tri'),
('2', 229, 'fulltri'),
('2', 230, 'l'),
('2', 231, 'm'),
('2', 232, 'backl'),
('2', 233, 'n'),
('2', 234, 'a'),
('2', 235, 'horstrike'),
('2', 236, 'z'),
('2', 237, 'vertstrike'),
('2', 238, 'p'),
('2', 239, 'zodiac'),
('2', 240, 'u'),
('2', 241, 'backp'),
('2', 242, 'backk'),
('2', 243, 'a'),
('2', 244, 'tri'),
('2', 245, 'fullbox'),
('2', 246, 'b'),
('2', 247, 'v'),
('2', 248, 'w'),
('2', 249, 'backslash'),
('2', 250, 'plus'),
('2', 251, 'v'),
('2', 252, 't'),
('2', 253, 'flipt'),
('2', 254, 'o'),
('2', 255, 'p'),
('2', 256, 'carrot'),
('2', 257, 'pi'),
('2', 258, 's'),
('2', 259, 'backr'),
('2', 260, 'backl'),
('2', 261, 'backf'),
('2', 262, 'u'),
('2', 263, 'backe'),
('2', 264, 'circledot'),
('2', 265, 'tridot'),
('2', 266, 'd'),
('2', 267, 'zodiac'),
('2', 268, 'g'),
('2', 269, 'lrbox'),
('2', 270, 'lrbox'),
('2', 271, 'i'),
('2', 272, 'm'),
('2', 273, 'n'),
('2', 274, 'backk'),
('2', 275, 'horstrike'),
('2', 276, 's'),
('2', 277, 'backc'),
('2', 278, 'e'),
('2', 279, 'forslash'),
('2', 280, 'tri'),
('2', 281, 'lrbox'),
('2', 282, 'lrbox'),
('2', 283, 'z'),
('2', 284, 'backf'),
('2', 285, 'a'),
('2', 286, 'p'),
('2', 287, 'fullbox'),
('2', 288, 'b'),
('2', 289, 'v'),
('2', 290, 'backp'),
('2', 291, 'backe'),
('2', 292, 'x'),
('2', 293, 'backq'),
('2', 294, 'w'),
('2', 295, 'backq'),
('2', 296, 'box'),
('2', 297, 'f'),
('2', 298, 'fullbox'),
('2', 299, 'fulltri'),
('2', 300, 'backc'),
('2', 301, 'plus'),
('2', 302, 'boxdot'),
('2', 303, 'tri'),
('2', 304, 'a'),
('2', 305, 'tri'),
('2', 306, 'b'),
('2', 307, 'lrbox'),
('2', 308, 'o'),
('2', 309, 't'),
('2', 310, 'fullcircle'),
('2', 311, 'r'),
('2', 312, 'u'),
('2', 313, 'backc'),
('2', 314, 'plus'),
('2', 315, 'box'),
('2', 316, 'backd'),
('2', 317, 'y'),
('2', 318, 'backq'),
('2', 319, 'box'),
('2', 320, 'carrot'),
('2', 321, 's'),
('2', 322, 'backq'),
('2', 323, 'w'),
('2', 324, 'v'),
('2', 325, 'z'),
('2', 326, 'backe'),
('2', 327, 'g'),
('2', 328, 'y'),
('2', 329, 'k'),
('2', 330, 'e'),
('2', 331, 'box'),
('2', 332, 't'),
('2', 333, 'y'),
('2', 334, 'a'),
('2', 335, 'tri'),
('2', 336, 'lrbox'),
('2', 337, 'fullbox'),
('2', 338, 'l'),
('2', 339, 'flipt'),
('2', 340, 'box'),
('2', 341, 'h'),
('2', 342, 'anchor'),
('2', 343, 'f'),
('2', 344, 'b'),
('2', 345, 'x'),
('2', 346, 'tri'),
('2', 347, 'zodiac'),
('2', 348, 'x'),
('2', 349, 'a'),
('2', 350, 'd'),
('2', 351, 'backd'),
('2', 352, 'backslash'),
('2', 353, 'tridot'),
('2', 354, 'l'),
('2', 355, 'anchor'),
('2', 356, 'pi'),
('2', 357, 'backq'),
('2', 358, 'box'),
('2', 359, 'backe'),
('2', 360, 'backd'),
('2', 361, 'fullbox'),
('2', 362, 'fullbox'),
('2', 363, 'circledot'),
('2', 364, 'backe'),
('2', 365, 'fullcircle'),
('2', 366, 'p'),
('2', 367, 'o'),
('2', 368, 'r'),
('2', 369, 'x'),
('2', 370, 'q'),
('2', 371, 'f'),
('2', 372, 'lrbox'),
('2', 373, 'g'),
('2', 374, 'backc'),
('2', 375, 'z'),
('2', 376, 'boxdot'),
('2', 377, 'j'),
('2', 378, 't'),
('2', 379, 'flipt'),
('2', 380, 'backq'),
('2', 381, 'box'),
('2', 382, 'fulltri'),
('2', 383, 'j'),
('2', 384, 'i'),
('2', 385, 'plus'),
('2', 386, 'backr'),
('2', 387, 'b'),
('2', 388, 'p'),
('2', 389, 'q'),
('2', 390, 'w'),
('2', 391, 'circledot'),
('2', 392, 'v'),
('2', 393, 'e'),
('2', 394, 'x'),
('2', 395, 'backr'),
('2', 396, 'tri'),
('2', 397, 'w'),
('2', 398, 'i'),
('2', 399, 'circledot'),
('2', 400, 'backq'),
('2', 401, 'e'),
('2', 402, 'h'),
('2', 403, 'm'),
('2', 404, 'horstrike'),
('2', 405, 'pi'),
('2', 406, 'u'),
('2', 407, 'i'),
('2', 408, 'backk');

-- Table: solution

-- DROP TABLE solution;

CREATE TABLE solution
(
  id serial NOT NULL,
  total_matches integer,
  created_timestamp timestamp without time zone NOT NULL DEFAULT now(),
  cipher_id integer NOT NULL,
  unique_matches integer DEFAULT 0,
  adjacent_matches integer DEFAULT 0,
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
      ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE plaintext
  OWNER TO postgres;


--------------------------------------------------------------------------------
-- Copyright 2015 George Belden
-- 
-- This file is part of DecipherEngine.
-- 
-- DecipherEngine is free software: you can redistribute it and/or modify it under
-- the terms of the GNU General Public License as published by the Free Software
-- Foundation, either version 3 of the License, or (at your option) any later
-- version.
-- 
-- DecipherEngine is distributed in the hope that it will be useful, but WITHOUT
-- ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
-- FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
-- details.
-- 
-- You should have received a copy of the GNU General Public License along with
-- DecipherEngine. If not, see <http://www.gnu.org/licenses/>.
--------------------------------------------------------------------------------

-- Database: "DecipherEngine"

-- DROP DATABASE "DecipherEngine";

CREATE DATABASE "DecipherEngine"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.UTF-8'
       LC_CTYPE = 'en_US.UTF-8'
       CONNECTION LIMIT = -1;
	   
-- Table: parts_of_speech

-- DROP TABLE parts_of_speech;

CREATE TABLE parts_of_speech
(
  word character varying NOT NULL,
  part_of_speech character varying(17) NOT NULL,
  frequency_weight integer DEFAULT 1,
  CONSTRAINT pk_word_pos PRIMARY KEY (part_of_speech, word)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE parts_of_speech
  OWNER TO postgres;

-- Index: idx_word

-- DROP INDEX idx_word;

CREATE INDEX idx_word
  ON parts_of_speech
  USING btree
  (word COLLATE pg_catalog."default");


-- Table: n_gram

-- DROP TABLE n_gram;

CREATE TABLE n_gram
(
  n_gram character varying NOT NULL,
  frequency_weight integer NOT NULL DEFAULT 1,
  num_words smallint NOT NULL,
  CONSTRAINT pk_n_gram_num_words PRIMARY KEY (n_gram, num_words)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE n_gram
  OWNER TO postgres;
  
 
-- Table: execution_stats

-- DROP TABLE execution_stats;

CREATE TABLE execution_stats
(
  id serial NOT NULL,
  start_date timestamp without time zone,
  end_date timestamp without time zone,
  population_size integer,
  lifespan integer,
  survival_rate double precision,
  mutation_rate double precision,
  crossover_rate double precision,
  crossover_algorithm character varying,
  fitness_evaluator character varying,
  mutation_algorithm character varying,
  CONSTRAINT pk_execution_id PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE execution_stats
  OWNER TO postgres;
  
  
-- Table: generation_stats

-- DROP TABLE generation_stats;

CREATE TABLE generation_stats
(
  id serial NOT NULL,
  execution_id integer NOT NULL,
  generation integer NOT NULL,
  execution_time bigint,
  best_fitness double precision,
  average_fitness double precision,
  known_solution_proximity double precision,
  num_mutations integer,
  num_crossovers integer,
  num_generated integer,
  num_selected integer,
  entropy double precision,
  CONSTRAINT pk_generation_id PRIMARY KEY (id),
  CONSTRAINT fk_execution_id FOREIGN KEY (execution_id)
      REFERENCES execution_stats (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE generation_stats
  OWNER TO postgres;
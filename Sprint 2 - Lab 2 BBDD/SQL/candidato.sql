DROP DATABASE IF EXISTS cv;
CREATE DATABASE cv;

USE cv;

CREATE TABLE skill (
                       id BIGINT(20) AUTO_INCREMENT,
                       nombre VARCHAR(128),
                       PRIMARY KEY (id)
);

CREATE TABLE idioma (
                        id BIGINT(20) AUTO_INCREMENT,
                        nombre VARCHAR(128),
                        PRIMARY KEY (id)
);

CREATE TABLE educacion (
                           id BIGINT(20) AUTO_INCREMENT,
                           nombre VARCHAR(128),
                           centro_educativo VARCHAR(128),
                           PRIMARY KEY (id)
);
CREATE TABLE competencia (
                           id BIGINT(20) AUTO_INCREMENT,
                           nombre VARCHAR(128),
                           PRIMARY KEY (id)
);


CREATE TABLE candidato (
                           id BIGINT(20) AUTO_INCREMENT,
                           nombre VARCHAR(128),
                           apellidos VARCHAR(256),
                           avatar VARCHAR(512),
                           PRIMARY KEY (id)
);

CREATE TABLE candidato_skill (
                                 id_candidato BIGINT(20),
                                 id_skill BIGINT(20),
                                 nivel VARCHAR(128),
                                 PRIMARY KEY (id_candidato,id_skill)
);

CREATE TABLE candidato_idioma (
                                  id_candidato BIGINT(20),
                                  id_idioma BIGINT(20),
                                  nivel VARCHAR(128),
                                  PRIMARY KEY (id_candidato,id_idioma)
);


CREATE TABLE candidato_educacion (
                                 id_candidato BIGINT(20),
                                 id_educacion BIGINT(20),
                                 nivel VARCHAR(128),
                                 PRIMARY KEY (id_candidato,id_educacion)
);

CREATE TABLE educacion_competencia (
                                       id_educacion BIGINT(20),
                                       id_competencia BIGINT(20),
                                       PRIMARY KEY (id_educacion,id_competencia)
);

CREATE TABLE recomendacion (
                                 id_recomendacion BIGINT(20)AUTO_INCREMENT,
                                 id_candidato  BIGINT(20),
                                 nombre_recomendador VARCHAR(100),
                                 puesto_recomendador VARCHAR(100),
                                 foto_recomendador BLOB,
                                 comentario VARCHAR(280),
                                 fecha DATE,
                                 PRIMARY KEY(id_recomendacion),
                                 FOREIGN KEY (id_candidato) REFERENCES candidato(id)
);

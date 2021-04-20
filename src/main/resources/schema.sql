CREATE SEQUENCE personne_id_seq start 1 increment 1;

CREATE TABLE PERSONNE (
    id serial PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100)NOT NULL
);

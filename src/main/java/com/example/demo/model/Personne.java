package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Personne {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_personne")
	@SequenceGenerator(name = "seq_personne", sequenceName = "personne_id_seq", allocationSize = 1)
	private Integer id;
	private String nom;
	private String prenom;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

}

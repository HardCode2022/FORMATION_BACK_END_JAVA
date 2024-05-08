package com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.entite;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name ="UTILISATEUR")
public class Utilisateur implements Serializable {

	private  static final long serialVersionUID= -32227755586657672L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long  id ;
	@Column(name = "NOM")
	private String nom ;
	@Column(name = "AGE")
	private Long age;
	@Column(name = "POSTE")
	private String poste;
	@Column(name = "COMPETENCES")
	private String competences;
	@Column(name ="NOTE")
	private String note;
	@Column(name = "IMAGE")
	private String image;
	
	public Utilisateur() {
		
	}

	public Utilisateur(Long id, String nom, Long age, String poste, String competences, String note, String image) {
		super();
		this.id = id;
		this.nom = nom;
		this.age = age;
		this.poste = poste;
		this.competences = competences;
		this.note = note;
		this.image = image;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Long getAge() {
		return age;
	}
	public void setAge(Long age) {
		this.age = age;
	}
	public String getPoste() {
		return poste;
	}
	public void setPoste(String poste) {
		this.poste = poste;
	}
	public String getCompetences() {
		return competences;
	}
	public void setCompetences(String competences) {
		this.competences = competences;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", nom=" + nom + ", age=" + age + ", poste=" + poste + ", competences="
				+ competences + ", note=" + note + ", image=" + image + "]";
	}
}

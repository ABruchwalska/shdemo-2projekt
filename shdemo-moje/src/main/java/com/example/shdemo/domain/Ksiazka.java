package com.example.shdemo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity //encja w bazie danych
@NamedQueries({ //widok w sql
		@NamedQuery(name = "ksiazka.published", query = "Select k from Ksiazka k where k.published = false")
})

public class Ksiazka {
	
	private Long id;
	private String tytul;
	private String autor;
	private Boolean published = false;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public String getTytul(){
		return this.tytul;
	}
	
	public void setTytul(String tytul){
		this.tytul = tytul;
	}
	
	@Column(unique = true, nullable = false)
	
	public String getAutor(){
		return this.autor;
	}
	
	public void setAutor(String autor){
		this.autor = autor;
	}
	
	public Boolean getPublished(){
		return this.published;
	}
	
	public void setPublished(Boolean published){
		this.published = published;
	}
	
	
}

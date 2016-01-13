package com.example.shdemo.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({ 
	@NamedQuery(name = "autor.all", query = "Select a from Autor a"),
	@NamedQuery(name = "autor.byCountry", query = "Select a from Autor a where a.country = :country")
})
public class Autor {
	
	private Long id;
	
	private String imie = "Adolf Kowalski";
	private int wiek = 35;
	private String country = "Poland";
	
	private List<Ksiazka> ksiazkas = new ArrayList<Ksiazka>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getImie() {
		return this.imie;
	}
	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getCountry() {
		return this.country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}

	public int getWiek(){
		return this.wiek;
	}
	
	public void setWiek(int wiek){
		this.wiek = wiek;
	}
	
	// Be careful here, both with lazy and eager fetch type
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Ksiazka> getKsiazkas() {
		return ksiazkas;
	}
	
	public void setKsiazkas(List<Ksiazka> ksiazkas) {
		this.ksiazkas = ksiazkas;
	}
}
	


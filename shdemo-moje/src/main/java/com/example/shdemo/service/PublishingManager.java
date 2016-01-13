package com.example.shdemo.service;

import java.util.List;

import com.example.shdemo.domain.Ksiazka;
import com.example.shdemo.domain.Autor;

public interface PublishingManager {
	
	Long addAutor(Autor autor);
	List<Autor> getAllAutors();
	void deleteAutor(Autor autor);
	List<Autor> findAutorsByCountry(String country);
	Autor findAutorById(Long id);
	void moveAutor(Long autorId, String country);
	
	Long addNewKsiazka(Ksiazka ksiazka,Autor autor);
	List<Ksiazka> getAvailableKsiazkas();
	void unpublishKsiazka(Autor autor, Ksiazka ksiazka);
	Ksiazka findKsiazkaById(Long id);

	List<Ksiazka> getPublishedKsiazkas(Autor autor);

}

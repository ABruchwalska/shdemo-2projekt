package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Ksiazka;
import com.example.shdemo.domain.Autor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class PublishingManagerTest {
	

	@Autowired
	PublishingManager pM;

	private final String IMIE_1 	= "Andrzej Piekarz";
	private final int WIEK_1			= 10;
	private final String COUNTRY_1 	= "Poland";

	private final String IMIE_2 	= "Garou";
	private final String COUNTRY_2 	= "France";

	private final String TYTUL_1 	= "Inkwizytor"; 
	private final String AUTOR_1 		= "Andrzej Piekarz";

	private final String TYTUL_2 	= "Amour";
	private final String AUTOR_2 		= "Garou";
	
	private Autor autor;
	
	@Before
	public void infoAutor(){
		
		autor = new Autor();
		autor.setImie(IMIE_1);
		autor.setWiek(WIEK_1);
		autor.setCountry(COUNTRY_1);
	}
	
	@Test
	public void addAutorCheck() { //sprawdza,czy dodaje poprawnie autora

		
		Long AutorId = pM.addAutor(autor);

		Autor retrievedAutor = pM.findAutorById(AutorId); //retrieved- wziety z bazy

		assertEquals(IMIE_1, retrievedAutor.getImie());
		assertEquals(WIEK_1, retrievedAutor.getWiek());
		assertEquals(COUNTRY_1, retrievedAutor.getCountry());

	}

	@Test
	public void addKsiazkaCheck() { //sprawdza,czy poprawnie dodaje ksiazki

		Ksiazka ksiazka= new Ksiazka();
		ksiazka.setTytul(TYTUL_1);
		ksiazka.setAutor(AUTOR_1);
		
				
		Long KsiazkaId = pM.addNewKsiazka(ksiazka, autor);

		Ksiazka retrievedKsiazka = pM.findKsiazkaById(KsiazkaId);
		assertEquals(TYTUL_1, retrievedKsiazka.getTytul());
		assertEquals(AUTOR_1, retrievedKsiazka.getAutor());

	}

	@Test
	public void getAvailableKsiazkasCheck() { //sprawdza czy zwraca dostepne ksiazki

		int amount = pM.getAvailableKsiazkas().size();//pobiera ilosc ksiazek w bazie		
		
		Ksiazka ksiazka = new Ksiazka();
		ksiazka.setTytul(TYTUL_2);

		pM.addNewKsiazka(ksiazka, autor);

	
		List<Ksiazka> getAvailableKsiazkas = pM.getAvailableKsiazkas();

		assertEquals(amount + 1, getAvailableKsiazkas.size());
		assertEquals(TYTUL_2, getAvailableKsiazkas.get(amount).getTytul());
		assertEquals(autor.getImie(), getAvailableKsiazkas.get(amount).getAutor());
	}

	@Test
	public void unpublishKsiazkaCheck() { //unpublish- ze masz ksiazke ktora 
										  //jest published = true
										  //a unpublish
										  //robi = false
		
		Long AutorId = pM.addAutor(autor);
		
		Ksiazka ksiazka = new Ksiazka();
		ksiazka.setTytul(TYTUL_1);
		ksiazka.setAutor(AUTOR_1);
		
		pM.addNewKsiazka(ksiazka, autor);
		
				
		Autor retrievedAutor = pM.findAutorById(AutorId);
		assertEquals(1, retrievedAutor.getKsiazkas().size());
		
		pM.unpublishKsiazka(retrievedAutor, ksiazka);
		assertEquals(0, retrievedAutor.getKsiazkas().size());
	}
	
	@Test
	public void deleteAutorCheck(){ //sprawdza,czy poprawnie usuwa autora
		
		
		Long AutorId = pM.addAutor(autor);
		
		pM.deleteAutor(autor);
		assertEquals(null, pM.findAutorById(AutorId));
	}
	
	@Test
	public void moveAutorCheck(){ //sprawdza,czy zmienia autorowi jestgo "country"
				
		Long AutorId = pM.addAutor(autor);
		
		pM.moveAutor(AutorId, COUNTRY_2);
		
		assertEquals(COUNTRY_2, pM.findAutorById(AutorId).getCountry());
	}

}


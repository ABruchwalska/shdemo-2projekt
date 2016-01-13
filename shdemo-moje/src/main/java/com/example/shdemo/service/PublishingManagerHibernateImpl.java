package com.example.shdemo.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Ksiazka;
import com.example.shdemo.domain.Autor;

@Component
@Transactional
public class PublishingManagerHibernateImpl implements PublishingManager {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public Long addAutor(Autor autor){
		autor.setId(null);
		return (Long) sessionFactory.getCurrentSession().save(autor);
	}
	
	@Override
	public void deleteAutor(Autor autor) {
		autor = (Autor) sessionFactory.getCurrentSession().get(Autor.class,
				autor.getId());
		
		for (Ksiazka ksiazka : autor.getKsiazkas()) {
			ksiazka.setPublished(true);
			sessionFactory.getCurrentSession().update(ksiazka);
		}
		sessionFactory.getCurrentSession().delete(autor);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Autor> getAllAutors() {
		return sessionFactory.getCurrentSession().getNamedQuery("Autor.all")
				.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Autor> findAutorsByCountry(String country) {
		return sessionFactory.getCurrentSession().getNamedQuery("autor.byCountry").setString("country", country).list();
	}

	@Override
	public Autor findAutorById(Long id){
		return (Autor) sessionFactory.getCurrentSession().get(Autor.class, id);
	}

	@Override
	public void moveAutor(Long autorId, String country){
		Autor autor = (Autor) sessionFactory.getCurrentSession().get(
				Autor.class, autorId);
		autor.setCountry(country);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Long addNewKsiazka(Ksiazka ksiazka, Autor autor) {
		ksiazka.setId(null);
		ksiazka.setAutor(autor.getImie());
		List<Ksiazka>ListaKsiazek = autor.getKsiazkas();
		ListaKsiazek.add(ksiazka);
		autor.setKsiazkas(ListaKsiazek);
		sessionFactory.getCurrentSession().save(autor);
		return (Long) sessionFactory.getCurrentSession().save(ksiazka);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Ksiazka> getAvailableKsiazkas() {
		return sessionFactory.getCurrentSession().getNamedQuery("ksiazka.published")
				.list();
	}
	@Override
	@SuppressWarnings("unchecked")
	public void unpublishKsiazka(Autor autor, Ksiazka ksiazka) {

		autor = (Autor) sessionFactory.getCurrentSession().get(Autor.class,
				autor.getId());
		ksiazka = (Ksiazka) sessionFactory.getCurrentSession().get(Ksiazka.class,
				ksiazka.getId());

		Ksiazka toUnpublish = null;
		for (Ksiazka aKsiazka : autor.getKsiazkas())
			if (aKsiazka.getId().compareTo(ksiazka.getId()) == 0) {
				toUnpublish = aKsiazka;
				break;
			}

		if (toUnpublish != null)
			autor.getKsiazkas().remove(toUnpublish);

		ksiazka.setPublished(true);
	}


	@Override
	public Ksiazka findKsiazkaById(Long id) {
		return (Ksiazka) sessionFactory.getCurrentSession().get(Ksiazka.class, id);
	}

	@Override
	public List<Ksiazka> getPublishedKsiazkas(Autor autor) {
		autor = (Autor) sessionFactory.getCurrentSession().get(Autor.class,
				autor.getId());
		List<Ksiazka> ksiazkas = new ArrayList<Ksiazka>(autor.getKsiazkas());
		return ksiazkas;
	}
	
}

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
	public Long addNewKsiazka(Ksiazka ksiazka, Autor autor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ksiazka> getAvailableKsiazkas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unpublishKsiazka(Autor autor, Ksiazka ksiazka) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Ksiazka findKsiazkaById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ksiazka> getPublishedKsiazkas(Autor autor) {
		// TODO Auto-generated method stub
		return null;
	}

	
}

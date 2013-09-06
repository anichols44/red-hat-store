package org.jboss.rhs.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.rhs.model.Cart;

@Stateless
public class CartPersister {

	@PersistenceContext
	EntityManager em;
	
	public void persist(Cart cart) {
		em.persist(cart);
	}
	
	public void merge(Cart cart) {
		em.merge(cart);
	}

}

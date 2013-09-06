package org.jboss.rhs.service;

import javax.ejb.EJB;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.rhs.model.Cart;

public class CartEventHandler {
	
	@EJB
	private CartPersister cartPersister;
	
	@Inject @CurrentCart
	private Cart cart;
	
	public void handleNewCart(@Observes @NewCart CartEvent cartevent) {
		cartPersister.persist(cart);
	}
	
	public void handleCartChange(@Observes @CartChange CartEvent cartevent) {
		cartPersister.merge(cart);
	}

}

package org.jboss.rhs.data;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.rhs.model.Cart;
import org.jboss.rhs.service.CurrentCart;
import org.jboss.rhs.view.UserBean;

@ApplicationScoped
public class CartRepository {
	
	@Inject
	private EntityManager em;
	
	@Inject
	private UserBean userBean;
	
	public Cart findByUserId(long id) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Cart> cq = cb.createQuery(Cart.class);
		Root<Cart> cart = cq.from(Cart.class);
		cq.select(cart).where(cb.equal(cart.get("userId"), id));
		return em.createQuery(cq).getSingleResult();
	}

}

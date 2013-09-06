package org.jboss.rhs.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.rhs.model.User;

@ApplicationScoped
public class UserRepository {

	@Inject
	private EntityManager em;
	
	public User findById(long id) {
		return em.find(User.class, id);
	}
	
	public long findIdByUsername(String username) {
		return findByUsername(username).getUserId();
	}
	
	public User findByUsername(String username) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> user = cq.from(User.class);
		cq.select(user).where(cb.equal(user.get("username"), username));
		return em.createQuery(cq).getSingleResult();
	}
	
	public boolean usernameMatchesPassword(String username, String password) {
		User user = findByUsername(username);
		return password.equals(user.getPassword());
	}
	
	public User findByEmail(String email) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> user = cq.from(User.class);
		cq.select(user).where(cb.equal(user.get("email"), email));
		return em.createQuery(cq).getSingleResult();
	}
	
	public List<User> findAllUsers() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> user = cq.from(User.class);
		cq.select(user);
		return em.createQuery(cq).getResultList();
	}
	
}

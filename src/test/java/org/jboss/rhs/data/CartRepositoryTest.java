package org.jboss.rhs.data;

import static org.junit.Assert.*;

import java.util.List;
import java.io.File;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.rhs.model.Cart;
import org.jboss.rhs.model.User;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CartRepositoryTest {
	
	@Inject 
	UserRepository userRepo;

	@Inject
	CartRepository cartRepo;
	
	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive jar = ShrinkWrap
				.create(JavaArchive.class, "test.jar")
				.addPackages(true, "org.jboss.rhs")
				.addAsManifestResource(
						new File("src/main/webapp/WEB-INF", "beans.xml"))
				.addAsManifestResource(
						new File("src/main/resources/META-INF",
								"persistence.xml"))
				.addAsManifestResource(
						new File("pom.xml"));
		System.out.println(jar.toString(true)); // for debugging
		return jar;
	}
	
	@Test
	public void testFindByUserId() {
		List<User> users = userRepo.findAllUsers();
		Cart cart;
		for (User u:users) {
			try {
				cart = cartRepo.findByUserId(u.getUserId());
			} catch (NoResultException e) {
				fail("No cart found for user " + u.getUsername() + " with id " + u.getUserId());
			}
		}
	}

}

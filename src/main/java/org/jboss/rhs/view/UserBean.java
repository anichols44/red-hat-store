package org.jboss.rhs.view;

import java.util.Collection;
import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.jboss.rhs.data.CartRepository;
import org.jboss.rhs.data.UserRepository;
import org.jboss.rhs.model.Cart;
import org.jboss.rhs.model.Item;
import org.jboss.rhs.model.User;
import org.jboss.rhs.service.CartChange;
import org.jboss.rhs.service.CartEvent;
import org.jboss.rhs.service.CurrentCart;
import org.jboss.rhs.service.LogoutEvent;
import org.jboss.rhs.service.NewCart;
import org.jboss.rhs.service.UserRegistration;

@SessionScoped
@Named
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1l;

	@Inject
	private FacesContext context;

	@Inject
	private CartRepository cartRepo;

	@Inject
	private UserRepository userRepo;

	@Inject
	private UserRegistration userRegistration;

	@Inject
	@NewCart
	private Event<CartEvent> newCartEvent;

	@Inject
	@CartChange
	private Event<CartEvent> cartChangeEvent;

	@Inject
	private Event<LogoutEvent> logoutEvent;

	@Named
	@Produces
	private User user;

	@Produces
	@CurrentCart
	private Cart cart;

	private boolean loggedIn;

	@PostConstruct
	public void initUser() {
		user = new User();
	}

	public void register() {
		try {
			userRegistration.register(user);
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "New user registered",
					"Registration successful"));
			login();
		} catch (Exception e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Registration failed", e.getLocalizedMessage()));
		}
	}

	public void loginRedirect() {
		if (loggedIn) {
			try {
				context.getExternalContext().redirect(
						"/red-hat-store/welcome.jsf");
			} catch (IOException e) {
			}
		}
	}

	public void login() {
		try {
			if (userRepo.usernameMatchesPassword(user.getUsername(),
					user.getPassword())) {
				loggedIn = true;
			} else {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Login failed: incorrect username or password",
						"Incorrect username or password"));
				loggedIn = false;
			}
		} catch (Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Login failed: " + e.getLocalizedMessage(), e
									.getLocalizedMessage()));
			loggedIn = false;
		}
		if (loggedIn) {
			long id = userRepo.findIdByUsername(user.getUsername());
			try {
				cart = cartRepo.findByUserId(id);
			} catch (NoResultException e) {
				cart = new Cart();
				cart.setUserId(id);
				newCartEvent.fire(new CartEvent());
			}
			loginRedirect();
		}
	}

	public String logout() {
		logoutEvent.fire(new LogoutEvent());
		loggedIn = false;
		user = new User();
		cart = new Cart();
		return "index";
	}

	public void clearCart() {
		cart.removeAllItems();
		cartChangeEvent.fire(new CartEvent());
	}

	public void addToCart(Item item) {
		cart.addItem(item);
		cartChangeEvent.fire(new CartEvent());
	}

	public void removeFromCart(Item item) {
		cart.removeItem(item);
		cartChangeEvent.fire(new CartEvent());
	}

	public Collection<Item> getItemsInCart() {
		return cart.getItems();
	}

	public int numItemsInCart() {
		return cart.getItems().size();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
		cartChangeEvent.fire(new CartEvent());
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String viewCart() {
		return "viewcart";
	}

}

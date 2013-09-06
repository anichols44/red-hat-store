package org.jboss.rhs.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

@SuppressWarnings("serial")
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames = {"email", "username"}))
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long userId;
	
	@NotNull
	@Size(min=4, max=15)
	private String username;
	
	@Email
	private String email;

	@NotNull
	@Size(min=4, max=15)
	private String password;
	
	private Cart cart;
	
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long id) {
		this.userId = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
package org.jboss.rhs.controller;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.rhs.service.LogoutEvent;
import org.jboss.rhs.view.UserBean;

@ConversationScoped
@Named
public class CheckoutController implements Serializable {

	private static final long serialVersionUID = 6701555081967505381L;

	@Inject
	private Conversation conversation;
	
	@Inject
	private UserBean userBean;
	
	private String name, streetAddress, cityState, zip;

	private String payName, payStreetAddress, payCityState, payZip;

	private long cardNumber;

	private String cardExpDate;

	private boolean billingAddressEqualsDelivery = true;

	public String begin() {
		if (conversation.isTransient()) {
			conversation.begin();
		}
		return "review";
	}

	public String delivery() {
		return "delivery";
	}

	public String payment() {
		setBillingAddress();
		return "payment";
	}

	public String confirmation() {
		return "confirmation";
	}

	public String order() {
		conversation.end();
		userBean.clearCart();
		return "order";

	}

	public void setBillingAddress() {
		if (billingAddressEqualsDelivery) {
			payName = name;
			payStreetAddress = streetAddress;
			payCityState = cityState;
			payZip = zip;
		} else {
			payName = "";
			payStreetAddress = "";
			payCityState = "";
			payZip = "";
		}
	}

	public void logoutObserser(@Observes @Any LogoutEvent logoutEvent) {
		if (!conversation.isTransient())
			conversation.end();
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCityState() {
		return cityState;
	}

	public void setCityState(String cityState) {
		this.cityState = cityState;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public String getPayStreetAddress() {
		return payStreetAddress;
	}

	public void setPayStreetAddress(String payStreetAddress) {
		this.payStreetAddress = payStreetAddress;
	}

	public String getPayCityState() {
		return payCityState;
	}

	public void setPayCityState(String payCityState) {
		this.payCityState = payCityState;
	}

	public String getPayZip() {
		return payZip;
	}

	public void setPayZip(String payZip) {
		this.payZip = payZip;
	}

	public String getCardExpDate() {
		return cardExpDate;
	}

	public void setCardExpDate(String payExpDate) {
		this.cardExpDate = payExpDate;
	}

	public long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}

	public boolean isBillingAddressEqualsDelivery() {
		return billingAddressEqualsDelivery;
	}

	public void setBillingAddressEqualsDelivery(
			boolean billingAddressEqualsDelivery) {
		this.billingAddressEqualsDelivery = billingAddressEqualsDelivery;
	}

}

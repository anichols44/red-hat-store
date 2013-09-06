package org.jboss.rhs.model;

import java.util.ArrayList;
import java.util.Collection;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@SuppressWarnings("serial")
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames = "userId"))
public class Cart implements Serializable {

	@Id
	@GeneratedValue
	private long id;

	private long userId;

	@ManyToMany(fetch=FetchType.EAGER)
	private Collection<Item> items = new ArrayList<Item>();
	
	private float total = 0;

	public void addItem(Item item) {
		total += item.getPrice();
		items.add(item);
	}
	
	public Collection<Item> getItems() {
		return items;
	}

	public void setItems(Collection<Item> items) {
		this.items = items;
		total = 0;
		for (Item i:items) {
			total += i.getPrice();
		}
	}

	public void removeItem(Item item) {
		items.remove(item);
		total -= item.getPrice();
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

}

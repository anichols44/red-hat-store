package org.jboss.rhs.rest;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.jboss.rhs.model.Item;

/**
 * 
 */
@Stateless
@Path("/items")
public class ItemEndpoint {
	
	@PersistenceContext (unitName="primary")
	private EntityManager em;
	
	@POST
	@Consumes("application/json")
	public Response create(Item entity) {
		em.persist(entity);
		return Response.created(
				UriBuilder.fromResource(ItemEndpoint.class)
						.path(String.valueOf(entity.getId())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") long id) {
		Item entity = em.find(Item.class, id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		em.remove(entity);
		return Response.noContent().build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") long id) {
		TypedQuery<Item> findByIdQuery = em.createQuery(
				"SELECT i FROM Item i WHERE i.id = :entityId", Item.class);
		findByIdQuery.setParameter("entityId", id);
		Item entity = findByIdQuery.getSingleResult();
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(entity).build();
	}

	@GET
	@Produces("application/json")
	public List<Item> listAll() {
		final List<Item> results = em.createQuery("SELECT i FROM Item i",
				Item.class).getResultList();
		return results;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(@PathParam("id") long id, Item entity) {
		entity.setId(id);
		entity = em.merge(entity);
		return Response.noContent().build();
	}
}
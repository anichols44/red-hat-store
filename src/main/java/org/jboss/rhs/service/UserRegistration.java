package org.jboss.rhs.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.rhs.model.User;

@Stateless
public class UserRegistration {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<User> userEventSrc;

    public void register(User user) throws Exception {
        log.info("Registering " + user.getUsername());
        em.persist(user);
        userEventSrc.fire(user);
    }
}

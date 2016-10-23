package org.frangoro.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.frangoro.dao.UserDao;
import org.frangoro.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDaoHbn implements UserDao {
	
	Logger log = LoggerFactory.getLogger(UserDaoHbn.class);
	
	@PersistenceContext
	EntityManager em;

	@Override
	public User findById(Long id) {
		log.debug("Get the User by id: " + id);
		try {
			User instance = em.find(User.class,id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	@Transactional
	public Boolean update(User user) {
		log.debug("update user.");
		try {
			em.merge(user);
			log.debug("update successful. Id: " + user.getId());
			return true;
		} catch (RuntimeException re) {
			log.error("persist failed.");
			throw re;
		}
	}
}

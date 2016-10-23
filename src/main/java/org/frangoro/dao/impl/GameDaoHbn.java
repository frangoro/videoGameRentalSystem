package org.frangoro.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.frangoro.dao.GameDao;
import org.frangoro.domain.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class GameDaoHbn implements GameDao {
	
	Logger log = LoggerFactory.getLogger(GameDaoHbn.class);
	
	@PersistenceContext
	EntityManager em;

	public Game findById(Long id) {
		log.debug("Get the game by id: " + id);
		try {
			Game instance = em.find(Game.class,id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}

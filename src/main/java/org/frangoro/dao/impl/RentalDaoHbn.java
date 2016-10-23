package org.frangoro.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.frangoro.dao.RentalDao;
import org.frangoro.domain.Rental;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RentalDaoHbn implements RentalDao {

	Logger log = LoggerFactory.getLogger(GameDaoHbn.class);

	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional
	public Boolean save(Rental rental) {
		log.debug("persist rental.");
		try {
			em.persist(rental);
			log.debug("persist successful. Id: " + rental.getId());
			return true;
		} catch (RuntimeException re) {
			log.error("persist failed.");
			throw re;
		}
	}
	
	@Override
	@Transactional
	public Boolean update(Rental rental) {
		log.debug("update rental.");
		try {
			em.merge(rental);
			log.debug("update successful. Id: " + rental.getId());
			return true;
		} catch (RuntimeException re) {
			log.error("persist failed.");
			throw re;
		}
	}

	@Override
	public Rental findRentedGameByUser(Long gameId, Long userId) {
		log.debug("Get a rented game: " + gameId + " by user: " + userId);
		try {
			Query query = em.createQuery("from Rental r where r.game.id = :gameId and "
					+ "r.user.id = :userId and r.returnDate is null");
			query.setParameter("gameId", gameId);
			query.setParameter("userId", userId);
			Rental instance = (Rental)query.getSingleResult();
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}

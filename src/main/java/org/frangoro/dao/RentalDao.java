package org.frangoro.dao;

import org.frangoro.domain.Rental;

public interface RentalDao {

	public Boolean save(Rental rental);

	public Rental findRentedGameByUser(Long gameId, Long userId);

	public Boolean update(Rental rental);
	
}

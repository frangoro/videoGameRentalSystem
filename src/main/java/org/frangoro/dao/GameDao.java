package org.frangoro.dao;

import org.frangoro.domain.Game;

public interface GameDao {
	
	public Game findById(Long id);
}

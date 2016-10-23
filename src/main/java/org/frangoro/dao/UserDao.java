package org.frangoro.dao;

import org.frangoro.domain.User;

public interface UserDao {
	
	public User findById(Long id);

	public Boolean update(User user);
}

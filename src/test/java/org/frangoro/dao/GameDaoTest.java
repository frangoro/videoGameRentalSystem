package org.frangoro.dao;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.frangoro.config.DatabaseConfigTest;
import org.frangoro.dao.impl.GameDaoHbn;
import org.frangoro.domain.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={DatabaseConfigTest.class, GameDaoHbn.class})
public class GameDaoTest {

	@Autowired
	private GameDao gameDao;

	@PersistenceContext
	private EntityManager em;

	static Game game;
	static Long gameId;

	@Before
	public void setupData() {

		game = new Game();
		game.setId(gameId);
	}

	/**
	 * Find a game by id.
	 */
	@Test
	public void findByIdSuccess() {
		gameId = 1l;

		// When
		Game game = gameDao.findById(gameId);

		// Then
		assertThat(game.getId()).isEqualTo(1l);
	}

	/**
	 * Not find a game by id.
	 */
	@Test
	public void findByIdNotFound() {
		// gameId 5 don't exists
		gameId = 5l;

		// When
		Game game = gameDao.findById(gameId);

		// Then
		assertThat(game).isNull();
	}
}

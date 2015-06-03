package com.soleccy.games.tictactoe.my;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class MiniMaxPlayerTest {

	private static Player player = new DefaultPlayer("X");
	private static Player oponent = new DefaultPlayer("O");
	private static Player empty = Player.NO_PLAYER;
	private Board board;

	@Before
	public void setUp() throws Exception {
		board = new Board(3);
	}

	@Test
	public void test() {

		// Given
		// -------
		// |X|X|X|
		// -------
		// | |O| |
		// -------
		// | | |O|
		// -------

		board.getTile(0, 0).setPlayer(player);
		board.getTile(0, 1).setPlayer(player);
		board.getTile(0, 2).setPlayer(player);
		board.getTile(1, 0).setPlayer(empty);
		board.getTile(1, 1).setPlayer(oponent);
		board.getTile(1, 2).setPlayer(empty);
		board.getTile(2, 0).setPlayer(empty);
		board.getTile(2, 1).setPlayer(empty);
		board.getTile(2, 2).setPlayer(oponent);

		// When
		int actualScore = MiniMaxPlayer.evaluate(board, player);

		// Then
		assertThat(actualScore, equalTo(98));

	}
}

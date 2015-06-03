package com.soleccy.games.tictactoe.my;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MiniMaxPlayerEvaluateTest {

	@Parameters(name = "{index}: {0} :: {2}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ "ppp", new Player[] { player, player, player }, 100, },
				{ "ppo", new Player[] { player, player, oponent }, 0 },
				{ "ppe", new Player[] { player, player, empty }, 10 },
				{ "pop", new Player[] { player, oponent, player }, 0 },
				{ "poo", new Player[] { player, oponent, oponent }, 0 },
				{ "poe", new Player[] { player, oponent, empty }, 0 },
				{ "pep", new Player[] { player, empty, player }, 10 },
				{ "peo", new Player[] { player, empty, oponent }, -1 },
				{ "pee", new Player[] { player, empty, empty }, 1 },
				{ "ppp", new Player[] { oponent, player, player }, 0, },
				{ "ppo", new Player[] { oponent, player, oponent }, 0 },
				{ "ppe", new Player[] { oponent, player, empty }, 0 },
				{ "pop", new Player[] { oponent, oponent, player }, 0 },
				{ "poo", new Player[] { oponent, oponent, oponent }, -100 },
				{ "poe", new Player[] { oponent, oponent, empty }, -10 },
				{ "pep", new Player[] { oponent, empty, player }, 0 },
				{ "peo", new Player[] { oponent, empty, oponent }, -10 },
				{ "pee", new Player[] { oponent, empty, empty }, -1 },
				{ "ppp", new Player[] { empty, player, player }, 10, },
				{ "ppo", new Player[] { empty, player, oponent }, -1 },
				{ "ppe", new Player[] { empty, player, empty }, 1 },
				{ "pop", new Player[] { empty, oponent, player }, 0 },
				{ "poo", new Player[] { empty, oponent, oponent }, -10 },
				{ "poe", new Player[] { empty, oponent, empty }, -1 },
				{ "pep", new Player[] { empty, empty, player }, 1 },
				{ "peo", new Player[] { empty, empty, oponent }, -1 },
				{ "eee", new Player[] { empty, empty, empty }, 0 } });
	}

	private static Player player = new DefaultPlayer("X");
	private static Player oponent = new DefaultPlayer("O");
	private static Player empty = Player.NO_PLAYER;

	private Board board;

	private Player[] players;
	private int score;

	public MiniMaxPlayerEvaluateTest(String title, Player[] players, int score) {
		this.players = players;
		this.score = score;
	}

	@Before
	public void setUp() throws Exception {
		board = new Board(3);
	}

	@Test
	public void testEvaluateLine() {

		// Given
		board.getTile(0, 0).setPlayer(players[0]);
		board.getTile(0, 1).setPlayer(players[1]);
		board.getTile(0, 2).setPlayer(players[2]);

		// When
		int actualScore = MiniMaxPlayer.evaluateLine(0, 0, 0, 1, 0, 2, board,
				player);

		// Then
		assertThat(actualScore, equalTo(score));

	}
}

package com.soleccy.games.tictactoe.my;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TicTacToeTest {

	private static final Player PL_1 = new Player("X");
	private static final Player PL_2 = new Player("O");
	private static final Player PL_3 = new Player("+");
	private List<Player> players2 = new ArrayList<>();
	private List<Player> players3 = new ArrayList<>();

	@Before
	public void setup() {
		players2.add(PL_1);
		players2.add(PL_2);
		players3.add(PL_1);
		players3.add(PL_2);
		players3.add(PL_3);
	}

	@Test
	public void test2Init() {
		assertThat(TicTacToe.getNextPlayer(null, players2), anyOf(equalTo(PL_1), equalTo(PL_2)));
	}

	@Test
	public void test2Next1() {
		assertThat(TicTacToe.getNextPlayer(PL_1, players2), equalTo(PL_2));
	}

	@Test
	public void test2Next2() {
		assertThat(TicTacToe.getNextPlayer(PL_2, players2), equalTo(PL_1));
	}

	@Test
	public void test3Init() {
		assertThat(TicTacToe.getNextPlayer(null, players3), anyOf(equalTo(PL_1), equalTo(PL_2), equalTo(PL_3)));
	}

	@Test
	public void test3Next1() {
		assertThat(TicTacToe.getNextPlayer(PL_1, players3), equalTo(PL_2));
	}

	@Test
	public void test3Next2() {
		assertThat(TicTacToe.getNextPlayer(PL_2, players3), equalTo(PL_3));
	}

	@Test
	public void test3Next3() {
		assertThat(TicTacToe.getNextPlayer(PL_3, players3), equalTo(PL_1));
	}

	@Test
	public void testTile() {
		assertThat(new Tile(1,1), equalTo(new Tile(1,1)));
		assertThat(new Tile(1,1, new Player("X")), equalTo(new Tile(1,1, new Player("O"))));
		assertThat(new Tile(1,1), not(equalTo(new Tile(1,2))));
		assertThat(new Tile(1,1, new Player("X")), not(equalTo(new Tile(1,2, new Player("X")))));
	}

	@Test
	public void testSerialize() {

		// Given
		Player player = new Player("X");
		Board board = Board.builder().dim(3).build();
		Tile tile = board.getFreeTiles().get(0);
		tile.setPlayer(player);
		board.setTile(tile);

		// When
		String input = Board.serialize(board.getTiles(), player);

		// Then
		assertThat(input, equalTo("|--------"));

	}

	@Test
	public void testPatterns() {

		// Given
		String pattern = "|||------";
		String state   = "|||--|-|-";

		// When
		boolean matches = Board.contains(state, Arrays.asList(pattern));

		// Then
		assertTrue(matches);

	}

	@Test
	public void testGame() {

		// Given
		TicTacToe game = new TicTacToe();
		game.init(3, new Player("X"), new Player("O"));

		// When
		game.play();

		// Then
		assertThat(game.getResult(), equalTo(State.DRAW));

	}

}

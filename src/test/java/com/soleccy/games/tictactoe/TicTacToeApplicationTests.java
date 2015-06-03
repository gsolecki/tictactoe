package com.soleccy.games.tictactoe;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.soleccy.games.tictactoe.my.TicTacToeManager;

public class TicTacToeApplicationTests {

	@Test
	public void testTicTacToe() {

		// Given
		StringBuilder isb = new StringBuilder();
		isb.append("3\n");
		isb.append("X\n");
		isb.append("X_X\n");
		isb.append("O_O\n");
		isb.append("___\n");
		isb.append("O\n");
		isb.append("XOX\n");
		isb.append("_O_\n");
		isb.append("___\n");
		isb.append("X\n");
		isb.append("___\n");
		isb.append("___\n");
		isb.append("___");

		String input = isb.toString();
		TicTacToeManager game = new TicTacToeManager();
		game.init(input);

		// When
		game.play();

		// Then
		StringBuilder osb = new StringBuilder();
		osb.append("Case 1: X wins\n");
		osb.append("Case 2: O wins\n");
		osb.append("Case 3: Draw\n");
		String output = osb.toString();

		assertThat(game.result(), equalTo(output));
	}

}

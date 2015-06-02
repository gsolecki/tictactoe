package com.soleccy.games.tictactoe;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.soleccy.games.tictactoe.my.TicTacToeManager;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TicTacToeApplication.class)
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
		osb.append("Case 3: Draw");
		String output = osb.toString();

		assertThat(game.result(), equalTo(output));
	}

}

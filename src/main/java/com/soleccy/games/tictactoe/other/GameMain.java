package com.soleccy.games.tictactoe.other;

import java.util.Scanner;

public class GameMain {
	private Board board;
	private AIPlayerMinimax player1;
	private AIPlayerMinimax player2;
	private State currentState;
	private Seed currentPlayer;

	private static Scanner in = new Scanner(System.in);

	public GameMain() {
		board = new Board();
		player1 = new AIPlayerMinimax(board, Seed.NOUGHT);
		player2 = new AIPlayerMinimax(board, Seed.CROSS);

		initGame();

		do {
			playerMove(currentPlayer);
			board.paint();
			updateGame(currentPlayer);

			if (currentState == State.CROSS_WON) {
				System.out.println("'X' won! Bye!");
			} else if (currentState == State.NOUGHT_WON) {
				System.out.println("'O' won! Bye!");
			} else if (currentState == State.DRAW) {
				System.out.println("It's Draw! Bye!");
			}

			currentPlayer = currentPlayer == Seed.CROSS ? Seed.NOUGHT : Seed.CROSS;

		} while (currentState == State.PLAYING);
	}

	public void initGame() {
		board.init();
		currentPlayer = Seed.CROSS;
		currentState = State.PLAYING;
	}

	public void playerMove(Seed theSeed) {

		boolean validInput = false;

		do {
			int row = -1;
			int col = -1;
			if (theSeed == Seed.CROSS) {
				System.out.print("Player 'X', enter your move (row[1-3] column[1-3]): ");
				System.out.println("");
				int[] move = player2.move();
				row = move[0];
				col = move[1];
				System.out.println("");
			} else {
				System.out.print("Player 'O', enter your move (row[1-3] column[1-3]): ");
				System.out.println("");
				int[] move = player1.move();
				row = move[0];
				col = move[1];
				System.out.println("");
			}

			if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS && board.cells[row][col].content == Seed.EMPTY) {
				board.cells[row][col].content = theSeed;
				board.setCurrentRow(row);
				board.setCurrentCol(col);
				validInput = true;
			} else {
				System.out.println("This move at (" + (row + 1) + "," + (col + 1) + ") is not valid. Try again...");
			}
		} while (!validInput);
	}

	public void updateGame(Seed theSeed) {
		if (board.hasWon(theSeed)) {
			currentState = theSeed == Seed.CROSS ? State.CROSS_WON : State.NOUGHT_WON;
		} else if (board.isDraw()) {
			currentState = State.DRAW;
		}
	}

	public static void main(String[] args) {
		new GameMain();
	}
}
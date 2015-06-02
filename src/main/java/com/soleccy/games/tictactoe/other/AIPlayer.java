package com.soleccy.games.tictactoe.other;

import com.soleccy.games.tictactoe.my.TicTacToe;

public abstract class AIPlayer {

	protected int ROWS = TicTacToe.DEFAULT_DIM;
	protected int COLS = TicTacToe.DEFAULT_DIM;

	protected Cell[][] cells;

	protected Seed mySeed;
	protected Seed oppSeed;

	public AIPlayer(Board board, Seed seed) {
		cells = board.cells;
		this.mySeed = seed;
		oppSeed = mySeed == Seed.CROSS ? Seed.NOUGHT : Seed.CROSS;
	}

	abstract int[] move();
}
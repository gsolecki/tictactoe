package com.soleccy.games.tictactoe.other;
public class Board {

	public static final int ROWS = 3;
	public static final int COLS = 3;

	Cell[][] cells;
	private int currentRow;
	private int currentCol;

	public Board() {
		cells = new Cell[ROWS][COLS];
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				cells[row][col] = new Cell(row, col);
			}
		}
	}

	public void init() {
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				cells[row][col].clear();
			}
		}
	}

	public boolean isDraw() {
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				if (cells[row][col].content == Seed.EMPTY) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean hasWon(Seed theSeed) {
		return cells[getCurrentRow()][0].content == theSeed
				&& cells[getCurrentRow()][1].content == theSeed
				&& cells[getCurrentRow()][2].content == theSeed
				|| cells[0][getCurrentCol()].content == theSeed
				&& cells[1][getCurrentCol()].content == theSeed
				&& cells[2][getCurrentCol()].content == theSeed
				|| getCurrentRow() == getCurrentCol()
				&& cells[0][0].content == theSeed
				&& cells[1][1].content == theSeed
				&& cells[2][2].content == theSeed
				|| getCurrentRow() + getCurrentCol() == 2
				&& cells[0][2].content == theSeed
				&& cells[1][1].content == theSeed
				&& cells[2][0].content == theSeed;
	}

	public void paint() {
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				cells[row][col].paint();
				if (col < COLS - 1) {
					System.out.print("|");
				}
			}
			System.out.println();
			if (row < ROWS - 1) {
				System.out.println("-----------");
			}
		}
	}

	public int getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}

	public int getCurrentCol() {
		return currentCol;
	}

	public void setCurrentCol(int currentCol) {
		this.currentCol = currentCol;
	}
}
package com.soleccy.games.tictactoe.other;

import java.util.ArrayList;
import java.util.List;

public class Utils {

	public static int[] winningPatterns = { 0b111000000, 0b000111000,
			0b000000111, 0b100100100, 0b010010010, 0b001001001, 0b100010001,
			0b001010100 };

	public static boolean hasWon(Seed thePlayer, Cell[][] cells) {

		int pattern = getCurrentPattern(thePlayer, cells);
		return hasPattern(pattern);

	}

	private static boolean hasPattern(int pattern) {
		for (int winningPattern : winningPatterns) {
			if ((pattern & winningPattern) == winningPattern) {
				return true;
			}
		}
		return false;
	}

	public static int getCurrentPattern(Seed thePlayer, Cell[][] cells) {
		int pattern = 0b000000000;

		for (int row = 0; row < cells.length; ++row) {
			for (int col = 0; col < cells.length; ++col) {
				if (cells[row][col].content == thePlayer) {
					pattern |= (1 << (row * cells.length + col));
				}
			}
		}
		return pattern;
	}

	public static List<int[]> emptyMoves(Cell[][] cells) {
		List<int[]> nextMoves = new ArrayList<int[]>();
		for (int row = 0; row < cells.length; ++row) {
			for (int col = 0; col < cells.length; ++col) {
				if (cells[row][col].content == Seed.EMPTY) {
					nextMoves.add(new int[] { row, col });
				}
			}
		}
		return nextMoves;
	}

}

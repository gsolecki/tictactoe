package com.soleccy.games.tictactoe.other;

import java.util.ArrayList;
import java.util.List;

public class AIPlayerMinimax extends AIPlayer {

	public AIPlayerMinimax(Board board, Seed seed) {
		super(board, seed);
	}

	@Override
	int[] move() {
		int[] result = minimax(2, mySeed);
		int[] results = new int[] { result[1], result[2] };
		return results;
	}

	private int[] minimax(int depth, Seed player) {

		List<int[]> nextMoves = generateMoves();

		int bestScore = player == mySeed ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		int currentScore;
		int bestRow = -1;
		int bestCol = -1;

		if (nextMoves.isEmpty() || depth == 0) {

			bestScore = evaluate();
			//			System.out.println(Integer.toBinaryString(Utils.getCurrentPattern(mySeed, cells)) + ""
			//					+ " :: "
			//					+ Integer.toBinaryString(Utils.getCurrentPattern(oppSeed, cells))
			//					+ " = " + bestScore);
		} else {
			for (int[] move : nextMoves) {

				cells[move[0]][move[1]].content = player;
				if (player == mySeed) {
					currentScore = minimax(depth - 1, oppSeed)[0];
					if (currentScore > bestScore) {
						bestScore = currentScore;
						bestRow = move[0];
						bestCol = move[1];
						//						System.out.println(depth + " :: " + bestScore + " :: " + bestRow + " :: " + bestCol );
					}
				} else {
					currentScore = minimax(depth - 1, mySeed)[0];
					if (currentScore < bestScore) {
						bestScore = currentScore;
						bestRow = move[0];
						bestCol = move[1];
						//						System.out.println(depth + " :: " + bestScore + " :: " + bestRow + " :: " + bestCol );
					}
				}

				cells[move[0]][move[1]].content = Seed.EMPTY;
			}
		}
		return new int[] { bestScore, bestRow, bestCol };
	}

	private List<int[]> generateMoves() {

		if (Utils.hasWon(mySeed, cells) || Utils.hasWon(oppSeed, cells)) {
			return new ArrayList<int[]>();
		}

		return Utils.emptyMoves(cells);
	}

	private int evaluate() {
		int score = 0;

		score += evaluateLine(0, 0, 0, 1, 0, 2);
		score += evaluateLine(1, 0, 1, 1, 1, 2);
		score += evaluateLine(2, 0, 2, 1, 2, 2);
		score += evaluateLine(0, 0, 1, 0, 2, 0);
		score += evaluateLine(0, 1, 1, 1, 2, 1);
		score += evaluateLine(0, 2, 1, 2, 2, 2);
		score += evaluateLine(0, 0, 1, 1, 2, 2);
		score += evaluateLine(0, 2, 1, 1, 2, 0);
		return score;
	}

	private int evaluateLine(int row1, int col1, int row2, int col2, int row3,
			int col3) {
		int score = 0;

		if (cells[row1][col1].content == mySeed) {
			score = 1;
		} else if (cells[row1][col1].content == oppSeed) {
			score = -1;
		}

		if (cells[row2][col2].content == mySeed) {
			if (score == 1) {
				score = 10;
			} else if (score == -1) {
				return 0;
			} else {
				score = 1;
			}
		} else if (cells[row2][col2].content == oppSeed) {
			if (score == -1) {
				score = -10;
			} else if (score == 1) {
				return 0;
			} else {
				score = -1;
			}
		}

		if (cells[row3][col3].content == mySeed) {
			if (score > 0) {
				score *= 10;
			} else if (score < 0) {
				return 0;
			} else {
				score = 1;
			}
		} else if (cells[row3][col3].content == oppSeed) {
			if (score < 0) {
				score *= 10;
			} else if (score > 1) {
				return 0;
			} else {
				score = -1;
			}
		}
		return score;
	}

}
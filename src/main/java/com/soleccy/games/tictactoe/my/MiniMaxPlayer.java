package com.soleccy.games.tictactoe.my;

import java.util.ArrayList;
import java.util.List;

public class MiniMaxPlayer extends DefaultPlayer {

	private Board board;
	private List<Player> players;

	public MiniMaxPlayer(String code) {
		super(code);
	}

	@Override
	protected Tile makeAdvancedMove(TicTacToe ticTacToe) {
		this.board = ticTacToe.getBoard();
		this.players = ticTacToe.getPlayers();
		Tile result = minimax(2, this);
		return result;
	}

	private Tile minimax(int depth, Player player) {

		List<Tile> nextMoves = generateMoves(board);

		int bestScore = player.equals(this) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		int currentScore;
		Tile bestTile = null;

		if (nextMoves.isEmpty() || depth == 0) {
			bestScore = evaluate(board, player);
		} else {

			for (Tile tile : nextMoves) {

				tile.setPlayer(player);

				currentScore = minimax(depth - 1, TicTacToe.getNextPlayer(player, players)).getScore();

				if (player == this) {
					if (currentScore > bestScore) {
						bestScore = currentScore;
						bestTile = tile;
					}
				} else {
					if (currentScore < bestScore) {
						bestScore = currentScore;
						bestTile = tile;
					}
				}

				tile.setPlayer(Player.NO_PLAYER);

			}
		}

		return new Tile(bestTile, bestScore);
	}

	private List<Tile> generateMoves(Board board) {

		if (board.hasWon()) {
			return new ArrayList<>();
		}

		return board.getFreeTiles();
	}

	public static int evaluate(Board board, Player player) {
		int score = 0;
		score += evaluateLine(0, 0, 0, 1, 0, 2, board, player);
		score += evaluateLine(1, 0, 1, 1, 1, 2, board, player);
		score += evaluateLine(2, 0, 2, 1, 2, 2, board, player);
		score += evaluateLine(0, 0, 1, 0, 2, 0, board, player);
		score += evaluateLine(0, 1, 1, 1, 2, 1, board, player);
		score += evaluateLine(0, 2, 1, 2, 2, 2, board, player);
		score += evaluateLine(0, 0, 1, 1, 2, 2, board, player);
		score += evaluateLine(0, 2, 1, 1, 2, 0, board, player);
		return score;
	}

	public static int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3, Board board, Player player) {
		int score = 0;

		if (board.getTile(row1, col1).getPlayer().equals(player)) {
			score = 1;
		} else if (!board.getTile(row1, col1).getPlayer().equals(Player.NO_PLAYER)) {
			score = -1;
		}

		if (board.getTile(row2, col2).getPlayer().equals(player)) {
			if (score == 1) {
				score = 10;
			} else if (score == -1) {
				return 0;
			} else {
				score = 1;
			}
		} else if (!board.getTile(row2, col2).getPlayer().equals(Player.NO_PLAYER)) {
			if (score == -1) {
				score = -10;
			} else if (score == 1) {
				return 0;
			} else {
				score = -1;
			}
		}

		if (board.getTile(row3, col3).getPlayer().equals(player)) {
			if (score > 0) {
				score *= 10;
			} else if (score < 0) {
				return 0;
			} else {
				score = 1;
			}
		} else if (!board.getTile(row3, col3).getPlayer().equals(Player.NO_PLAYER)) {
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
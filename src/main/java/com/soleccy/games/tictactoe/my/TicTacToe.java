package com.soleccy.games.tictactoe.my;

import java.util.List;

public class TicTacToe {

	public static Integer DEFAULT_DIM = 3;
	private Board board;
	private List<Player> players;
	private Player currentPlayer;

	public void play() {
		if (currentPlayer == null) {
			setCurrentPlayer(getPlayers().get(0));
		}
		do {
			Tile chosenTile = getCurrentPlayer().makeMove(this);
			chosenTile.setPlayer(currentPlayer);
			getBoard().setTile(chosenTile);
			if (getCurrentState().equals(State.WON) || getCurrentState().equals(State.DRAW)) {
				break;
			}
			changePlayer();
		} while (true);

	}

	private void changePlayer() {
		setCurrentPlayer(getNextPlayer(getCurrentPlayer(), getPlayers()));
	}

	public static Player getNextPlayer(Player currentPlayer, List<Player> players) {
		return players.get((players.indexOf(currentPlayer) + 1) % players.size());

	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public State getCurrentState() {
		if (board.hasWon()) {
			return State.WON;
		} else if (board.isDraw()) {
			return State.DRAW;
		}
		return State.PLAYING;
	}

}

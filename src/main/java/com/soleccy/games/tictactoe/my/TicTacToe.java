package com.soleccy.games.tictactoe.my;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TicTacToe implements Runnable {

	public static Integer DEFAULT_DIM = 3;
	private Board board;
	private List<Player> players;
	private Player currentPlayer;
	private State currentState;

	public static void main(String[] args) {
		new TicTacToe().run();
	}

	@Override
	public void run() {
		init();
		play();
	}

	public void init() {
		init(DEFAULT_DIM, new Player("X"), new Player("O"));
	}

	public void init(int dim, Player ...players) {
		board = Board.builder().dim(dim).build();
		this.players = Arrays.asList(players);
		currentPlayer = getNextPlayer(null, this.players);
	}

	public void play() {
		do {
			Tile chosenTile = currentPlayer.makeMove(board);
			board.setTile(chosenTile);
			System.out.println(board);
			currentState = getState(board);
			if (currentState.equals(State.WON) || currentState.equals(State.DRAW)) {
				break;
			}
			currentPlayer = getNextPlayer(currentPlayer, players);

		} while (true);

		if (currentState == State.WON) {
			System.out.println("Player " + currentPlayer + " won! Bye!");
		} else if (currentState == State.DRAW) {
			System.out.println("It's Draw! Bye!");
		}

	}

	private static State getState(Board board) {
		if (board.hasWon()) {
			return State.WON;
		} else if (board.isDraw()) {
			return State.DRAW;
		}
		return State.PLAYING;
	}

	public static Player getNextPlayer(Player currentPlayer, List<Player> players) {
		if (currentPlayer == null) {
			return players.get(new Random().nextInt(players.size()));
		}
		return players.get((players.indexOf(currentPlayer) + 1) % players.size());

	}

	public State getResult() {
		return currentState;
	}
}

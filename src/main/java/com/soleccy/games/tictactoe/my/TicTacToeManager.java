package com.soleccy.games.tictactoe.my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicTacToeManager {
	
	private List<TicTacToe> games;

	public void init(String input) {
		BufferedReader reader = new BufferedReader(new StringReader(input));
		try {
			int noOfGames = Integer.valueOf(reader.readLine());
			games = new ArrayList<>();
			for (int i = 0; i < noOfGames; i++) {
				TicTacToe game = new TicTacToe();
				MiniMaxPlayer xPlayer = new MiniMaxPlayer("X");
				MiniMaxPlayer oPlayer = new MiniMaxPlayer("O");
				game.setPlayers(Arrays.asList(new Player[]{xPlayer, oPlayer}));
				game.setCurrentPlayer(new MiniMaxPlayer(reader.readLine()));
				Board board = new Board(3);
				game.setBoard(board);
				String row1 = reader.readLine();
				board.getTile(0, 0).setPlayer(getPlayer(row1.substring(0, 1)));
				board.getTile(0, 1).setPlayer(getPlayer(row1.substring(1, 2)));
				board.getTile(0, 2).setPlayer(getPlayer(row1.substring(2)));
				String row2 = reader.readLine();
				board.getTile(1, 0).setPlayer(getPlayer(row2.substring(0, 1)));
				board.getTile(1, 1).setPlayer(getPlayer(row2.substring(1, 2)));
				board.getTile(1, 2).setPlayer(getPlayer(row2.substring(2)));
				String row3 = reader.readLine();
				board.getTile(2, 0).setPlayer(getPlayer(row3.substring(0, 1)));
				board.getTile(2, 1).setPlayer(getPlayer(row3.substring(1, 2)));
				board.getTile(2, 2).setPlayer(getPlayer(row3.substring(2)));
				games.add(game);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

	private Player getPlayer(String code) {
		if ("_".equals(code)) {
			return Player.NO_PLAYER;
		}
		return new MiniMaxPlayer(code);
	}

	public void play() {
		for (TicTacToe game : games) {
			game.play();
		}
	}

	public String result() {
		StringBuilder osb = new StringBuilder();
		for (int i = 0; i < games.size(); i++) {
			TicTacToe game = games.get(i);
			osb.append("Case ");
			osb.append(i + 1);
			osb.append(": ");
			if (game.getCurrentState().equals(State.DRAW)) {
				osb.append("Draw\n");
			} else {
				osb.append(game.getCurrentPlayer());
				osb.append(" wins\n");
			}
			
		}
		
		String output = osb.toString();

		return output;
	}

}

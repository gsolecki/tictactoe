package com.soleccy.games.tictactoe.my;

public interface Player {
	
	public static final String NO_PLAYER_CODE = " ";
	public static Player NO_PLAYER = new DefaultPlayer(NO_PLAYER_CODE);

	Tile makeMove(TicTacToe ticTacToe);

	String getCode();

}
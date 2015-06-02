package com.soleccy.games.tictactoe.my;

import java.util.List;
import java.util.Random;

public class Player {

	public static final String NO_PLAYER_CODE = " ";
	public static Player NO_PLAYER = new Player(NO_PLAYER_CODE);

	private String code;

	public Player(String code) {
		this.code = code;
	}

	public Tile makeMove(Board board) {
		List<Tile> freeTiles = board.getFreeTiles();
		Tile tile = freeTiles.get(new Random().nextInt(freeTiles.size()));
		tile.setPlayer(this);
		return tile;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (code == null ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Player other = (Player) obj;
		if (code == null) {
			if (other.code != null) {
				return false;
			}
		} else if (!code.equals(other.code)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return code;
	}

}
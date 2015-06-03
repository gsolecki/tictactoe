package com.soleccy.games.tictactoe.my;

import java.util.List;
import java.util.Random;

public class DefaultPlayer implements Player {

	private String code;

	public DefaultPlayer(String code) {
		this.code = code;
	}

	@Override
	public Tile makeMove(TicTacToe ticTacToe) {

		Tile tile = makeAdvancedMove(ticTacToe);

		if (tile == null) {
			List<Tile> freeTiles = ticTacToe.getBoard().getFreeTiles();
			tile = freeTiles.get(new Random().nextInt(freeTiles.size()));
		}

		return tile;
	}

	protected Tile makeAdvancedMove(TicTacToe ticTacToe) {
		return null;
	}

	@Override
	public String getCode() {
		return code;
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
		DefaultPlayer other = (DefaultPlayer) obj;
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
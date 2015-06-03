package com.soleccy.games.tictactoe.my;

public class Tile {

	private int x;
	private int y;
	private int score;
	private Player palyer;

	public boolean isEmpty() {
		return Player.NO_PLAYER.equals(getPlayer());
	}

	public Tile(Tile tile, int score) {
		if (tile != null) {
			this.x = tile.getX();
			this.y = tile.getY();
		}
		setScore(score);
	}

	public Tile(int x, int y) {
		this(x, y, Player.NO_PLAYER);
	}

	public Tile(int x, int y, Player palyer) {
		super();
		setX(x);
		setY(y);
		this.palyer = palyer;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Player getPlayer() {
		return palyer;
	}

	public void setPlayer(Player miniMaxPlayer) {
		this.palyer = miniMaxPlayer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		Tile other = (Tile) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("tile:{x:");
		builder.append(x);
		builder.append(", y:");
		builder.append(y);
		builder.append(", player:\"");
		builder.append(palyer);
		builder.append("\"}");
		return builder.toString();
	}

}
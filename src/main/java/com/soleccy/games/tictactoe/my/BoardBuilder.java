package com.soleccy.games.tictactoe.my;

public class BoardBuilder {

	private int dim;

	public Board build() {
		return new Board(dim);
	}

	public BoardBuilder dim(int dim) {
		this.dim = dim;
		return this;
	}

}
package com.soleccy.games.tictactoe.my;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Board {

	private int dim;
	private List<Tile> tiles;
	private Tile lastTile;
	private List<String> winningPatterns;

	public Board() {
	}

	public boolean isDraw() {
		return !getTiles().stream().anyMatch(tile -> tile.isEmpty());
	}

	public boolean hasWon() {

		Player lastPlayer = lastTile.getPlayer();
		String state = serialize(getTiles(), lastPlayer);
		return contains(state, winningPatterns);
	}

	public static boolean contains(String state, List<String> winningPatterns) {
		for (String pattern : winningPatterns) {
			if (matches(state, pattern)) {
				return true;
			};
		}

		return false;
	}

	private static boolean matches(String state, String pattern) {
		int pos = 0;
		do {
			pos = pattern.indexOf("|", pos);
			if (pos >= 0 && state.charAt(pos) != '|') {
				return false;
			}
			pos++;
		} while (pos > 0);
		return true;
	}

	public static String serialize(List<Tile> board, Player lastPlayer) {
		StringBuilder output = new StringBuilder();
		board.stream().forEachOrdered(tile -> output.append(tile.getPlayer().equals(lastPlayer)? "|": "-"));
		return output.toString();
	}

	public Board(int dim) {
		setDim(dim);
		setTiles(new ArrayList<>());
		for (int x = 0; x < getDim(); x++) {
			for (int y = 0; y < getDim(); y++) {
				getTiles().add(new Tile(x, y));
			}
		}
	}

	public static BoardBuilder builder() {
		return new BoardBuilder();
	}

	public List<Tile> getFreeTiles() {
		return getTiles().stream().filter(tile -> tile.isEmpty()).collect(Collectors.toList());
	}

	public int getDim() {
		return dim;
	}

	public void setDim(int dim) {
		this.dim = dim;
		winningPatterns = Arrays.asList("|||------", "---|||---", "------|||", "|--|--|--", "-|--|--|-", "--|--|--|", "|---|---|", "--|-|-|--");
	}

	public void setTile(Tile newTile) {
		lastTile = getTiles().stream().filter(tile -> tile.equals(newTile)).findFirst().get();
		lastTile.setPlayer(newTile.getPlayer());
	}

	public void print(StringBuilder sb) {

		for (int i = 0; i < getTiles().size(); i++) {
			if (i == 0 || i % dim == 0) {
				printVertical(sb, i);
			}

			Tile tile = getTiles().get(i);
			sb.append("|");
			sb.append(" ");
			sb.append(tile.getPlayer() != null ? String.valueOf(tile.getPlayer()) : " ");
			sb.append(" ");
		}
		printVertical(sb, -1);

	}

	private void printVertical(StringBuilder sb, int i) {
		sb.append(i == 0 ? "" : "|");
		sb.append(System.getProperty("line.separator"));
		sb.append(" ");
		for (int j = 0; j < getDim(); j++) {
			sb.append("----");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(System.getProperty("line.separator"));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		print(sb);
		return sb.toString();
	}

	public List<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
	}


}
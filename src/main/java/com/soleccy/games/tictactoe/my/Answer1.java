package com.soleccy.games.tictactoe.my;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Answer1 {

    public static void main(String[] args) {
        Answer1 game = new Answer1();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(new File(args[0])));
            game.init(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private List<TicTacToe> games;

    private Player getPlayer(String code) {
        if ("_".equals(code)) {
            return Player.NO_PLAYER;
        }
        return new MiniMaxPlayer(code);
    }

    public void init(BufferedReader reader) {
        try {
            int noOfGames = Integer.valueOf(reader.readLine());
            games = new ArrayList<TicTacToe>();
            for (int i = 0; i < noOfGames; i++) {
                TicTacToe game = new TicTacToe();
                MiniMaxPlayer xPlayer = new MiniMaxPlayer("X");
                MiniMaxPlayer oPlayer = new MiniMaxPlayer("O");
                game.setPlayers(Arrays.asList(new Player[] { xPlayer, oPlayer }));
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

class TicTacToe {

    public static Player getNextPlayer(Player currentPlayer, List<Player> players) {
        return players.get((players.indexOf(currentPlayer) + 1) % players.size());

    }

    public static Integer DEFAULT_DIM = 3;
    private Board board;
    private List<Player> players;

    private Player currentPlayer;

    private void changePlayer() {
        setCurrentPlayer(getNextPlayer(getCurrentPlayer(), getPlayers()));
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public State getCurrentState() {
        if (board.hasWon()) {
            return State.WON;
        } else if (board.isDraw()) {
            return State.DRAW;
        }
        return State.PLAYING;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void play() {
        if (currentPlayer == null) {
            setCurrentPlayer(getPlayers().get(0));
        }
        do {
            Tile chosenTile = getCurrentPlayer().makeMove(this);
            chosenTile.setPlayer(currentPlayer);
            board.setTile(chosenTile);
            if (getCurrentState().equals(State.WON) || getCurrentState().equals(State.DRAW)) {
                break;
            }
            changePlayer();
        } while (true);

    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

}

enum State {
    PLAYING, DRAW, WON
}

interface Player {

    public static final String NO_PLAYER_CODE = " ";
    public static Player NO_PLAYER = new DefaultPlayer(NO_PLAYER_CODE);

    Tile makeMove(TicTacToe ticTacToe);

    String getCode();

}

class DefaultPlayer implements Player {

    private String code;

    public DefaultPlayer(String code) {
        this.code = code;
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

    protected Tile makeAdvancedMove(TicTacToe ticTacToe) {
        return null;
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

    @Override
    public String toString() {
        return code;
    }

}

class MiniMaxPlayer extends DefaultPlayer {

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

    private Board board;

    private List<Player> players;

    public MiniMaxPlayer(String code) {
        super(code);
    }

    private List<Tile> generateMoves(Board board) {

        if (board.hasWon()) {
            return new ArrayList<Tile>();
        }

        return board.getFreeTiles();
    }

    @Override
    protected Tile makeAdvancedMove(TicTacToe ticTacToe) {
        board = ticTacToe.getBoard();
        players = ticTacToe.getPlayers();
        Tile result = minimax(3, this);
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
                board.setTile(tile);

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

}

class Board {

    private int dim;

    private List<Tile> tiles;

    private Tile lastTile;

    private List<String> winningPatterns;

    public Board(int dim) {
        setDim(dim);
        setTiles(new ArrayList<Tile>());
        for (int x = 0; x < getDim(); x++) {
            for (int y = 0; y < getDim(); y++) {
                getTiles().add(new Tile(x, y));
            }
        }
    }

    public int getDim() {
        return dim;
    }

    public List<Tile> getFreeTiles() {

        List<Tile> result = new ArrayList<Tile>();

        for (Tile tile : tiles) {
            if (tile.isEmpty()) {
                result.add(tile);
            }
        }

        return result;
    }

    public Tile getTile(int row1, int col1) {
        for (Tile tile : tiles) {
            if (tile.equals(new Tile(row1, col1))) {
                return tile;
            }
        }
        return null;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public boolean hasWon() {
        if (lastTile == null) {
            return false;
        }
        Player lastPlayer = lastTile.getPlayer();
        String state = serialize(getTiles(), lastPlayer);
        return contains(state, winningPatterns);
    }

    public boolean isDraw() {
        return !(getFreeTiles().size() > 0);
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

    public void setDim(int dim) {
        this.dim = dim;
        winningPatterns = Arrays.asList("|||------", "---|||---", "------|||", "|--|--|--", "-|--|--|-", "--|--|--|", "|---|---|", "--|-|-|--");
    }

    public void setTile(Tile newTile) {
        for (Tile tile : tiles) {
            if (tile.equals(newTile)) {
                lastTile = tile;
                break;
            }
        }
		lastTile.setPlayer(newTile.getPlayer());
	}

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        print(sb);
        return sb.toString();
    }

    public static boolean contains(String state, List<String> winningPatterns) {
        for (String pattern : winningPatterns) {
            if (matches(state, pattern)) {
                return true;
            }
        }

        return false;
    }

    public static boolean matches(String state, String pattern) {
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

    public static String serialize(List<Tile> tiles, Player lastPlayer) {
		StringBuilder output = new StringBuilder();
		for (Tile tile : tiles) {
		    output.append(tile.getPlayer().equals(lastPlayer) ? "|" : "-");
        }
		return output.toString();
	}
}

class Tile {

    private int x;
    private int y;
    private int score;
    private Player palyer;

    public Tile(int x, int y) {
        this(x, y, Player.NO_PLAYER);
    }

    public Tile(int x, int y, Player palyer) {
        super();
        setX(x);
        setY(y);
        this.palyer = palyer;
    }

    public Tile(Tile tile, int score) {
        if (tile != null) {
            x = tile.getX();
            y = tile.getY();
        }
        setScore(score);
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

    public Player getPlayer() {
        return palyer;
    }

    public int getScore() {
        return score;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    public boolean isEmpty() {
        return Player.NO_PLAYER.equals(getPlayer());
    }

    public void setPlayer(Player miniMaxPlayer) {
        palyer = miniMaxPlayer;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
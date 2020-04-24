package amoba;

import java.util.HashSet;

public class Board {

    static final int BOARD_WIDTH = 3;
    static final int BOARD_HEIGHT = 3;

    public enum State {blank, blue, red}
    private State[][] board;
    private State playersTurn;
    private State winner;
    private HashSet<Integer> movesAvailable;
    private int moveCount;
    private boolean gameOver;

    Board() {
        board = new State[BOARD_HEIGHT][BOARD_WIDTH];
        movesAvailable = new HashSet<>();
        reset();
    }

    private void init() {
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                board[row][col] = State.blank;
            }
        }

        movesAvailable.clear();
        for (int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH; i++) {
            movesAvailable.add(i);
        }
    }

    void reset() { 
        moveCount = 0;
        gameOver = false;
        playersTurn = State.blue;
        winner = State.blank;
        init();
    }

    public boolean move (int index) {
        return move(index% BOARD_WIDTH, index/ BOARD_WIDTH);
    }

    private boolean move (int x, int y) {

        if (gameOver) {
            throw new IllegalStateException("Game is over. No moves can be played.");
        }

        if (board[y][x] == State.blank) {
            board[y][x] = playersTurn;
        } else {
            return false;
        }

        moveCount++;
        movesAvailable.remove(y * BOARD_HEIGHT + x);

        // The game is a draw.
        if (moveCount == BOARD_HEIGHT * BOARD_WIDTH) {
            winner = State.blank;
            gameOver = true;
        }

        // Check for a winner.
        checkRow(y);
        checkColumn(x);
        checkDiagonalFromTopLeft(x, y);
        checkDiagonalFromTopRight(x, y);

        playersTurn = (playersTurn == State.blue) ? State.red : State.blue;
        return true;
    }

    public boolean isGameOver () {
        return gameOver;
    }

    State[][] toArray () {
        return board.clone();
    }

    public State getTurn () {
        return playersTurn;
    }

    public State getWinner () {
        if (!gameOver) {
            throw new IllegalStateException("TicTacToe is not over yet.");
        }
        return winner;
    }

    public HashSet<Integer> getAvailableMoves () {
        return movesAvailable;
    }

    private void checkRow (int row) {
        for (int i = 1; i < BOARD_HEIGHT; i++) {
            if (board[row][i] != board[row][i-1]) {
                break;
            }
            if (i == BOARD_HEIGHT -1) {
                winner = playersTurn;
                gameOver = true;
            }
        }
    }

    private void checkColumn (int column) {
        for (int i = 1; i < BOARD_WIDTH; i++) {
            if (board[i][column] != board[i-1][column]) {
                break;
            }
            if (i == BOARD_WIDTH -1) {
                winner = playersTurn;
                gameOver = true;
            }
        }
    }

    private void checkDiagonalFromTopLeft (int x, int y) {
        if (x == y) {
            for (int i = 1; i < BOARD_HEIGHT; i++) {
                    if (board[i][i] != board[i-1][i-1]) {
                        break;
                    }
                    if (i == BOARD_WIDTH -1) {
                        winner = playersTurn;
                        gameOver = true;
                    }
            }
        }
    }

    private void checkDiagonalFromTopRight (int x, int y) {
        if (BOARD_WIDTH -1-x == y) {
            for (int i = 1; i < BOARD_WIDTH; i++) {
                    if (board[BOARD_WIDTH -1-i][i] != board[BOARD_WIDTH -i][i-1]) {
                        break;
                    }
                    if (i == BOARD_WIDTH -1) {
                        winner = playersTurn;
                        gameOver = true;
                    }
                }
            }
    }

    public Board getDeepCopy () {
        Board board             = new Board();

        for (int i = 0; i < board.board.length; i++) {
            board.board[i] = this.board[i].clone();
        }

        board.playersTurn       = this.playersTurn;
        board.winner            = this.winner;
        board.movesAvailable    = new HashSet<>();
        board.movesAvailable.addAll(this.movesAvailable);
        board.moveCount         = this.moveCount;
        board.gameOver          = this.gameOver;
        return board;
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {

                if (board[y][x] == State.blank) {
                    sb.append("-");
                } else {
                    sb.append(board[y][x].name());
                }
                sb.append(" ");

            }
            if (y != BOARD_HEIGHT -1) {
                sb.append("\n");
            }
        }

        return new String(sb);
    }
}
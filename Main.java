package amoba;

import java.util.Scanner;

public class Main {

    private Board board;
    private Scanner sc = new Scanner(System.in);

    private Main() {
        board = new Board();
    }

    private void play() {

        System.out.println("Starting a new game.");

        while (true) {
            printGameStatus();
            playMove();

            if (board.isGameOver()) {
                printWinner();
            }
        }
    }

    private void playMove () {
        if (board.getTurn() == Board.State.blue) {
            getPlayerMove();
        } else {
            MiniMax.run(board.getTurn(),board,10);
        }
    }

    private void printGameStatus () {
        System.out.println("\n" + board + "\n");
        System.out.println(board.getTurn().name() + "'s turn.");
    }

    private void getPlayerMove () {
        System.out.print("Index of move: ");

        int move = sc.nextInt();

        if (move < 0 || move >= Board.BOARD_WIDTH* Board.BOARD_WIDTH) {
            System.out.println("\nInvalid move.");
            System.out.println("\nThe index of the move must be between 0 and "
                    + (Board.BOARD_WIDTH * Board.BOARD_WIDTH - 1) + ", inclusive.");
        } else if (!board.move(move)) {
            System.out.println("\nInvalid move.");
            System.out.println("\nThe selected index must be blank.");
        }
    }

    private void printWinner () {
        Board.State winner = board.getWinner();

        System.out.println("\n" + board + "\n");

        if (winner == Board.State.blank) {
            System.out.println("The TicTacToe is a Draw.");
        } else {
            System.out.println("Player " + winner.toString() + " wins!");
        }
    }


    public static void main(String[] args) {
        Main ticTacToe = new Main();
        ticTacToe.play();
    }
}
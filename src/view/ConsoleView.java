package view;

import exception.HexapawnException;
import model.GameArea;
import model.GameMove;
import model.Pawn;

import java.util.Scanner;

public class ConsoleView implements View {

    private Scanner sc;
    private GameArea ga;

    public ConsoleView() {
        this.sc = new Scanner(System.in);
    }

    public ConsoleView(GameArea ga) {
        this.ga = ga;
    }

    @Override
    public void displayPreGameScreen() throws HexapawnException {
        System.out.println("Welcome to Hexapawn!");
        createBoard();
    }

    @Override
    public void displayGame() {
        displayHeader(ga);
        displayBoard(ga);
        System.out.println("Player" + ga.getPlayer() + "'s turn!");
    }

    @Override
    public void displayTheEndOfGame() {
        int player = ga.getPlayer();
        System.out.println("The game is over!");
        System.out.println("Player " + player + " has lost the game.");
        player = (player == 1) ? 2 : 1;
        System.out.println("Player " + player + " has won the game.");
    }


    @Override
    public GameMove getPlayersInput() {
        String input = sc.next();

        if (isValidInput(input)) {
            return new GameMove(input);
        }
        return null;
    }

    @Override
    public boolean isValidInput(String input) {
        boolean valid = true;
        if (input.equals("ERROR")) {
            System.err.println("String input = \"ERROR\"!!!");
            System.err.println("Input has default value.");
            valid = false;
        }

        if (input.equals("q")) {
            System.exit(0);
        }

        if (input.chars().filter(ch -> ch == ':').count() != 3) {
            valid = false;
        }

        if (!valid) {
            System.out.printf("Invalid input!\nYour input: \"%s\" \nInput should be like: 2:0:1:0 \n%n", input);
        }

        return valid;
    }

    @Override
    public void makeTheMove(GameArea ga, GameMove gm) {
        //TODO: This should be changed
        ga.makeAMove(gm);
    }

    @Override
    public void displayInvalidMoveError(GameMove gm) {
        System.out.println("Invalid move!\nYour move: " + gm.getGameMoveInString());
    }

    private void displayHeader(GameArea ga) {
        System.out.println("\n****************************************************");
        String header = "\\\\\\ ";

        for (int i = 0; i < ga.getColumnCount(); i++) {
            header += i + " - ";
        }
        header = header.substring(0, header.length() - 3);
        System.out.println(header);
    }

    private void displayBoard(GameArea ga) {
        Pawn[][] board = ga.getBoard();
        String boardRow;

        for (int i = 0; i < ga.getRowCount(); i++) {
            boardRow = "" + i + "-> ";

            for (int j = 0; j < ga.getColumnCount(); j++) {
                Pawn pawn = board[i][j];
                if (pawn == null) {
                    boardRow += "0 | ";
                } else {
                    boardRow += pawn.getPlayer() + " | ";
                }
            }
            boardRow = boardRow.substring(0, boardRow.length() - 3);
            System.out.println(boardRow);
        }
    }

    private void createBoard() throws HexapawnException {
        int row = -1;
        int column = -1;
        String input;

        System.out.println("How many rows would should the board consist of? (must be at least 2)");

        do {
            System.out.println("Number of rows: ");
            input = sc.next();
            try {
                row = inputChecker(input);
            } catch (HexapawnException e) {
                System.out.println(e.getMessage());
            }
        } while (row == -1);

        System.out.println("There will be " + row + " rows!");

        System.out.println("How many columns should the board consist of? (must be at least 1)");

        do {
            System.out.println("Number of columns: ");
            input = sc.next();
            try {
                column = inputChecker(input);
            } catch (HexapawnException e) {
                System.out.println(e.getMessage());
            }
        } while (column == -1);

        System.out.println("There will be " + column + " column" + ((column > 1) ? "s!" : "!"));

        ga = new GameArea(row, column);
        if (ga.getRowCount() == 2 && ga.getColumnCount() == 1) {
            displayGame();
            displayTheEndOfGame();
            System.exit(0);
        }
    }

    private int inputChecker(String input) throws HexapawnException {
        int value;
        try {
            value = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new HexapawnException("Incorrect value!");
        }
        return value;
    }

    public GameArea getGameArea() {
        return ga;
    }

    @Override
    public void setGameArea(GameArea gameArea) {
        this.ga = gameArea;
    }

    @Override
    public void setGameArea(int row, int column) throws HexapawnException {
        this.ga = new GameArea(row, column);
    }
}

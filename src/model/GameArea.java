package model;

import exception.HexapawnException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class GameArea {

    enum FileType {
        POSITION,
        WEIGHT
    }

    private int player;
    private int rowCount;
    private int columnCount;
    private Pawn[][] board;

    private List<Pawn> players1Pieces;
    private List<Pawn> players2Pieces;
    private List<String> positionList;
    private List<String> weightList;

    public GameArea(int rowCount, int columnCount) throws HexapawnException {
        this.player = 1;
        setRowCount(rowCount);
        setColumnCount(columnCount);
        this.board = new Pawn[rowCount][columnCount];
        this.players1Pieces = new ArrayList<>();
        this.players2Pieces = new ArrayList<>();

        for (int i = 0; i < columnCount; i++) {
            Pawn p1 = new Pawn(rowCount - 1, i, 1);
            Pawn p2 = new Pawn(0, i, 2);

            board[0][i] = p2;
            board[rowCount - 1][i] = p1;

            this.players1Pieces.add(p1);
            this.players2Pieces.add(p2);
        }
    }

    public GameArea(GameArea ga) {
        this.player = ga.getPlayer();
        this.rowCount = ga.getRowCount();
        this.columnCount = ga.getColumnCount();
        this.board = new Pawn[rowCount][columnCount];
        this.players1Pieces = new ArrayList<>();
        this.players2Pieces = new ArrayList<>();

        Pawn[][] board = ga.getBoard();

        for (int i = 0; i < ga.getBoardSize(); i++) {
            int row = i / ga.getColumnCount();
            int column = i % ga.getColumnCount();
            Pawn pawn = board[row][column];

            if (pawn != null) {
                pawn = new Pawn(pawn);

                if (pawn.getPlayer() == 1) {
                    this.players1Pieces.add(pawn);
                } else {
                    this.players2Pieces.add(pawn);
                }
            }
            this.board[row][column] = pawn;
        }

    }

    public List<Pawn> getPlayers1Pieces() {
        return players1Pieces;
    }

    public List<Pawn> getPlayers2Pieces() {
        return players2Pieces;
    }

    public int getBoardSize() {
        return rowCount * columnCount;
    }

    public int getPlayer() {
        return player;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Pawn[][] getBoard() {
        return board;
    }

    public List<String> getPositionList() {
        return positionList;
    }

    public List<String> getWeightList() {
        return weightList;
    }

    private void setRowCount(int rowCount) throws HexapawnException {
        if (rowCount < 0) {
            throw new HexapawnException("The number of rows must be at least 2!");
        }
        this.rowCount = rowCount;
    }

    private void setColumnCount(int columnCount) throws HexapawnException {
        if (columnCount < 1) {
            throw new HexapawnException("The number of columns must be at least 1!");
        }
        this.columnCount = columnCount;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void setBoard(int row, int column, Pawn value) {
        board[row][column] = value;
    }

    public void addToPositionList(String str) {
        this.positionList.add(str);
    }

    public void addToWeightList(String str) {
        this.weightList.add(str);
    }

    public void updatePlayer() {
        if (this.getPlayer() == 1) {
            this.setPlayer(2);
        } else {
            this.setPlayer(1);
        }
    }

    public void removeTakenPieceFromPlayersList(Pawn pawn, int player) {

        if (player == 1) {
            players1Pieces.remove(pawn);
        } else {
            players2Pieces.remove(pawn);
        }
    }

    public void makeAMove(GameMove gm) {
        Pawn pawnToMove = board[gm.getRowFrom()][gm.getColumnFrom()];
        Pawn pawnAtTarget = board[gm.getRowTo()][gm.getColumnTo()];

        pawnToMove.updatePawnCoordinates(gm);

        setBoard(gm.getRowTo(), gm.getColumnTo(), pawnToMove);
        setBoard(gm.getRowFrom(), gm.getColumnFrom(), null);

        if (pawnAtTarget != null) {
            removeTakenPieceFromPlayersList(pawnAtTarget, pawnAtTarget.getPlayer());
        }
    }

    public List<String> getFileLines(int lineCount, int columnCount, FileType fileType) {
        List<String> fileLines;
        String path;

        File file = new File("");

        switch (fileType) {
            case POSITION:
                path = String.format("Position_%d_%d.txt", lineCount, columnCount);
                file = new File(path);
                break;
            case WEIGHT:
                path = String.format("Weight_%d_%d.txt", lineCount, columnCount);
                file = new File(path);
                break;
        }

        try {
            if (!file.exists()) {
                file.createNewFile();
                return new ArrayList<>();
            }
        } catch (IOException e) {
            System.err.println("Error in GameArea.createFiles ~ creating file.\n" + e);
        }

        try {
            fileLines = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            System.err.println("Error in GameArea.createFiles ~ reading file's lines.\n" + e);
            fileLines = new ArrayList<>();
        }

        return fileLines;
    }
}

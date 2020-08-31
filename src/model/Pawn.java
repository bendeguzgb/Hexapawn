package model;

import java.util.Objects;

public class Pawn {

    private int player;
    private int rowIndex;
    private int columnIndex;
    private int pieceValue;


    public Pawn(int rowIndex, int columnIndex, int player) {
        this.player = player;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.pieceValue = 1;
    }

    public Pawn(Pawn pawn) {
        this.player = pawn.getPlayer();
        this.rowIndex = pawn.getRowIndex();
        this.columnIndex = pawn.getColumnIndex();
        this.pieceValue = pawn.getPieceValue();
    }

    public int getPlayer() {
        return player;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int getPieceValue() {
        return pieceValue;
    }

    public void setPieceValue(int pieceValue) {
        this.pieceValue = pieceValue;
    }

    public void updatePawnCoordinates(GameMove gm) {
        this.rowIndex = gm.getRowTo();
        this.columnIndex = gm.getColumnTo();

    }

    public void updatePawnCoordinates(int rowTo, int columnTo) {
        this.rowIndex = rowTo;
        this.columnIndex = columnTo;


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pawn pawn = (Pawn) o;
        return getPlayer() == pawn.getPlayer() &&
                getRowIndex() == pawn.getRowIndex() &&
                getColumnIndex() == pawn.getColumnIndex();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlayer(), getRowIndex(), getColumnIndex());
    }
}

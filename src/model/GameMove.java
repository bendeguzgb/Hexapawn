package model;

public class GameMove {
    private Integer rowFrom;
    private Integer columnFrom;
    private Integer rowTo;
    private Integer columnTo;

    public GameMove() {
    }

    public GameMove(int rowFrom, int columnFrom, int rowTo, int columnTo) {
        this.rowFrom = rowFrom;
        this.columnFrom = columnFrom;
        this.rowTo = rowTo;
        this.columnTo = columnTo;
    }

    public GameMove(GameMove gm) {
        this.rowFrom = gm.rowFrom;
        this.columnFrom = gm.columnFrom;
        this.rowTo = gm.rowTo;
        this.columnTo = gm.columnTo;
    }

    public GameMove(String input) {
        this.setRowFrom(changeInput(input, 0));
        this.setColumnFrom(changeInput(input, 1));
        this.setRowTo(changeInput(input, 2));
        this.setColumnTo(changeInput(input, 3));
    }

    public int getRowFrom() {
        return rowFrom;
    }

    public void setRowFrom(int rowFrom) {
        this.rowFrom = rowFrom;
    }

    public int getRowTo() {
        return rowTo;
    }

    public void setRowTo(int rowTo) {
        this.rowTo = rowTo;
    }

    public int getColumnFrom() {
        return columnFrom;
    }

    public void setColumnFrom(int columnFrom) {
        this.columnFrom = columnFrom;
    }

    public int getColumnTo() {
        return columnTo;
    }

    public void setColumnTo(int columnTo) {
        this.columnTo = columnTo;
    }

    public String getGameMoveInString() {
        return getRowFrom() + ":" + getColumnFrom() + ":" + getRowTo() + ":" + getColumnTo();
    }

    public void setMoveValues(String input) {
        this.setRowFrom(changeInput(input, 0));
        this.setColumnFrom(changeInput(input, 1));
        this.setRowTo(changeInput(input, 2));
        this.setColumnTo(changeInput(input, 3));
    }

    public int changeInput(String input, int position) {
        String[] s = input.split(":");
        return Integer.parseInt(s[position]);
    }


}

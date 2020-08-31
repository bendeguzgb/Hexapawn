package view;

import exception.HexapawnException;
import model.GameArea;
import model.GameMove;

public interface View {

    void displayPreGameScreen() throws HexapawnException;
    void displayGame() throws HexapawnException;
    void displayTheEndOfGame();
    void displayInvalidMoveError(GameMove gameMove);
    boolean isValidInput(String input);
    void makeTheMove(GameArea ga, GameMove gm);
    GameMove getPlayersInput();
    GameArea getGameArea();
    void setGameArea(GameArea gameArea);
    void setGameArea(int row, int column) throws HexapawnException;


}

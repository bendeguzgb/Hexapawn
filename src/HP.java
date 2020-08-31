import exception.HexapawnException;
import model.GameMode;
import sevice.manager.GameManager;
import view.ConsoleView;

public class HP {
    public static void main(String[] args) throws HexapawnException {
        GameManager gameManager = new GameManager();
        int row = 3;
        int column = 2;
        int count = 3;

        gameManager.testSetupNTimes(new ConsoleView(), row, column, count);
//        gameManager.game(new ConsoleView(), GameMode.PvP);
    }
}

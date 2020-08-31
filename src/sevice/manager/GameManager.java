package sevice.manager;

import exception.HexapawnException;
import model.GameArea;
import model.GameMode;
import model.GameMove;
import sevice.PositionService;
import sevice.algorithm.AlgorithmService;
import sevice.algorithm.MinimaxAlg;
import sevice.algorithm.RandomMoveStrategy;
import sevice.rules.RuleService;
import view.ConsoleView;
import view.View;

public class GameManager {
    private RuleService ruleService;
    private AlgorithmService algorithmService;

    public GameManager() {
        ruleService = RuleService.getInstance();
        algorithmService = new AlgorithmService();
    }

    public void game(View view, GameMode gameMode) throws HexapawnException {
        view.displayPreGameScreen();

        GameArea ga = view.getGameArea();
        GameMove gm = new GameMove();

        do {
            switch (gameMode) {
                //game mode: player vs player
                case PvP:
                    view.displayGame();
                    gm = view.getPlayersInput();
                    break;

                //game mode: player vs random moves
                case PvR:
                    if (ga.getPlayer() == 1) {
                        view.displayGame();
                        gm = view.getPlayersInput();
                    } else {
                        gm = algorithmService.randomMoveStrategy(ga);
                    }
                    break;

                case PvAI:
                    if (ga.getPlayer() == 1) {
                        view.displayGame();
                        gm = view.getPlayersInput();
                    } else {
                        gm = algorithmService.minimaxAlgorithm(ga);
                    }
                    break;
            }

            if (gm != null) {
                if (ruleService.isLegalMove(ga, gm)) {
                    view.makeTheMove(ga, gm);
                    ga.updatePlayer();
                } else {
                    view.displayInvalidMoveError(gm);
                }
            }
        } while (!ruleService.isGameOver(ga));

        view.displayTheEndOfGame();
    }


    public void testSetupNTimes(ConsoleView view, int row, int column, int count) throws HexapawnException {
        int player1wins = 0;
        int player2wins = 0;

        boolean DISPLAY_GAME = true;
        GameArea ga;
        GameMove gm;

        if (DISPLAY_GAME) {
//            view.displayPreGameScreen();
        }

        for (int i = 0; i < count; i++) {
            ga = new GameArea(row, column);
            view.setGameArea(ga);

            if (DISPLAY_GAME) {
                view.displayGame();
            }

            do {
//                gm = algorithmService.randomMoveStrategy(ga);
                gm = algorithmService.minimaxAlgorithm(ga);
                view.makeTheMove(ga, gm);
                ga.updatePlayer();

                if (DISPLAY_GAME) {
                    view.displayGame();
                }

            } while (!ruleService.isGameOver(ga));

            if (ga.getPlayer() == 1) {
                player2wins += 1;
            } else {
                player1wins += 1;
            }
        }

        System.out.println("player1wins = " + player1wins);
        System.out.println("player2wins = " + player2wins);
    }

}

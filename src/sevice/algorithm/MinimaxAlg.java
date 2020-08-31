package sevice.algorithm;


import model.GameArea;
import model.GameMove;
import sevice.PositionService;
import sevice.rules.RuleService;

import java.util.List;

public class MinimaxAlg {

    private RuleService ruleService;
    private PositionService positionService;

    public MinimaxAlg() {
        this.ruleService = RuleService.getInstance();
        this.positionService = new PositionService();
    }

    public GameMove giveMeAMoveByMinimax(GameArea ga) {
        int player = ga.getPlayer();
        int bestScore = (player == 1) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        List<GameMove> moves = positionService.getLegalMoves(ga);
        GameMove bestGM = null;
        GameArea gaCopy;

        for (GameMove gm : moves) {
            gaCopy = new GameArea(ga);
            gaCopy.makeAMove(gm);

            int score = minimax(ga);

            if (player == 1) {
                if (score > bestScore) {
                    bestScore = score;
                    bestGM = gm;
                }
            } else {
                if (score < bestScore) {
                    bestScore = score;
                    bestGM = gm;
                }
            }

        }

        return bestGM;
    }


    private int minimax(GameArea ga) {
        int player = ga.getPlayer();

        if (ruleService.isGameOver(ga)) {
            return (player == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }

        int bestScore = (player == 1) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        GameArea gaCopy;
        List<GameMove> moves = positionService.getLegalMoves(ga);


        for (GameMove gm : moves) {
            gaCopy = new GameArea(ga);
            gaCopy.makeAMove(gm);
            int score =  minimax(gaCopy);
            bestScore = (player == 1) ? Integer.max(score, bestScore) : Integer.min(score, bestScore);
        }

        return bestScore;
    }
}

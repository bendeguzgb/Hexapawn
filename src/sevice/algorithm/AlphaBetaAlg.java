package sevice.algorithm;

import model.GameArea;
import model.GameMove;
import sevice.PositionService;
import sevice.rules.RuleService;

import java.util.List;

public class AlphaBetaAlg {

    private RuleService ruleService;
    private PositionService positionService;

    public AlphaBetaAlg() {
        this.ruleService = RuleService.getInstance();
        this.positionService = new PositionService();
    }

    public GameMove giveMeAMoveByAlphaBeta(GameArea ga) {
        int player = ga.getPlayer();
        int bestScore = (player == 1) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        List<GameMove> moves = positionService.getLegalMoves(ga);
        GameMove bestGM = null;
        GameArea gaCopy;

        for (GameMove gm : moves) {
            gaCopy = new GameArea(ga);
            gaCopy.makeAMove(gm);

            int score = alphaBetaPruning(ga, Integer.MIN_VALUE, Integer.MAX_VALUE);

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


    private int alphaBetaPruning(GameArea ga, int alpha, int beta) {
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
            //TODO: finish this

//              int score = alphaBetaPruning(gaCopy);
//            bestScore = (player == 1) ? Integer.max(score, bestScore) : Integer.min(score, bestScore);
        }

        //***************************************************

        if (player == 1) {
            bestScore = Integer.MIN_VALUE;

            for (GameMove gm : moves) {
                int score = alphaBetaPruning(ga, alpha, beta);
            }
        }


        return bestScore;
    }
}

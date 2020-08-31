package sevice.algorithm;

import model.GameArea;
import model.GameMove;

public class AlgorithmService {

    private AlwaysTakeStrategy alwaysTakeStrategy;
    private MinimaxAlg minimaxAlg;
    private RandomMoveStrategy randomMoveStrategy;
    private AlphaBetaAlg alphaBetaAlg;

    public AlgorithmService() {
        alwaysTakeStrategy = new AlwaysTakeStrategy();
        minimaxAlg = new MinimaxAlg();
        randomMoveStrategy = new RandomMoveStrategy();
        alphaBetaAlg = new AlphaBetaAlg();
    }

    public GameMove alwaysTake(GameArea ga) {
        return alwaysTakeStrategy.getMoveByAlwaysTake(ga);
    }

    public GameMove minimaxAlgorithm(GameArea ga) {
        return minimaxAlg.giveMeAMoveByMinimax(ga);
    }

    public GameMove randomMoveStrategy(GameArea ga) {
        return randomMoveStrategy.giveMeARandomMove(ga);
    }

    public GameMove alphaBetaAlg(GameArea ga) {
        return alphaBetaAlg.giveMeAMoveByAlphaBeta(ga);
    }

}

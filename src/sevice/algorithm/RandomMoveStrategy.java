package sevice.algorithm;

import model.GameArea;
import model.GameMove;
import sevice.PositionService;

import java.util.List;
import java.util.Random;

public class RandomMoveStrategy {

    private PositionService positionService;

    public RandomMoveStrategy() {
        this.positionService = new PositionService();
    }

    public GameMove giveMeARandomMove(GameArea ga) {
        List<GameMove> moves = positionService.getLegalMoves(ga);

        if (moves.size() == 0) {
            return null;
        }

        int moveIndex = new Random().nextInt(moves.size());
        return moves.get(moveIndex);
    }
}

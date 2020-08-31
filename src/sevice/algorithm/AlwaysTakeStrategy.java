package sevice.algorithm;

import model.GameArea;
import model.GameMove;
import sevice.PositionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlwaysTakeStrategy {

    private PositionService positionService;

    public AlwaysTakeStrategy() {
        this.positionService = new PositionService();
    }

    public GameMove getMoveByAlwaysTake(GameArea ga) {
        GameMove finalGM;


        List<GameMove> moves = positionService.getLegalMoves(ga);

        List<GameMove> results = new ArrayList<>();

        for (GameMove gm : moves) {
            if (isStrike(ga, gm)) {
                results.add(gm);
            }
        }

        if(results.size() != 0) {
            finalGM = results.get(new Random().nextInt(results.size()));
        } else {
            finalGM = moves.get(new Random().nextInt(moves.size()));
        }

        return finalGM;
    }

    private boolean isStrike(GameArea ga, GameMove gm) {
        return ga.getBoard()[gm.getRowTo()][gm.getColumnTo()] != null;
    }
}

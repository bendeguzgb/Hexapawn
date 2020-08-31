package sevice;

import model.GameArea;
import model.GameMove;
import model.Pawn;
import sevice.rules.RuleService;

import java.util.ArrayList;
import java.util.List;

public class PositionService {

    private RuleService ruleService;

    public PositionService() {
        ruleService = RuleService.getInstance();
    }

    public List<GameMove> getLegalMoves(GameArea ga) {
        GameMove gm;
        List<Pawn> pieces;
        List<GameMove> gmList = new ArrayList<>();
        int rowDiff;

        if (ga.getPlayer() == 1) {
            pieces = ga.getPlayers1Pieces();
            rowDiff = -1;
        } else {
            pieces = ga.getPlayers2Pieces();
            rowDiff = 1;
        }

        for (Pawn pawn : pieces) {
            gm = new GameMove();

            gm.setRowFrom(pawn.getRowIndex());
            gm.setColumnFrom(pawn.getColumnIndex());
            gm.setRowTo(gm.getRowFrom() + rowDiff);

            if (ruleService.isNonExistingRow(ga.getRowCount(), gm)) {
                continue;
            }

            for (int i = gm.getColumnFrom() - 1; i < gm.getColumnFrom() + 2; i++) {
                gm.setColumnTo(i);

                if (ruleService.isNonExistingColumn(ga.getColumnCount(), gm)) {
                    continue;
                }

                if (ruleService.isLegalMove(ga, gm)) {
                    gmList.add(new GameMove(gm));
                }
            }
        }

        return gmList;
    }
}

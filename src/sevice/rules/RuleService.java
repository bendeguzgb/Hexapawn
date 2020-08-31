package sevice.rules;


import model.GameArea;
import model.GameMove;
import model.Pawn;

import java.util.List;


public class RuleService {

    private static RuleHelper ruleHelper;
    private static RuleService ruleService = null;

    private RuleService() {

    }

    public static RuleService getInstance() {
        if (ruleService == null) {
            ruleService = new RuleService();
            ruleHelper = new RuleHelper();
        }

        return ruleService;
    }


    public boolean isLegalMove(GameArea ga, GameMove gm) {
        if (gm.getRowFrom() < 0 || gm.getRowFrom() >= ga.getRowCount()) {
            return false;
        }

        if (gm.getRowTo() < 0 || gm.getRowTo() >= ga.getRowCount()) {
            return false;
        }

        if (gm.getColumnFrom() < 0 || gm.getColumnFrom() >= ga.getColumnCount()) {
            return false;
        }

        if (gm.getColumnTo() < 0 || gm.getColumnTo() >= ga.getColumnCount()) {
            return false;
        }

        return ruleHelper.isValidMove(ga, gm) || ruleHelper.isValidStrike(ga, gm);
    }

    public boolean isGameOver(GameArea ga) {
        if (ruleHelper.isBaselineReached(ga)) {
            return true;
        }
        return isNoMoveLeft(ga);
    }

    public boolean isNoMoveLeft(GameArea ga) {
        GameMove gm = new GameMove();
        List<Pawn> pieces;
        int nextRow;

        if (ga.getPlayer() == 1) {
            pieces = ga.getPlayers1Pieces();
            nextRow = -1;
        } else {
            pieces = ga.getPlayers2Pieces();
            nextRow = 1;
        }

        for (Pawn pawn : pieces) {
            gm.setRowFrom(pawn.getRowIndex());
            gm.setColumnFrom(pawn.getColumnIndex());

            gm.setRowTo(gm.getRowFrom() + nextRow);

            if (isNonExistingRow(ga.getRowCount(), gm)) {
                continue;
            }

            for (int i = gm.getColumnFrom() - 1; i < gm.getColumnFrom() + 2; i++) {
                gm.setColumnTo(i);

                if (isNonExistingColumn(ga.getColumnCount(), gm)) {
                    continue;
                }

                if (isLegalMove(ga, gm)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isNonExistingColumn(int columnCount, GameMove gm) {
        boolean result = false;
        if (gm.getColumnTo() < 0 || gm.getColumnTo() >= columnCount) {
            result = true;
        }
        return result;
    }

    public boolean isNonExistingRow(int rowCount, GameMove gm) {
        boolean result = false;
        if (gm.getRowTo() < 0 || gm.getRowTo() >= rowCount) {
            result = true;
        }
        return result;
    }

}

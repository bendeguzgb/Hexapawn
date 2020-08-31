package sevice.rules;

import model.GameArea;
import model.GameMove;
import model.Pawn;

public class RuleHelper {


    public boolean isValidMove(GameArea ga, GameMove gm) {
        return isPlayersPiece(ga, gm) &&
                !isOpponentsPiece(ga, gm) &&
                isNextRow(ga, gm) &&
                isSameColumn(gm) &&
                isEmptySquare(ga, gm);
    }

    public boolean isValidStrike(GameArea ga, GameMove gm) {
        return isPlayersPiece(ga, gm) &&
                isOpponentsPiece(ga, gm) &&
                isNextRow(ga, gm) &&
                isNeighbouringColumn(gm);
    }

    public boolean isBaselineReached(GameArea ga) {
        Pawn[][] board = ga.getBoard();
        Pawn p1sPawn;
        Pawn p2sPawn;

        for (int i = 0; i < ga.getColumnCount(); i++) {
            p1sPawn = board[0][i];
            p2sPawn = board[ga.getRowCount() - 1][i];

            if (p1sPawn != null && p1sPawn.getPlayer() == 1) {
                return true;
            }

            if (p2sPawn != null && p2sPawn.getPlayer() == 2) {
                return true;
            }
        }
        return false;
    }

    private boolean isPlayersPiece(GameArea ga, GameMove gm) {
        return ga.getBoard()[gm.getRowFrom()][gm.getColumnFrom()].getPlayer() == ga.getPlayer();
    }

    private boolean isOpponentsPiece(GameArea ga, GameMove gm) {
        Pawn pawn = ga.getBoard()[gm.getRowTo()][gm.getColumnTo()];

        if (pawn != null) {
            return pawn.getPlayer() != ga.getPlayer();
        }

        return false;
    }

    private boolean isNextRow(GameArea ga, GameMove gm) {
        if (ga.getPlayer() == 1) {
            return gm.getRowFrom() - gm.getRowTo() == 1;
        } else {
            return gm.getRowFrom() - gm.getRowTo() == -1;
        }
    }

    private boolean isNeighbouringColumn(GameMove gm) {
        return Math.abs(gm.getColumnFrom() - gm.getColumnTo()) == 1;
    }

    private boolean isEmptySquare(GameArea ga, GameMove gm) {
        return ga.getBoard()[gm.getRowTo()][gm.getColumnTo()] == null;
    }

    private boolean isSameColumn(GameMove gm) {
        return gm.getColumnFrom() == gm.getColumnTo();
    }
}

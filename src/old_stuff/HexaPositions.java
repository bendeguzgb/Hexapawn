package old_stuff;

import model.GameArea;
import model.GameMove;
import model.Pawn;
import sevice.rules.RuleService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * This class handles the IO functions.
 */
public class HexaPositions {

    private RuleService ruleService;

    public HexaPositions() {
        ruleService = RuleService.getInstance();
    }

    /**
     * If this position does not exist in the files, it adds it with all the possible moves for Player2.
     * This method is called before Player2's every turns.
     */
    public void positionCheck(GameArea ga) {
        if (!isExistingPosition(ga)) {
            String newPosition = getBoardAsString(ga);
            newPosition += " " + getLegalMoves(ga);
            ga.addToPositionList(newPosition);

            String s = getBoardAsString(ga) + " ";
            String movesString = getLegalMoves(ga);
            String[] movesArray = movesString.split(" ");

            for (int i = 0; i < movesArray.length; i++) {
                s += new Random().nextDouble() + " ";
            }
            ga.addToWeightList(s);
        }

    }

    /**
     * @return if the current position already exists
     */
    public boolean isExistingPosition(GameArea ga) {
        String strBoard = getBoardAsString(ga);
        for (String s : ga.getPositionList()) {
            if (s.split(" ")[0].equals(strBoard)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return a string with the possible moves for the current position eg. if the position is 222010101 then "0:0:1:0 0:0:1:1 0:2:1:1 0:2:1:2"
     */
    public String getLegalMoves(GameArea ga) {
        GameMove gm = new GameMove();
        List<Pawn> pieces;
        int rowDiff;
        String move = "";

        if (ga.getPlayer() == 1) {
            pieces = ga.getPlayers1Pieces();
            rowDiff = -1;
        } else {
            pieces = ga.getPlayers2Pieces();
            rowDiff = 1;
        }

        for (Pawn pawn : pieces) {

            gm.setRowFrom(pawn.getRowIndex());
            gm.setColumnFrom(pawn.getColumnIndex());
            gm.setRowTo(gm.getRowFrom() + rowDiff);

            if (ruleService.isNonExistingRow(ga.getRowCount(), gm)) {
                continue;
            }

            for (int i = gm.getColumnFrom() - 1; i < gm.getColumnFrom() + 1; i++) {
                gm.setColumnTo(i);

                if (ruleService.isNonExistingColumn(ga.getColumnCount(), gm)) {
                    continue;
                }

                if (ruleService.isLegalMove(ga, gm)) {
                    move += gm.getRowFrom() + ":";
                    move += gm.getColumnFrom() + ":";
                    move += gm.getRowTo() + ":";
                    move += gm.getColumnTo() + " ";
                }
            }
        }
        return move;
    }

    /**
     * @return board in a string format eg. "222000111"
     */
    public String getBoardAsString(GameArea ga) {
        String stringBoard = "";
        for (int i = 0; i < ga.getBoard().length; i++) {
            stringBoard += Arrays.toString(ga.getBoard()[i]).replace("[", "").replace("]", "").replace(", ", "");
        }
        return stringBoard;
    }

    public String giveMeARandomMove(GameArea ga) {
        List<String> positionList = ga.getPositionList();
        List<String> weightList = ga. getWeightList();

        //this problem happens if there was a problem with writing the weights and positions into the files and the line aren't matching
        if (positionList.size() != weightList.size()) {
            System.err.println("\nThe number of line in Weight_x_y.txt and Position_x_y.txt is different!\nThis can cause an error in HexaPositions.giveMeAMove().\nA random move is given.\n");
            String[] s = getLegalMoves(ga).split(" ");
            return s[new Random().nextInt(s.length)];
        }
        List<String> moves = new ArrayList<>();
        List<Double> weights = new ArrayList<>();

        String boardInString = getBoardAsString(ga);
        double sumWeights = 0;

        //find current possible move's weights
        for (int i = 0; i < weightList.size(); i++) {
            // element[i] -> 222000111 0.5684 0.89462 0.268465
            String[] elements = weightList.get(i).split(" ");

            if (boardInString.equals(elements[0])) {
                for (int j = 1; j < elements.length; j++) {
                    double d = Double.parseDouble(elements[j]);
                    sumWeights += d;
                    weights.add(d);
                    moves.add(positionList.get(i).split(" ")[j]);
                }
                break;
            }
        }

        //TODO: solve ArrayOutOfBoundException && input being ""
        double random = new Random().nextDouble() * sumWeights;
        int index = weights.size();
        while (random < sumWeights) {
            index--;
            sumWeights -= weights.get(index);
        }
//        ga.setMoveIndex(index);
        return moves.get(index);
    }




    public double sigmoid(double x, Boolean derivate) {
        if (derivate)
            return x * (1.0 - x);
        return 1 / (1.0 + Math.exp(-x));
    }

    public String arrayToString(String[] s) {
        return Arrays.toString(s).replace("[", "").replace(",", "").replace("]", "");
    }

    public void updateData(GameArea ga, Map<String, Integer> movesPlayed) {
        List<String> positionList = ga.getPositionList();
        List<String> weightList = ga. getWeightList();
        int output = (ga.getPlayer() == 1) ? 1 : -1;

        for (Map.Entry<String, Integer> stringIntegerEntry : movesPlayed.entrySet()) {
            String key = stringIntegerEntry.getKey();
            int value = stringIntegerEntry.getValue();

            for (int i = 0; i < weightList.size(); i++) {
                String[] s = weightList.get(i).split(" ");
                if (key.equals(s[0])) {
                    s[value] = String.valueOf((Double.parseDouble(s[value]) - output) * sigmoid(output, true));
                    weightList.set(i, arrayToString(s));
                    break;
                }
            }
        }
        Path outPosition = Paths.get(String.format("Position_%d_%d.txt", ga.getRowCount(), ga.getColumnCount()));
        Path outWight = Paths.get(String.format("Weight_%d_%d.txt", ga.getRowCount(), ga.getColumnCount()));
        try {
            Files.write(outPosition, positionList, Charset.defaultCharset());
            Files.write(outWight, weightList, Charset.defaultCharset());

        } catch (IOException e) {
            System.err.println("Error in HexaPositions.updateData(). \n" + e);
        }


    }

    public void writeDifferences(List<String> before, int line, int column) {
        String name = String.format("Weight_Differences_%d_%d.txt", line, column);
        List<String> after = null;
        try {
            after = Files.readAllLines(Paths.get(String.format("Weight_%d_%d.txt", line, column)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(name);
        try {
            if(!file.exists())
                file.createNewFile();
        } catch (IOException e){
        }
            for (int i = 0; i < before.size(); i++) {
                String[] s1 = before.get(i).split(" ");
                String[] s2 = after.get(i).split(" ");

                for (int j = 1; j < s1.length; j++) {
                    double d = Double.parseDouble(s2[j]) - Double.parseDouble(s1[j]);
                    s2[j] = String.valueOf(d);
                }
                after.set(i, arrayToString(s2));
            }
        try {
            Files.write(Paths.get(name), after, Charset.defaultCharset());
        } catch (IOException e) {

        }

    }
}

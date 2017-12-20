package reversi;

import java.util.ArrayList;


//role за кого играет ИИ
//0 red 1 blue
public class MinimaxAgent extends Agent {

    private int timeLimit;
    private ReversiController controller;
    private final int inf = 9999999;

    public MinimaxAgent(String name, int time, ReversiController con) {
        super(name);
        timeLimit = time;
        controller = con;
    }

    @Override
    public int makeMove(Game game) {

        ReversiGame reversiGame = (ReversiGame) game;

        CellValueTuple best = max(reversiGame, -inf, inf, timeLimit);


        if (best.row == -1) {
            System.out.println("not expected!");

            return 0;
        } else {


            return reversiGame.makeMove(best.row, best.col, getRole());
        }

    }

    private CellValueTuple max(ReversiGame game, int alpha, int beta, int d) {
        CellValueTuple maxCVT = new CellValueTuple();
        maxCVT.utility = -inf;

        int winner = game.checkForWin();

        //
        if (winner == getRole()) {
            maxCVT.utility = inf - 5; //this agent wins
            return maxCVT;
        } else if (winner != -1) {
            maxCVT.utility = -inf + 5; //opponent wins
            return maxCVT;
        } else if (game.isBoardFull()) {
            maxCVT.utility = 0; //draw
            return maxCVT;
        }
        if (d == 0) {
            if (getRole() == 0)
                maxCVT.utility = game.approximateUtility(getRole());
            else if (getRole() == 1)
                maxCVT.utility = game.greedyUtility(getRole());
            return maxCVT;
        }

        for (int i = 0; i < 8; i++) {
            int j, p, k;

            for (j = 0, p = 7; j < 8; j++, p--) {

                if (getRole() == 0)
                    k = j;
                 else
                     k = p;
                if (!game.isValidCell(i, k, getRole())) {
                    continue;
                }
                ArrayList<CellValueTuple> tempMoves = game.makeTemporaryMove(i, k, getRole());

                int v = min(game, alpha, beta, d - 1).utility;
                game.removeTemporaryMoves(tempMoves);
                if (v == inf) {
                    v = 500;
                }
                if (v > maxCVT.utility) {
                    maxCVT.utility = v;
                    maxCVT.row = i;
                    maxCVT.col = k;
                }
                if (maxCVT.utility >= beta) {

                    return maxCVT;
                }
                if (maxCVT.utility > alpha) {
                    alpha = maxCVT.utility;
                }
            }
        }
        return maxCVT;

    }

    private CellValueTuple min(ReversiGame game, int alpha, int beta, int d) {
        CellValueTuple minCVT = new CellValueTuple();
        minCVT.utility = inf;
        int winner = game.checkForWin();

        //terminal check
        if (winner == getRole()) {
            minCVT.utility = inf - 5; //max wins
            return minCVT;
        } else if (winner != -1) {
            minCVT.utility = -inf + 5; //min wins
            return minCVT;
        } else if (game.isBoardFull()) {
            minCVT.utility = 0; //draw
            return minCVT;
        }

        if (d == 0) {
            if (getRole() == 0)
                minCVT.utility = game.approximateUtility(getRole());
            else if (getRole() == 1)
                minCVT.utility = game.greedyUtility(getRole());

            return minCVT;
        }

        for (int i = 0; i < 8; i++) {

            int j, p, k;
            for (j = 0, p = 7; j < 8; p--, j++) {

                if (getRole() == 0)
                    k = j;
                else
                    k = p;
                if (!game.isValidCell(i, k, minRole())) {
                    continue;
                }
                ArrayList<CellValueTuple> tempMoves = game.makeTemporaryMove(i, k, minRole());

                int v = max(game, alpha, beta, d - 1).utility;
                game.removeTemporaryMoves(tempMoves);

                if (v == -inf)
                    v = -500;
                if (v < minCVT.utility) {
                    minCVT.utility = v;
                    minCVT.row = i;
                    minCVT.col = k;
                }
                if (minCVT.utility <= alpha) {
                    return minCVT;
                }
                if (minCVT.utility < beta) {
                    beta = minCVT.utility;
                }
            }
        }
        return minCVT;

    }

    private int minRole() {
        return (getRole() + 1) % 2;
    }


}

class CellValueTuple {

    int row, col, utility;

    CellValueTuple() {
        row = -1;
        col = -1;
    }

    CellValueTuple(int r, int c, int role) {
        row = r;
        col = c;
        utility = role;
    }
}

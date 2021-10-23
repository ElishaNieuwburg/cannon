package cannon;

import java.util.ArrayList;
import java.util.List;
import game.Game;
import java.util.concurrent.ThreadLocalRandom;
import main.collections.FastArrayList;
import metadata.ai.heuristics.terms.HeuristicTerm;
import other.AI;
import other.context.Context;
import other.move.Move;
import other.state.State;
import utils.AIUtils;
import metadata.ai.heuristics.Heuristics;
import metadata.ai.heuristics.terms.Material;
import search.minimax.AlphaBetaSearch;

public class myAI extends AI{
    protected int player = -1;
    private AlphaBetaSearching ab;

    public myAI()
    {
        this.friendlyName = "my AI";
    }

    @Override
    public Move selectAction(
            final Game game,
            final Context context,
            final double maxSeconds,
            final int maxIterations,
            final int maxDepth
    )
    {
        // We'll respect any limitations on max seconds and max iterations (don't care about max depth)
        final long stopTime = (maxSeconds > 0.0) ? System.currentTimeMillis() + (long) (maxSeconds * 1000L) : Long.MAX_VALUE;
        final int maxIts = (maxIterations >= 0) ? maxIterations : Integer.MAX_VALUE;

        int numIterations = 0;

        // Our main loop through MCTS iterations
        while
        (
                numIterations < maxIts && 					// Respect iteration limit
                        System.currentTimeMillis() < stopTime && 	// Respect time limit
                        !wantsInterrupt								// Respect GUI user clicking the pause button
        )
        {
            ab = new AlphaBetaSearching(context, game, player);
            ab.NegaMax(context, 3, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        System.out.println("The best move is: " + ab.bestMove);
        return ab.bestMove;
    }

    public void initAI(Game game, int playerID) {
        this.player = playerID;
    }

}

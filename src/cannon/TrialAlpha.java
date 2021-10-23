package cannon;

import game.Game;
import other.AI;
import other.GameLoader;
import other.context.Context;
import other.model.Model;
import other.trial.Trial;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TrialAlpha {

    /** The number of trials that we'd like to run */
    private static final int NUM_TRIALS = 1;

    public static void main(final String[] args) {

        // Load Cannon game
        final Game game = GameLoader.loadGameFromFile(new File("resources/luds/Cannon.lud"));
        System.out.println("Loaded game: " + game.name());

        // Do a trial
        final Trial trial = new Trial(game);
        final Context context = new Context(game, trial);

        // Get a random AI
        final List<AI> ais = new ArrayList<AI>();
        ais.add(null);
        for (int p = 1; p <= game.players().count(); ++p)
        {
            ais.add(new myAI());
        }

        for (int i = 0; i < NUM_TRIALS; ++i)
        {
            // This starts a new trial (resetting the Context and Trial objects if necessary)
            game.start(context);
            System.out.println("Starting a new trial!");

            // Random AI technically doesn't require initialisation, but it's good practice to do so
            // for all AIs at the start of every new trial
            for (int p = 1; p <= game.players().count(); ++p)
            {
                ais.get(p).initAI(game, p);
            }

            // This "model" object lets us go through a trial step-by-step using a single API
            // that works correctly for alternating-move as well as simultaneous-move games
            final Model model = context.model();

            // We keep looping for as long as the trial is not over
            while (!trial.over())
            {
                // This call simply takes a single "step" in the game, using the list of AIs we give it,
                // and 1.0 second of "thinking time" per move.
                //
                // A step is a single move in an alternating-move game (by a single player), or a set of
                // moves (one per active player) in a simultaneous-move game.
                model.startNewStep(context, ais, 1.0);
            }

            // When we reach this code, we know that the trial is over and we can see what ranks the
            // different players achieved
            final double[] ranking = trial.ranking();

            for (int p = 1; p <= game.players().count(); ++p)
            {
                // Here we print the rankings as achieved by every agent, where
                // the "agent indices" correspond to the order of agents prior
                // to the game's start. This order will usually still be the same
                // at the end of a trial, but may be different if any swaps happened.
                //
                // ranking[p] tells you which rank was achieved by the player
                // who controlled the p'th "colour" at the end of a trial, and
                // trial.state().playerToAgent(p) tells you which agent (in the list
                // of AI objects) controls that colour at the end of the trial.
                System.out.println("Agent " + context.state().playerToAgent(p) + " achieved rank: " + ranking[p]);
            }
            System.out.println();
        }

    }
}

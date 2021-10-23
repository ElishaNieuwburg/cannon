package cannon;

import game.Game;
import main.collections.FastArrayList;
import other.context.Context;
import other.context.TempContext;
import other.move.Move;

import search.minimax.AlphaBetaSearch;
import utils.data_structures.transposition_table.TranspositionTable;

public class AlphaBetaSearching {
    final Context context;
    final Game game;
    public Move bestMove;
    final int player;
    final int opponent;
    //    private AlphaBetaSearch alphaBeta;
    protected TranspositionTable transpositionTable = null;
    final int maxDepth;

    public AlphaBetaSearching(final Context context, final Game game, final int player, int maxDepth) {
        this.context = context;
        this.game = game;
        this.player = player;
        this.maxDepth = maxDepth;
        if (player == 1) this.opponent = 2;
        else this.opponent = 1;

//        System.out.println("Player is " + player);


//        this.alphaBeta = new AlphaBetaSearch();
    }

    public int heuristic(Context context, int depth) {
        int myTower = context.state().owned().positions(player)[1].size();
        int myPawns = context.state().owned().positions(player)[0].size();


//        int myMovesCount = context.game().moves(context).count();

        int enemyTower = context.state().owned().positions(opponent)[1].size();
        int enemyPawns = context.state().owned().positions(opponent)[0].size();

        if (myTower == 0)
            return -100;
        if (enemyTower == 0)
            return 100;

        return myPawns - enemyPawns;
//        if (!(depth == 0)) {
//            if (tower == 0 && pawns != 15) return -100;
//            else return 100;
//        } else {
//            return -context.state().owned().positions(opponent)[0].size() + context.state().owned().positions(player)[0].size();
//        }

    }

    public int testH(Context context, int depth) {
        if (!(depth == 0)) {
            return 100;
        } else return 10;
    }

    public int NegaMax(Context context, int depth, int alpha, int beta) {
        if (depth == 0 || context.trial().over()) {
            return heuristic(context, depth);
        }

        Game game = context.game();
        FastArrayList<Move> moves = game.moves(context).moves();
//        this.bestMove = moves.get(0);
        int numLegalMoves = moves.size();
        Context copyContext;
        int value;
        int score = Integer.MIN_VALUE;
        Move currentMove;
        int i = 0;
        int maxAlpha = alpha;

        for (Move move : moves) {

            Context mContext = context.deepCopy();
            mContext.game().apply(mContext, move);

            value = -1 * this.NegaMax(mContext, depth - 1, -beta, -maxAlpha);
            if (value > score) {
                if (depth == maxDepth) {
                    this.bestMove = move;

                }
                score = value;
            }

            if (score > alpha) {
                maxAlpha = score;
            }

            if (score >= beta) {
                break;
            }
        }

        return score;


    }

//    public int AlphaSearcher(Context context, int depth, int alpha, int beta, int maxPlayer) {
////        System.out.println(depth);
//        if (!context.trial().over() && context.active(maxPlayer)) {
//            State state = context.state();
//            System.out.println(depth);
//
//
//            if (depth == 0 || context.trial().over()) {
//                return heuristic(context, depth);
//            } else {
//                Game game = context.game();
//                int mover = state.playerToAgent(state.mover());
//                FastArrayList<Move> moves = game.moves(context).moves();
//                this.bestMove = moves.get(0);
//                int numLegalMoves = moves.size();
//                Context copyContext = new TempContext(context);
//                int value;
//                int score;
//                Move currentMove;
//                int i;
//
//                if (mover == maxPlayer) {
//                    score = Integer.MIN_VALUE;
//                    i = 0;
//
//                    while (true) {
//                        if (i < numLegalMoves) {
//                            currentMove = (Move)moves.get(i);
//                            copyContext = new TempContext(copyContext);
//
//                            // apply the move
//                            game.apply(copyContext, currentMove);
//                            value = this.AlphaSearcher(copyContext, depth - 1, alpha, beta, maxPlayer);
//
//                            if (value > score) {
//                                this.bestMove = currentMove;
//                                score = value;
//                            }
//
//                            if (score > alpha) {
//                                alpha = score;
//                            }
//
//                            if (!(alpha >= beta)) {
//                                ++i;
//                                continue;
//                            }
//                        }
//                        return score;
//                    }
//                } else {
//                    score = Integer.MAX_VALUE;
//                    i = 0;
//
//                    while (true) {
//                        if (i < numLegalMoves) {
//                            currentMove = (Move) moves.get(i);
//                            copyContext = new TempContext(copyContext);
//
//                            // apply the move
//                            game.apply(copyContext, currentMove);
//                            value = this.AlphaSearcher(copyContext, depth - 1, alpha, beta, maxPlayer);
//
//                            if (value < score) {
//                                this.bestMove = currentMove;
//                                score = value;
//                            }
//
//                            if (score < beta) {
//                                beta = score;
//                            }
//
//                            if (!(alpha >= beta)) {
//                                ++i;
//                                continue;
//                            }
//                        }
//                        return score;
//                    }
//                }
//            }
//        } else return -context.state().owned().positions(opponent)[0].size()-context.state().owned().positions(maxPlayer)[0].size();
//    }
}


//    public static class Node {
//        /**
//         * Our parent node
//         */
//        private final Node parent;
//
//        /**
//         * The move that led from parent to this node
//         */
//        private final Move moveFromParent;
//
//        /**
//         * This objects contains the game state for this node (this is why we don't support stochastic games)
//         */
//        private final Context context;
//
//        /**
//         * Visit count for this node
//         */
//        private int visitCount = 0;
//
//        /**
//         * For every player, sum of utilities / scores backpropagated through this node
//         */
//        private final double[] scoreSums;
//
//        /**
//         * Child nodes
//         */
//        private final List<Node> children = new ArrayList<Node>();
//
//        /**
//         * List of moves for which we did not yet create a child node
//         */
//        private final FastArrayList<Move> unexpandedMoves;
//
//        /**
//         * Constructor
//         *
//         * @param parent
//         * @param moveFromParent
//         * @param context
//         */
//        public Node(final Node parent, final Move moveFromParent, final Context context) {
//            this.parent = parent;
//            this.moveFromParent = moveFromParent;
//            this.context = context;
//            final Game game = context.game();
//            scoreSums = new double[game.players().count() + 1];
//
//            unexpandedMoves = new FastArrayList<Move>(game.moves(context).moves());
//
//            if (parent != null)
//                parent.children.add(this);
//        }
//
//    }
//}

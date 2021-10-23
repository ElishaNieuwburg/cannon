//package cannon;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//@RunWith(MockitoJUnitRunner.class)
//public class AlphaBetaTest {
//
//    private final AlphaBetaSearch searcher =
//            new AlphaBetaSearch();
//
//    // Mockito creates a dummy, programmable Game instance for us
//    @Mock
//    private Game game;
//
//    @Test
//    public void testSubTree() {
//        // we are creating the move tree with names and scores
//        final Move root = createMockedMove("root");
//        final Move moveA = createMockedMove("A");
//        final Move moveB = createMockedMove("B");
//        final Move moveAA = createLeafMove("AA", 5);
//        final Move moveAB = createLeafMove("AB", 6);
//        final Move moveBA = createLeafMove("BA", 7);
//        final Move moveBB = createLeafMove("BB", 4);
//        final Move moveBC = createLeafMove("BC", 5);
//
//        // programming the dummy Game instance
//        // calling generateMoves() with the root node will
//        // return moveA and moveB
//        when(game.generateMoves(root)).
//                thenReturn(newMoveArray(moveA, moveB));
//        when(game.generateMoves(moveA)).
//                thenReturn(newMoveArray(moveAA, moveAB));
//        when(game.generateMoves(moveB)).
//                thenReturn(newMoveArray(moveBA, moveBB, moveBC));
//
//        final Move bestMove = searcher.findBestMove(5, game, root);
//        // checking the name of the best move
//        assertEquals("BA", bestMove.getName());
//    }
//
//    private Move createMockedMove(String name) {
//        // creating a dummy/programmable Move instance
//        // whose getName() method will return name
//        final Move mock = mock(Move.class);
//        when(mock.getName()).thenReturn(name);
//        return mock;
//    }
//
//    private Move createLeafMove(String name, int score) {
//        final Move move = createMockedMove(name);
//        when(move.isLeaf()).thenReturn(true);
//        when(move.getScore()).thenReturn(score);
//        return move;
//    }
//
//    private Move[] newMoveArray(Move... moves) {
//        return moves;
//    }
//}

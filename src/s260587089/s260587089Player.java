package s260587089;

import boardgame.Board;
import boardgame.BoardState;
import boardgame.Move;
import boardgame.Player;
import omweso.CCBoardState;
import omweso.CCBoard;
import omweso.CCMove;

import java.util.ArrayList;
import java.util.Random;

/** A random Omweso player. */
public class s260587089Player extends Player {
    Random rand = new Random();

    /** You must provide a default public constructor like this,
     * which does nothing but call the base-class constructor with
     * your student number. */
    public s260587089Player() { super("260587089"); }
    public s260587089Player(String s) { super(s); }

    /** Leave this method unchanged. */
    public Board createBoard() { return new CCBoard(); }

    /** Use this method to take actions when the game is over. */
    public void gameOver( String msg, BoardState bs) {
        CCBoardState board_state = (CCBoardState) bs;

        if(board_state.haveWon()){
            System.out.println("Unimpressed.");
        }else if(board_state.haveLost()){
            System.out.println("Que mala suerte...");
        }else if(board_state.tieGame()){
            System.out.println("Swerve.");
        }else{
            System.out.println("What the hell just happened?");
        }
    }

    /** Implement a very stupid way of picking moves. */
    public Move chooseMove(BoardState bs)
    {
        // Cast the arguments to the objects we want to work with.
        CCBoardState board_state = (CCBoardState) bs;

        // Get the contents of the pits so we can use it to make decisions.
        int[][] pits = board_state.getBoard();

        // Our pits in first row of array, opponent pits in second row.
        int[] my_pits = pits[0];
        int[] op_pits = pits[1];

        if(!board_state.isInitialized()){
            // Code for setting up our initial position. Also, if your agent needs to be
            // set-up in any way (e.g. loading data from files) you should do it here.

            //CCBoardState.SIZE is the width of the board, in pits.
            int[] initial_pits = new int[2 * CCBoardState.SIZE];

            // Make sure your initialization move includes the proper number of seeds.
            int num_seeds = CCBoardState.NUM_INITIAL_SEEDS;

            if(board_state.playFirst()){
                // If we're going to play first in this game, choose one random way of setting up.
                // Throw each seed in a random pit that we control.
                for(int i = 0; i < num_seeds; i++){
                    int pit = rand.nextInt(2 * CCBoardState.SIZE);
                    initial_pits[pit]++;
                }
            }else{
                // If we're going to play second this game, choose another random way of setting up.
                // Throw each seed in a random pit that we control, but in the row closest to us.
                for(int i = 0; i < num_seeds; i++){
                    int pit = rand.nextInt(CCBoardState.SIZE);
                    initial_pits[pit]++;
                }
            }

            return new CCMove(initial_pits);
        }else{
            // Play a normal turn. Choose a random pit to play.
            ArrayList<CCMove> moves = board_state.getLegalMoves();
            return moves.get(rand.nextInt(moves.size()));
        }
    }
    
    public enum NodeType { MIN, MAX }
    
    public static class Node
    {
    	private NodeType type;
    	private CCBoardState boardState;
    	private ArrayList<Node> children = new ArrayList<Node>();
    	
    	public Node(int depth, CCBoardState boardState )
    	{
    		this.type = (depth % 2 == 0) ? NodeType.MAX : NodeType.MIN;
    		this.boardState = boardState;
    		
    		
    	}
    }
}

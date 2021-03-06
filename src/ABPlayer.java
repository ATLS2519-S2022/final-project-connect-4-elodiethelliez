/**
 * A Connect-4 player that makes move based on alpha-beta pruned minimax strategy to look several steps into the future
 * 
 * @author Daniel Szafir & elodie thelliez
 *
 */
public class ABPlayer implements Player
{
    private static java.util.Random rand = new java.util.Random();
    
    int id; //to use locally
    int opponent_id;
    int cols;
    int alpha;
    int beta;
    

    @Override
    public String name() {
        return "Alpha Beta";
    }

    @Override
    public void init(int id, int msecPerMove, int rows, int cols) {
    	this.id = id; //id is player's id, opponent's id is 3-id
    	opponent_id = 3-id;
    	this.cols = cols;
    }

    @Override
    public void calcMove( //tell move to make
        Connect4Board board, int oppMoveCol, Arbitrator arb) 
        throws TimeUpException {
    	
        // Make sure there is room to make a move.
        if (board.isFull()) {
            throw new Error ("Complaint: The board is full!");
        }
        
        int move = 0;
        int maxDepth = 1;
//        int alpha = 0;
//        int beta = 0;
//        
        //while there is time remaining and search depth <= number of moves remaining
        while(!arb.isTimeUp() && maxDepth <= board.numEmptyCells()) {
        	//run FIRST LEVEL of alpha beta search and set move to be column corresponding to best score
  
        	int bestScore = -1000;
        	int currentScore; //temporary storage variable
        	//compare to best score
        	//update best score accordingly
        			
        	for(int i = 0; i < cols; i++ ) {
    			if(board.isValidMove(i)) {
    				
	    			//check for valid move
	    			board.move(i, id); //for your player;
	    			currentScore = alphabeta(board, maxDepth - 1, alpha, beta, false, arb);
	    			
	    			if (currentScore > bestScore) {
        				bestScore = currentScore;
        				move = i;
        			}
	    			
	    			bestScore = Math.max(bestScore,  currentScore);
	    			alpha = Math.max(alpha, bestScore);
	    			
	    			if (alpha >= beta) {
	    				break;
	    			}
	    			board.unmove(i, id);
    			}
    		}
        	
        arb.setMove(move);
        maxDepth++;
        	
        }
    }
    
   
    //iterative deepening minimax with alpha beta pruning
    public int alphabeta(Connect4Board board, int depth, int alpha, int beta, boolean isMaximizing, Arbitrator arb) {
    	
    	if(depth == 0 || board.isFull() || arb.isTimeUp()) {
    		return calcScore(board,id) - calcScore(board,opponent_id); //extra credit to improve this score calculation
    	}
    	
    	if (isMaximizing) {
    		int bestScore = -1000;
    		
    		for(int i = 0; i < cols; i++ ) {
    			if(board.isValidMove(i)) {
	    			//check for valid move
	    			board.move(i, id); //for your player;
	    			bestScore = Math.max(bestScore,  alphabeta(board, depth - 1, alpha, beta, false, arb));
	    			alpha = Math.max(alpha, bestScore);
	    			
	    			if (alpha >= beta) {
	    				//System.out.println("code got to alpha break");
	    				break;
	    			}
	    			board.unmove(i, id);
    			}
    		}
    		//System.out.println("returning maximize score");
    		return bestScore;
    	}
    
    	else {
    		int bestScore = 1000;
    		
    		for(int i = 0; i < cols; i++ ) {
    			if(board.isValidMove(i)) {
    				board.move(i, opponent_id);
    				bestScore = Math.min(bestScore,  alphabeta(board, depth - 1, alpha, beta, true, arb));
    				beta = Math.min(beta, bestScore);
    			
    				if(alpha >= beta) {
    					//System.out.println("code got to alpha break");
    					break;
    				}	
    				board.unmove(i, opponent_id);
    			}
    		}
    		//System.out.println("returning minimize score");
    		return bestScore;
    	}
    }

//Return the number of connect-4s that player #id has.
	public int calcScore(Connect4Board board, int id)
	{
		final int rows = board.numRows();
		final int cols = board.numCols();
		int score = 0;
		// Look for horizontal connect-4s.
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c <= cols - 4; c++) {
				if (board.get(r, c + 0) != id) continue;
				if (board.get(r, c + 1) != id) continue;
				if (board.get(r, c + 2) != id) continue;
				if (board.get(r, c + 3) != id) continue;
				score++;
			}
		}
		// Look for vertical connect-4s.
		for (int c = 0; c < cols; c++) {
			for (int r = 0; r <= rows - 4; r++) {
				if (board.get(r + 0, c) != id) continue;
				if (board.get(r + 1, c) != id) continue;
				if (board.get(r + 2, c) != id) continue;
				if (board.get(r + 3, c) != id) continue;
				score++;
			}
		}
		// Look for diagonal connect-4s.
		for (int c = 0; c <= cols - 4; c++) {
			for (int r = 0; r <= rows - 4; r++) {
				if (board.get(r + 0, c + 0) != id) continue;
				if (board.get(r + 1, c + 1) != id) continue;
				if (board.get(r + 2, c + 2) != id) continue;
				if (board.get(r + 3, c + 3) != id) continue;
				score++;
			}
		}
		for (int c = 0; c <= cols - 4; c++) {
			for (int r = rows - 1; r >= 4 - 1; r--) {
				if (board.get(r - 0, c + 0) != id) continue;
				if (board.get(r - 1, c + 1) != id) continue;
				if (board.get(r - 2, c + 2) != id) continue;
				if (board.get(r - 3, c + 3) != id) continue;
				score++;
			}
		}
		return score;
	}
}

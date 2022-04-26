/**
 * A Connect-4 player that makes move based on minimax strategy to look several steps into the future
 * 
 * @author Daniel Szafir & elodie thelliez
 *
 */
public class MinimaxPlayer implements Player
{
    private static java.util.Random rand = new java.util.Random();
    
    int id; //to use locally
    int opponent_id;
    int cols;
    

    @Override
    public String name() {
        return "Mini Max";
    }

    @Override
    public void init(int id, int msecPerMove, int rows, int cols) {
    	this.id = id; //id is player's id, opponent's id is 3-id
    	opponent_id = 3-id;
    	this.cols = cols;
    	//don't really need to access the rows
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
        
        //while there is time remaining and search depth <= number of moves remaining
        while(!arb.isTimeUp() && maxDepth <= board.numEmptyCells()) {
        	//run FIRST LEVEL of minimax search and set move to be column corresponding to best score
        	//can uses tree or use recursively
        	
  
        	int bestScore = -1000;
        	int currentScore = 0;
        	//compare to best score
        	//update best score accordingly
        			
        	for(int i = 0; i < cols; i++) {
        		if(board.isValidMove(i)) {
        			board.move(i, id); //for your player;
        			currentScore = minimax(board, maxDepth - 1, false, arb);
    			
        			if (currentScore > bestScore) {
        				bestScore = currentScore;
        				move = i;
        			}
        			
    			board.unmove(i, id);
    			
        		}
        	
        	}
        	maxDepth++;
        	arb.setMove(move);
        	
        }
    }
    
   
    //iterative deepening minimax
    public int minimax(Connect4Board board, int depth, boolean isMaximizing, Arbitrator arb) {
    	
    	int bestScore;
    	
    	if(depth == 0 || board.isFull() || arb.isTimeUp()) {
    		return calcScore(board,id) - calcScore(board,opponent_id); //extra credit to improve this score calculation
    	}
    	
    	if (isMaximizing) {
    		bestScore = -1000;
    		
    		for(int i = 0; i < cols; i++ ) {
    			if(board.isValidMove(i)) {
	    			//check for valid move
	    			board.move(i, id); //for your player;
	    			bestScore = Math.max(bestScore,  minimax(board, depth - 1, false, arb));
	    			board.unmove(i, id);
    			}
    		}
    		return bestScore;
    	}
    
    	else {
    		bestScore = 1000;
    		
    		for(int i = 0; i < cols; i++ ) {
    			if(board.isValidMove(i)) {
    			board.move(i, opponent_id);
    			bestScore = Math.min(bestScore,  minimax(board, depth - 1, true, arb));
    			board.unmove(i, opponent_id);
    		}
    		}
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

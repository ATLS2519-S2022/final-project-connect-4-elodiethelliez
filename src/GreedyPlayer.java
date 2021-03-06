/**
 * A Connect-4 player that makes move based on best next move.
 * 
 * @author Daniel Szafir & elodie thelliez
 *
 */
public class GreedyPlayer implements Player
{
    private static java.util.Random rand = new java.util.Random();
    int id; //to use locally
    int cols;
    

    @Override
    public String name() {
        return "Greedy Goblin";
    }

    @Override
    public void init(int id, int msecPerMove, int rows, int cols) {
    	this.id = id; //id is player's id, opponent's id is 3-id
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
//        For each move:
//            Temporarily make the move using board.move()
//            Calculate a score based on how the board is for you now that you've made the move
//            Undo the move using board.unmove 
//        Return the move that had the highest calculated score
        
        int col;
        int currentScore;
        int max = -1000;
        int maxCol = 0; 
        
        for(col = 0; col < cols; col++) {
        	if(board.isValidMove(col)) {
        		board.move(col, id);
        		currentScore = calcScore(board, id);
        		
        		if (currentScore > max) {
        			max = currentScore;
        			maxCol = col;
        			//System.out.println("max score: " + max + " in column: " + maxCol);
        		}
        		
        		board.unmove(col, id);
        	}
        }
           
        arb.setMove(maxCol);
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

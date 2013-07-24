/**
*This program implement Conway's Game of Life
*@version NYU-Poly: CS9053 Intro to Java
*@author Shulin Zhang 0494786
*/
public class EachGenerationOfGame{
	private int[][] initState;
	private int[][] tempState;
	private int row;
	private int column;
	
	public EachGenerationOfGame(int[][] initState, int row, int column){
		this.initState = initState;
		this.row = row;
		this.column = column;
		this.setTempState();
		this.refreshState();
		
	}
	/**
	 * Refresh the initState using rules.
	 */
	public void refreshState(){
		int numOfNeighbours;
		for(int i = 0; i < row; i++){
			for(int j = 0; j < column; j++){
				numOfNeighbours = this.countNeighbours(i+1, j+1);
				if(initState[i][j] == 1){
					if(numOfNeighbours == 2){
						initState[i][j] = 1;
					}
					else if(numOfNeighbours == 3){
						initState[i][j] = 1;
					}
					else{
						initState[i][j] = 0;
					}
				}
				if(initState[i][j] == 0){
					if(numOfNeighbours == 3){
						initState[i][j] = 1;
					}
					else{
						initState[i][j] = 0;
					}
				}
			}
		}
	}
	public void setTempState(){
		tempState = new int[row + 2][column + 2];
		for(int i = 0; i < row + 2; i++){
			for(int j = 0; j < column + 2; j++){
				tempState[i][j] = 0;
			}
		}
		for(int i = 0; i < row; i++){
			for(int j = 0; j < column; j++){
				tempState[i+1][j+1] = initState[i][j];
			}
		}
	}
	/**
	 * Count the number of neighbors.
	 * @param (rX,cY) is the position of certain node in 2D array
	 * @return the number of neighbors for each node
	 */
	public int countNeighbours(int rX, int cY){
		int numOfNeighbours = 0;
		if(tempState[rX][cY-1] == 1){
			numOfNeighbours = numOfNeighbours + 1;
		}
		if(tempState[rX-1][cY-1] == 1){
			numOfNeighbours = numOfNeighbours + 1;
		}
		if(tempState[rX-1][cY] == 1){
			numOfNeighbours = numOfNeighbours + 1;
		}
		if(tempState[rX-1][cY+1] == 1){
			numOfNeighbours = numOfNeighbours + 1;
		}
		if(tempState[rX][cY+1] == 1){
			numOfNeighbours = numOfNeighbours + 1;
		}
		if(tempState[rX+1][cY] == 1){
			numOfNeighbours = numOfNeighbours + 1;
		}
		if(tempState[rX+1][cY-1] == 1){
			numOfNeighbours = numOfNeighbours + 1;
		}
		if(tempState[rX+1][cY+1] == 1){
			numOfNeighbours = numOfNeighbours + 1;
		}
		return numOfNeighbours;
	}
	/**
	 * Get the refreshedState.
	 * @return initState which is refreshed
	 */
	public int[][] getRefreshedState(){
		return initState;
	}
}
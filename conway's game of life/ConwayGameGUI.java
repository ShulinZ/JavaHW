import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
*This program implement Conway's Game of Life
*@version NYU-Poly: CS9053 Intro to Java
*@author Shulin Zhang 0494786
*/
/**
 * A frame with two buttons and one panel to display Conway's Game of Life.
 * Next Step button can display next generation of game.
 * Clear button can clear the board.
 */
public class ConwayGameGUI extends JFrame{
	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 500;
	private Cell[][] board;
	private int row;
	private int column;
	public ConwayGameGUI(String[] args){
		this.getRowColumn(args);
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		//add Next Step button and Clear button to btnPanel.
		JPanel btnPanel = new JPanel();
		btnPanel.setVisible(true);
		JButton nextStepBtn = new JButton("Next Step");
		JButton clearBoard = new JButton("Clear");
		
		btnPanel.add(nextStepBtn);
		btnPanel.add(clearBoard);
		this.add(btnPanel, BorderLayout.NORTH);
		
		//define actions and add buttons for these actions.
		ActionListener nextStepListener = new NextStepAction();
		ActionListener clearBoardListener = new ClearBoardAction();
		nextStepBtn.addActionListener(nextStepListener);
		clearBoard.addActionListener(clearBoardListener);
		
		//add board display panel to frame.
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(row, column));
		board = new Cell[row][column];
		for(int i = 0; i < row; i++){
			for(int j = 0; j < column; j++){
				board[i][j] = new Cell();
				panel.add(board[i][j]);
			}
		}
		this.add(panel, BorderLayout.CENTER);
	}
	/**
	 * Gets numbers of row and column from command line.
	 * @param args
	 */
	public void getRowColumn(String[] args){
		switch(args.length){
		case 0:
			row = 30;
			column = 30;
			break;
		case 1:
			try{
				row = Integer.parseInt(args[0]);
				column = 30;
			} 
			catch(NumberFormatException e){
				System.err.println("Arguments must be integer.");
				System.exit(1);
			}
			break;
		case 2:
			try{
				row = Integer.parseInt(args[0]);
				column = Integer.parseInt(args[1]);
			} 
			catch(NumberFormatException e){
				System.err.println("Arguments must be integer.");
				System.exit(1);
			}
			break;
		}
		System.out.println("row = " + row + "; column = " + column);
	}
	/**
	 * Gets the initState from the board.
	 * @return initState of the board
	 */
	public int[][] getInitState(){
		int[][] initState = new int[row][column];
		for(int i = 0; i < row; i++){
			for(int j = 0; j < column; j++){
				initState[i][j] = 0;
			}
		}
		for(int i = 0; i < row; i++){
			for(int j = 0; j < column; j++){
				if(board[i][j].getLife()){
					initState[i][j] = 1;
				}
			}
		}
		return initState;
	}
	/**
	 * Refreshes the board depend on the refreshedState
	 * @param refreshedState
	 */
	public void printOutBoard(int[][] refreshedState){
		for(int i = 0; i < row; i++){
			for(int j = 0; j < column; j++){
				if(refreshedState[i][j] == 1){
					board[i][j].setAlive();
				}
				else{
					board[i][j].setDead();
				}
			}
		}
	}
	/**
	 * An action listener that gets the next generation states and display it.
	 */
	private class NextStepAction implements ActionListener{
		public void actionPerformed(ActionEvent event){
			System.out.println("Next Step");
			int[][] initState = getInitState();;
			EachGenerationOfGame eachGenerationOfGame = new EachGenerationOfGame(initState, row, column);
			printOutBoard(eachGenerationOfGame.getRefreshedState());
		}
	}
	/**
	 * An action listener that clear the board.
	 */
	private class ClearBoardAction implements ActionListener{
		public void actionPerformed(ActionEvent event){
			System.out.println("Clear");
			for(int i = 0; i < row; i++){
				for(int j = 0; j < column; j++){
					board[i][j].setDead();
				}
			}
		}
	}
}
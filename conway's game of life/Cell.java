import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
/**
*This program implement Conway's Game of Life
*@version NYU-Poly: CS9053 Intro to Java
*@author Shulin Zhang 0494786
*/
/**
 * A cell panel to display the state of certain cell.
 * A cell panel has an alive state and border.
 * Mouse Pressed can change the state of cell panel.
 */
public class Cell extends JPanel{
	private boolean alive;
	private static final int DEFAULT_WIDTH = 8;
	private static final int DEFAULT_HEIGHT = 8;
	public Cell(){
		alive = false;
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setBorder(new LineBorder(Color.WHITE, 1));
		setBackground(Color.GRAY);
		addMouseListener(new MouseHandler());
	}
	/**
	 * Gets the alive state.
	 * @return alive state of cell
	 */
	public boolean getLife(){
		return alive;
	}
	/**
	 * Sets the cell to be alive.
	 */
	public void setAlive(){
		alive = true;
		setBackground(Color.YELLOW);
	}
	/**
	 * Sets the cell to be dead.
	 */
	public void setDead(){
		alive = false;
		setBackground(Color.GRAY);
	}
	/**
	 * An MouseAdapter that can change the state of cell by mouse pressed.
	 * MouseHandler can change the background and the alive state.
	 */
	private class MouseHandler extends MouseAdapter{
		public void mousePressed(MouseEvent event){
			System.out.println("Mouse Pressed");
			if(!alive){
				setAlive();
			}
			else{
				setDead();
			}
		}
	}
}

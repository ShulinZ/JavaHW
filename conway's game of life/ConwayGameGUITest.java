import java.awt.EventQueue;
import javax.swing.JFrame;
/**
*This program implement Conway's Game of Life
*@version NYU-Poly: CS9053 Intro to Java
*@author Shulin Zhang 0494786
*/
public class ConwayGameGUITest{
	public static void main(final String[] args){		
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				ConwayGameGUI game = new ConwayGameGUI(args);
				game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				game.setTitle("Conway Game Of Life!");
				game.setVisible(true);
			}
		});
	}
}

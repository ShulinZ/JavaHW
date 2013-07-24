import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
*This program implements Chat Room client side GUI.
*@version NYU-Poly: CS9053 Intro to Java
*@author Shulin Zhang 0494786
*/
public class ClientGUI extends JFrame{
	private static final int DEFAULT_WIDTH = 600;
	private static final int DEFAULT_HEIGHT = 400;
	private static final int TEXTAREA_ROWS = 8;
	private static final int TEXTAREA_COLUMNS = 40;
	private Client client;
	private JTextArea txtAreaDisplay;
	final JTextField txtFieldMsg;
	final JTextField txtFieldAddr;
	final JTextField txtFieldPort;
	final JTextField txtFieldName;
	private String name;
	
	public ClientGUI(){
		client = Client.getInstance();
		this.setTitle("Let's Chat! --- Client");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.setVisible(true);
		
		//add address, port and name information and connect button.
		JPanel panelNorth = new JPanel();
		panelNorth.setLayout(new GridLayout(5, 2));
		panelNorth.setVisible(true);
		
		panelNorth.add(new JLabel("Address: "));
		txtFieldAddr = new JTextField("192.168.1.101", 20);
		panelNorth.add(txtFieldAddr);
		panelNorth.add(new JLabel("Port: "));
		txtFieldPort = new JTextField("8080", 20);
		panelNorth.add(txtFieldPort);
		panelNorth.add(new JLabel("Name: "));
		txtFieldName = new JTextField("Client", 20);
		panelNorth.add(txtFieldName);
		add(panelNorth, BorderLayout.NORTH);
		JButton connectBtn = new JButton("Connect");
		panelNorth.add(connectBtn);
		JButton disconnectBtn = new JButton("Disconnect");
		panelNorth.add(disconnectBtn);
		
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.PAGE_AXIS));
		panelCenter.setVisible(true);
		
		panelCenter.add(new JLabel("Messages: "));
		txtAreaDisplay = new JTextArea(TEXTAREA_ROWS, TEXTAREA_COLUMNS);
		panelCenter.add(txtAreaDisplay);
		panelCenter.add(new JLabel("Input: "));
		txtFieldMsg = new JTextField("Default input", 20);
		panelCenter.add(txtFieldMsg);
		add(panelCenter, BorderLayout.CENTER);
		
		//add send massage button.
		JPanel panelSouth = new JPanel();
		JButton sendBtn = new JButton("Send");
		panelSouth.add(sendBtn);
		add(panelSouth, BorderLayout.SOUTH);
		pack();
		
		//send listener
		sendBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String msg = txtFieldMsg.getText();
				if(msg.length() > 0){
					client.sendMsg(name + ": " + msg);
					//displayMsg(name + ": " + msg);
				}
			}
		});
		//connect listener
		connectBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String address = txtFieldAddr.getText();
				String port = txtFieldPort.getText();
				name = txtFieldName.getText();
				client.exchangeData(address, port);
			}
		});
		//disconnect listener
		disconnectBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				client.disconnect();
			}
		});
		
	}
	/**
	 * Displays the message on the textArea.
	 * @param msg
	 */
	public void displayMsg(String msg){
		txtAreaDisplay.append(msg + "\n");
		int lines = txtAreaDisplay.getLineCount();
		if(lines > TEXTAREA_ROWS){
			String content = txtAreaDisplay.getText();
			int firstLineEnd = content.indexOf('\n');
			content = content.substring(firstLineEnd + 1, content.length());
			txtAreaDisplay.setText(content);
		}
	}
}
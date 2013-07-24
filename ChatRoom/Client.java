import java.awt.EventQueue;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
/**
*This program implements Chat Room client side.
*@version NYU-Poly: CS9053 Intro to Java
*@author Shulin Zhang 0494786
*/
public class Client extends Thread{
	private static ClientGUI panel;
	private static Client instance = new Client();
	final String BYE = "BYE";
	public Socket server;
	private Scanner in;
	private PrintWriter out;
	
	public static void main(String[] args) throws IOException{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ClientGUI clientGUI = new ClientGUI();
				panel = clientGUI;
			}
		});
	}
	/**
	 * Gets the instance of the ChatClient
	 * @return singleton instance of ChatClient
	 */
	public static Client getInstance(){
		if(instance == null){
			instance = new Client();
			return instance;
		}
		else{
			return instance;
		}
	}
	public void run(){
		while(true){
			try{
				String line = in.nextLine();
				panel.displayMsg(line);
			}
			catch(Exception e){
				break;
			}
		}
	}
	/**
	 * Exchanges the address and port information.
	 * @param address
	 * @param port
	 */
	public void exchangeData(String address,String port){
		if(server != null){
			sendMsg(BYE);
			server = null;
		}
		try{
			server = new Socket(address,Integer.parseInt(port));
			System.out.print(server);
			out = new PrintWriter(server.getOutputStream(), true);
			in = new Scanner(server.getInputStream() );
			Thread t = new Thread(this);
			t.start();
		}
		catch(Exception e){
			server = null;
			System.out.println(e);
		}
	}
	/**
	 * Disconnects current thread from the server side.
	 */
	public void disconnect(){
		while(Thread.currentThread().isInterrupted() == false){
			sendMsg(BYE);
			out = null;
			in = null;
			server = null;
			Thread.currentThread().interrupt();
		}
	}
	/**
	 * Sends messages.
	 * @param msg
	 */
	public void sendMsg(String msg){
		if(msg.length() > 0){
			try{
				out.println(msg);
			}
			catch(Exception e){
				System.out.println(e);
			}
		}
	}
}
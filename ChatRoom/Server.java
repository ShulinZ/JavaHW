import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
/**
*This program implements Chat Room server side.
*@version NYU-Poly: CS9053 Intro to Java
*@author Shulin Zhang 0494786
*/
public class Server{
	final int MSG_QUEUE_SIZE = 150;
	final int CLIENT_QUEUE_SIZE = 50;
	private int port = 8080;
	private BlockingQueue<String> msgQueue = new ArrayBlockingQueue<>(MSG_QUEUE_SIZE);
	private Set<ClientThread> clientThreadSet = Collections.synchronizedSet(new HashSet<ClientThread>());
	
	public static void main(String[] args) throws IOException{
		Server server  = new Server();
		server.setupServer();
	}
	/**
	 * Sets up the server.
	 */
	public void setupServer(){
		try{
			int numOfClient = 1;
			ServerSocket server = new ServerSocket(port);
			System.out.println("Ready to serve...");
			while(true){
				Socket incoming = server.accept();
				
				System.out.println("Client: " + numOfClient + " Connected...");
				ClientThread clientThread = new ClientThread(this, incoming, msgQueue);
				clientThread.start();
				synchronized(clientThreadSet){
					clientThreadSet.add((ClientThread) clientThread);	
				}
				synchronized(clientThreadSet){
					SendMsgThread sendMsgThread = new SendMsgThread(clientThreadSet, msgQueue);
					sendMsgThread.start();
				}
				numOfClient++;
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * Disconnects the clientThread from server.
	 * @param clientThread the thread need to be disconnected
	 */
	public void disconnect(ClientThread clientThread){
		synchronized(clientThreadSet){
			clientThreadSet.remove(clientThread);
			clientThread.interrupt();
		}
	}
}
/**
 * Broadcasts the massages in the Queue to each connected client.
 */
class SendMsgThread extends Thread{
	private Set<ClientThread> clientThreadSet = Collections.synchronizedSet(new HashSet<ClientThread>());
	private BlockingQueue<String> msgQueue;
	public SendMsgThread(Set<ClientThread> clientThreadSet, BlockingQueue<String> msgQueue){
		this.clientThreadSet = clientThreadSet;
		this.msgQueue = msgQueue;
	}
	public void run(){
		while(true){
			try{
				String line = msgQueue.take();
				synchronized(clientThreadSet){
					for(ClientThread clientThread : clientThreadSet){
						clientThread.sendMsg(line);
					}
				}
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}
/**
 * Gets the massages from clientThread and saves them to blocking queue.
 * And sends the massage to server.
 */
class ClientThread extends Thread{
	private Server server;
	private Socket incoming;
	private OutputStream outStream;
	private PrintWriter out;
	private BlockingQueue<String> blockingQueue;
	final String BYE = "BYE";
	public ClientThread(Server server, Socket i, BlockingQueue<String> blockingQueue){
		this.incoming = i;
		this.blockingQueue = blockingQueue;
		this.server = server;
	}
	public void run(){
		while(Thread.currentThread().isInterrupted() == false){
			try{
				try{
					InputStream inStream = incoming.getInputStream();
					outStream = incoming.getOutputStream();
					
					Scanner in = new Scanner(inStream);
					out = new PrintWriter(outStream, true);
					
					sendMsg("Hello!");
					
					while(in.hasNextLine()){
						String line = in.nextLine();
						try{
							blockingQueue.put(line);
						}
						catch(IllegalArgumentException e){
							e.printStackTrace();
						}
						catch(InterruptedException e){
							e.printStackTrace();
						}
						catch(Exception e){
							e.printStackTrace();
						}
						if(line.trim().equals(BYE)){
							System.out.println("Disconnected.");
							server.disconnect(this);
							Thread.currentThread().interrupt();
						}	
					}
				}
				finally{
					incoming.close();
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * Sends massage to server.
	 * @param msg
	 */
	public void sendMsg(String msg){
		if(msg.length() > 0){
			try{
				out.println(msg);
			}catch(Exception e){
				System.out.println(e);
			}
		}
	}
}
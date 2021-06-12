import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

class ServerThread extends Thread {
	private String name;
	private static int connectCounter = 0;
	private static int historyConnector = 0;
	private final int ans;
	
	private Socket clientSocket;
	private InputStream in;
	private OutputStream out;
	
	public ServerThread(Socket clientSocket) {
		connectCounter++;
		historyConnector++;
		name = "Player" + historyConnector;
		ans = (int) Math.random() * 10000;
		
		this.clientSocket = clientSocket;
		try {
            in = this.clientSocket.getInputStream();
            out = this.clientSocket.getOutputStream();
        } catch (IOException e) {
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, e);
        }
	}
	
	@Override
	public void run() {
		DataInputStream din = new DataInputStream(in);
		DataOutputStream dout = new DataOutputStream(out);
		while (true) {
			
		}
	}
}

public class Server {
	private boolean isStart = true;
    private int port = 8000;
    Socket s;
    public static void main(String[] args) throws IOException{
        SingleServer server = new SingleServer();
        server.startServer();
    }

    public void startServer() throws IOException{
        ServerSocket server = new ServerSocket(port);
        System.out.println("Server ready!");

        while (isStart) {
            s = server.accept();
            SingleServerThread handleThread = new SingleServerThread(s);
            handleThread.start();
        }

        System.out.println("Server closed!");
    }

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}
}

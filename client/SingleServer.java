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

class SingleServerThread extends Thread {
	private String name;
	private static int connectCounter = 0;
	private static int historyConnector = 0;
	private final int ans[] = new int[4];
	
	private Socket clientSocket;
	private InputStream in;
	private OutputStream out;
	
	public SingleServerThread(Socket clientSocket) {
		connectCounter++;
		historyConnector++;
		name = "Player" + historyConnector;
		for (int i = 0; i < 4; i++)
			ans[i] = (int)(Math.random() * 10);
		
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
			int a = 0, b = 0;
			int[] temp = new int[4];
			try {
				int num[] = new int[4];
				System.out.println("\nAnswer = " + ans[0] + ans[1] + ans[2] + ans[3]);
				System.out.print("Input = ");
				for (int i = 0; i < 4; i++) {
					num[i] = din.readInt();
					temp[i] = ans[i];
					System.out.print(num[i]);
					if (num[i] == temp[i]) {
						a++;
						num[i] = -1;
						temp[i] = -1;
					}
				}
				System.out.println("");
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 4; j++) {
						if (i == j || (num[i] == -1 || temp[j] == -1)) continue;
                        if (num[i] == temp[j]) {
                            b++;
                            num[i] = -1;
                            temp[j] = -1;
                        }
					}
				}
				System.out.println(a + "A" + b + "B");
				
				//return data
				dout.writeInt(a);
				dout.writeInt(b);
				
				//close
				if (a == 4 && b == 0) {
					Thread.currentThread().interrupt();
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

public class SingleServer {
	private boolean isStart = true;
    private int port = 8000;
    Socket s;
    public static void main(String[] args) throws IOException{
        Server server = new Server();
        server.startServer();
    }

    public void startServer() throws IOException{
        ServerSocket server = new ServerSocket(port);
        System.out.println("Server ready!");

        while (isStart) {
            s = server.accept();
            ServerThread serverThread = new ServerThread(s);
            serverThread.start();
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

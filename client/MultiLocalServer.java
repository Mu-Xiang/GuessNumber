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

class MultiLocalServerThread extends Thread {
	private static int connectCounter = 0;
	private static int historyConnector = 0;
	private final int ans[] = new int[4];
	
	private String p1Name;
	private String p2Name;
	private Socket p1Socket;
	private Socket p2Socket;
	private InputStream p1In;
	private InputStream p2In;
	private OutputStream p1Out;
	private OutputStream p2Out;
	
	public MultiLocalServerThread(Socket p1Socket, Socket p2Socket) {
		connectCounter++;
		historyConnector++;
		p1Name = "Player " + historyConnector;
		p2Name = "Player " + historyConnector;
		
		for (int i = 0; i < 4; i++)
			ans[i] = (int)(Math.random() * 10);
		
		this.p1Socket = p1Socket;
		this.p2Socket = p2Socket;
		try {
            p1In = this.p1Socket.getInputStream();
            p1Out = this.p1Socket.getOutputStream();
            p2In = this.p2Socket.getInputStream();
            p2Out = this.p2Socket.getOutputStream();
        } catch (IOException e) {
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, e);
        }
		
		System.out.println("Thread construct finish!");
	}
	
	@Override
	public void run() {
		DataInputStream p1Din = new DataInputStream(p1In);
		DataOutputStream p1Dout = new DataOutputStream(p1Out);
		DataInputStream p2Din = new DataInputStream(p2In);
		DataOutputStream p2Dout = new DataOutputStream(p2Out);
		
		boolean bool = false;
		while (true) {
			int a = 0, b = 0;
			int[] temp = new int[4];
			try {
				//return timePause boolean
				p1Dout.writeBoolean(bool);
				p2Dout.writeBoolean(!bool);
				
				//read number
				int num[] = new int[4];
				for (int i = 0; i < 4; i++) {
					if (!bool)
						num[i] = p1Din.readInt();
					else
						num[i] = p2Din.readInt();
					temp[i] = ans[i];
				}
				
				//server print
				System.out.println("\nAnswer = " + ans[0] + ans[1] + ans[2] + ans[3]);
				if (!bool) System.out.print(p1Name + " ");
				else System.out.print(p2Name + " ");
				System.out.print("Input = " + num[0] + num[1] + num[2] + num[3]);
				
				//a & b
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 4; j++) {
						if (i == j && num[i] == temp[j]) {
							a++;
							num[i] = -1;
							temp[i] = -1;
						}
						else if (i == j || (num[i] == -1 || temp[j] == -1)) continue;
                        if (num[i] == temp[j]) {
                            b++;
                            num[i] = -1;
                            temp[j] = -1;
                        }
					}
				}
				
				//return a & b
				if (!bool) {
					p1Dout.writeInt(a);
					p1Dout.writeInt(b);
				} else {
					p2Dout.writeInt(a);
					p2Dout.writeInt(b);
				}
				bool = bool ? false : true;
				
				//interrupt
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

public class MultiLocalServer {
	private boolean isStart = true;
    private int port = 8000;
    Socket p1;
    Socket p2;
    public static void main(String[] args) throws IOException{
        MultiLocalServer server = new MultiLocalServer();
        server.startServer();
    }

    public void startServer() throws IOException{
        ServerSocket server = new ServerSocket(port);
        System.out.println("Server ready!");

        while (isStart) {
            p1 = server.accept();
            System.out.println("Player1 connect.");
            p2 = server.accept();
            System.out.println("Player2 connect.");
            MultiLocalServerThread serverThread = new MultiLocalServerThread(p1, p2);
            serverThread.start();
        }

        System.out.println("Server closed!");
    }
}

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author ªL®p¥¿
 * @param make by Eclipse
 */

public class SingleSpeed extends SingleGameFrame {
	private final static String proName = "Guess Number - Single Speed";
	private static SingleSpeed frame;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new SingleSpeed();
					frame.setVisible(true);
					frame.setTitle(proName);
					frame.setSize(720, 480);
					frame.setResizable(false);
					//set window center
					Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
					frame.setLocation((int) screensize.getWidth() / 2 - frame.getWidth() / 2, (int) screensize.getHeight() / 2 - frame.getHeight() / 2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SingleSpeed() {
		super();
		new Thread(new ServerThread()).start();
		new Thread(new GameThread()).start();
	}
	
	public void btSubmitRun() {
		try {
			//send data : 4 number
			dout.writeInt((int)spinner1.getValue());
			dout.writeInt((int)spinner2.getValue());
			dout.writeInt((int)spinner3.getValue());
			dout.writeInt((int)spinner4.getValue());
			
			//read data : A B
			int a = din.readInt();
			int b = din.readInt();
			lbAB.setText(a + " A " + b + " B");
			textAreaRecord.setText(textAreaRecord.getText() + " " + ++recordCounter + ". " + 
								   (int)spinner1.getValue() + (int)spinner2.getValue() + (int)spinner3.getValue() + (int)spinner4.getValue() + 
								   " -> " + lbAB.getText() + "\n");
			if (a == 4 && b == 0) {
				isGameEnd = true;
				btAgain.setVisible(true);
				btExit.setVisible(true);
				btSubmit.setEnabled(false);
				spinner1.setEnabled(false);
				spinner2.setEnabled(false);
				spinner3.setEnabled(false);
				spinner4.setEnabled(false);
				lbAB.setForeground(Color.RED);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void startGame() throws IOException {
		s = new Socket("localhost", 8000);
        din = new DataInputStream(s.getInputStream());
        dout = new DataOutputStream(s.getOutputStream());
	}
	
	private class ServerThread implements Runnable {
		public void run() {
			while (true) {
				try {
					SingleServer.main(null);
				} catch (IOException e) {
					//e.printStackTrace();
				}
				break;
			}
		}
	}
	
	private class GameThread implements Runnable {
		public void run() {
			while (true) {
				try {
					Thread.sleep(100);
					frame.startGame();
					break;
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

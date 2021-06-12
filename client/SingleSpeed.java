import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JSpinner;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;

/**
 * @author ªL®p¥¿
 * @param make by Eclipse
 */

public class SingleSpeed extends JFrame {
	//var
	private final static String proName = "Guess Number - Single Speed";
	private static SingleSpeed frame;
	private int recordCounter;
	private boolean isGameEnd;

	//gui
	private JPanel contentPane;
	JTextArea textAreaRecord;
	JSpinner spinner1;
	JSpinner spinner2;
	JSpinner spinner3;
	JSpinner spinner4;
	JLabel lbAB;
	
	//timer
	private int timer = 0;
	JLabel lbTimer;
	
	//server
	Socket s;
	DataInputStream din;
	DataOutputStream dout;

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
		//initial var
		recordCounter = 0;
		isGameEnd = false;
		
		//initial gui
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JButton btAgain;
		JButton btSubmit;
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.LIGHT_GRAY);
		contentPane.add(desktopPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(520, 60, 160, 300);
		desktopPane.add(scrollPane);
		
		JLabel lbRecordText = new JLabel("Record");
		lbRecordText.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lbRecordText.setHorizontalAlignment(SwingConstants.CENTER);
		lbRecordText.setBounds(520, 35, 160, 15);
		desktopPane.add(lbRecordText);
		
		//textAreaRecord
		textAreaRecord = new JTextArea();
		textAreaRecord.setEditable(false);
		textAreaRecord.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		textAreaRecord.setTabSize(4);
		scrollPane.setViewportView(textAreaRecord);
		
		//lbTimer
		lbTimer = new JLabel("Timer  00 : 00");
		lbTimer.setHorizontalAlignment(SwingConstants.CENTER);
		lbTimer.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lbTimer.setBounds(520, 380, 160, 15);
		desktopPane.add(lbTimer);
		
		//btAgain
		btAgain = new JButton("Again");
		btAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SingleSpeed.main(null);
				dispose();
			}
		});
		btAgain.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btAgain.setBounds(300, 220, 100, 25);
		desktopPane.add(btAgain);
		btAgain.setVisible(false);
		
		//spinner 1
		spinner1 = new JSpinner();
		spinner1.setModel(new SpinnerNumberModel(0, 0, 9, 1));
		spinner1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		spinner1.setBounds(250, 275, 35, 30);
		desktopPane.add(spinner1);
		
		//spinner 2
		spinner2 = new JSpinner();
		spinner2.setModel(new SpinnerNumberModel(0, 0, 9, 1));
		spinner2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		spinner2.setBounds(305, 275, 35, 30);
		desktopPane.add(spinner2);
		
		//spinner 3
		spinner3 = new JSpinner();
		spinner3.setModel(new SpinnerNumberModel(0, 0, 9, 1));
		spinner3.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		spinner3.setBounds(360, 275, 35, 30);
		desktopPane.add(spinner3);
		
		//spinner 4
		spinner4 = new JSpinner();
		spinner4.setModel(new SpinnerNumberModel(0, 0, 9, 1));
		spinner4.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		spinner4.setBounds(415, 275, 35, 30);
		desktopPane.add(spinner4);
		
		//btSummit
		btSubmit = new JButton("Submit");
		btSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		});
		btSubmit.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btSubmit.setBounds(310, 335, 80, 25);
		desktopPane.add(btSubmit);
		
		JLabel lbTitle = new JLabel("Guess Number");
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setForeground(Color.BLACK);
		lbTitle.setFont(new Font("Viner Hand ITC", Font.PLAIN, 40));
		lbTitle.setBounds(200, 60, 300, 40);
		desktopPane.add(lbTitle);
		
		//lbAB
		lbAB = new JLabel("0 A 0 B");
		lbAB.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lbAB.setHorizontalAlignment(SwingConstants.CENTER);
		lbAB.setBounds(250, 170, 200, 25);
		desktopPane.add(lbAB);
		
		new Thread(new TimerThread()).start();
		new Thread(new ServerThread()).start();
		new Thread(new GameThread()).start();
	}

	private void startGame() throws IOException {
		s = new Socket("localhost", 8000);
        din = new DataInputStream(s.getInputStream());
        dout = new DataOutputStream(s.getOutputStream());
	}
	
	private class TimerThread implements Runnable {
		public void run() {
			while (true) {
				try {
					Thread.sleep(1000);
					timer++;
					String timerText = "Timer  " + (((timer / 60) < 10) ? "0" : "") + (timer / 60) + " : " + (((timer % 60) < 10) ? "0" : "") + (timer % 60);
					lbTimer.setText(timerText);
					if (isGameEnd) break;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
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

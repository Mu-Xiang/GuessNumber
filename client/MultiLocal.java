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

public class MultiLocal extends JFrame {
	//var
	private final static String proName = "Guess Number - Local";
	private static MultiLocal frame1;
	private static MultiLocal frame2;
	private static int step = 0;
	private int recordCounter;
	private boolean yourTurn;
	private boolean isGameEnd;

	//gui
	private JPanel contentPane;
	JTextArea textAreaRecord;
	JSpinner spinner1;
	JSpinner spinner2;
	JSpinner spinner3;
	JSpinner spinner4;
	JButton btSubmit;
	JLabel lbAB;
	JButton btAgain;
	JButton btExit;
	
	
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
					//player 1
					frame1 = new MultiLocal();
					frame1.setVisible(true);
					frame1.setTitle(proName + " Player 1");
					frame1.setSize(540, 480);
					frame1.setResizable(false);
					Dimension screensize1 = Toolkit.getDefaultToolkit().getScreenSize();
					frame1.setLocation((int) screensize1.getWidth() / 5  - frame1.getWidth() / 5, (int) screensize1.getHeight() / 2 - frame1.getHeight() / 2);
/*
					//player 2
					frame2 = new MultiLocal();
					frame2.setVisible(true);
					frame2.setTitle(proName + " Player 2");
					frame2.setSize(540, 480);
					frame2.setResizable(false);
					Dimension screensize2 = Toolkit.getDefaultToolkit().getScreenSize();
					frame2.setLocation((int) screensize2.getWidth() / 5 * 4 - frame2.getWidth() / 5 * 4, (int) screensize2.getHeight() / 2 - frame2.getHeight() / 2);
*/
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MultiLocal() {
		//initial var
		recordCounter = 0;
		isGameEnd = false;
		
		//initial gui
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 540, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.LIGHT_GRAY);
		contentPane.add(desktopPane, BorderLayout.CENTER);
		
		JLabel lbTitle = new JLabel("Guess Number");
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setForeground(Color.BLACK);
		lbTitle.setFont(new Font("Viner Hand ITC", Font.PLAIN, 40));
		lbTitle.setBounds(20, 60, 300, 40);
		desktopPane.add(lbTitle);
		
		JLabel lbRecordText = new JLabel("Record");
		lbRecordText.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lbRecordText.setHorizontalAlignment(SwingConstants.CENTER);
		lbRecordText.setBounds(340, 35, 160, 15);
		desktopPane.add(lbRecordText);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(340, 60, 160, 330);
		desktopPane.add(scrollPane);
		
		//textAreaRecord
		textAreaRecord = new JTextArea();
		textAreaRecord.setEditable(false);
		textAreaRecord.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		textAreaRecord.setTabSize(4);
		scrollPane.setViewportView(textAreaRecord);
		
		//lbTimer
		lbTimer = new JLabel("Timer  00 : 00");
		lbTimer.setHorizontalAlignment(SwingConstants.CENTER);
		lbTimer.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lbTimer.setBounds(90, 125, 160, 20);
		desktopPane.add(lbTimer);
		
		//spinner 1
		spinner1 = new JSpinner();
		spinner1.setModel(new SpinnerNumberModel(0, 0, 9, 1));
		spinner1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		spinner1.setBounds(70, 220, 35, 30);
		desktopPane.add(spinner1);
		
		//spinner 2
		spinner2 = new JSpinner();
		spinner2.setModel(new SpinnerNumberModel(0, 0, 9, 1));
		spinner2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		spinner2.setBounds(125, 220, 35, 30);
		desktopPane.add(spinner2);
		
		//spinner 3
		spinner3 = new JSpinner();
		spinner3.setModel(new SpinnerNumberModel(0, 0, 9, 1));
		spinner3.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		spinner3.setBounds(180, 220, 35, 30);
		desktopPane.add(spinner3);
		
		//spinner 4
		spinner4 = new JSpinner();
		spinner4.setModel(new SpinnerNumberModel(0, 0, 9, 1));
		spinner4.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		spinner4.setBounds(235, 220, 35, 30);
		desktopPane.add(spinner4);
		
		//btSummit
		btSubmit = new JButton("Submit");
		btSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dout.writeInt((int)spinner1.getValue());
					dout.writeInt((int)spinner2.getValue());
					dout.writeInt((int)spinner3.getValue());
					dout.writeInt((int)spinner4.getValue());
					System.out.println("Send");
					//read data : A B
					int a = din.readInt();
					int b = din.readInt();
					lbAB.setText(a + " A " + b + " B");
					textAreaRecord.setText(textAreaRecord.getText() + " " + ++recordCounter + ". " + 
										   (int)spinner1.getValue() + (int)spinner2.getValue() + (int)spinner3.getValue() + (int)spinner4.getValue() + 
										   " -> " + lbAB.getText() + "\n");
					btSubmit.setEnabled(false);
					if (a == 4 && b == 0) {
						isGameEnd = true;
						btAgain.setVisible(true);
						spinner1.setEnabled(false);
						spinner2.setEnabled(false);
						spinner3.setEnabled(false);
						spinner4.setEnabled(false);
						lbAB.setForeground(Color.RED);
					} else
						yourTurn = din.readBoolean();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btSubmit.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btSubmit.setBounds(130, 280, 80, 25);
		desktopPane.add(btSubmit);
		
		//lbAB
		lbAB = new JLabel("0 A 0 B");
		lbAB.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lbAB.setHorizontalAlignment(SwingConstants.CENTER);
		lbAB.setBounds(70, 170, 200, 25);
		desktopPane.add(lbAB);
		
		//btAgain
		btAgain = new JButton("Again");
		btAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MultiLocal.main(null);
				dispose();
			}
		});
		btAgain.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btAgain.setBounds(40, 340, 80, 25);
		desktopPane.add(btAgain);
		btAgain.setVisible(false);
		
		//btExit
		btExit = new JButton("Exit");
		btExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btExit.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btExit.setBounds(220, 340, 80, 25);
		desktopPane.add(btExit);
		btExit.setVisible(false);
		
		//Thread
		System.out.println("Thread.");
		new Thread(new ServerThread()).start();
		new Thread(new GameStartThread()).start();
		new Thread(new TimerThread()).start();
	}
	
	private void startGame() {
		try {
			s = new Socket("localhost", 8000);
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());
			System.out.println("startGame");
			btSubmit.setEnabled(false);
			yourTurn = din.readBoolean();
			System.out.println("read " + yourTurn);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class TimerThread implements Runnable {
		public void run() {
			while (true) {
				try {
					//System.out.println(yourTurn);
					if (yourTurn) {
						btSubmit.setEnabled(true);
						Thread.sleep(1000);
						timer++;
						String timerText = "Timer  " + (((timer / 60) < 10) ? "0" : "") + (timer / 60) + " : " + (((timer % 60) < 10) ? "0" : "") + (timer % 60);
						lbTimer.setText(timerText);
						if (isGameEnd) break;
					}
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
					MultiLocalServer.main(null);
				} catch (IOException e) {
					//e.printStackTrace();
				}
				break;
			}
		}
	}
	
	private class GameStartThread implements Runnable {
		public void run() {
			while (true) {
				try {
					Thread.sleep(100);
					frame1.startGame();
					frame2.startGame();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
}

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class SingleGameFrame extends JFrame {
	//var
	private final static String proName = "";
	private static SingleGameFrame frame;
	protected int recordCounter;
	protected boolean isGameEnd;

	//gui
	private JPanel contentPane;
	public JTextArea textAreaRecord;
	public JSpinner spinner1;
	public JSpinner spinner2;
	public JSpinner spinner3;
	public JSpinner spinner4;
	public JButton btSubmit;
	public JLabel lbAB;
	public JButton btAgain;
	public JButton btExit;
	
	//timer
	private int timer = 0;
	public JLabel lbTimer;

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
					frame = new SingleGameFrame();
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
	public SingleGameFrame() {
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

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.LIGHT_GRAY);
		contentPane.add(desktopPane, BorderLayout.CENTER);

		JLabel lbTitle = new JLabel("Guess Number");
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setForeground(Color.BLACK);
		lbTitle.setFont(new Font("Viner Hand ITC", Font.PLAIN, 40));
		lbTitle.setBounds(200, 60, 300, 40);
		desktopPane.add(lbTitle);

		JLabel lbRecordText = new JLabel("Record");
		lbRecordText.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lbRecordText.setHorizontalAlignment(SwingConstants.CENTER);
		lbRecordText.setBounds(520, 35, 160, 15);
		desktopPane.add(lbRecordText);
	
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(520, 60, 160, 330);
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
		lbTimer.setBounds(270, 125, 160, 20);
		desktopPane.add(lbTimer);

		//spinner 1
		spinner1 = new JSpinner();
		spinner1.setModel(new SpinnerNumberModel(0, 0, 9, 1));
		spinner1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		spinner1.setBounds(250, 220, 35, 30);
		desktopPane.add(spinner1);

		//spinner 2
		spinner2 = new JSpinner();
		spinner2.setModel(new SpinnerNumberModel(0, 0, 9, 1));
		spinner2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		spinner2.setBounds(305, 220, 35, 30);
		desktopPane.add(spinner2);

		//spinner 3
		spinner3 = new JSpinner();
		spinner3.setModel(new SpinnerNumberModel(0, 0, 9, 1));
		spinner3.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		spinner3.setBounds(360, 220, 35, 30);
		desktopPane.add(spinner3);

		//spinner 4
		spinner4 = new JSpinner();
		spinner4.setModel(new SpinnerNumberModel(0, 0, 9, 1));
		spinner4.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		spinner4.setBounds(415, 220, 35, 30);
		desktopPane.add(spinner4);

		//btSummit
		btSubmit = new JButton("Submit");
		btSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btSubmitRun();
			}
		});
		btSubmit.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btSubmit.setBounds(310, 280, 80, 25);
		desktopPane.add(btSubmit);

		//lbAB
		lbAB = new JLabel("0 A 0 B");
		lbAB.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lbAB.setHorizontalAlignment(SwingConstants.CENTER);
		lbAB.setBounds(250, 170, 200, 25);
		desktopPane.add(lbAB);

		//btAgain
		btAgain = new JButton("Again");
		btAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SingleSpeed.main(null);
				dispose();
			}
		});
		btAgain.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btAgain.setBounds(220, 340, 80, 25);
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
		btExit.setBounds(400, 340, 80, 25);
		desktopPane.add(btExit);
		btExit.setVisible(false);

		//Thread
		new Thread(new TimerThread()).start();
	}
	
	public void btSubmitRun() {
		
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

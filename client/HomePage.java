import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDesktopPane;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class HomePage extends JFrame {
	private static final String proName = "HomePage";
	private static HomePage frame;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new HomePage();
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
	public HomePage() {
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
		
		JButton btSingle = new JButton("\u55AE\u4EBA\u904A\u6232");
		JButton btMulti = new JButton("\u591A\u4EBA\u904A\u6232");
		JButton btSetting = new JButton("\u8A2D\u5B9A");
		JButton btRank = new JButton("\u6392\u884C\u699C");
		JButton btExit = new JButton("\u96E2\u958B");
		JButton btBack = new JButton("<--Back");
		JButton btSingleSpeed = new JButton("\u55AE\u4EBA\u7AF6\u731C");
		JButton btSingleAI = new JButton("AI");
		JButton btMultiLocal = new JButton("\u672C\u6A5F\u591A\u4EBA");
		JButton btMultiConnect = new JButton("\u591A\u4EBA\u9023\u7DDA");
		JButton btMultiCustom = new JButton("\u81EA\u8A02");
		
		//btSingle
		btSingle.setToolTipText("Just you and you.");
		btSingle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btSingle.setVisible(false);
				btMulti.setVisible(false);
				btSetting.setVisible(false);
				btRank.setVisible(false);
				btExit.setVisible(false);
				btBack.setVisible(true);
				btSingleSpeed.setVisible(true);
				btSingleAI.setVisible(true);
			}
		});
		btSingle.setBounds(290, 150, 120, 25);
		desktopPane.add(btSingle);
		
		//btMulti
		btMulti.setToolTipText("Play with your friends.");
		btMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btSingle.setVisible(false);
				btMulti.setVisible(false);
				btSetting.setVisible(false);
				btRank.setVisible(false);
				btExit.setVisible(false);
				btBack.setVisible(true);
				btMultiLocal.setVisible(true);
				btMultiConnect.setVisible(true);
				btMultiCustom.setVisible(true);
			}
		});
		btMulti.setBounds(290, 200, 120, 25);
		desktopPane.add(btMulti);
		
		//btSetting
		btSetting.setToolTipText("WIP");
		btSetting.setEnabled(false);
		btSetting.setBounds(290, 250, 120, 25);
		desktopPane.add(btSetting);
		
		//btRank
		btRank.setToolTipText("WIP");
		btRank.setEnabled(false);
		btRank.setBounds(290, 300, 120, 25);
		desktopPane.add(btRank);
		btBack.setVisible(false);
		
		//btExit
		btExit.setToolTipText("Just go!");
		btExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btExit.setBounds(290, 350, 120, 25);
		desktopPane.add(btExit);
		
		//btBack
		btBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btSingle.setVisible(true);
				btMulti.setVisible(true);
				btSetting.setVisible(true);
				btRank.setVisible(true);
				btExit.setVisible(true);
				btBack.setVisible(false);
				btSingleSpeed.setVisible(false);
				btSingleAI.setVisible(false);
				btMultiLocal.setVisible(false);
				btMultiConnect.setVisible(false);
				btMultiCustom.setVisible(false);
			}
		});
		btBack.setBackground(SystemColor.activeCaption);
		btBack.setToolTipText("Back to home page.");
		btBack.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btBack.setBounds(10, 10, 80, 25);
		desktopPane.add(btBack);
		
		//btSingleTimer
		btSingleSpeed.setToolTipText("Speed run.");
		btSingleSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SingleSpeed.main(null);
				dispose();
			}
		});
		btSingleSpeed.setBounds(150, 275, 100, 30);
		desktopPane.add(btSingleSpeed);
		btSingleSpeed.setVisible(false);
		
		//btSingleAI
		btSingleAI.setToolTipText("WIP");
		btSingleAI.setEnabled(false);
		btSingleAI.setBounds(450, 275, 100, 30);
		desktopPane.add(btSingleAI);
		btSingleAI.setVisible(false);
		
		//btMultiLocal
		btMultiLocal.setToolTipText("Play with your friend.");
		btMultiLocal.setEnabled(false);
		btMultiLocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new MultiLocalServerThread()).run();
				new Thread(new MultiLocalThread()).run();
				dispose();
			}
		});
		btMultiLocal.setBounds(150, 275, 100, 30);
		desktopPane.add(btMultiLocal);
		btMultiLocal.setVisible(false);
		
		//btMultiConnect
		btMultiConnect.setToolTipText("WIP");
		btMultiConnect.setEnabled(false);
		btMultiConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btMultiConnect.setBounds(300, 275, 100, 30);
		desktopPane.add(btMultiConnect);
		btMultiConnect.setVisible(false);
		
		//btMultiCustom
		btMultiCustom.setToolTipText("Play with your friend online.");
		btMultiCustom.setEnabled(false);
		btMultiCustom.setBounds(450, 275, 100, 30);
		desktopPane.add(btMultiCustom);
		btMultiCustom.setVisible(false);
	}
	
	private class MultiLocalThread implements Runnable {
		public void run() {
			MultiLocal.main(null);
		}
	}
	
	private class MultiLocalServerThread implements Runnable {
		public void run() {
			while (true) {
				try {
					MultiLocalServer.main(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
}

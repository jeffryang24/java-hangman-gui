package aoop.app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sun.misc.JavaLangAccess;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Component;
import java.awt.Dimension;

public class Hangman extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Hangman frame = new Hangman();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Hangman() {
		setResizable(false);
		setTitle("Hangman");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 959, 513);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel pnlUpper = new JPanel();
		pnlUpper.setPreferredSize(new Dimension(10, 60));
		FlowLayout flowLayout = (FlowLayout) pnlUpper.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(pnlUpper, BorderLayout.NORTH);
		
		JLabel lblScore = new JLabel("Score: ");
		lblScore.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlUpper.add(lblScore);
		
		JLabel lblScoreText = new JLabel("%answered%/%total%");
		lblScoreText.setFont(new Font("Tahoma", Font.BOLD, 15));
		pnlUpper.add(lblScoreText);
		
		JLabel lblSpacing1 = new JLabel(" ");
		lblSpacing1.setPreferredSize(new Dimension(350, 14));
		pnlUpper.add(lblSpacing1);
		
		JLabel lblName = new JLabel("Name: ");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlUpper.add(lblName);
		
		JLabel lblNameText = new JLabel("%name%");
		lblNameText.setPreferredSize(new Dimension(275, 14));
		lblNameText.setFont(new Font("Tahoma", Font.BOLD, 15));
		pnlUpper.add(lblNameText);
		
		JLabel lblTopic = new JLabel("Topic: ");
		lblTopic.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlUpper.add(lblTopic);
		
		JLabel lblTopicText = new JLabel("%topic%");
		lblTopicText.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTopicText.setPreferredSize(new Dimension(850, 14));
		pnlUpper.add(lblTopicText);
		
		JPanel pnlMiddle = new JPanel();
		contentPane.add(pnlMiddle, BorderLayout.CENTER);
		
		JPanel pnlLeft = new JPanel();
		contentPane.add(pnlLeft, BorderLayout.WEST);
		
		JLabel lblHangmanPic = new JLabel("%lblhangman%");
		// Load Image
		try {
			BufferedImage img = ImageIO.read(this.getClass().getResource("assets/images/icon.png"));
			lblHangmanPic.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		pnlLeft.add(lblHangmanPic);	
	}

}

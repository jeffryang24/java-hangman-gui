package aoop.app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import aoop.helper.*;

public class ScoreList extends JFrame implements MouseListener{
	// Panel atas
	JTable tblScore = new JTable();
	JScrollPane scorePane = new JScrollPane(tblScore);
	
	// Panel Bawah
	JPanel pnlBack = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JButton btnBack = new JButton("Back");
	
	// DBContext
	Statement st = DBContext.getStatement();
	String sql = "";
	
	public ScoreList(){
		super("Hangman Score List");
		// Initialize DBConnection
		DBContext.Connect();
		
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		
		// ScrollPane
		setTableData();
		add(scorePane, BorderLayout.NORTH);
		
		// Panel bawah
		btnBack.addMouseListener(this);
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlBack.add(btnBack);
		add(pnlBack, BorderLayout.SOUTH);
		
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		
		setVisible(true);
	}
	
	public Vector<Object> addRow(String userName, String TopicName, String Score, String datePlayed){
		Vector<Object> tmp = new Vector<Object>();
		tmp.add(userName);
		tmp.add(TopicName);
		tmp.add(Score);
		tmp.add(datePlayed);
		return tmp;
	}
	
	public void setTableData(){
		Vector<Object> columnData = new Vector<Object>();
		columnData.add("Username");
		columnData.add("Topic Name");
		columnData.add("Score");
		columnData.add("Date Played");
		
		Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
		
		sql = "select a.username, c.totalanswered, b.topicdesc, c.datetested, d.totalquestion from " +
				" User a, TopicHeader b, Score c, (select count(aa.answerid) as TotalQuestion, aa.topicid from TopicDetail aa group by aa.topicid) d " +
				" where a.userid = c.userid and b.topicid = c.topicid and b.topicid = d.topicid order by a.username,b.topicdesc";
		try {
			ResultSet rs = st.executeQuery(sql);
			if (rs.isBeforeFirst()){
				while(rs.next()){
					String username = rs.getString("username");
					String topic = rs.getString("topicdesc");
					String score = rs.getString("totalanswered");
					String totalquestion = rs.getString("totalquestion");
					String dateTested = rs.getString("datetested");
					
					rowData.add(addRow(username, topic, score + "/" + totalquestion, dateTested));
				}
			}else{
				rowData.add(addRow("-", "-", "-", "-"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		// add data to table
		tblScore.setModel(new DefaultTableModel(rowData, columnData));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		
		if (source == btnBack){
			SplashScreen app = new SplashScreen();
			app.main(null);
			setVisible(false);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

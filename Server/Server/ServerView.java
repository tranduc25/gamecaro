package Server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;

public class ServerView extends JFrame {

	private JPanel contentPane;
	private JTextArea textArea_1;
	private JTextArea textArea;
	private JTextArea dson;

	
	public ServerView() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(448, 434);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Danh sách Online!", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);

		dson = new JTextArea();
		dson.setFont(new Font("Monospaced", Font.PLAIN, 16));
		scrollPane.setViewportView(dson);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Danh sách phòng!", null, panel_1, null);
		panel_1.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1, BorderLayout.CENTER);

		textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
		scrollPane_1.setViewportView(textArea);

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Các thông báo", null, panel_2, null);
		panel_2.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_2 = new JScrollPane();
		panel_2.add(scrollPane_2, BorderLayout.CENTER);

		textArea_1 = new JTextArea();
		textArea_1.setFont(new Font("Monospaced", Font.PLAIN, 17));
		scrollPane_2.setViewportView(textArea_1);
		setVisible(true);
	}
	public void  setnotify(String sms) {
		this.textArea_1.setText(this.textArea_1.getText()+"\n"+sms);
	}
	public void SetOn(String sms) {
		this.dson.setText(sms);
	}
	public void  Room(String sms) {
		this.textArea.setText(sms+"\n");
	}

}

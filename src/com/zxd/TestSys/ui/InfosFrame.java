package com.zxd.TestSys.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneLayout;
import javax.swing.table.DefaultTableModel;

import com.zxd.TestSys.service.ClientContext;

public class InfosFrame extends JFrame{
	private JTable infosTable ;
	private JScrollPane textPanel;
	private JPanel buttonPanel;
	private JButton confirmButton;
	private ClientContext uiController;
	public InfosFrame() {
		infosTable = new JTable();
		buttonPanel = new JPanel();
		textPanel = new JScrollPane(infosTable);
		confirmButton = new JButton("确定");
		this.setSize(400, 200);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.GUIinit();
	}
	public void GUIinit() {
		this.setLayout(new BorderLayout());
		buttonPanel.setLayout(new FlowLayout());
		textPanel.setLayout(new ScrollPaneLayout());
		infosTable.setFont(new Font("宋体", Font.PLAIN, 16));
		confirmButton.setFont(new Font("宋体", Font.PLAIN, 16));
		buttonPanel.add(confirmButton);
		this.add(textPanel,BorderLayout.CENTER);
		this.add(buttonPanel,BorderLayout.SOUTH);
		ActionListen();
	}
	public void ActionListen() {
		confirmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			}
		});
	}
	public void initTableFormat() {
		infosTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		infosTable.getColumnModel().getColumn(1).setPreferredWidth(300);
		infosTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	public void updateInfos(Vector<String> columsName,Vector<Vector<String>> rowData) {
		DefaultTableModel dtm =new DefaultTableModel(rowData, columsName);
		infosTable.setModel(dtm);
		textPanel.setViewportView(infosTable);
	}
	public void setUiController(ClientContext cc) {
		this.uiController = cc;
	}
	public static void main(String[] args) {
		new InfosFrame().setVisible(true);
	}
}

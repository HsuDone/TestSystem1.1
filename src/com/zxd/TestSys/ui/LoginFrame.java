package com.zxd.TestSys.ui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.zxd.TestSys.Util.Resources;
import com.zxd.TestSys.beans.User;
import com.zxd.TestSys.service.ClientContext;
import com.zxd.TestSys.service.EntityContext;

public class LoginFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private JTextField IdField ;
	private JLabel welcome;
	private JPasswordField pswField;
	private JLabel userlabel,pswlabel;
	private JPanel userpanel;
	private JPanel buttpanel;
	private JButton regist,yes,no; 
	private ClientContext uiController ;
	private EntityContext database;
	
	public LoginFrame() {
		 IdField = new JTextField();
		 pswField = new JPasswordField();
		 welcome = new JLabel("欢迎进入考试系统登陆界面",JLabel.CENTER);
		 welcome.setFont(new Font("黑体", Font.PLAIN, 20));
		 userlabel = new JLabel("用户名");
		 pswlabel = new JLabel("密码");
		 userlabel.setFont(new Font("黑体", Font.PLAIN, 16));
		 pswlabel.setFont(new Font("黑体", Font.PLAIN, 16));
		 yes = new JButton("登录");
		 no = new JButton("退出");
		 regist = new JButton("注册");
		 userpanel = new JPanel();
		 buttpanel = new JPanel();
		 this.add(welcome);
		 this.add(userpanel);
		  this.add(buttpanel);  //将两块面板添加到登陆框上面
		         //设置显示
		  this.setSize(400, 400);
		  this.setResizable(false);
		  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  this.setLocationRelativeTo(null);
		  this.setTitle("请登录");
		  this.setPanels();
	}
	public void setUiController(ClientContext Controller) {
		this.uiController = Controller;
	}
	public void setDatabase(EntityContext ec) {
		this.database = ec;
	}
	public void setPanels() {
		   this.setLayout(new GridLayout(3,1,5,30));
		   userpanel.setLayout(null);
		   userpanel.add(userlabel); 
		   userpanel.add(IdField);//第一块面板添加用户名和文本框 
		   userpanel.add(pswlabel);
		   userpanel.add(pswField);//第一块面板添加密码和密码输入框
		   userlabel.setBounds(80,10,50,50);
		   IdField.setBounds(150,20,200,30);
		   pswlabel.setBounds(80,50,50,50);
		   pswField.setBounds(150,60,200,30);
		   buttpanel.add(yes);
		   buttpanel.add(regist); //第二块面板添加确认取消注册
		   buttpanel.add(no);
		   ActionListen();
	}
	
	public void ActionListen() {
		pswField.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {
				int keyChar = e.getKeyChar();				
				if((keyChar >= '0' && keyChar <= '9') 
						||(keyChar >= 'A' && keyChar <= 'z') ){
				}else{
					e.consume(); //屏蔽掉非法输入
				}
			}
		});

	   yes.addMouseListener(new MouseAdapter() {
			 @Override
			public void mouseClicked(MouseEvent e) {
				 String inputId = IdField.getText();
				 String inputPsw = new String(pswField.getPassword());
				 database.setInputUser(inputId);
				 uiController.login(inputId, inputPsw);
			 }
		});
	   no.addMouseListener(new MouseAdapter() {
		   @Override
		public void mouseClicked(MouseEvent e) {
			   System.exit(0);
		   }
	   });
	}
	
	public static void main(String[] args) {
		LoginFrame l = new LoginFrame();
		l.setVisible(true);
	}
	
}

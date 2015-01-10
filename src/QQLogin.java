/*
 * QQLogin,登陆界面和逻辑
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;

@SuppressWarnings({ "serial", "restriction" })
public class QQLogin extends JFrame implements ActionListener {
	public static void main(String args[]) {
		// 导致 runnable 的 run 方法在 EventQueue 的指派线程上被调用。
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// 使用 LookAndFeel 对象设置当前的默认外观。
					UIManager.setLookAndFeel(new NimbusLookAndFeel()) ;// 设置一个灰常漂亮的外观
					QQLogin w = new QQLogin() ;
					w.setVisible(true) ;
				} catch (Exception e) {
					e.printStackTrace() ;
				}
			}
		}) ;
	}
	
	// 定义组件
	JLabel jl1, jl2, jl3, jl4, jl5 ;
	JPanel jp1, jp2 ;
	JButton jb3 ;
	JCheckBox jcb1, jcb2 ;
	JTextField txtUser ;
	JPasswordField txtPass ;
	
	public QQLogin() {
		/* 处理组件 */
		Font font = new Font("楷体", Font.BOLD, 15) ;
		// 北部
		jl1 = new JLabel(new ImageIcon("image/tou.jpg")) ;

		// 中部
		jp2 = new JPanel() ;
		txtUser = new JTextField(15) ;
		txtPass = new JPasswordField(15) ;
		jl2 = new JLabel(" QQ号码  ", JLabel.CENTER) ;
		jl2.setFont(font) ;
		jl3 = new JLabel(" QQ密码  ", JLabel.CENTER) ;
		jl3.setFont(font) ;
		jl4 = new JLabel(" 注册账号", JLabel.CENTER) ;
		jl4.setForeground(Color.BLUE) ;
		jl4.setFont(font) ;
		jl5 = new JLabel(" 忘记密码", JLabel.CENTER) ;
		jl5.setForeground(Color.BLUE) ;
		jl5.setFont(font) ;
		jcb1 = new JCheckBox("记住密码") ;
		jcb1.setFont(font) ;
		jcb2 = new JCheckBox("自动登录") ;
		jcb2.setFont(font) ;

		jp2.setLayout(new FlowLayout()) ;
		jp2.add(jl2) ;
		jp2.add(txtUser) ;
		jp2.add(jl4) ;
		jp2.add(jl3) ;
		jp2.add(txtPass) ;
		jp2.add(jl5) ;
		jp2.add(jcb1) ;
		jp2.add(jcb2) ;

		// 南部
		jp1 = new JPanel() ;
		jb3 = new JButton("     登录      ") ;
		jb3.setFont(font) ;
		jp1.add(jb3) ;
		jb3.addActionListener(this) ;
		jl4.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() >= 1) {
					JOptionPane.showMessageDialog(null, "注册!") ;
				}
			}
		}) ;

		// 添加组件
		this.add(jl1, "North") ;// jpg图片
		this.add(jp2, "Center") ;
		this.add(jp1, "South") ;
		this.setTitle("QQ2013") ;
		this.setIconImage(new ImageIcon("image/qq.jpg").getImage()) ;
		this.setSize(365, 280) ;
		this.setLocation(500, 200) ;
		this.setResizable(false) ;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		this.setVisible(true) ;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("     登录      ")) {
			try {
				// 发送用户名和密码到服务器
				String user = txtUser.getText() ;
				String pass = txtPass.getText() ;
				
				// 此Socket用于和服务器通信
				Socket s = new Socket("127.0.0.1", 8000) ;

				OutputStream os = s.getOutputStream() ;
				OutputStreamWriter osw = new OutputStreamWriter(os) ;
				PrintWriter pw = new PrintWriter(osw, true) ;

				pw.println(user + "%" + pass) ;

				// 接受服务器发送回来的确认信息
				InputStream is = s.getInputStream() ;
				InputStreamReader isr = new InputStreamReader(is) ;
				BufferedReader br = new BufferedReader(isr) ;

				String yorn = br.readLine() ;

				// 显示主窗体
				if (yorn.equals("ok")) {
					QQMain w = new QQMain(user) ;
					w.setSocket(s) ;
					w.setVisible(true) ;
					this.setVisible(false) ;
				} else {
					JOptionPane.showMessageDialog(this, "对不起，验证失败") ;
				}
			} catch (Exception err) {
				err.printStackTrace() ;
			}
		}
	}
}
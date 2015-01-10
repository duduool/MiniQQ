/*
 * QQLogin,��½������߼�
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
		// ���� runnable �� run ������ EventQueue ��ָ���߳��ϱ����á�
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// ʹ�� LookAndFeel �������õ�ǰ��Ĭ����ۡ�
					UIManager.setLookAndFeel(new NimbusLookAndFeel()) ;// ����һ���ҳ�Ư�������
					QQLogin w = new QQLogin() ;
					w.setVisible(true) ;
				} catch (Exception e) {
					e.printStackTrace() ;
				}
			}
		}) ;
	}
	
	// �������
	JLabel jl1, jl2, jl3, jl4, jl5 ;
	JPanel jp1, jp2 ;
	JButton jb3 ;
	JCheckBox jcb1, jcb2 ;
	JTextField txtUser ;
	JPasswordField txtPass ;
	
	public QQLogin() {
		/* ������� */
		Font font = new Font("����", Font.BOLD, 15) ;
		// ����
		jl1 = new JLabel(new ImageIcon("image/tou.jpg")) ;

		// �в�
		jp2 = new JPanel() ;
		txtUser = new JTextField(15) ;
		txtPass = new JPasswordField(15) ;
		jl2 = new JLabel(" QQ����  ", JLabel.CENTER) ;
		jl2.setFont(font) ;
		jl3 = new JLabel(" QQ����  ", JLabel.CENTER) ;
		jl3.setFont(font) ;
		jl4 = new JLabel(" ע���˺�", JLabel.CENTER) ;
		jl4.setForeground(Color.BLUE) ;
		jl4.setFont(font) ;
		jl5 = new JLabel(" ��������", JLabel.CENTER) ;
		jl5.setForeground(Color.BLUE) ;
		jl5.setFont(font) ;
		jcb1 = new JCheckBox("��ס����") ;
		jcb1.setFont(font) ;
		jcb2 = new JCheckBox("�Զ���¼") ;
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

		// �ϲ�
		jp1 = new JPanel() ;
		jb3 = new JButton("     ��¼      ") ;
		jb3.setFont(font) ;
		jp1.add(jb3) ;
		jb3.addActionListener(this) ;
		jl4.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() >= 1) {
					JOptionPane.showMessageDialog(null, "ע��!") ;
				}
			}
		}) ;

		// ������
		this.add(jl1, "North") ;// jpgͼƬ
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
		if (e.getActionCommand().equals("     ��¼      ")) {
			try {
				// �����û��������뵽������
				String user = txtUser.getText() ;
				String pass = txtPass.getText() ;
				
				// ��Socket���ںͷ�����ͨ��
				Socket s = new Socket("127.0.0.1", 8000) ;

				OutputStream os = s.getOutputStream() ;
				OutputStreamWriter osw = new OutputStreamWriter(os) ;
				PrintWriter pw = new PrintWriter(osw, true) ;

				pw.println(user + "%" + pass) ;

				// ���ܷ��������ͻ�����ȷ����Ϣ
				InputStream is = s.getInputStream() ;
				InputStreamReader isr = new InputStreamReader(is) ;
				BufferedReader br = new BufferedReader(isr) ;

				String yorn = br.readLine() ;

				// ��ʾ������
				if (yorn.equals("ok")) {
					QQMain w = new QQMain(user) ;
					w.setSocket(s) ;
					w.setVisible(true) ;
					this.setVisible(false) ;
				} else {
					JOptionPane.showMessageDialog(this, "�Բ�����֤ʧ��") ;
				}
			} catch (Exception err) {
				err.printStackTrace() ;
			}
		}
	}
}
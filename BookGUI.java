import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class BookGUI {
	// 把数据库连接变成全局变量
	static Connection conn = null;

	public static void main(String[] args) {
		// 1.连接数据库
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/library?useSSl=false&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "root", "123456");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 2.创建窗口
		JFrame frame = new JFrame("图书管理系统-图形版");
		frame.setSize(400, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new FlowLayout());

		// 3.创建输入框和标签
		JLabel nameJLabel = new JLabel("书名:");
		JTextField nameField = new JTextField(20);

		JLabel authorJLabel = new JLabel("作者:");
		JTextField authorField = new JTextField(20);

		JLabel pricJLabel = new JLabel("价格:");
		JTextField priceField = new JTextField(20);

		// 4.创建按钮
		JButton addButton = new JButton("添加图书");

		// 5.给按钮加一个"点击事件" (核心)
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// 获取输入框里的内容
					String name = nameField.getText();
					String author = authorField.getText();
					double price = Double.parseDouble(priceField.getText());

					// 往数据库里存
					String sql = "INSERT INTO books (name,author,price)VALUES(?,?,?)";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1, name);
					ps.setString(2, author);
					ps.setDouble(3, price);
					ps.executeUpdate();
					ps.close();

					// 弹窗提示成功
					JOptionPane.showMessageDialog(frame, "成功添加一本书! ");

					// 输入清楚框
					nameField.setText("");
					authorField.setText("");
					priceField.setText("");
				} catch (Exception e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(frame, "添加失败,请检查输入!");
				}

			}
		});

		// 6.把所有组件加到窗口上
		frame.add(nameJLabel);
		frame.add(nameField);
		frame.add(authorJLabel);
		frame.add(authorField);
		frame.add(pricJLabel);
		frame.add(priceField);
		frame.add(addButton);

		// 7.显示窗口
		frame.setVisible(true);
	}

}

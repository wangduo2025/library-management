import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class BookGUI {
	// 把数据库连接变成全局变量
	static Connection conn = null;

	public static void main(String[] args) {
		// 1. 连接数据库
		try {
			conn = DBUtil.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 2. 创建窗口
		JFrame frame = new JFrame("图书管理系统-图形版");
		frame.setSize(930, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new FlowLayout());

		// 3. 创建输入框和标签
		JLabel nameLabel = new JLabel("书名：");
		JTextField nameField = new JTextField(20);

		JLabel authorLabel = new JLabel("作者：");
		JTextField authorField = new JTextField(20);

		JLabel priceLabel = new JLabel("价格：");
		JTextField priceField = new JTextField(20);

		// 4. 创建"添加"按钮和"查看"按钮
		JButton addButton = new JButton("添加图书");
		JButton showButton = new JButton("查看所有图书");
		JTextArea textArea = new JTextArea(10, 30);
		JTextField idField = new JTextField(10);
		JButton deleteButton = new JButton("按id删除");
		JTextField searchField = new JTextField(10);
		JButton searchButton = new JButton("按书名搜索");
		JTextField updateNameField = new JTextField(10);
		JTextField updatePriceField = new JTextField(10);
		JButton updateButton = new JButton("修改价格");

		// 5. "添加"按钮的点击事件
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String name = nameField.getText();
					String author = authorField.getText();
					double price = Double.parseDouble(priceField.getText());

					String sql = "INSERT INTO books (name, author, price) VALUES (?, ?, ?)";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1, name);
					ps.setString(2, author);
					ps.setDouble(3, price);
					ps.executeUpdate();
					ps.close();

					JOptionPane.showMessageDialog(frame, "成功添加一本书！");
					nameField.setText("");
					authorField.setText("");
					priceField.setText("");
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(frame, "添加失败，请检查输入！");
				}
			}
		});

		// 6. "查看"按钮的点击事件
		showButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					textArea.setText("");
					String sql = "SELECT * FROM books";
					PreparedStatement ps = conn.prepareStatement(sql);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						textArea.append("ID:" + rs.getInt("id") + " 书名:" + rs.getString("name") + " 作者:"
								+ rs.getString("author") + " 价格:" + rs.getDouble("price") + "\n");
					}
					rs.close();
					ps.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		// 7. "删除"按钮的点击事件
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(idField.getText());
					String sql = "DELETE FROM books WHERE id = ?";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setInt(1, id);
					int rows = ps.executeUpdate();
					ps.close();
					JOptionPane.showMessageDialog(frame, "成功删除" + rows + "本书!");
					idField.setText("");
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(frame, "删除失败,请检查id");
				}

			}
		});

		// 8. "搜索"按钮的点击事件
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					textArea.setText("");
					String keyword = searchField.getText();
					String sql = "SELECT * FROM books WHERE name LIKE ?";
					PreparedStatement pS = conn.prepareStatement(sql);
					pS.setString(1, "%" + keyword + "%");
					ResultSet rs = pS.executeQuery();

					while (rs.next()) {
						textArea.append("ID:" + rs.getInt("id") + "书名:" + rs.getString("name") + "作者:"
								+ rs.getString("author") + "价格:" + rs.getDouble("price"));
					}
					rs.close();
					pS.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});

		// 9. "修改"按钮的点击事件
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String name = updateNameField.getText();
					double newPrice = Double.parseDouble(updatePriceField.getText());

					String sql = "UPDATE books SET price = ? WHERE name LIKE ?";
					PreparedStatement pS = conn.prepareStatement(sql);
					pS.setDouble(1, newPrice);
					pS.setString(2, "%" + name + "%");
					
					int rows = pS.executeUpdate();
					pS.close();

					JOptionPane.showMessageDialog(frame, "成功修改" + rows + "本书!");
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(frame, "修改失败,请检查输入");
				}

			}
		});

		// 10. 把所有组件加到窗口上
		// 分组1:添加图书
		JPanel panel1 = new JPanel(new FlowLayout());
		panel1.add(nameLabel);
		panel1.add(nameField);
		panel1.add(authorLabel);
		panel1.add(authorField);
		panel1.add(priceLabel);
		panel1.add(priceField);
		panel1.add(addButton);

		// 分组2:删除,搜索和修改
		JPanel panel2 = new JPanel(new FlowLayout());
		panel2.add(idField);
		panel2.add(deleteButton);
		panel2.add(searchField);
		panel2.add(searchButton);
		panel2.add(updateNameField);
		panel2.add(updatePriceField);
		panel2.add(updateButton);

		// 分组3:显示结果
		JPanel panel3 = new JPanel(new FlowLayout());
		panel3.add(showButton);
		panel3.add(textArea);

		frame.add(panel1);
		frame.add(panel2);
		frame.add(panel3);

		// 11. 显示窗口
		frame.setVisible(true);
	}
}
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC";
            conn = DriverManager.getConnection(url, "root", "123456");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. 创建窗口
        JFrame frame = new JFrame("图书管理系统-图形版");
        frame.setSize(550,600);
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
                        textArea.append("ID:" + rs.getInt("id") + " 书名:" + rs.getString("name") + " 作者:" + rs.getString("author") + " 价格:" + rs.getDouble("price") + "\n");
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
        
        //8. "搜索"按钮的点击事件
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
						textArea.append("ID:" + rs.getInt("id") + "书名:" + rs.getString("name") + "作者:" + rs.getString("author") + "价格:" + rs.getDouble("price"));
					}
					rs.close();
					pS.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}	
				
			}
		});
        
        // 9. 把所有组件加到窗口上
        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(authorLabel);
        frame.add(authorField);
        frame.add(priceLabel);
        frame.add(priceField);
        frame.add(addButton);
        frame.add(showButton);
        frame.add(textArea);
        frame.add(idField);
        frame.add(deleteButton);
        frame.add(searchField);
        frame.add(searchButton);

        // 10. 显示窗口
        frame.setVisible(true);
    }
}
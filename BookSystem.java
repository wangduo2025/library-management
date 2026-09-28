import java.sql.*;
import java.util.Scanner;
public class BookSystem {
	//把数据库连接变成"全局变量",方便下面所有的方法共用
	static Connection conn = null;
	
	public static void main(String[] args) {
		try {
			//1.连接数据库(只连一次)
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/library?useSSl=false&serveTimezone=UTC";
			conn = DriverManager.getConnection(url,"root","123456");
			Scanner sc = new Scanner(System.in);
			
			//2.循环显示菜单,直到用户退出
			while (true) {
				System.out.println("\n==== 图书管理系统 ====");
				System.out.println("1.添加图书");
				System.out.println("2.查询所有图书");
				System.out.println("3.修改图书价格");
				System.out.println("4.删除图书");
				System.out.println("5.退出");
				System.out.println("请输入你的选择");
				
				int choice =sc.nextInt(); //等待用户输入数字
				
				if (choice == 1) {
					addBook(sc); //调用"添加图书"的方法
				} else if (choice == 2){
					showBooks(); //调用"查看图书"的方法
				} else if (choice == 3) {
					updateBook(sc);
				} else if (choice == 4) {
					deleteBook(sc);
				} else if (choice == 5){
					System.out.println("再见!");
					break; //跳出循环,程序结束
				}else {
					System.out.println("输入有误,请重新输入!");
				}
			}
			
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// =========== 方法1: 查看所有图书 ========
	private static void showBooks() {
		try {
		    String sql = "SELECT * FROM books";
		    PreparedStatement ps = conn.prepareStatement(sql);
		    ResultSet rs = ps.executeQuery();
		    System.out.println("--- 当前所有图书 ---");
		    while (rs.next()) {
		        System.out.println("ID:" + rs.getInt("id") + " 书名:" + rs.getString("name") + " 作者:" + rs.getString("author") + " 价格:" + rs.getDouble("price"));
		    }
		    ps.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
	}

	// =========== 方法2: 添加图书 ========
	private static void addBook(Scanner sc) {
		try {
		    System.out.print("请输入书名：");
		    String name = sc.next();
		    System.out.print("请输入作者：");
		    String author = sc.next();
		    System.out.print("请输入价格：");
		    double price = sc.nextDouble();

		    String sql = "INSERT INTO books (name, author, price) VALUES (?, ?, ?)";
		    PreparedStatement ps = conn.prepareStatement(sql);
		    ps.setString(1, name);
		    ps.setString(2, author);
		    ps.setDouble(3, price);
		    int rows = ps.executeUpdate();
		    System.out.println("成功添加 " + rows + " 本书！");
		    ps.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
	}
	
	// ======== 方法3:删除图书 ========
	public static void  deleteBook(Scanner sc) {
		try {
			System.out.println("请输入要删除的书名: ");
			String name = sc.next();
			
			String sql = "DELETE FROM books WHERE name LIKE ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,"%" + name + "%");
			int rows = ps.executeUpdate();
			System.out.println("成功删除" + rows + "本书!");
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// ======== 方法4:修改图书价格 ========
	public static void updateBook(Scanner sc) {
		try {
			System.out.println("请输入要修改的书名");
			String name = sc.next();
			System.out.println("请输入新的价格");
			double newPrice =sc.nextDouble();
			
			String sql = "UPDATE books SET price = ? WHERE name = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, newPrice);
			ps.setString(2, name);
			int rows = ps.executeUpdate();
			System.out.println("成功修改" + rows + "本书!");
			ps.close();
		} catch (Exception e) {
				e.printStackTrace();
			}
		
		
 }
	
	
	
	
	
}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
public class BookManager {

	public static void main(String[] args) {
		try {
			//1.加载驱动
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//2.建立连接
			String url = "jdbc:mysql://localhost:3306/library?useSSL=false&serveTimezone=UTC";
			String user ="root";
			String password ="123456";
			Connection conn =DriverManager.getConnection(url,user,password);
			
			//3.准备SQL语句：往books表里插入一条数据
			String sql ="INSERT INTO books(name,author,price) VALUES(?,?,?)";
			
			//4.创建“预编译执行者”，并把SQL交给它
			PreparedStatement pS =conn.prepareStatement(sql);
			
			//5.给问号（？）填充上内容
			pS.setString(1,"西游记" );
			pS.setString(2,"吴承恩" );
			pS.setDouble(3, 39.9);
			
			//6.正式执行插入操作
			int rows =pS.executeUpdate();
			System.out.println("成功插入"+rows+"本书");
			
			//7.关闭连接
			pS.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

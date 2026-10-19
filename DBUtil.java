import java.sql.Connection;
import java.sql.DriverManager;
	public class DBUtil {
		//数据库连接信息
		private static final String URL = "jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC";
		private static final String USER = "root";
		private static final String PASSWORD = "123456";
		
		//静态代码块:类加载时自动执行一次,用来加载驱动
		static {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//提供一个方法,让外面的人拿到数据库连接
		public static Connection getConnection() throws Exception {
			return DriverManager.getConnection(URL,USER,PASSWORD);
		}	
}

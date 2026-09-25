import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {
    public static void main(String[] args) {
        try {
            // 1. 加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. 建立连接
            String url = "jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC";
            String user = "root";
            String password = "123456"; // 换成你设的密码

            Connection conn = DriverManager.getConnection(url, user, password);

            // 3. 如果没报错，说明连上了
            System.out.println("连接成功！你的Java已经能跟MySQL说话了！");

            // 4. 关掉连接
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
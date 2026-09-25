import java.sql.*;

public class BookCRUD {
    public static void main(String[] args) {
        try {
            // 1. 加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. 建立连接
            String url = "jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC";
            String user = "root";
            String password = "123456"; // 你的密码
            Connection conn = DriverManager.getConnection(url, user, password);
            
            // ===== 动作1：查（全表查询） =====
            System.out.println("--- 当前所有图书 ---");
            String sql1 = "SELECT * FROM books";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                System.out.println("ID:" + rs1.getInt("id") + " | 书名:" + rs1.getString("name") + " | 作者:" + rs1.getString("author") + " | 价格:" + rs1.getDouble("price"));
            }
            ps1.close();

            // ===== 动作2：改（改《活着》的价格为99.9） =====
            System.out.println("\n--- 修改《活着》的价格 ---");
            String sql2 = "UPDATE books SET price = ? WHERE name = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setDouble(1, 99.9);
            ps2.setString(2, "活着");
            int rows2 = ps2.executeUpdate();
            System.out.println("修改了 " + rows2 + " 本书");
            ps2.close();

            // ===== 动作3：删（删掉《水浒传》） =====
            System.out.println("\n--- 删除《水浒传》 ---");
            String sql3 = "DELETE FROM books WHERE name = ?";
            PreparedStatement ps3 = conn.prepareStatement(sql3);
            ps3.setString(1, "水浒传");
            int rows3 = ps3.executeUpdate();
            System.out.println("删除了 " + rows3 + " 本书");
            ps3.close();

            // ===== 最后：再查一次，看看结果 =====
            System.out.println("\n--- 操作后的图书列表 ---");
            PreparedStatement ps4 = conn.prepareStatement("SELECT * FROM books");
            ResultSet rs4 = ps4.executeQuery();
            while (rs4.next()) {
                System.out.println("ID:" + rs4.getInt("id") + " | 书名:" + rs4.getString("name") + " | 作者:" + rs4.getString("author") + " | 价格:" + rs4.getDouble("price"));
            }
            ps4.close();

            // 关闭连接
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

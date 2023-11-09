import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.example.dto.Env;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JdbcTest {

    @Test
    @DisplayName("table 생성 테스트")
    void createTableTest(){


        String createTable = "CREATE TABLE quote ("
                + "id INT PRIMARY KEY AUTO_INCREMENT,\n "
                + "content VARCHAR(100) NOT NULL,\n "
                + "author VARCHAR(30) NOT NULL,\n "
                + "created DATETIME NOT NULL\n "
                + ");";
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        System.out.println(createTable);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/quote_board?", Env.mySqlId, Env.mySqlPassword);
            stmt = conn.createStatement();
            stmt.executeUpdate(createTable);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}

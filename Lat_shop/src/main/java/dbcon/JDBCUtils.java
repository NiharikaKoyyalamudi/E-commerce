package dbcon;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {

	private static Connection con;

	public static Connection getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			FileInputStream fis = new FileInputStream(
					"C:\\Users\\Niharika\\eclipse-workspace\\Lat_shop\\src\\main\\java\\db.properties");
			Properties prop = new Properties();
			prop.load(fis);
			con = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"),
					prop.getProperty("password"));
			fis.close();
		} catch (SQLException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void closeConnection(Connection con, ResultSet rs, Statement st) {
		try {

			if (con != null) {
				con.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

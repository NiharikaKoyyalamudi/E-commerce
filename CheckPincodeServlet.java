package mvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import com.pennant.JdbcConnections.JDBCUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CheckPincodeServlet")
public class CheckPincodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pincode = Integer.parseInt(request.getParameter("pincode"));
		int productId = Integer.parseInt(request.getParameter("productId"));
		boolean validPincode = checkPincode(pincode, productId);

		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("valid", validPincode);

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jsonResponse.toString());
	}

	private boolean checkPincode(int pincode, int productId) {

		String query = "SELECT COUNT(*) FROM ServiceableRegions_06 s "
				+ "INNER JOIN ProductCategoryWiseServiceableRegions_06 p " + "ON s.srrg_id = p.srrg_id "
				+ "WHERE ? BETWEEN s.srrg_pinfrom AND s.srrg_pinto " + "AND p.prct_id = ?";

		try (Connection conn = JDBCUtils.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setLong(1, pincode);
			ps.setInt(2, productId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					int count = rs.getInt(1);
					return count > 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}

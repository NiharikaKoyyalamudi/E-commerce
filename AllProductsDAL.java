package mvc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pennant.JdbcConnections.JDBCUtils;

public class AllProductsDAL {

	public boolean CheckValidCoupon(String dcode, double total) {
		boolean couponApplied = false;

		try {
			// Connect to the database
			Connection conn = JDBCUtils.getConnection();
			String sql = "SELECT cpn_id FROM Coupons_06 WHERE cpn_code = ? AND ? BETWEEN cpn_minval AND cpn_maxval";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, dcode);
			stmt.setDouble(2, total);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int remainingCoupons = rs.getInt("cpn_id");
				if (remainingCoupons > 0) {
					couponApplied = true;
					UpdateCoupon(dcode);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return couponApplied;
	}

	public void UpdateCoupon(String dcode) {
		try {
			// Connect to the database
			Connection conn = JDBCUtils.getConnection();
			String sql = "UPDATE Coupons_06 SET cpn_noc = cpn_noc - 1 WHERE cpn_code = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, dcode);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getCouponID(String dcode) {
		int cpid = 0;
		try {
			// Connect to the database
			Connection conn = JDBCUtils.getConnection();
			String sql = "SELECT cpn_id FROM Coupons_06 WHERE cpn_code = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, dcode);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				cpid = rs.getInt("cpn_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cpid;
	}

	public double getCouponAmount(int cpid) {
		double p = 0;
		try {
			// Connect to the database
			Connection conn = JDBCUtils.getConnection();
			String sql = "SELECT cpn_amount FROM Coupons_06 WHERE cpn_id= ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cpid);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				p = rs.getDouble("cpn_amount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
}

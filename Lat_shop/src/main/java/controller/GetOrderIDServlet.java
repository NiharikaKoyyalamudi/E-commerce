package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbcon.JDBCUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;


@WebServlet("/GetOrderIDServlet")
public class GetOrderIDServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String customerId = request.getParameter("customerId");
     

        try (Connection  conn = JDBCUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT ord_id FROM orders WHERE cust_id = ?")) {
            stmt.setString(1, customerId);
            ResultSet rs = stmt.executeQuery();

            String orderId = "";
            if (rs.next()) {
                orderId = rs.getString("ord_id");
            }

            request.setAttribute("orderId", orderId);
            request.getRequestDispatcher("final.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
}

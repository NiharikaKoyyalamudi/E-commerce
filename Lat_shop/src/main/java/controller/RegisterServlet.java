package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dbcon.JDBCUtils;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegisterServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String name = request.getParameter("name");
        String mobile = request.getParameter("mobile");
        String location = request.getParameter("location");
        String address = request.getParameter("address");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
     
        Connection conn = null;
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;

        try {
        	 conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false); // Disable auto-commit to ensure both inserts are successful

            // Insert into users table
            String sql1 = "INSERT INTO users (user_name, pass_word) VALUES (?, ?)";
            stmt1 = conn.prepareStatement(sql1);
            stmt1.setString(1, username);
            stmt1.setString(2, password);
            stmt1.executeUpdate();

            // Insert into customers table
            String sql2 = "INSERT INTO customers (cust_name, phone_no, location, address, user_name) VALUES (?, ?, ?, ?, ?)";
            stmt2 = conn.prepareStatement(sql2);
            stmt2.setString(1, name);
            stmt2.setString(2, mobile);
            stmt2.setString(3, location);
            stmt2.setString(4, address);
            stmt2.setString(5, username);
            stmt2.executeUpdate();

            conn.commit(); // Commit the transaction if both inserts are successful
            response.sendRedirect("login.jsp");
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback the transaction if an error occurs
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            response.getWriter().write("Error processing registration");
        } finally {
            try {
                if (stmt1 != null)
                    stmt1.close();
                if (stmt2 != null)
                    stmt2.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    }



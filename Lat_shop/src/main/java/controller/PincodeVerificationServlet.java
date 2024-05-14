package controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.sql.*;



public class PincodeVerificationServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int productId = Integer.parseInt(request.getParameter("productId"));
	    int pincode = Integer.parseInt(request.getParameter("pincode"));

	    // Database connection parameters
	    String url = "jdbc:postgresql://localhost:5432/postgres";
	    String user = "postgres";
	    String password = "niha123";

	    boolean isServiceable = false;

	    try {
	        // Load the PostgreSQL JDBC driver
	        Class.forName("org.postgresql.Driver");

	        // Connect to the database
	        Connection connection = DriverManager.getConnection(url, user, password);

	        // Create a statement
	        Statement statement = connection.createStatement();

	        // Execute the query to check if the product's region is serviceable
	     // Execute the query to check if the product's region is serviceable
	        String query="SELECT COUNT(*) \r\n"
	        		+ "FROM ProductCategoryWiseServiceableRegions AS pcsr\r\n"
	        		+ "JOIN ServiceableRegions AS sr ON pcsr.srrg_id = sr.srrg_id\r\n"
	        		+ "WHERE pcsr.prct_id = ? \r\n"
	        		+ "  AND sr.srrg_pinfrom <= ? \r\n"
	        		+ "  AND sr.srrg_pinto >= ?\r\n"
	        		+ "";
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, productId);
	        preparedStatement.setInt(2, pincode);
	        preparedStatement.setInt(3, pincode);

	        ResultSet resultSet = preparedStatement.executeQuery();

	        // If the product's region is found in the database, it's serviceable
	        if (resultSet.next()) {
	            int count = resultSet.getInt(1);
	            if (count > 0) {
	                isServiceable = true;
	            }
	        }

	        // Close the connections
	        resultSet.close();
	        preparedStatement.close();
	        statement.close();
	        connection.close();
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }

	    response.setContentType("text/plain");
	    PrintWriter out = response.getWriter();
	    out.print(isServiceable);
	}

}

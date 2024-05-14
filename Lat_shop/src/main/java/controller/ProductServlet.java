package controller;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Products;

//Import statements

public class ProductServlet extends HttpServlet {
 private static final long serialVersionUID = 1L;

 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     String category = request.getParameter("Categories");
     String minPrice = request.getParameter("minPrice");
     String maxPrice = request.getParameter("maxPrice");

     String url = "jdbc:postgresql://localhost:5432/postgres";
     String username = "postgres";
     String password = "niha123";

     Connection conn = null;
     PreparedStatement stmt = null;
     ResultSet rs = null;

     List<Products> products = new ArrayList<>();

     try {
         Class.forName("org.postgresql.Driver");
         conn = DriverManager.getConnection(url, username, password);

         String query = "SELECT * FROM Products p JOIN ProductCategories pc ON p.prod_prct_id = pc.prct_id WHERE pc.prct_title = ?";

         if (minPrice != null && !minPrice.isEmpty() && maxPrice != null && !maxPrice.isEmpty()) {
             query += " AND p.prod_price BETWEEN ? AND ?";
         }

         stmt = conn.prepareStatement(query);
         stmt.setString(1, category);

         if (minPrice != null && !minPrice.isEmpty() && maxPrice != null && !maxPrice.isEmpty()) {
             stmt.setInt(2, Integer.parseInt(minPrice));
             stmt.setInt(3, Integer.parseInt(maxPrice));
         }

         rs = stmt.executeQuery();

         while (rs.next()) {
             Products product = new Products();
             product.setProd_id(rs.getInt("prod_id"));
             product.setProd_title(rs.getString("prod_title"));
             product.setProd_price(rs.getInt("prod_price"));
             product.setProd_image(rs.getString("prod_image"));
             products.add(product);
         }

         request.setAttribute("products", products);
         request.getRequestDispatcher("products.jsp").forward(request, response);
     } catch (SQLException | ClassNotFoundException e) {
         e.printStackTrace();
     } finally {
         try {
             if (rs != null) rs.close();
             if (stmt != null) stmt.close();
             if (conn != null) conn.close();
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
 }
}

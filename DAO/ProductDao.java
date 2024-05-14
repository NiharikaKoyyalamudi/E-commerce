package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Products;

public class ProductDao {
    private final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String DB_USER = "postgres";
    private final String DB_PASSWORD = "niha123";

    public List<Products> getAllProducts() throws SQLException {
        List<Products> productsList = new ArrayList<>();
        String query = "SELECT * FROM Products";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int prod_id = rs.getInt("prod_id");
                String prod_title = rs.getString("prod_title");
                int prod_prct_id = rs.getInt("prod_prct_id");
                int prod_hsnc_id = rs.getInt("prod_hsnc_id");
                String prod_brand = rs.getString("prod_brand");
                String prod_image = rs.getString("prod_image");
                double prod_price = rs.getDouble("prod_price");

                Products product = new Products();
                product.setProd_id(prod_id);
                product.setProd_title(prod_title);
                product.setProd_prct_id(prod_prct_id);
                product.setProd_hsnc_id(prod_hsnc_id);
                product.setProd_brand(prod_brand);
                product.setProd_image(prod_image);
                product.setProd_price(prod_price);

                productsList.add(product);
            }
        }
        return productsList;
    }
}

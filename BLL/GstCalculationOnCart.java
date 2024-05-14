package BLL;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import dbcon.JDBCUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/GstCalculationOnCart")
public class GstCalculationOnCart extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double totalAmount = 0.0;
        double totalGst = 0.0;
        double totalShippingCharges = 0.0;
        double totalGstOnShippingCharges = 0.0;


        try {
        	 Connection conn = JDBCUtils.getConnection();
            List<model.Products> cartItems = (List<model.Products>) request.getSession().getAttribute("cartItems");

            if (cartItems != null) {
                for (model.Products item : cartItems) {
                    String hsncQuery = "SELECT hsnc_gstc_percentage FROM HSNCodes WHERE hsnc_id = " + item.getProd_hsnc_id();
                    Statement statement = conn.createStatement();
                    ResultSet hsncResult = statement.executeQuery(hsncQuery);

                    if (hsncResult.next()) { // Move cursor to the first row
                        double gstPercentage = hsncResult.getDouble("hsnc_gstc_percentage");
                        double itemPrice = item.getProd_price();
                        double gstAmount = (itemPrice * gstPercentage) / 100;
                        double totalItemPrice = itemPrice + gstAmount;
                        totalAmount += totalItemPrice;
                        totalGst += gstAmount;
                    }

                    String shippingQuery = "SELECT orvl_shippingamount FROM OrderValueWiseShippingCharges WHERE orvl_from <= " + totalAmount + " AND orvl_to >= " + totalAmount;
                    ResultSet shippingResult = statement.executeQuery(shippingQuery);

                    if (shippingResult.next()) { // Move cursor to the first row
                        double shippingCharges = shippingResult.getDouble("orvl_shippingamount");
                        totalShippingCharges += shippingCharges;
                    }

                    String shippingGstQuery = "SELECT hsnc_gstc_percentage FROM HSNCodes WHERE hsnc_id = " + item.getProd_hsnc_id();
                    ResultSet shippingGstResult = statement.executeQuery(shippingGstQuery);

                    if (shippingGstResult.next()) { // Move cursor to the first row
                        double gstOnShippingCharges = shippingGstResult.getDouble("hsnc_gstc_percentage");
                        totalGstOnShippingCharges += (totalShippingCharges * gstOnShippingCharges) / 100;
                    }

                    statement.close();
                }
            }

            double grandTotal = totalAmount + totalShippingCharges + totalGstOnShippingCharges;

            request.setAttribute("totalAmount", String.format("%.2f", totalAmount));
            request.setAttribute("totalGst", String.format("%.2f", totalGst));
            request.setAttribute("totalShippingCharges", String.format("%.2f", totalShippingCharges));
            request.setAttribute("totalGstOnShippingCharges", String.format("%.2f", totalGstOnShippingCharges));
            request.setAttribute("grandTotal", String.format("%.2f", grandTotal));

            request.getRequestDispatcher("cart.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}

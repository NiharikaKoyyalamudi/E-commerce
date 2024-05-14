<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Products" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    // Get cart items from session
    List<model.Products> cartItems = (List<model.Products>) request.getSession().getAttribute("cart");

    // Calculate total price
    double totalPrice = 0.0;
    if (cartItems != null) {
        for (model.Products item : cartItems) {
            totalPrice += item.getProd_price();
        }
    }

    // Generate order ID
    String orderId = "ORD" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

    // Generate customer ID
    String customerId = "CUST" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

    // Calculate GST
    double gst = totalPrice * 0.18;

    // Calculate total bill including GST
    double totalBill = totalPrice + gst;
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Final Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-image: url("hpy.jpg");
            
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .order-details {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 20px;
            max-width: 400px;
            margin: 20px;
            text-align: center;
            color: #333;
        }

        h2 {
            color: #007bff;
            margin-bottom: 20px;
        }

        .thank-you {
            color: #28a745;
            margin-top: 20px;
        }
    </style>
</head>

<body>
    <div class="order-details">
        <h2>Order Details</h2>
        <p><strong>Order ID:</strong> <%= orderId %></p>
        <p><strong>Customer ID:</strong> <%= customerId %></p>
        <p><strong>Order Date:</strong> <%= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) %></p>
        <p><strong>Total Price:</strong> $<%= totalPrice %></p>
        <p><strong>GST (18%):</strong> $<%= gst %></p>
        <p><strong>Total Bill (including GST):</strong> $<%= totalBill %></p>
        <div class="thank-you">
            <p>Thank you for shopping with us!</p>
        </div>
    </div>
</body>

</html>
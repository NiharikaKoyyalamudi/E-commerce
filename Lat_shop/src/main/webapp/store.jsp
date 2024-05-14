<%@ page import="java.util.List" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.ArrayList" %>

<%
// Define database connection parameters
String url = "jdbc:postgresql://localhost:5432/postgres";
String username = "postgres";
String password = "niha123";

// Initialize database connection
Connection conn = null;
Statement stmt = null;
ResultSet rs = null;
List<String> categories = new ArrayList<>();

try {
    // Register JDBC driver
    Class.forName("org.postgresql.Driver");

    // Open a connection
    conn = DriverManager.getConnection(url, username, password);

    // Execute a query to fetch categories from productCategories table
    stmt = conn.createStatement();
    rs = stmt.executeQuery("SELECT prct_title FROM productCategories");

    // Add categories to the list
    while (rs.next()) {
        categories.add(rs.getString("prct_title"));
    }
} catch (SQLException se) {
    se.printStackTrace();
} catch (Exception e) {
    e.printStackTrace();
} finally {
    // Close resources
    try {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
    } catch (SQLException se) {
        se.printStackTrace();
    }
}
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Store</title>
    <style>
    
	  .header {
            height: 80px;
            width: 100%;
            background-color: #343a40;
            color: white;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 15px;
            box-sizing: border-box;
        }
		h1{
		text-align:center;
		margin-right:550px;
		}
        .header .logo {
            height:70px;
            width :70px;
            border-radius:50px;
        }
      body {
    font-family: Arial, sans-serif;
    background-image: url('https://png.pngtree.com/thumb_back/fh260/back_our/20190619/ourmid/pngtree-shopping-mall-supermarket-selection-merchandise-poster-background-material-image_133225.jpg');
    background-size: cover;
    background-repeat: no-repeat;
    background-attachment: fixed;
    margin: 0;
    padding: 0;
    display: flex;
    flex-direction: column;
    align-items: center;
    min-height: 100vh;
    color: #333;
}

h1 {
    margin-top: 20px;
    text-align: center;
    color: #fff; /* White text color */
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5); /* Text shadow for better readability */
}

form {
    margin-top: 20px;
    display: flex;
    flex-wrap: wrap; /* Wrap form elements if they exceed the width */
    justify-content: center;
    align-items: center;
    background-color: rgba(255, 255, 255, 0.8); /* Semi-transparent white background */
    padding: 10px;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3); /* Box shadow for a raised effect */
}

select, input[type="text"], input[type="submit"] {
    padding: 5px;
    margin: 5px;
    border-radius: 5px;
    border: 1px solid #ccc;
    font-size: 14px;
}

input[type="submit"] {
    padding: 5px 10px;
    background-color: #007bff; /* Blue button color */
    color: white;
    border: none;
    cursor: pointer;
    transition: background-color 0.3s;
}

input[type="submit"]:hover {
    background-color: #0056b3; /* Darker blue on hover */
}

@media only screen and (max-width: 600px) {
    form {
        width: 90%;
    }
}

    </style>
</head>
<body>
    
     <div class="header">
        <img src="shoplogo.jpg" class="logo">
         <center><h1>Welcome to our Online Store</h1></center>
        <div class="cart"><img src="cartimg.png" width="30px" height="30px"/><p id="count">0</p></div>
    </div>

    <!-- Category and Price Range Search Form -->
    <form id="searchForm" action="ProductServlet" method="get">
    
 		 <select name="Categories" id="Categories">
        <option value="">All Categories</option>
        <% for (String category : categories) { %>
            <option value="<%= category %>"><%= category %></option>
        <% } %>
    </select>



        Price Range:
        <select name="minPrice" id="minPrice">
            <option value="">Min</option>
            <option value="1000">1000</option>
            <option value="10001">10001</option>
            <option value="50001">50001</option>
            <!-- Add more options as needed -->
        </select>
        -
        <select name="maxPrice" id="maxPrice">
            <option value="">Max</option>
            <option value="10000">10000</option>
            <option value="50000">50000</option>
            <option value="100000">100000</option>
            <!-- Add more options as needed -->
        </select>
        <input type="submit" value="Search">
    </form>

    <!-- Display Products Here -->
    <!-- Your code to display products goes here -->

    <script>
        document.getElementById("searchForm").onsubmit = function() {
            var category = document.getElementById("category").value;
            var minPrice = document.getElementById("minPrice").value;
            var maxPrice = document.getElementById("maxPrice").value;
            var url = "ProductServlet?category=" + category + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice;
            window.location.href = url;
            return false; // Prevent the form from actually submitting
        };
    </script>

</body>

</html>
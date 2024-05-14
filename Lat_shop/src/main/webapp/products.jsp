<%@ page import="java.util.List" %>
<%@ page import="model.Products" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            display: flex;
            flex-direction: column;
            align-items: center;
            min-height: 100vh;
            padding: 20px;
        }

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
            margin-top: -16px;
        
        }

        .header .logo {
            font-size: 30px;
            font-weight: bold;
        }

        .cart {
            display: flex;
            background-color: white;
            justify-content: space-between;
            align-items: center;
            padding: 7px 10px;
            border-radius: 3px;
            width: 80px;
        }

        .cart p {
            height: 22px;
            width: 22px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 22px;
            background-color: #343a40;
            color: white;
            margin-left: 5px;
        }

        .container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            grid-gap: 20px;
            width: 100%;
            max-width: 1200px;
        }

        .box {
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 15px;
            background-color: white;
        }

        .img-box {
            height: 180px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-bottom: 15px;
        }

        .images {
            max-width: 100%;
            max-height: 100%;
            object-fit: cover;
            object-position: center;
        }
        .header .logo {
            height:70px;
            width :70px;
            border-radius:50px;
        }

        .bottom {
            text-align: center;
        }

        h2 {
            font-size: 20px;
            color: green;
            margin-bottom: 10px;
        }
        h1{
        color:white
        }

        button {
            width: 100%;
            border: none;
            border-radius: 5px;
            background-color: #343a40;
            padding: 7px 10px;
            cursor: pointer;
            color: white;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: #333;
        }

        .view-cart-btn {
            margin-top: 20px;
            background-color: #343a40;
            color: white;
            border: none;
            border-radius: 3px;
            padding: 5px 10px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s;
        }

        .view-cart-btn:hover {
            background-color: #333;
        }

        @media only screen and (max-width: 600px) {
            .header {
                padding: 10px;
            }

           

            .cart {
                width: 60px;
            }

            .box {
                padding: 10px;
            }
			
            h2 {
                font-size: 18px;
            }

            button {
                padding: 5px 8px;
            }

            .view-cart-btn {
                font-size: 12px;
                padding: 3px 6px;
            }
        }
    </style>
</head>
<body>
    <div class="header">
        <img src="shoplogo.jpg" class="logo">
        <center><h1>Online Shopping</h1></center>
        <button class="cart" onclick="window.location.href='AddToCartServlet'"><img src="cartimg.png" width="30px" height="30px"/><p id="count">0</p></button>
    </div>
	<br><br>
    <div class="container">
        <%
        List<model.Products> products1 = (List<model.Products>) request.getAttribute("products");
        if (products1 != null) {
            for (model.Products product : products1) {
        %>
        <div class='box'>
    <div class='img-box'>
        <img class='images' src="<%= product.getProd_image() %>">
    </div>
    <div class='bottom'>
        <p><%= product.getProd_title() %></p>
        <h2>$ <%= product.getProd_price() %>/-</h2>
        <button onclick="addtocart('<%= product.getProd_id() %>', '<%= product.getProd_title() %>', '<%= product.getProd_price() %>','<%= product.getProd_image() %>')" class="btn btn-primary">Add to Cart</button>
    </div>
</div>

        <%
            }
        }
        %>
    </div>

    <script src="https://kit.fontawesome.com/92d70a2fd8.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        var cart = [];

   
         function addtocart(prod_id, prod_title, prod_price, prod_image) {
        	    var pincode = prompt("Enter Pincode:");

        	    if (pincode === null || pincode === "") {
        	        alert("Please enter a valid pincode.");
        	        return;
        	    }

        	    if (pincode.trim() === "534313") {
        	        var data = {
        	            productId: prod_id,
        	            productName: prod_title,
        	            productPrice: prod_price,
        	            productImage: prod_image
        	        };

        	        $.ajax({
        	            type: "POST",
        	            url: "AddToCartServlet",
        	            data: data,
        	            success: function(response) {
        	                console.log(response);
        	                alert("Item added");
        	                // Increment the count
        	                var countElement = document.getElementById("count");
        	                var count = parseInt(countElement.textContent);
        	                countElement.textContent = count + 1;
        	            },
        	            error: function(xhr, status, error) {
        	                console.error(xhr.responseText);
        	                alert("Error");
        	            }
        	        });
        	    } else {
        	        alert("Sorry, we can't deliver to this pin code.");
        	    }
        	}

 function viewCart() {
     window.location.href = "AddToCartServlet";
 }
    </script>
</body>
</html>
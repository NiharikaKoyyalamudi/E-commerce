<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="model.Products" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping Cart</title>
       <script src="https://checkout.razorpay.com/v1/checkout.js"></script>
   <style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;

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
    .container {
        max-width: 500px;
        margin: 0 auto;
        padding: 20px;
    }

    .cart-item {
        border: 1px solid #ddd;
        border-radius: 8px;
        padding: 10px;
        margin-bottom: 20px;
        display: flex;
        align-items: center;
        background-color:white;
    }

    .cart-item img {
        width: 100px;
        height: auto;
        margin-right: 20px;
    }

    .cart-item-details {
        flex: 1;
    }

    .cart-item-details h3 {
        margin: 0;
        font-size: 18px;
        color: #333;
    }

    .cart-item-details p {
        margin: 5px 0;
        color: #777;
    }

    .remove-button,
    .checkout-button {
        background-color: green;
        border: none;
        color: white;
        padding: 8px 16px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 14px;
        cursor: pointer;
        border-radius: 4px;
        transition: background-color 0.3s;
    }

    .remove-button:hover,
    .checkout-button:hover {
        background-color: #2980b9;
    }

    @media only screen and (max-width: 600px) {
        .cart-item img {
            width: 80px;
            margin-right: 10px;
        }

        .cart-item-details h3 {
            font-size: 16px;
        }

        .cart-item-details p {
            font-size: 12px;
        }

        .remove-button,
        .checkout-button {
            padding: 6px 12px;
            font-size: 12px;
        }
    }
</style>
<script src="https://checkout.razorpay.com/v1/checkout.js"></script>

</head>
<body>

<div class="header">
        <img src="shoplogo.jpg" class="logo">
        <center><h1>Online Shopping</h1></center>
        
    </div>	
	<br><br>
    <div class="container">
        <h2>Shopping Cart</h2>
      <% 
List<model.Products> cartItems = (List<model.Products>) request.getAttribute("cartItems"); // Use request.getAttribute
if (cartItems != null) {
    for (model.Products item : cartItems) {
%>
<div class="cart-item">
    <div class="cart-item-details">
        <img src=<%=item.getProd_image() %>>
  <p>Total Amount: $<%= request.getAttribute("totalAmount") %></p>
        <h3><%= item.getProd_title() %></h3>
        <p>Price: $<%= item.getProd_price() %></p>
        <button class="remove-button" onclick="removeItem('<%= item.getProd_id() %>')">Remove</button>
    </div>
</div>
   
<% 
    }
}
%>
<div>
        <p>Total Amount: $<%= request.getAttribute("totalAmount") %></p>
        <p>Total GST: $<%= request.getAttribute("totalGst") %></p>
        <p>Total Shipping Charges: $<%= request.getAttribute("totalShippingCharges") %></p>
        <p>Total GST on Shipping Charges: $<%= request.getAttribute("totalGstOnShippingCharges") %></p>
        <p>Grand Total: $<%= request.getAttribute("grandTotal") %></p>
    </div>

        <a href="orders.jsp" class="checkout-button">CHECK OUT</a>
         <a id="rzp-button1" href="#" class="checkout-button" onclick="placeOrder()">Place Order</a>
        
        <a href="ProductServlet" class="checkout-button" onclick="checkout()">Continue shopping</a>
    </div>
    <script>
   
  
    function placeOrder() {
        var rzpButton = document.getElementById('rzp-button1');
     
        if (rzpButton) {
            var options = {
                // Razorpay options here
                  "key": "rzp_test_GD8S8aHD7CFQUE", // Enter the Key ID generated from the Dashboard
    	    "amount": "1000",
    	    "currency": "INR",
    	    "description": "Online Shopping",
    	    "image": "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg",
    	    "prefill":
    	    {
    	      "email": "gaurav.kumar@example.com",
    	      "contact": +919900000000,
    	    },
    	    config: {
    	      display: {
    	        blocks: {
    	          utib: { //name for Axis block
    	            name: "Pay using Axis Bank",
    	            instruments: [
    	              {
    	                method: "card",
    	                issuers: ["UTIB"]
    	              },
    	              {
    	                method: "netbanking",
    	                banks: ["UTIB"]
    	              },
    	            ]
    	          },
    	          other: { //  name for other block
    	            name: "Other Payment modes",
    	            instruments: [
    	              {
    	                method: "card",
    	                issuers: ["ICIC"]
    	              },
    	              {
    	                method: 'netbanking',
    	              }
    	            ]
    	          }
    	        },
    	        hide: [
    	          {
    	          method: "upi"
    	          }
    	        ],
    	        sequence: ["block.utib", "block.other"],
    	        preferences: {
    	          show_default_blocks: false // Should Checkout show its default blocks?
    	        }
    	      }
    	    },
    	    "handler": function (response) {
    	      alert(response.razorpay_payment_id);
    	    },
    	    "modal": {
    	      "ondismiss": function () {
    	        if (confirm("Are you sure, you want to close the form?")) {
    	          txt = "You pressed OK!";
    	          console.log("Checkout form closed by the user");
    	        } else {
    	          txt = "You pressed Cancel!";
    	          console.log("Complete the Payment")
    	        }
    	      }
    	    }
            };
            var rzp1 = new Razorpay(options);
            rzpButton.onclick = function (e) {
                rzp1.open();
                e.preventDefault();
            };
        } else {
            console.error('Element with ID "rzp-button1" not found');
        }
    }

        function checkout() {
            // Check if the user is logged in (you can use a session attribute to store login status)
            window.location.href = "login.html"; 
        }

        function removeItem(productId) {
            fetch('AddToCartServlet?productId=' + productId, {
                method: 'DELETE'
            })
            .then(response => {
                if (response.ok) {
                    window.location.reload();
                } else {
                    console.error('Failed to remove item from cart');
                }
            })
            .catch(error => {
                console.error('Error removing item from cart:', error);
            });
        }
     
       
    </script>
</body>
</html>





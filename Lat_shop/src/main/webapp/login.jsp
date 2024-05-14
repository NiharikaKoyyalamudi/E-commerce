<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f0f0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-image: url("https://st3.depositphotos.com/5411610/15334/i/450/depositphotos_153344150-stock-photo-small-empty-shopping-cart.jpg");
            background-repeat: no-repeat;
            background-size: cover; /* Optional: Adjust the background size */
            background-position: center; /* Optional: Center the background image */	
    }
        .container {
            width: 385px;
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .container h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            font-weight: bold;
            border-bottom: 1px solid #ddd;
            padding-bottom: 5px;
            margin-bottom: 5px;
        }

        .form-group input {
            width: 350px;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 3px;
        }

        .form-group input[type="submit"] {
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }

        .form-group input[type="submit"]:hover {
            background-color: #0056b3;
        }

        .error-message {
            color: red;
            text-align: center;
            margin-top: 10px;
        }

        .link {
            text-align: center;
            margin-top: 20px;
        }

        .link a {
            color: #007bff;
            text-decoration: none;
        }

        .link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Login</h2>
    <form action="/LoginServlet" id="loginform" method="post">
        <div class="form-group">
            <label for="username"  >UserName:</label>
            <input type="text" id="username" name="username" required><br><br>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br><br>
        </div>
        <div class="form-group">
            <input type="submit" value="Login">
        </div><br>
          <div class="form-group">
            <p style="display: inline-block; margin-right: 10px;">No Account?</p>
            <a href="register.jsp" class="register-link">Sign Up/Register</a>
        </div>
        <p id="hello" class="error-message"></p>
    </form>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
    $(document).ready(function () {
        $('#loginform').submit(function (e) {
            e.preventDefault();
            var formData = $(this).serialize();
            $.ajax({
                type: 'POST',
                url: 'LoginServlet',
                data: formData,
                success: function (response) {
                    if (response.trim() === 'yes') { // Trim the response to remove any extra spaces
                        window.location.href = "store.jsp";
                    } else if (response.trim() === 'no') {
                        $('#hello').text('Invalid username or password').show(); // Display error message from the server
                    }
                },
                error: function () {
                    $('#hello').text('Internal server error occurred');
                }
            });
        });
    });
</script>

</body>
</html>

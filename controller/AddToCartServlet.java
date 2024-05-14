package controller;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Products;


public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

  
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productId = request.getParameter("productId");
        String productName = request.getParameter("productName");
        String productPrice = request.getParameter("productPrice");
        String productImage = request.getParameter("productImage");

        Products product = new Products();
        product.setProd_id(Integer.parseInt(productId));
        product.setProd_title(productName);
        product.setProd_price(Double.parseDouble(productPrice));
        product.setProd_image(productImage);

        List<Products> cartItems = getCartItemsFromSession(request);
        cartItems.add(product);
        updateCartInSession(request, cartItems);

        response.sendRedirect("cart.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Products> cartItems = getCartItemsFromSession(request);
        request.setAttribute("cartItems", cartItems); // Set attribute as request attribute
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }



    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productIdToRemove = request.getParameter("productId");
        if (productIdToRemove != null) {
            int productId = Integer.parseInt(productIdToRemove);
            List<Products> cartItems = getCartItemsFromSession(request);
            cartItems.removeIf(item -> item.getProd_id() == productId);
            updateCartInSession(request, cartItems);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @SuppressWarnings("unchecked")
	private List<Products> getCartItemsFromSession(HttpServletRequest request) {
        List<Products> cartItems = (List<Products>) request.getSession().getAttribute("cartItems");
        if (cartItems == null) {
            cartItems = new ArrayList<>();
            request.getSession().setAttribute("cartItems", cartItems);
        }
        return cartItems;
    }

    private void updateCartInSession(HttpServletRequest request, List<Products> cartItems) {
        request.getSession().setAttribute("cartItems", cartItems);
    }
}
package mvc;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class CouponServlet
 */
@WebServlet("/CouponServlet")
public class CouponServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AllProductsDAL a = new AllProductsDAL();
		HttpSession session = request.getSession();
		int cpid = 0;
		double price = 0.0;
		double total = 0.0;
		total = (Double) session.getAttribute("totalcp");
		System.out.println("total:" + total);
		String dcode = request.getParameter("coupon");
		System.out.println("dcode:" + dcode);
		boolean couponApplied = false;
		couponApplied = a.CheckValidCoupon(dcode, total);
		if (couponApplied) {
			// Coupon applied successfull
			cpid = a.getCouponID(dcode);
			price = a.getCouponAmount(cpid);
			session.setAttribute("cpid", cpid);
			session.setAttribute("cprice", price);

			PrintWriter out = response.getWriter();
			out.println("Coupon applied successfully!");
		} else {
			// Coupon invalid
			PrintWriter out = response.getWriter();
			out.println("Invalid coupon");
		}
	}
}

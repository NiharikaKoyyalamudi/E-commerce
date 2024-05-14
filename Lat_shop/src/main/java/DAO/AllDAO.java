//package DAO;
//
//import java.sql.SQLException;
//import java.util.List;
//
//import model.products;
//
//public interface AllDAO {
//	List<ProductDao> getAllProducts() throws ClassNotFoundException;
//
//	String[] getAllProductCategories();
//
//	List<ProductDao> getProductsByCategory(String categoryId);
//
//	int checkDeliverablePincode(String pincode);
//
//	int checkavailability(int c, int p);
//
//	String generateOrderID() throws SQLException;
//
//	int getcid(String u);
//
//	void insertOrders(int cid, String oid, double totalPrice);
//
//	void insertOrderedProducts(CartList cartList, String oid);
//}
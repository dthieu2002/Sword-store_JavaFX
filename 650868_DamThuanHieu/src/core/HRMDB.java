package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HRMDB {
	/*
	 * Các tham số để kết nối database sẽ được truyền từ lớp ở tầng Controller (để
	 * link động có thể đổi database)
	 */
	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection conn;

	// Viết contructor cho phép tạo đối tượng với các tham số cần thiết
	public HRMDB(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		this.jdbcURL = jdbcURL;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
	}
	
	
	public boolean addSword(Sword sword) {
		// Tạo kết nối database với 3 tham số truyền vào
		conn = DBConnection.create(jdbcURL, jdbcUsername, jdbcPassword);
		// Tạo câu truy vấn kiểu prepare
		String sqlCommand = "INSERT INTO tblsword VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pst = null;

		boolean result = false;
		try {
			// Tạo đối tượng truy vấn kiểu Prepare
			pst = conn.prepareStatement(sqlCommand);
			// Đưa dữ liệu vào các vị trí dấu hỏi (?)
			pst.setString(8, sword.getId());
			pst.setString(1, sword.getName());
			pst.setString(2, Float.toString(sword.getHeight()));
			pst.setString(3, Float.toString(sword.getWeight()));
			pst.setString(4, sword.getMadeIn());
			pst.setString(5, sword.getMaterial());
			pst.setString(6, Float.toString(sword.getPrice()));
			pst.setString(7, sword.getImage());
			
			// Thực hiện chạy câu truy vấn
			int i = pst.executeUpdate();
			// Nếu thành công, 1 bản ghi được thêm vào
			if (i == 1) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // Đóng kết nối trong khối finally (bắt buộc chạy)
			DBConnection.closePreparedStatement(pst);
			DBConnection.closeConnect(conn);
		}
		return result;
	}
	
	public boolean updateSword(Sword sword) {
		conn = DBConnection.create(jdbcURL, jdbcUsername, jdbcPassword);
		String sqlCommand = "UPDATE tblsword SET Name = ?"
											+ ",Height = ?"
											+ ",Weight = ?"
											+ ",MadeIn = ?"
											+ ",Material = ?"
											+ ",Price = ?"
											+ ",Image = ?"
											+ "WHERE Id = ?";

		PreparedStatement pst = null;
		boolean result = false;
		try {
			pst = conn.prepareStatement(sqlCommand);
			pst.setString(1, sword.getName());
			pst.setString(2, Float.toString(sword.getHeight()));
			pst.setString(3, Float.toString(sword.getWeight()));
			pst.setString(4, sword.getMadeIn());
			pst.setString(5, sword.getMaterial());
			pst.setString(6, Float.toString(sword.getPrice()));
			pst.setString(7, sword.getImage());
			pst.setString(8, sword.getId());
			
			int i = pst.executeUpdate();
			if (i == 1) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closePreparedStatement(pst);
			DBConnection.closeConnect(conn);
		}
		return result;
	}
	
	public boolean deleteSword(String id) {
		conn = DBConnection.create(jdbcURL, jdbcUsername, jdbcPassword);
		String sqlCommand = "DELETE FROM tblsword WHERE Id = ?";
		PreparedStatement pst = null;

		boolean result = false;
		try {
			pst = conn.prepareStatement(sqlCommand);
			pst.setString(1, id);
			int i = pst.executeUpdate();
			if (i == 1) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closePreparedStatement(pst);
			DBConnection.closeConnect(conn);
		}
		return result;
	}
	
	
	public List<Sword> getSwordList() {
		List<Sword> swordList = new ArrayList<Sword>();
		Statement statement = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.create(jdbcURL, jdbcUsername, jdbcPassword);
			statement = conn.createStatement();
			rs = statement.executeQuery("Select * from tblsword");
			
			String id;
			String name;
			float height;
			float weight;
			String madeIn;
			String material;
			float price;
			String image;
			
			// Duyệt qua danh sách các bản thi nhận được trong đối tượng ResultSet
			while (rs.next()) {
				id = rs.getString("ID"); // ID is name column on database
				name = rs.getString("Name");
				height = Float.parseFloat(rs.getString("Height"));
				weight = Float.parseFloat(rs.getString("Weight"));
				madeIn = rs.getString("MadeIn");
				material = rs.getString("Material");
				price = Float.parseFloat(rs.getString("Price"));
				image = rs.getString("Image");
				
				// Thêm vào list
				swordList.add(new Sword(id,name,height,weight,madeIn,material,price,image));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(statement);
			DBConnection.closeConnect(conn);
		}
		return swordList;
	}
	
	
	public boolean addUser(User user) {
		// Tạo kết nối database với 3 tham số truyền vào
		conn = DBConnection.create(jdbcURL, jdbcUsername, jdbcPassword);
		// Tạo câu truy vấn kiểu prepare
		String sqlCommand = "INSERT INTO tbluser VALUES(?, ?)";
		PreparedStatement pst = null;

		boolean result = false;
		try {
			// Tạo đối tượng truy vấn kiểu Prepare
			pst = conn.prepareStatement(sqlCommand);
			// Đưa dữ liệu vào các vị trí dấu hỏi (?)
			pst.setString(1, user.getUserName());
			pst.setString(2, user.getPassword());
			
			// Thực hiện chạy câu truy vấn
			int i = pst.executeUpdate();
			// Nếu thành công, 1 bản ghi được thêm vào
			if (i == 1) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // Đóng kết nối trong khối finally (bắt buộc chạy)
			DBConnection.closePreparedStatement(pst);
			DBConnection.closeConnect(conn);
		}
		return result;
	}
	
	public List<User> getUserList(){
		List<User> userList = new ArrayList<User>();
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.create(jdbcURL, jdbcUsername, jdbcPassword);
			statement = conn.createStatement();
			rs = statement.executeQuery("Select * from tbluser");
			
			String userName;
			String password;
			
			// Duyệt qua danh sách các bản thi nhận được trong đối tượng ResultSet
			while (rs.next()) {
				userName = rs.getString("UserName");
				password = rs.getString("Password");
				
				// Thêm vào list
				userList.add(new User(userName, password));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(statement);
			DBConnection.closeConnect(conn);
		}
		
		return userList;
	}
}

import java.sql.*;

public class main {
	public static void main(String[] args) {
		
		String url= "jdbc:mysql://localhost:3306/lab10";
		String user = "root";
		String pwd = "Nirreddy14";
		
		try (Connection conn = DriverManager.getConnection(url,user,pwd)){
			String a = "CALL findProcedure(?,?);";
			CallableStatement ab = conn.prepareCall(a);
			
			ab.setString(1,"J");
			ab.execute();
			System.out.println("There are " + ab.getInt(2) + " students whose names start with J.");
			
			ab.setString(1, "L");
			ab.execute();
			System.out.println("There are " + ab.getInt(2) + " students whose names start with L.");
			
			
			String c = "SELECT findFunction(?);";
			PreparedStatement cd = conn.prepareStatement(c);
			cd.setString(1, "Josh");
			
			
			ResultSet rs = cd.executeQuery();
			while (rs.next()) {
				System.out.println("There are " + rs.getInt(1) + " students whose first name is Josh.");
			}
			
			
			String d = "SELECT fname, lname FROM Student WHERE DATE(birthdate) >= '2000-01-01'";
			PreparedStatement labD = conn.prepareStatement(d);
			rs = labD.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getString("fname") + " " + rs.getString("lname"));
			}
			
			
			String g = "SELECT fname,lname FROM Student,Department WHERE Student.deptID = Department.deptID AND collegeId = ?";
			
			
			PreparedStatement labE = conn.prepareStatement(g);
			labE.setString(1,"BUSS");
			rs = labE.executeQuery();
			
			
			
			System.out.println("List of students in BUSS college");
			while (rs.next()) {
				System.out.println(rs.getString("fname") + " " + rs.getString("lname"));
			}
			labE.setString(1,"SC");
			rs = labE.executeQuery();
			
			
			System.out.println("List of students in SC college");
			while (rs.next()) {
				System.out.println(rs.getString("fname") + " " + rs.getString("lname"));
			}
			
			labE.setString(1,"EDUC");
			rs = labE.executeQuery();
			
			
			System.out.println("List of students in EDUC college");
			while (rs.next()) {
				System.out.println(rs.getString("fname") + " " + rs.getString("lname") );
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}





import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.pool.OracleDataSource;

public class Driver {

	static Connection conn;

	public static void main(String[] args) throws SQLException {
		OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
		ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:ACAD111");
		conn = ds.getConnection("kkulkar3", "kunal2795");

		Scanner sc = new Scanner(System.in);
		int choice;
		while (true) {
			System.out.println("1.Display table");
			System.out.println("2.Display Class TAs");
			System.out.println("3.Get Prerequisites");
			choice = sc.nextInt();
			if (choice == 1) {
				int tChoice;
				System.out.println("1.Display Students Table");
				System.out.println("2.Display Courses Table");
				System.out.println("3.Display Prerequisites Table");
				System.out.println("4.Display Classes Table");
				System.out.println("5.Display Enrollments Table");
				System.out.println("6.Display Logs Table");
				tChoice = sc.nextInt();
				if (tChoice == 1) {
					displayStudents(conn);
				}
			}
			if (choice == 2) {
				System.out.println("Enter ClassID");
				String classid = sc.next();
				ClassTAs(conn, classid);
			}
			if (choice == 3) {
				System.out.println("Enter DeptCode and Course#");
				String deptcode = sc.next();
				int course_in = sc.nextInt();
				CheckPrerequisites(conn, deptcode, course_in);
			}
		}
	}

	private static ResultSet CheckPrerequisites(Connection conn2, String deptcode, int course_in) throws SQLException {
		// TODO Auto-generated method stub
		
		CallableStatement cs = conn.prepareCall("{ call procedures.CheckPrerequisites(?, ?, ?) }");

		cs.setString(1, deptcode);
		cs.setInt(2, course_in);
		cs.registerOutParameter(3, OracleTypes.CURSOR);
		cs.execute();

		ResultSet rs = (ResultSet) cs.getObject(3);

		if (rs != null) {
			ResultSetMetaData meta = rs.getMetaData();
			System.out.println("\n" + meta.getColumnName(1));
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
		}

		//cs.close();
		return rs;
	}

	private static Object ClassTAs(Connection conn2, String classid) throws SQLException {
		ResultSet rs = null;
		Object returnObject = null;
		CallableStatement cs = conn.prepareCall("{ call procedures.show_ClassTA(?, ?, ?) }");
		cs.setString(1, classid);
		cs.registerOutParameter(2, OracleTypes.VARCHAR);
		cs.registerOutParameter(3, OracleTypes.CURSOR);
		cs.execute();

		String err_message = cs.getString(2);

		if (err_message != null) {
			System.out.println(err_message);
			returnObject = err_message;
		} else {
			rs = (ResultSet) cs.getObject(3);
			returnObject = rs;
		}
		if (rs != null) {
			ResultSetMetaData meta = rs.getMetaData();
			System.out.println(
					"\n" + meta.getColumnName(1) + "\t" + meta.getColumnName(2) + "\t" + meta.getColumnName(3));
			while (rs.next()) {
				System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3));
			}
		}

		// cs.close();
		return returnObject;
		// TODO Auto-generated method stub

	}

	private static ResultSet displayStudents(Connection conn2) throws SQLException {
		CallableStatement cs = conn.prepareCall("{ call procedures.show_students(?) }");
		cs.registerOutParameter(1, OracleTypes.CURSOR);
		cs.execute();
		ResultSet rs = (ResultSet) cs.getObject(1);
		if (rs != null) {
			ResultSetMetaData meta = rs.getMetaData();
			System.out.println(
					"\n" + meta.getColumnName(1) + "\t" + meta.getColumnName(2) + "\t" + meta.getColumnName(3) + "\t"
							+ meta.getColumnName(4) + "\t" + meta.getColumnName(5) + "\t" + meta.getColumnName(6));
			while (rs.next()) {
				System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t"
						+ rs.getString(4) + "\t" + rs.getDouble(5) + "\t" + rs.getString(6));
			}

		}
		return rs;
	}

}

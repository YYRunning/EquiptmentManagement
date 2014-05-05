package org.EM.Hander;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHander {

	private static String DATABASE_URL = "jdbc:mysql://localhost:3306/db_equiptmentmanagement";

	/*
	 * ��ȡ���ݿ�����
	 * @return ���ݿ�����
	 */
	public static Connection provideConnection() {

		Connection connection = null;

		try {
			// ע����
			Class.forName("com.mysql.jdbc.Driver");
			// ��ȡ����
			connection = DriverManager.getConnection(DATABASE_URL, "root",	"123456");

			return connection;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}

	/*
	 * ִ��SQL��䲢�ҷ���Ӱ�������
	 * @paras query SQL��䣬 args���ܴ��ڵĲ���
	 */
	public static int ExecuteNonQuery(String query, Object... args) {

		// ��ȡ���ݿ�����
		Connection connection = provideConnection();

		try {

			PreparedStatement pstmt = connection.prepareStatement(query);

			// ���������η���Statement��
			for (int i = 0; i < args.length; i++) {

				pstmt.setObject(i + 1, args[i]);

			}

			int affectRows = pstmt.executeUpdate();

			// �ر�Statement
			pstmt.close();

			// �ر����ݿ�����
			connection.close();

			return affectRows		;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	// �����з���ֵ��SQL���
	public static ResultSet ExecuteQuery(String query, Object... args) {

		// ��ȡ���ݿ�����
		Connection connection = provideConnection();

		try {

			PreparedStatement pstmt = connection.prepareStatement(query);

			// ����������Statement��
			for (int i = 0; i < args.length; i++) {

				pstmt.setObject(i + 1, args[i]);

			}

			// ����ResultSet
			ResultSet rs = pstmt.executeQuery();
			
			return rs;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}
}

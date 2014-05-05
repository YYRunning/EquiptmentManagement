package org.EMS.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.EM.Hander.DatabaseHander;
import org.EMS.Model.TransModel;

public class TransDAO {
	
	private static String TABLE_NAME = "t_transfer";
	
	//�жϵ����Ƿ����
	public static Boolean Exist(long TranID){
		
		String query = "selsct * from " + TABLE_NAME + " where TranID=?";
		
		ResultSet rs = DatabaseHander.ExecuteQuery(query, TranID);
		try{
			if(rs.next()){
				return true;
			}
			else{
				return false;
			}
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	//��ȡ������Ϣ
	public static TransModel GetModel(Long TranID){
		
		if(TranID==null){
			return null;
		}

		String query = "select * from " + TABLE_NAME + " where TranID=?";
		
		ResultSet rs = DatabaseHander.ExecuteQuery(query, TranID);
		
		try{		
			if(rs.next()){
				
				TransModel trans = new TransModel();
				trans.setTranID(rs.getLong("TranID"));
				trans.setTranEqpRFID(rs.getString("TranEqpRFID"));
				trans.setTranPeople(rs.getString("TranPeople"));
				trans.setTranDate(rs.getDate("TranDate"));
				trans.setTranDepartmentID(rs.getInt("TranDepartmentID"));
				trans.setTranToDepartmentID(rs.getInt("TranToDepartmentID"));
			
				return trans;
			}
			else{
				return null;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//���������Ϣ
	public static int Insert(TransModel trans){
		
		String query = "insert into " + TABLE_NAME + " (TranID,TranEqpRFID,TranPeople,TranDate,TranDepartmentID,TranToDepartmentID) values (?,?,?,?,?,?)";
		return DatabaseHander.ExecuteNonQuery(query, trans.getTranID(),trans.getTranEqpRFID(),trans.getTranPeople(),trans.getTranDate(),trans.getTranDepartmentID(),trans.getTranToDepartmentID());
	}
	
	
	//���µ�����Ϣ
	public static int Update(TransModel trans){
		
		String query = "Update " + TABLE_NAME + " set TranEqpRFID=?,TranPeople=?,TranDate=?,TranDepartmentID=?,TranToDepartmentID=? where TranID=?";
		return DatabaseHander.ExecuteNonQuery(query, trans.getTranEqpRFID(),trans.getTranPeople(),trans.getTranDate(),trans.getTranDepartmentID(),trans.getTranToDepartmentID(),trans.getTranID());
	}
	
	//ɾ��������Ϣ
	public static int Delete(TransModel trans){
		
		String query = "delete from " + TABLE_NAME + " where TranID=?";
		
		return DatabaseHander.ExecuteNonQuery(query, trans.getTranID());
		
	}
	
	//������еĵ�������
	public static ResultSet GetAllList(String whereStr, Object... args){
		
		String query = "select * from " + TABLE_NAME;
		
		if(whereStr != ""){
			
			query += " where " + whereStr;
			
		}
		
		return DatabaseHander.ExecuteQuery(query, args);
		
	}
	
}

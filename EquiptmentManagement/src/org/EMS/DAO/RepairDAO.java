package org.EMS.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.EM.Hander.DatabaseHander;
import org.EMS.Model.RepairModel;

//�ж�ά�޼�¼�Ƿ����
public class RepairDAO {
	
	private static String TABLE_NAME = "t_repair";
	
	public static Boolean Exist(long RpID){
		
		String query = "select * form " + TABLE_NAME + " where RpID=?";
		
		ResultSet rs =  DatabaseHander.ExecuteQuery(query, RpID);
		
		try{
			if(rs.next()){
				return true;
			}
			else{
				return false;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	//��ȡά����Ϣ
	public static RepairModel GetModel(Long RpID){
		
		if(RpID==null){
			return null;
		}
		
		String query = "select * from " + TABLE_NAME + " where RpID=?";
		
		ResultSet rs =  DatabaseHander.ExecuteQuery(query, RpID);
		try{
			
			if(rs.next()){
				
				RepairModel repair = new RepairModel();
				repair.setRpID(rs.getLong("RpID"));
				repair.setRpEqpRFID(rs.getString("RpEqpRFID"));
				repair.setRpDate(rs.getDate("RpDate"));
				repair.setRpPeople(rs.getLong("RpPeople"));
				repair.setRpDescribe(rs.getString("RpDescribe"));
				repair.setRpCondition(rs.getInt("RpCondition"));
				
				return repair;
			}
			else{
				return null;
			}		

		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	//����ά����Ϣ
	public static int Insert(RepairModel repair){
		
		String query = "insert into " + TABLE_NAME + " (RpID,RpEqpRFID,RpDate,RpPeople,RpDescribe,RpCondition) values (?,?,?,?,?,?)";
		
		return DatabaseHander.ExecuteNonQuery(query, repair.getRpID(),repair.getRpEqpRFID(),repair.getRpDate(),repair.getRpPeople(),repair.getRpDescribe(),repair.getRpCondition());
		
	}
	
	//����ά����Ϣ
	public static int Update(RepairModel repair){
		
		String query = "Update " + TABLE_NAME + " set RpEqpRFID=?,RpDate=?,RpPeople=?,RpDescribe=?,RpCondition=? where RpID=?";
		
		return DatabaseHander.ExecuteNonQuery(query, repair.getRpEqpRFID(),repair.getRpDate(),repair.getRpPeople(),repair.getRpDescribe(),repair.getRpCondition(),repair.getRpID());
		
	}
	
	//ɾ��ά����Ϣ
	public static  int Delete(RepairModel repair){
		 
		String query = "Delete from " + TABLE_NAME + " where RpID=?";
		
		return DatabaseHander.ExecuteNonQuery(query, repair.getRpID());
				
	}
	
	//������е�ά������
	public static ResultSet GetAllList(String whereStr, Object... args){
		
		String query = "select * from " + TABLE_NAME;
		
		if(whereStr != ""){
			
			query += " where " + whereStr;
			
		}
		
		return DatabaseHander.ExecuteQuery(query, args);
		
	}
	
}

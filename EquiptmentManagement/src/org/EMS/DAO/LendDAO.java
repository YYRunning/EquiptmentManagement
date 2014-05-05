package org.EMS.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.EM.Hander.DatabaseHander;
import org.EMS.Model.LendModel;

public class LendDAO {
	
	private static String TABLE_NAME = "t_lend";
	
	//�жϴ˽�����Ϣ�Ƿ����
	public static Boolean Exist(long LendID){
		
		String query = "select * from " + TABLE_NAME + " where LendID=?";
		
		ResultSet rs = DatabaseHander.ExecuteQuery(query, LendID);
		
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
	public static LendModel GetModel(Long lendID){
		
		if(lendID == null){
			
			//staffIDΪ�գ�����
			return null;
		}
		
		//SQL���
		String query = "select * from " + TABLE_NAME + " where lendID=?";
		
		//ִ��SQL��䣬���ҵõ�����ֵ
		ResultSet rs = DatabaseHander.ExecuteQuery(query, lendID);
		try {
			if(rs.next()){
				
				LendModel lend = new LendModel();
				lend.setLendID(rs.getLong("LendID"));
				lend.setLendEqpRFID(rs.getString("LendEqpRFID"));
				lend.setLendDepartmentID(rs.getInt("LendDepartmentID"));
				lend.setLendDate(rs.getDate("LendDate"));
				lend.setLendTime(rs.getDate("LendTime"));
				lend.setLendDeadline(rs.getDate("LendDeadline"));
				lend.setLendToPeople(rs.getLong("LendToPeople"));
				lend.setLendFromCharge(rs.getLong("LendFromCharge"));
				lend.setLendFlag(rs.getBoolean("LendFlag"));
				
				return lend;

			}
			else{
				
				return null;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	//���������Ϣ
	public static int Insert(LendModel lend){
		
		String query = "Insert into " + TABLE_NAME + " (LendID,LendEqpRFID,LendDepartmentID,LendDate,LendTime,LendDeadline,LendToPeople,LendFromCharge,LendFlag) values (?,?,?,?,?,?,?,?,?)";
	
		return DatabaseHander.ExecuteNonQuery(query, lend.getLendID(),lend.getLendEqpRFID(),lend.getLendDepartmentID(),lend.getLendDate(),lend.getLendTime(),lend.getLendDeadline(),lend.getLendToPeople(),lend.getLendFromCharge(),lend.getLendFlag());
			
	}
	
	//���½�����Ϣ
	public static int Update(LendModel lend){
		
		//����ID������Ա���ĸ���
		String query = "Update " + TABLE_NAME + " set LendEqpRFID=?,LendDepartmentID=?,LendDate=?,LendTime=?,LendDeadline=?,LendToPeople=?,LendFromCharge=?,LendFlag=? where LendID=?";
	
		return DatabaseHander.ExecuteNonQuery(query, lend.getLendEqpRFID(),lend.getLendDepartmentID(),lend.getLendDate(),lend.getLendTime(),lend.getLendDeadline(),lend.getLendToPeople(),lend.getLendFromCharge(),lend.getLendFlag(),lend.getLendID());
		
	}
	
	//ɾ��������Ϣ
	public static int Delete(LendModel lend){
		
		String query = "delete from " + TABLE_NAME + " where lendID=?";
		
		return DatabaseHander.ExecuteNonQuery(query, lend.getLendID());
		
	}
	
	//������еĽ�������
	public static ResultSet GetAllList(String whereStr, Object... args){
		
		String query = "select * from " + TABLE_NAME;
		
		if(whereStr != ""){
			
			query += " where " + whereStr;
			
		}
		
		return DatabaseHander.ExecuteQuery(query, args);
		
	}
	
}

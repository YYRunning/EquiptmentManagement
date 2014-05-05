package org.EMS.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.EM.Hander.DatabaseHander;
import org.EMS.Model.AdminModel;
import org.EMS.Model.StaffModel;

public class StaffDAO {

	private static String TABLE_NAME = "t_staff";
	
	//�ж�Ա���Ƿ����
	public static Boolean Exist(long staffID){
		
		String query = "select * from " + TABLE_NAME + " where staffID=?";
		
		//ִ���з���ֵ��SQL���
		ResultSet rs = DatabaseHander.ExecuteQuery(query, staffID);
		try {
			if(rs.next()){				
				//����
				return true;
			}
			else{
				
				return false;			
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	//��ȡԱ����Ϣ
	public static StaffModel GetModel(Long staffID){
		
		if(staffID == null){
			
			//staffIDΪ�գ�����
			return null;
		}
		
		//SQL���
		String query = "select * from " + TABLE_NAME + " where staffID=?";
		
		//ִ��SQL��䣬���ҵõ�����ֵ
		ResultSet rs = DatabaseHander.ExecuteQuery(query, staffID);
		try {
			if(rs.next()){
				
				StaffModel staff = new StaffModel();
				staff.setStaffId(rs.getLong("StaffID"));
				staff.setStaffDepartmentID(rs.getInt("StaffDepartmentID"));
				staff.setStaffGender(rs.getBoolean("StaffGender"));
				staff.setStaffLimit(rs.getInt("StaffLimit"));
				staff.setStaffName(rs.getString("StaffName"));
				staff.setStaffPassword(rs.getString("StaffPassword"));
				staff.setStaffProfession(rs.getString("StaffProfession"));
				staff.setStaffTitle(rs.getString("StaffTitle"));
				
				return staff;
			}
			else{
				
				return null;				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	//����Ա����Ϣ
	public static int Insert(StaffModel staff){
		
		//SQL���
		String query = "Insert into " + TABLE_NAME + " (StaffID, staffname, staffgender, staffdepartmentid, staffProfession, staffTitle, stafflimit, staffpassword) values (?,?,?,?,?,?,?,?)";
		
		//ִ�в������
		return DatabaseHander.ExecuteNonQuery(query, staff.getStaffId(), staff.getStaffName(), staff.getStaffGender(), staff.getStaffDepartmentID(), staff.getStaffProfession(), staff.getStaffTitle(), staff.getStaffLimit(), staff.getStaffPassword());
		
	}
	
	//����Ա����Ϣ
	public static int Update(StaffModel staff){
		
		//����ID������Ա���ĸ���
		String query = "Update " + TABLE_NAME + " set staffName=?,staffgender=?,staffdepartmentid=?,staffprofession=?,stafftitle=?,stafflimit=?,staffpassword=? where staffid=?";
		
		return DatabaseHander.ExecuteNonQuery(query, staff.getStaffName(), staff.getStaffGender(), staff.getStaffDepartmentID(), staff.getStaffProfession(), staff.getStaffTitle(), staff.getStaffLimit(), staff.getStaffPassword(), staff.getStaffId());
		
	}
	
	//ɾ��Ա����Ϣ
	
	
	
	public static int Delete(Long staffId){
		
		//����Ա����IDɾ��Ա��
		String query = "delete from " + TABLE_NAME + " where staffId=?";
		
		return DatabaseHander.ExecuteNonQuery(query, staffId);
		
	}
	
	public static int Delete(StaffModel staff){
		
		return Delete(staff.getStaffId());
		
	}
	
	//������е�Ա������
	public static ResultSet GetAllList(String whereStr, Object... args){
		
		String query = "select * from " + TABLE_NAME;
		
		if(whereStr != ""){
			
			query += " where " + whereStr;
			
		}
		
		return DatabaseHander.ExecuteQuery(query, args);
		
	}


	
}

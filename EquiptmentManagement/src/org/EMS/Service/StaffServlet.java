package org.EMS.Service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.EMS.BLL.StaffBLL;
import org.EMS.Model.StaffModel;

public class StaffServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StaffServlet() {
        super();

    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String action = request.getParameter("action");
		
		
		//��������Ϊ�г�����Ա����Ϣ
		if(action.toLowerCase().equals("list")){
		
			//�����ݿ��л�ȡ���е�Ա����Ŀ
			
			//�ѹ���Ա��Ϣ���ݵ�StaffManagement.jspҳ��
			ArrayList<StaffModel> staffList = StaffBLL.GetAllArrayList();
			if(staffList.size() == 0){
				request.setAttribute("infoType", "warning");
				request.setAttribute("infoContext", "û��Ա����Ϣ");
			}
			//��Ա���б��ݵ�ǰ̨jspҳ��
			request.setAttribute("staffList", staffList);
			
			RequestDispatcher rd = request.getRequestDispatcher("StaffManagement.jsp");
				
			rd.forward(request, response);
			
		}
		
		//*********************************************************************************************************
		//���Ա����Ϣ
		else if(action.toLowerCase().equals("add")){
			
			String save = request.getParameter("save");
			if(save == null || save == ""){
				
				//���Ǳ�����Ĺ���
				
				//�������Ա����Ϣ��ҳ��
				RequestDispatcher rd = request.getRequestDispatcher("StaffAdd.jsp");
				
				rd.forward(request, response);
			
			}
			else{
				
				//�Ǳ�����Ĺ���
				String staffId = request.getParameter("staffID");
				String staffName = request.getParameter("staffName");
				String staffPassword = request.getParameter("staffPassword");
				String staffGender = request.getParameter("staffGender");
				String staffDepartmentID = request.getParameter("staffDepartment");
				String staffTitle = request.getParameter("staffTitle");
				String staffProfession = request.getParameter("staffProfession");
				String staffLimit = request.getParameter("staffLimit");
				
				
				
				if(staffId == null || staffName == null || staffPassword == null || staffGender == null){
					
					//�û�������û������
					request.setAttribute("infoType", "error");
					request.setAttribute("infoContext", "������Ϣ������");
					RequestDispatcher rd = request.getRequestDispatcher("StaffAdd.jsp");
					rd.forward(request, response);
				}
				else{
					
					//�û���������������Ϣ
					
					//����StaffModel���ڲ��뵽���ݿ���
					StaffModel staff = new StaffModel();
					staff.setStaffId(Long.parseLong(staffId));
					staff.setStaffDepartmentID(Integer.parseInt(staffDepartmentID));
					staff.setStaffGender(staffGender.equals("true")  ? true : false);
					staff.setStaffLimit(Integer.parseInt(staffLimit));
					staff.setStaffName(staffName);
					staff.setStaffPassword(staffPassword);
					staff.setStaffProfession(staffProfession);
					staff.setStaffTitle(staffTitle);

					
					//ִ�в������
					if(StaffBLL.Insert(staff) > 0){
						
						//����ɹ�
						request.setAttribute("infoType", "success");
						request.setAttribute("infoContext", "����ɹ�");
						
						//��ת������Ա����ҳ��
						RequestDispatcher rd = request.getRequestDispatcher("StaffServlet?action=list");
						rd.forward(request, response);
					}
					else{
						
						//����ʧ��
						request.setAttribute("infoType", "error");
						request.setAttribute("infoContext", "����ʧ�ܣ��û����Ѵ���");
						RequestDispatcher rd = request.getRequestDispatcher("StaffAdd.jsp");
						rd.forward(request, response);
					}
				}
			}
		}
		
		
		//*************************************************************************************
		//�༭Ա����Ϣ
		else if(action.toLowerCase().equals("edit")){
			
			String save =  request.getParameter("save");
			if(save == null || save == ""){
				
				//���Ǳ�����̣�����������Ҫ�༭��Ա������Ϣ�����Ҵ��ݵ�ǰ̨
				
				//���Ա��ID
				String staffId = request.getParameter("staffId");
				
				if(staffId == null || staffId == ""){
					
					//û�л�ȡ��AdminID����ת������Ա�����б����
					request.setAttribute("infoType", "error");
					request.setAttribute("infoContext", "Ա����Ϣ������");
					RequestDispatcher rd = request.getRequestDispatcher("StaffServlet?action=list");
					rd.forward(request, response);
					
				}
				else{
					
					//��ȡ����StaffID
					StaffModel staff = StaffBLL.GetModel(Long.parseLong(staffId));
					if(staff == null){
						
						//û�������ݿ���ȡ��AdminModel��Ϣ
						request.setAttribute("infoType", "error");
						request.setAttribute("infoContext", "Ա����Ϣ������");
						RequestDispatcher rd = request.getRequestDispatcher("StaffServlet?action=list");
						rd.forward(request, response);
						
					}
					else{
						
						//��ȡ������Ӧ�Ĺ���Ա��Ϣ
						//����Ϣ���ݵ�AdminEdit.jspҳ�棬���и���
						request.setAttribute("StaffModel", staff);
						RequestDispatcher rd = request.getRequestDispatcher("StaffEdit.jsp");
						rd.forward(request, response);
						
					}				
				}
				
			}
			else{
				
				//�Ǳ������
				//�Ǳ�����Ĺ���
				String staffId = request.getParameter("staffID");
				String staffName = request.getParameter("staffName");
				String staffPassword = request.getParameter("staffPassword");
				String staffGender = request.getParameter("staffGender");
				String staffDepartmentID = request.getParameter("staffDepartment");
				String staffTitle = request.getParameter("staffTitle");
				String staffProfession = request.getParameter("staffProfession");
				String staffLimit = request.getParameter("staffLimit");
				
				if(staffId == null || staffName == null || staffPassword == null || staffGender == null){
					
					//�û�������û������
					request.setAttribute("infoType", "error");
					request.setAttribute("infoContext", "������Ϣ������");
					RequestDispatcher rd = request.getRequestDispatcher("StaffServlet?action=edit&staffId= " + staffId);
					rd.forward(request, response);
				}
				else{
					
					//�û���������������Ϣ
					System.out.println("123");
					
					//����StaffModel���ڲ��뵽���ݿ���
					StaffModel staff = new StaffModel();
					staff.setStaffId(Long.parseLong(staffId));
					staff.setStaffDepartmentID(Integer.parseInt(staffDepartmentID));
					staff.setStaffGender(staffGender.equals("true")  ? true : false);
					staff.setStaffLimit(Integer.parseInt(staffLimit));
					staff.setStaffName(staffName);
					staff.setStaffPassword(staffPassword);
					staff.setStaffProfession(staffProfession);
					staff.setStaffTitle(staffTitle);

					
					//ִ�в������
					if(StaffBLL.Update(staff) > 0){
						
						//���³ɹ�
						request.setAttribute("infoType", "success");
						request.setAttribute("infoContext", "���³ɹ�");
						
						//��ת������Ա����ҳ��
						RequestDispatcher rd = request.getRequestDispatcher("StaffServlet?action=list");
						rd.forward(request, response);
					}
					else{
						
						//����ʧ��
						request.setAttribute("infoType", "error");
						request.setAttribute("infoContext", "����ʧ��");
						RequestDispatcher rd = request.getRequestDispatcher("StaffServlet?action=list");
						rd.forward(request, response);
					}
				}
			}
						
		}
		
		
		//**************************************************************************************
		
				//ɾ��Ա����Ϣ
				else if(action.toLowerCase().equals("delete")){
					
					String staffId = request.getParameter("staffId");
					if(staffId == null){
						
						//û�л�ȡ������ԱID
						request.setAttribute("infoType", "error");
						request.setAttribute("infoContext", "ɾ��ʧ��");
						
						//ҳ����ת
						RequestDispatcher rd = request.getRequestDispatcher("staffServlet?action=list");
					
						rd.forward(request, response);
					}
					else{
						
						if(StaffBLL.Delete(Long.parseLong(staffId)) > 0){
							
							//ɾ���ɹ�
							request.setAttribute("infoType", "success");
							request.setAttribute("infoContext", "ɾ���ɹ�");
							
							//ҳ����ת
							RequestDispatcher rd = request.getRequestDispatcher("StaffServlet?action=list");
						
							rd.forward(request, response);
						}
						else{
							
							//ɾ��ʧ��
							//û�л�ȡ������ԱID
							request.setAttribute("infoType", "error");
							request.setAttribute("infoContext", "ɾ��ʧ��");
							
							//ҳ����ת
							RequestDispatcher rd = request.getRequestDispatcher("StaffServlet?action=list");
						
							rd.forward(request, response);
						}
					}			
				}
				
				
				
				else{
					
					//û�л�ȡ����Ӧ��action��Ϣ������
					response.sendRedirect("StaffServlet?action=list");
					
				}			
			}

		}

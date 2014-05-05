package org.EMS.Service;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.EMS.BLL.AdminBLL;
import org.EMS.Model.AdminModel;

public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminServlet() {
        super();
    }

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		String action = request.getParameter("action");
		
		//��������Ϊ�г����й���Ա��Ϣ
		if(action.toLowerCase().equals("list")){
		
			//�����ݿ��л�ȡ���еĹ���Ա��Ŀ
			
			//�ѹ���Ա��Ϣ���ݵ�AdminManagement.jspҳ��
			ArrayList<AdminModel> adminList = AdminBLL.GetAllArrayList();
			if(adminList.size() == 0){
				request.setAttribute("infoType", "warning");
				request.setAttribute("infoContext", "û�й���Ա��Ϣ");
			}
			//������Ա�б��ݵ�ǰ̨jspҳ��
			request.setAttribute("adminList", adminList);
			
			RequestDispatcher rd = request.getRequestDispatcher("AdminManagement.jsp");
				
			rd.forward(request, response);
			
		}
		
		//***********************************************************************
		//��ӹ���Ա��Ϣ
		else if(action.toLowerCase().equals("add")){
						
			String save = request.getParameter("save");
			if(save == null || save == ""){
				
				//���Ǳ�����Ĺ���
				
				//������ӹ���Ա��Ϣ��ҳ��
				RequestDispatcher rd = request.getRequestDispatcher("AdminAdd.jsp");
				
				rd.forward(request, response);
			
			}
			else{
				
				//�Ǳ�����Ĺ���
				String adminID = request.getParameter("adminID");
				String adminName = request.getParameter("adminName");
				String adminPassword = request.getParameter("adminPassword");
				String adminIsSuper = request.getParameter("adminIsSuper");
				
				if(adminID == null || adminName == null || adminPassword == null){
					
					//�û�������û������
					request.setAttribute("infoType", "error");
					request.setAttribute("infoContext", "������Ϣ������");
					RequestDispatcher rd = request.getRequestDispatcher("AdminAdd.jsp");
					rd.forward(request, response);
				}
				else{
					
					//�û���������������Ϣ
					
					//����AdminModel���ڲ��뵽���ݿ���
					AdminModel admin = new AdminModel();
					admin.setAdminID(adminID);
					admin.setAdminName(adminName);
					admin.setAdminPassword(adminPassword);
					if(adminIsSuper == null){
						admin.setAdminIsSuper(false);
					}
					else{
						admin.setAdminIsSuper(true);
					}
					
					//ִ�в������
					if(AdminBLL.Insert(admin) > 0){
						
						//����ɹ�
						request.setAttribute("infoType", "success");
						request.setAttribute("infoContext", "����ɹ�");
						
						//��ת������Ա����ҳ��
						RequestDispatcher rd = request.getRequestDispatcher("AdminServlet?action=list");
						rd.forward(request, response);
					}
					else{
						
						//����ʧ��
						request.setAttribute("infoType", "error");
						request.setAttribute("infoContext", "����ʧ�ܣ��û����Ѵ���");
						RequestDispatcher rd = request.getRequestDispatcher("AdminAdd.jsp");
						rd.forward(request, response);
					}
				}
			}
		}
		
		//*******************************************************************************************
		//�༭����Ա��Ϣ
		else if(action.toLowerCase().equals("edit")){
						
			String save =  request.getParameter("save");
			if(save == null || save == ""){
				
				//���Ǳ�����̣�����������Ҫ�༭�Ĺ���Ա����Ϣ�����Ҵ��ݵ�ǰ̨
				
				//��ù���ԱID
				String adminId = request.getParameter("adminId");
				
				if(adminId == null || adminId == ""){
					
					//û�л�ȡ��AdminID����ת������Ա�����б����
					request.setAttribute("infoType", "error");
					request.setAttribute("infoCotnext", "����Ա��Ϣ������");
					RequestDispatcher rd = request.getRequestDispatcher("AdminServlet?action=list");
					rd.forward(request, response);
					
				}
				else{
					
					//��ȡ����AdminID
					AdminModel admin = AdminBLL.GetModel(adminId);
					if(admin == null){
						
						//û�������ݿ���ȡ��AdminModel��Ϣ
						request.setAttribute("infoType", "error");
						request.setAttribute("infoCotnext", "����Ա��Ϣ������");
						RequestDispatcher rd = request.getRequestDispatcher("AdminServlet?action=list");
						rd.forward(request, response);
						
					}
					else{
						
						//��ȡ������Ӧ�Ĺ���Ա��Ϣ
						//����Ϣ���ݵ�AdminEdit.jspҳ�棬���и���
						request.setAttribute("adminModel", admin);
						RequestDispatcher rd = request.getRequestDispatcher("AdminEdit.jsp");
						rd.forward(request, response);
						
					}				
				}
				
			}
			else{
				
				//�Ǳ������
				String adminID = request.getParameter("adminID");
				String adminName = request.getParameter("adminName");
				String adminPassword = request.getParameter("adminPassword");
				String adminIsSuper = request.getParameter("adminIsSuper");
				
				if(adminID == null || adminName == null || adminPassword == null){
					
					//�û�������û������
					request.setAttribute("infoType", "error");
					request.setAttribute("infoContext", "������Ϣ������");
					RequestDispatcher rd = request.getRequestDispatcher("AdminServlet?action=edit&adminId= " + adminID);
					rd.forward(request, response);
				}
				else{
					
					//�û���������������Ϣ
					
					//����AdminModel���ڲ��뵽���ݿ���
										
					AdminModel admin = new AdminModel();
					admin.setAdminID(adminID);
					admin.setAdminName(adminName);
					admin.setAdminPassword(adminPassword);
					if(adminIsSuper == null){
						admin.setAdminIsSuper(false);
					}
					else{
						admin.setAdminIsSuper(true);
					}
					
					//ִ�в������
					if(AdminBLL.Update(admin) > 0){
						
						//���³ɹ�
						request.setAttribute("infoType", "success");
						request.setAttribute("infoContext", "���³ɹ�");
						
						//��ת������Ա����ҳ��
						RequestDispatcher rd = request.getRequestDispatcher("AdminServlet?action=list");
						rd.forward(request, response);
					}
					else{
						
						//����ʧ��
						request.setAttribute("infoType", "error");
						request.setAttribute("infoContext", "����ʧ��");
						RequestDispatcher rd = request.getRequestDispatcher("AdminServlet?action=list");
						rd.forward(request, response);
					}
				}				
			}
						
		}
		
		//**************************************************************************************
		
		//ɾ������Ա��Ϣ
		else if(action.toLowerCase().equals("delete")){
			
			String adminId = request.getParameter("adminId");
			if(adminId == null){
				
				//û�л�ȡ������ԱID
				request.setAttribute("infoType", "error");
				request.setAttribute("infoContext", "ɾ��ʧ��");
				
				//ҳ����ת
				RequestDispatcher rd = request.getRequestDispatcher("AdminServlet?action=list");
			
				rd.forward(request, response);
			}
			else{
				
				if(AdminBLL.Delete(adminId) > 0){
					
					//ɾ���ɹ�
					request.setAttribute("infoType", "success");
					request.setAttribute("infoContext", "ɾ���ɹ�");
					
					//ҳ����ת
					RequestDispatcher rd = request.getRequestDispatcher("AdminServlet?action=list");
				
					rd.forward(request, response);
				}
				else{
					
					//ɾ��ʧ��
					//û�л�ȡ������ԱID
					request.setAttribute("infoType", "error");
					request.setAttribute("infoContext", "ɾ��ʧ��");
					
					//ҳ����ת
					RequestDispatcher rd = request.getRequestDispatcher("AdminServlet?action=list");
				
					rd.forward(request, response);
				}
			}			
		}
		
		
		
		else{
			
			//û�л�ȡ����Ӧ��action��Ϣ������
			response.sendRedirect("AdminServlet?action=list");
			
		}			
	}

}

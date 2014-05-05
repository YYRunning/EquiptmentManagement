package org.EMS.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.EMS.BLL.LendBLL;
import org.EMS.Model.LendModel;

public class LendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LendServlet() {
		super();

	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");

		// ��������Ϊ�г����н����Ϣ
		if (action.toLowerCase().equals("list")) {

			// �����ݿ��л�ȡ���еĽ����Ŀ

			// �ѹ���Ա��Ϣ���ݵ�lendManagement.jspҳ��
			ArrayList<LendModel> lendList = LendBLL.GetAllArrayList();
			if (lendList.size() == 0) {
				request.setAttribute("infoType", "warning");
				request.setAttribute("infoContext", "û�н����Ϣ");
			}
			// ������б��ݵ�ǰ̨jspҳ��
			request.setAttribute("lendList", lendList);

			RequestDispatcher rd = request.getRequestDispatcher("LendManagement.jsp");

			rd.forward(request, response);

		}

		// *********************************************************************************************************
		// ����豸��Ϣ
		else if (action.toLowerCase().equals("add")) {

			String save = request.getParameter("save");
			if (save == null || save == "") {

				//���Ǳ�����Ĺ���
				
				//��������豸��Ϣ��ҳ��
				RequestDispatcher rd = request.getRequestDispatcher("LendAdd.jsp");
				
				rd.forward(request, response);
				
			} else {

				// �Ǳ�����Ĺ���
				String lendEqpRFID = request.getParameter("lendEqpRFID");
				String lendDepartmentID = request.getParameter("lendDepartmentID");
				String lendDate = request.getParameter("lendDate");
				String lendTime = request.getParameter("lendTime");
				String lendDeadline = request.getParameter("lendDeadline");
				String lendToPeople = request.getParameter("lendToPeople");
				String lendFromCharge = request.getParameter("lendFromCharge");
				String lendFlag = request.getParameter("lendFlag");

				if (lendEqpRFID == null || lendDepartmentID == null ) {

					// �û�������û������
					request.setAttribute("infoType", "error");
					request.setAttribute("infoContext", "������Ϣ������");
					RequestDispatcher rd = request.getRequestDispatcher("LendAdd.jsp");
					rd.forward(request, response);
				} else {

					// �û���������������Ϣ

					// ����LendModel���ڲ��뵽���ݿ���
					LendModel lend = new LendModel();
					lend.setLendEqpRFID(lendEqpRFID);
					lend.setLendDepartmentID(Integer.parseInt(lendDepartmentID));
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					// String����ת����Date����
					
					//���ʱ��
					Date startDate = new Date();
					try {
						startDate = sdf.parse(lendDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}		
					lend.setLendDate(startDate);
					
					//�黹ʱ��
					Date stayDate = new Date();
					try {
						stayDate = sdf.parse(lendTime);
					} catch (ParseException e) {
						e.printStackTrace();
					}							
					lend.setLendTime(stayDate);
					
					//�����ֹʱ��
					Date deadDate = new Date();
					try {
						deadDate = sdf.parse(lendDeadline);
					} catch (ParseException e) {
						e.printStackTrace();
					}											
					lend.setLendDeadline(deadDate);
					
					lend.setLendToPeople(Long.parseLong(lendToPeople));
					lend.setLendFromCharge(Long.parseLong(lendFromCharge));
					lend.setLendFlag(lendFlag.equals("true")  ? true : false);

					

					// ִ�в������
					if (LendBLL.Insert(lend) > 0) {

						// ����ɹ�
						request.setAttribute("infoType", "success");
						request.setAttribute("infoContext", "����ɹ�");

						// ��ת���������ҳ��
						RequestDispatcher rd = request.getRequestDispatcher("LendServlet?action=list");
						rd.forward(request, response);
					} else {

						// ����ʧ��
						request.setAttribute("infoType", "error");
						request.setAttribute("infoContext", "����ʧ�ܣ����豸���Ѵ���");
						RequestDispatcher rd = request.getRequestDispatcher("LendAdd.jsp");
						rd.forward(request, response);
					}
				}
			}
		}

		// *************************************************************************************
		// �༭�豸��Ϣ
		else if(action.toLowerCase().equals("edit")){
				
				String save =  request.getParameter("save");
				if(save == null || save == ""){
					
					//���Ǳ�����̣�����������Ҫ�༭�Ĺ��ϵ���Ϣ�����Ҵ��ݵ�ǰ̨
					
					//��ù���ID
					String lendID = request.getParameter("lendID");
					
					if(lendID == null || lendID == ""){
						
						//û�л�ȡ��lendID����ת������Ա�����б����
						request.setAttribute("infoType", "error");
						request.setAttribute("infoCotnext", "������Ϣ������");
						RequestDispatcher rd = request.getRequestDispatcher("LendServlet?action=list");
						rd.forward(request, response);
						
					}
					else{
						
						//��ȡ����lendID
						LendModel lend = LendBLL.GetModel(Long.parseLong(lendID));
						if(lend == null){
							
							//û�������ݿ���ȡ��LendModel��Ϣ
							request.setAttribute("infoType", "error");
							request.setAttribute("infoCotnext", "������Ϣ������");
							RequestDispatcher rd = request.getRequestDispatcher("LendServlet?action=list");
							rd.forward(request, response);
							
						}
						else{
							
							//��ȡ������Ӧ�Ĺ���Ա��Ϣ
							//����Ϣ���ݵ�LendEdit.jspҳ�棬���и���
							request.setAttribute("LendModel", lend);
							RequestDispatcher rd = request.getRequestDispatcher("LendEdit.jsp");
							rd.forward(request, response);
							
						}				
					}
					
				}
				else{
					
					//�Ǳ������
					//�Ǳ�����Ĺ���
					String lendID = request.getParameter("lendID");
					String lendEqpRFID = request.getParameter("lendEqpRFID");
					String lendDepartmentID = request.getParameter("lendDepartmentID");
					String lendDate = request.getParameter("lendDate");
					String lendTime = request.getParameter("lendTime");
					String lendDeadline = request.getParameter("lendDeadline");
					String lendToPeople = request.getParameter("lendToPeople");
					String lendFromCharge = request.getParameter("lendFromCharge");
					String lendFlag = request.getParameter("lendFlag");

					
					if (lendEqpRFID == null || lendDepartmentID == null ) {

						// �û�������û������
						request.setAttribute("infoType", "error");
						request.setAttribute("infoContext", "������Ϣ������");
						RequestDispatcher rd = request.getRequestDispatcher("LendAdd.jsp");
						rd.forward(request, response);
					}
					else{
						
						//�û���������������Ϣ
						
						//����LendModel���ڲ��뵽���ݿ���
						
								LendModel lend = new LendModel();
								lend.setLendID(Long.parseLong(lendID));
								lend.setLendEqpRFID(lendEqpRFID);
								lend.setLendDepartmentID(Integer.parseInt(lendDepartmentID));
								
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								// String����ת����Date����
								
								//���ʱ��
								Date startDate = new Date();
								try {
									startDate = sdf.parse(lendDate);
								} catch (ParseException e) {
									e.printStackTrace();
								}		
								lend.setLendDate(startDate);
								
								//�黹ʱ��
								Date stayDate = new Date();
								try {
									stayDate = sdf.parse(lendTime);
								} catch (ParseException e) {
									e.printStackTrace();
								}							
								lend.setLendTime(stayDate);
								
								//�����ֹʱ��
								Date deadDate = new Date();
								try {
									deadDate = sdf.parse(lendDeadline);
								} catch (ParseException e) {
									e.printStackTrace();
								}											
								lend.setLendDeadline(deadDate);
								
								lend.setLendToPeople(Long.parseLong(lendToPeople));
								lend.setLendFromCharge(Long.parseLong(lendFromCharge));
								lend.setLendFlag(lendFlag.equals("true")  ? true : false);
	
						
						//ִ�в������
						if(LendBLL.Update(lend) > 0){
							
							//���³ɹ�
							request.setAttribute("infoType", "success");
							request.setAttribute("infoContext", "���³ɹ�");
							
							//��ת������Ա����ҳ��
							RequestDispatcher rd = request.getRequestDispatcher("LendServlet?action=list");
							rd.forward(request, response);
						}
						else{
							
							//����ʧ��
							request.setAttribute("infoType", "error");
							request.setAttribute("infoContext", "����ʧ��");
							RequestDispatcher rd = request.getRequestDispatcher("LendServlet?action=list");
							rd.forward(request, response);
						}
					}
				}
							
			}

		// **************************************************************************************

		// ɾ���豸��Ϣ
		else if (action.toLowerCase().equals("delete")) {

			String lendID = request.getParameter("lendID");
			LendModel lend = LendBLL.GetModel(Long.parseLong(lendID));


			if (lendID == null) {

				// û�л�ȡ������ԱID
				request.setAttribute("infoType", "error");
				request.setAttribute("infoContext", "ɾ��ʧ��");

				// ҳ����ת
				RequestDispatcher rd = request.getRequestDispatcher("lendServlet?action=list");

				rd.forward(request, response);
			} else {

				if (LendBLL.Delete(lend) > 0) {

					// ɾ���ɹ�
					request.setAttribute("infoType", "success");
					request.setAttribute("infoContext", "ɾ���ɹ�");

					// ҳ����ת
					RequestDispatcher rd = request.getRequestDispatcher("LendServlet?action=list");

					rd.forward(request, response);
				} else {

					// ɾ��ʧ��
					// û�л�ȡ�����ID
					request.setAttribute("infoType", "error");
					request.setAttribute("infoContext", "ɾ��ʧ��");

					// ҳ����ת
					RequestDispatcher rd = request.getRequestDispatcher("LendServlet?action=list");

					rd.forward(request, response);
				}
			}
		}

		else {

			// û�л�ȡ����Ӧ��action��Ϣ������
			response.sendRedirect("LendServlet?action=list");

		}
	}

}

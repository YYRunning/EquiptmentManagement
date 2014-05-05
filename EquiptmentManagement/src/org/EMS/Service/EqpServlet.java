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

import org.EMS.BLL.EqpBLL;
import org.EMS.BLL.EqpBLL;
import org.EMS.BLL.StaffBLL;
import org.EMS.Model.EqpModel;
import org.EMS.Model.StaffModel;

public class EqpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EqpServlet() {
		super();

	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");

		// ��������Ϊ�г�����Ա����Ϣ
		if (action.toLowerCase().equals("list")) {

			// �����ݿ��л�ȡ���е�Ա����Ŀ

			// �ѹ���Ա��Ϣ���ݵ�eqpManagement.jspҳ��
			ArrayList<EqpModel> eqpList = EqpBLL.GetAllArrayList();
			if (eqpList.size() == 0) {
				request.setAttribute("infoType", "warning");
				request.setAttribute("infoContext", "û��Ա����Ϣ");
			}
			// ��Ա���б��ݵ�ǰ̨jspҳ��
			request.setAttribute("eqpList", eqpList);

			RequestDispatcher rd = request
					.getRequestDispatcher("EqpManagement.jsp");

			rd.forward(request, response);

		}

		// *********************************************************************************************************
		// ����豸��Ϣ
		else if (action.toLowerCase().equals("add")) {

			String save = request.getParameter("save");
			if (save == null || save == "") {

				//���Ǳ�����Ĺ���
				
				//��������豸��Ϣ��ҳ��
				RequestDispatcher rd = request.getRequestDispatcher("EqpAdd.jsp");
				
				rd.forward(request, response);
				
			} else {

				// �Ǳ�����Ĺ���
				String eqpRFID = request.getParameter("eqpRFID");
				String eqpName = request.getParameter("eqpName");
				String eqpID = request.getParameter("eqpID");
				String eqpModel = request.getParameter("eqpModel");
				String eqpManufacturer = request.getParameter("eqpManufacturer");
				String eqpStartUsingDate = request.getParameter("eqpStartUsingDate");
				String eqpAvailableTime = request.getParameter("eqpAvailableTime");
				String eqpDepartmentID = request.getParameter("eqpDepartmentID");
				String eqpUserID = request.getParameter("eqpUserID");

				if (eqpRFID == null || eqpName == null || eqpID == null) {

					// �û�������û������
					request.setAttribute("infoType", "error");
					request.setAttribute("infoContext", "������Ϣ������");
					RequestDispatcher rd = request.getRequestDispatcher("EqpAdd.jsp");
					rd.forward(request, response);
				} else {

					// �û���������������Ϣ

					// ����eqpModel���ڲ��뵽���ݿ���
					EqpModel eqp = new EqpModel();
					eqp.setEqpRFID(eqpRFID);
					eqp.setEqpName(eqpName);
					eqp.setEqpID(Integer.parseInt(eqpID));
					eqp.setEqpModel(eqpModel);
					eqp.setEqpManufacturer(eqpManufacturer);

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					// String����ת����Date����
					Date startDate = null;
					try {
						System.out.println("Date ====> " + eqpStartUsingDate);
						startDate = sdf.parse(eqpStartUsingDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					eqp.setEqpStartUsingDate(startDate);

					Date AvailDate = new Date();
					try {
						AvailDate = sdf.parse(eqpAvailableTime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					eqp.setEqpAvailableTime(AvailDate);

					eqp.setEqpDepartmentID(Integer.parseInt(eqpDepartmentID));
					eqp.setEqpUserID(Integer.parseInt(eqpUserID));

					// ִ�в������
					if (EqpBLL.Insert(eqp) > 0) {

						// ����ɹ�
						request.setAttribute("infoType", "success");
						request.setAttribute("infoContext", "����ɹ�");

						// ��ת������Ա����ҳ��
						RequestDispatcher rd = request.getRequestDispatcher("EqpServlet?action=list");
						rd.forward(request, response);
					} else {

						// ����ʧ��
						request.setAttribute("infoType", "error");
						request.setAttribute("infoContext", "����ʧ�ܣ����豸���Ѵ���");
						RequestDispatcher rd = request.getRequestDispatcher("EqpAdd.jsp");
						rd.forward(request, response);
					}
				}
			}
		}

		// *************************************************************************************
		// �༭�豸��Ϣ
		else if (action.toLowerCase().equals("edit")) {

			String save = request.getParameter("save");
			if (save == null || save == "") {

				//���Ǳ�����̣�����������Ҫ�༭��Ա������Ϣ�����Ҵ��ݵ�ǰ̨
				
				//���Ա��ID
				String eqpID = request.getParameter("eqpID");
				
				if(eqpID == null || eqpID == ""){
					
					//û�л�ȡ��AdminID����ת������Ա�����б����
					request.setAttribute("infoType", "error");
					request.setAttribute("infoContext", "�豸��Ϣ������");
					RequestDispatcher rd = request.getRequestDispatcher("EqpServlet?action=list");
					rd.forward(request, response);
					
				}
				else{
					
					//��ȡ����eqpID
					EqpModel eqp = EqpBLL.GetModelByID(eqpID);
					if(eqp == null){
						
						//û�������ݿ���ȡ��AdminModel��Ϣ
						request.setAttribute("infoType", "error");
						request.setAttribute("infoContext", "�豸��Ϣ������");
						RequestDispatcher rd = request.getRequestDispatcher("EqpServlet?action=list");
						rd.forward(request, response);
						
					}
					else{
						
						//��ȡ������Ӧ�Ĺ���Ա��Ϣ
						//����Ϣ���ݵ�AdminEdit.jspҳ�棬���и���
						request.setAttribute("EqpModel", eqp);
						RequestDispatcher rd = request.getRequestDispatcher("EqpEdit.jsp");
						rd.forward(request, response);
						
					}				
				}
				
			}
			else {

				// �Ǳ�����Ĺ���
				String eqpRFID = request.getParameter("eqpRFID");
				String eqpName = request.getParameter("eqpName");
				String eqpID = request.getParameter("eqpID");
				String eqpModel = request.getParameter("eqpModel");
				String eqpManufacturer = request.getParameter("eqpManufacturer");
				String eqpStartUsingDate = request.getParameter("eqpStartUsingDate");
				String eqpAvailableTime = request.getParameter("eqpAvailableTime");
				String eqpDepartmentID = request.getParameter("eqpDepartmentID");
				String eqpUserID = request.getParameter("eqpUserID");

				if (eqpRFID == null || eqpName == null || eqpID == null) {

					// �û�������û������
					request.setAttribute("infoType", "error");
					request.setAttribute("infoContext", "������Ϣ������");
					RequestDispatcher rd = request.getRequestDispatcher("EqpServlet?action=edit&eqpID= "+ eqpID);
					rd.forward(request, response);
				} else {

					// �û���������������Ϣ

					// ����eqpModel���ڲ��뵽���ݿ���
					EqpModel eqp = new EqpModel();
					eqp.setEqpRFID(eqpRFID);
					eqp.setEqpName(eqpName);
					eqp.setEqpID(Integer.parseInt(eqpID));
					eqp.setEqpModel(eqpModel);
					eqp.setEqpManufacturer(eqpManufacturer);

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					// String����ת����Date����
					Date startDate = new Date();
					try {
						startDate = sdf.parse(eqpStartUsingDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					eqp.setEqpStartUsingDate(startDate);
					
					System.out.println("Date ====> " + eqpAvailableTime);

					Date AvailDate = new Date();
					try {
						AvailDate = sdf.parse(eqpAvailableTime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					eqp.setEqpAvailableTime(AvailDate);

					eqp.setEqpDepartmentID(Integer.parseInt(eqpDepartmentID));
					eqp.setEqpUserID(Integer.parseInt(eqpUserID));

					// ִ�в������
					if (EqpBLL.Update(eqp) > 0) {

						// ����ɹ�
						request.setAttribute("infoType", "success");
						request.setAttribute("infoContext", "���³ɹ�");

						// ��ת������Ա����ҳ��
						RequestDispatcher rd = request
								.getRequestDispatcher("EqpServlet?action=list");
						rd.forward(request, response);
					} else {

						// ����ʧ��
						request.setAttribute("infoType", "error");
						request.setAttribute("infoContext", "����ʧ��");
						RequestDispatcher rd = request
								.getRequestDispatcher("EqpServlet?action=list");
						rd.forward(request, response);
					}
				}
			}
		}

		// **************************************************************************************

		// ɾ���豸��Ϣ
		else if (action.toLowerCase().equals("delete")) {

			String eqpID = request.getParameter("eqpID");
			EqpModel eqp = EqpBLL.GetModelByID(eqpID);

			if (eqpID == null) {

				// û�л�ȡ������ԱID
				request.setAttribute("infoType", "error");
				request.setAttribute("infoContext", "ɾ��ʧ��");

				// ҳ����ת
				RequestDispatcher rd = request
						.getRequestDispatcher("eqpServlet?action=list");

				rd.forward(request, response);
			} else {

				if (EqpBLL.Delete(eqp) > 0) {

					// ɾ���ɹ�
					request.setAttribute("infoType", "success");
					request.setAttribute("infoContext", "ɾ���ɹ�");

					// ҳ����ת
					RequestDispatcher rd = request
							.getRequestDispatcher("EqpServlet?action=list");

					rd.forward(request, response);
				} else {

					// ɾ��ʧ��
					// û�л�ȡ���豸ID
					request.setAttribute("infoType", "error");
					request.setAttribute("infoContext", "ɾ��ʧ��");

					// ҳ����ת
					RequestDispatcher rd = request
							.getRequestDispatcher("EqpServlet?action=list");

					rd.forward(request, response);
				}
			}
		}

		else {

			// û�л�ȡ����Ӧ��action��Ϣ������
			response.sendRedirect("EqpServlet?action=list");

		}
	}

}

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





import org.EMS.BLL.BreakBLL;
import org.EMS.Model.BreakModel;

public class BreakServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BreakServlet() {
        super();

    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String action = request.getParameter("action");
		
		
		//��������Ϊ�г����й�����Ϣ
		if(action.toLowerCase().equals("list")){
		
			//�����ݿ��л�ȡ���еĹ�����Ŀ
			
			//�ѹ���Ա��Ϣ���ݵ�BreakManagement.jspҳ��
			ArrayList<BreakModel> breakList = BreakBLL.GetAllArrayList();
			if(breakList.size() == 0){
				request.setAttribute("infoType", "warning");
				request.setAttribute("infoContext", "û�й�����Ϣ");
			}
			//��������Ϣ�б��ݵ�ǰ̨jspҳ��
			request.setAttribute("breakList", breakList);
			
			RequestDispatcher rd = request.getRequestDispatcher("BreakManagement.jsp");
				
			rd.forward(request, response);
			
		}
		
		//*********************************************************************************************************
		//��ӹ�����Ϣ
		else if(action.toLowerCase().equals("add")){
			
			String save = request.getParameter("save");
			if(save == null || save == ""){
				
				//���Ǳ�����Ĺ���
				
				//������ӹ�����Ϣ��ҳ��
				RequestDispatcher rd = request.getRequestDispatcher("BreakAdd.jsp");
				
				rd.forward(request, response);
			
			}
			else{
				
				//�Ǳ�����Ĺ���
				String breakEqpID = request.getParameter("breakEqpID");
				String breakEqpRFID = request.getParameter("breakEqpRFID");
				String breakDescribe = request.getParameter("breakDescribe");
				String breakFixman = request.getParameter("breakFixman");
				String breakReportDate = request.getParameter("breakReportDate");
				
				
				if(breakEqpID == null || breakEqpRFID == null){
					
					//�û�������û������
					
					request.setAttribute("infoType", "error");
					request.setAttribute("infoContext", "������Ϣ������");
					RequestDispatcher rd = request.getRequestDispatcher("BreakAdd.jsp");
					rd.forward(request, response);
				}
				else{
					
					//�û���������������Ϣ
					
					//����BreakModel���ڲ��뵽���ݿ���
					BreakModel brk = new BreakModel();
					brk.setBreakEqpID(Long.parseLong(breakEqpID));
					brk.setBreakEqpRFID(breakEqpRFID);
					brk.setBreakDescribe(breakDescribe);
					brk.setBreakFixman(Long.parseLong(breakFixman));
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					// String����ת����Date����
					Date startDate = null;
					try {
						startDate = sdf.parse(breakReportDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					brk.setBreakReportDate(startDate);

					
					//ִ�в������
					if(BreakBLL.Insert(brk) > 0){
						
						//����ɹ�
						request.setAttribute("infoType", "success");
						request.setAttribute("infoContext", "����ɹ�");
						
						//��ת������Ա����ҳ��
						RequestDispatcher rd = request.getRequestDispatcher("BreakServlet?action=list");
						rd.forward(request, response);
					}
					else{
						
						//����ʧ��
						request.setAttribute("infoType", "error");
						request.setAttribute("infoContext", "����ʧ��");
						RequestDispatcher rd = request.getRequestDispatcher("BreakAdd.jsp");
						rd.forward(request, response);
					}
				}
			}
		}
		
		
		//*************************************************************************************
		//�༭������Ϣ
		else if(action.toLowerCase().equals("edit")){
			
			String save =  request.getParameter("save");
			if(save == null || save == ""){
				
				//���Ǳ�����̣�����������Ҫ�༭�Ĺ��ϵ���Ϣ�����Ҵ��ݵ�ǰ̨
				
				//��ù���ID
				String breakID = request.getParameter("breakID");
				
				if(breakID == null || breakID == ""){
					
					//û�л�ȡ��AdminID����ת������Ա�����б����
					request.setAttribute("infoType", "error");
					request.setAttribute("infoCotnext", "������Ϣ������");
					RequestDispatcher rd = request.getRequestDispatcher("BreakServlet?action=list");
					rd.forward(request, response);
					
				}
				else{
					
					//��ȡ����BreakID
					BreakModel brk = BreakBLL.GetModel(Long.parseLong(breakID));
					if(brk == null){
						
						//û�������ݿ���ȡ��BreakModel��Ϣ
						request.setAttribute("infoType", "error");
						request.setAttribute("infoCotnext", "������Ϣ������");
						RequestDispatcher rd = request.getRequestDispatcher("BreakServlet?action=list");
						rd.forward(request, response);
						
					}
					else{
						
						//��ȡ������Ӧ�Ĺ���Ա��Ϣ
						//����Ϣ���ݵ�BreakEdit.jspҳ�棬���и���
						request.setAttribute("BreakModel", brk);
						RequestDispatcher rd = request.getRequestDispatcher("BreakEdit.jsp");
						rd.forward(request, response);
						
					}				
				}
				
			}
			else{
				
				//�Ǳ������
				//�Ǳ�����Ĺ���
				String breakID = request.getParameter("breakID");
				String breakEqpID = request.getParameter("breakEqpID");
				String breakEqpRFID = request.getParameter("breakEqpRFID");
				String breakDescribe = request.getParameter("breakDescribe");
				String breakFixman = request.getParameter("breakFixman");
				String breakReportDate = request.getParameter("breakReportDate");
				
				if(breakID == null || breakEqpID == null || breakEqpRFID == null){
					
					//�û�������û������
					request.setAttribute("infoType", "error");
					request.setAttribute("infoContext", "������Ϣ������");
					RequestDispatcher rd = request.getRequestDispatcher("BreakServlet?action=list");
					rd.forward(request, response);
				}
				else{
					
					//�û���������������Ϣ
					
					//����BreakModel���ڲ��뵽���ݿ���
					BreakModel brk = new BreakModel();
					brk.setBreakID(Long.parseLong(breakID));
					brk.setBreakEqpID(Long.parseLong(breakEqpID));
					brk.setBreakEqpRFID(breakEqpRFID);
					brk.setBreakDescribe(breakDescribe);
					brk.setBreakFixman(Long.parseLong(breakFixman));

					
					//ִ�в������
					if(BreakBLL.Update(brk) > 0){
						
						//���³ɹ�
						request.setAttribute("infoType", "success");
						request.setAttribute("infoContext", "���³ɹ�");
						
						//��ת������Ա����ҳ��
						RequestDispatcher rd = request.getRequestDispatcher("BreakServlet?action=list");
						rd.forward(request, response);
					}
					else{
						
						//����ʧ��
						request.setAttribute("infoType", "error");
						request.setAttribute("infoContext", "����ʧ��");
						RequestDispatcher rd = request.getRequestDispatcher("BreakServlet?action=list");
						rd.forward(request, response);
					}
				}
			}
						
		}
		
		
		//**************************************************************************************
		
				//ɾ��������Ϣ
				else if(action.toLowerCase().equals("delete")){
					
					String breakID = request.getParameter("breakID");
					BreakModel brk = BreakBLL.GetModel(Long.parseLong(breakID));
					if(breakID == null){
						
						//û�л�ȡ������ԱID
						request.setAttribute("infoType", "error");
						request.setAttribute("infoContext", "ɾ��ʧ��");
						
						//ҳ����ת
						RequestDispatcher rd = request.getRequestDispatcher("breakServlet?action=list");
					
						rd.forward(request, response);
					}
					else{
						
						if(BreakBLL.Delete(brk) > 0){
							
							//ɾ���ɹ�
							request.setAttribute("infoType", "success");
							request.setAttribute("infoContext", "ɾ���ɹ�");
							
							//ҳ����ת
							RequestDispatcher rd = request.getRequestDispatcher("BreakServlet?action=list");
						
							rd.forward(request, response);
						}
						else{
							
							//ɾ��ʧ��
							//û�л�ȡ������ԱID
							request.setAttribute("infoType", "error");
							request.setAttribute("infoContext", "ɾ��ʧ��");
							
							//ҳ����ת
							RequestDispatcher rd = request.getRequestDispatcher("BreakServlet?action=list");
						
							rd.forward(request, response);
						}
					}			
				}
				
				
				
				else{
					
					//û�л�ȡ����Ӧ��action��Ϣ������
					response.sendRedirect("BreakServlet?action=list");
					
				}			
			}

		}

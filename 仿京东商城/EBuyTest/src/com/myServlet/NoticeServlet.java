package com.myServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.NoticeDao;
import com.entity.Notice;
import com.util.PageUtil;

@SuppressWarnings("serial")
public class NoticeServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String op = request.getParameter("op");
		//显示用户
		if(op.equals("showNotice")){
			showNotice(request, response);
		}
		//添加公告
		if(op.equals("addNotice")){
			noticeAdd(request, response);
		}
		//显示公告详细信息
		if(op.equals("lookNotice")){
			lookUser(request, response);
		}
		//修改公告信息
		if(op.equals("upData")){
			upData(request, response);
		}
		if(op.equals("upNotice")){
			upNotice(request, response);
		}
		//删除公告
		if(op.equals("delOne")){
			delOne(request, response);
		}
		//跳转添加公告
		if(op.equals("intoNotice")){
			intoNotice(request, response);
		}
	}
	private void intoNotice(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/Backstage/noticeAdd.jsp").forward(request, response);
	}
	//删除公告
	private void delOne(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		NoticeDao nd = new NoticeDao();
		if(nd.delNotice(id)>0){
			showaAll(request, response);
		}else{
			showaAll(request, response);
		}
	}
	//修改公告
	private void upNotice(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		NoticeDao cd = new NoticeDao();
		Notice n = new Notice();
		int id = Integer.parseInt(request.getParameter("noticeID"));
		n.setId(id);
		String title = request.getParameter("noticeTitle");
		n.setTitle(title);
		String user = request.getParameter("noticeUser");
		n.setUserName(user);
		String text = request.getParameter("noticeText");
		n.setText(text);
		n.setCreateTime(cd.getDate());
		if(cd.upNotice(n)>0){
			showaAll(request, response);
			System.out.println("修改成功");
		}else{
			showaAll(request, response);
		}
	}
	private void upData(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		NoticeDao nd = new NoticeDao();
		Notice notice = nd.getOne(id);
		if(notice!=null){
			request.setAttribute("notice", notice);
			request.getRequestDispatcher("/Backstage/noticeUpdate.jsp").forward(request, response);
		}
	}
	private void noticeAdd(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		NoticeDao cd = new NoticeDao();
		Notice n = new Notice();
		String title = request.getParameter("noticeTitle");
		n.setTitle(title);
		String user = request.getParameter("noticeUser");
		n.setUserName(user);
		String text = request.getParameter("noticeText");
		n.setText(text);
		n.setCreateTime(cd.getDate());
		if(cd.addNotice(n)>0){
			showaAll(request, response);
			System.out.println("sceescs");
		}else{
			showaAll(request, response);
		}
	}
	//
	//公告详细信息
	public void lookUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		NoticeDao cd = new NoticeDao();
		Notice notice = cd.getOne(id);
		if(notice!=null){
			request.setAttribute("notice", notice);
			request.getRequestDispatcher("/Backstage/noticeView.jsp").forward(request, response);
		}
	}
	//不带关键字
	private void showaAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		NoticeDao  cd = new NoticeDao();
		int pageNo=1;
		int pageSize=8;
		PageUtil<Notice> showPageUser = cd.GetPageProviders(pageNo, pageSize);
		//分页集合返回到页面
		if(showPageUser!=null){
			request.setAttribute("page", showPageUser);
			//跳转到用户列表界面
			request.getRequestDispatcher("/Backstage/noticeList.jsp").forward(request, response);
		}
	}
	//带关键字
	public void showNotice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		NoticeDao cd = new NoticeDao();
		String pageN = request.getParameter("no");
		String word = request.getParameter("word");
		word=new String(word.getBytes("iso-8859-1"),"utf-8");
		int pageNo=1;
		if(pageN!=""){
			pageNo = Integer.parseInt(pageN);
		}
		int pageSize=8;
		//模糊查询
		if(word!=null&&!word.equals("")){
			PageUtil<Notice> showPageUser = cd.pageWord(pageNo, pageSize, word);
			if(showPageUser!=null){
				request.setAttribute("page", showPageUser);
				request.setAttribute("word", word);
				//跳转到用户列表界面
				request.getRequestDispatcher("/Backstage/noticeList.jsp").forward(request, response);
			}
		//全部查询
		}else{
			PageUtil<Notice> showPageUser = cd.GetPageProviders(pageNo, pageSize);
			//分页集合返回到页面
			if(showPageUser!=null){
				request.setAttribute("page", showPageUser);
				//跳转到用户列表界面
				request.getRequestDispatcher("/Backstage/noticeList.jsp").forward(request, response);
			}
		}
	}

}

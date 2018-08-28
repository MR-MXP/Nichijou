package com.myServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.ProviderTypeDao;
import com.dao.ProvidersDao;
import com.entity.ProviderType;
import com.entity.Providers;
import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.SmartUpload;
import com.util.PageUtil;

@SuppressWarnings("serial")
public class ProviderListServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String op = request.getParameter("op");
		//显示商品界面
		if(op.equals("showProvider")&&op!=null){
			showProvider(request, response);
		//显示用户
		}
		//校验重名
		if(op.equals("rname")){
			rname(request, response);
		}
		//跳转到添加商品界面
		if(op.equals("addProvider")){
			addJsp(request, response);
		}
		//添加商品
		if(op.equals("addpro")){
			addProvider(request, response);
		}
		//跳转添加商品图片
		if(op.equals("addPhoto")){
			addPhoto(request, response);
			//addPhoto(request, response);
		}
		//添加商品图片
		if(op.equals("addProPhoto")){
			addProPhoto(request, response);
		}
		//查看商品
		if(op.equals("lookUser")){
			lookUser(request, response);
		}
		//跳转修改界面
		if(op.equals("upData")){
			upData(request, response);
		}
		//修改商品
		if(op.equals("upProvider")){
			upProvider(request, response);
		}
		//删除商品
		if(op.equals("delOne")){
			dellProvider(request, response);
		}
	}
	//跳转添加图片界面
	private void addPhoto(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.getRequestDispatcher("/Backstage/providerAddPhoto.jsp").forward(request, response);
	}
	private void addJsp(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ProviderTypeDao ptd = new ProviderTypeDao();
		request.getRequestDispatcher("/Backstage/providerAdd.jsp").forward(request, response);
	}
	//添加商品
	private void upProvider(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Providers p = new Providers();
		ProvidersDao  cd = new ProvidersDao();
		//商品类型
		int providerId = Integer.parseInt(request.getParameter("providerId"));
		p.setGoodsId(providerId);
		String providerTypeId = request.getParameter("providerTypeId"); 
		p.setTypeName(providerTypeId);
		System.out.println(providerTypeId);
		//商品名称
		String providerName = request.getParameter("providerName");
		p.setGoosName(providerName);
		System.out.println(providerName);
		//商品价格
		float providerPrice = Float.parseFloat(request.getParameter("providerPrice"));
		p.setPrice(providerPrice);
		System.out.println(providerPrice);
		//打折
		float discount = Float.parseFloat(request.getParameter("discount"));
		p.setDiscount(discount);
		System.out.println(discount);
		//新品
		int isNew = Integer.parseInt(request.getParameter("isNew"));
		p.setIsNew(isNew);
		System.out.println(isNew);
		//下架
		int statuss = Integer.parseInt(request.getParameter("statuss"));
		p.setStatuss(statuss);
		System.out.println(statuss);
		//备注
		String remark = request.getParameter("remark");
		p.setRamark(remark);
		System.out.println(remark);
		int i = cd.updataC(p);
		if(i>0){
			showaAll(request, response);
		}else{
			showaAll(request, response);
		}
	}
	//跳转修改界面
	private void upData(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		ProvidersDao  cd = new ProvidersDao();
		Providers provider = cd.getOne(id);
		if(provider!=null){
			request.setAttribute("provider", provider);
			request.getRequestDispatcher("/Backstage/providerUp.jsp").forward(request, response);
		}else{
			showaAll(request, response);
		}
	}
	//商品详细信息
	public void lookUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		ProvidersDao  cd = new ProvidersDao();
		Providers providers = cd.getOne(id);
		if(providers!=null){
			request.setAttribute("providers", providers);
			request.getRequestDispatcher("/Backstage/providerListView.jsp").forward(request, response);
		}
	}
	//删除商品
	private void dellProvider(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String ids = request.getParameter("id");
		String[] idarr = ids.split(",");
		ProvidersDao  du = new ProvidersDao();
		int dnum = du.delCustomer(idarr);
		if(dnum==idarr.length){
			showaAll(request, response);
		}else{
			showaAll(request, response);
		}
	}
	//添加商品图片
	private void addProPhoto(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		SmartUpload su = new SmartUpload();
		String id = request.getParameter("id");
		ProvidersDao  cd = new ProvidersDao();
		String imgPath="";
		//提交商品图片路径
		try {
			//创建上传对象
			su.initialize(getServletConfig(), request, response);
			//设置中文乱码
			su.setCharset("utf-8");
			//限制文件上传大小
			su.setMaxFileSize(1024*1024*5);
			//上传临时文件夹
			su.upload();
			//获取临时文件夹的文件
			Files files = su.getFiles();
			for(int i=0;i<files.getCount();i++){
				//获取具体文件
				File file = files.getFile(i);
				//判断文件是否为空
				if(!file.isMissing()){
					//获取文件名
					Date d = new Date();
					Long date = d.getTime();
					String fileName = file.getFileName();
					//文件名跟上当前时间,避免重名
					String realPath = request.getRealPath("loade/"+date+fileName);
					imgPath="/loade/"+date+fileName;
					file.saveAs(realPath);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(imgPath!=null||imgPath!=""){
			int i = cd.addPhoto(imgPath,id);
			if(i>0){
				showaAll(request, response);
			}else{
				showaAll(request, response);
			}
		}
	}

	private void addProvider(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		Providers p = new Providers();
		ProvidersDao  cd = new ProvidersDao();
		//商品类型
		String providerTypeId = request.getParameter("providerTypeId"); 
		p.setTypeName(providerTypeId);
		//商品名称
		String providerName = request.getParameter("providerName");
		p.setGoosName(providerName);
		//商品价格
		float providerPrice = Float.parseFloat(request.getParameter("providerPrice"));
		p.setPrice(providerPrice);
		//打折
		float discount = Float.parseFloat(request.getParameter("discount"));
		p.setDiscount(discount);
		//新品
		int isNew = Integer.parseInt(request.getParameter("isNew"));
		p.setIsNew(isNew);
		//下架
		int statuss = Integer.parseInt(request.getParameter("statuss"));
		p.setStatuss(statuss);
		//备注
		String remark = request.getParameter("remark");
		p.setRamark(remark);
		if(cd.addGoods(p)>0){
			showaAll(request, response);
		}else{
			showaAll(request, response);
		}
	}
	//校验用户名
	private void rname(HttpServletRequest request, HttpServletResponse response) throws
	IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		name = new String(name.getBytes("ISO-8859-1"),"utf-8");
		ProvidersDao  cd = new ProvidersDao();
		if(cd.rProvider(name)||name==null||name.equals("")){
			out.print("<font color='red'>X已经存在并且不能为空</font>");
		}else{
			out.print("");
		}
	}
	//不带关键字
	@SuppressWarnings("unused")
	private void showaAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ProvidersDao  cd = new ProvidersDao();
		int pageNo=1;
		int pageSize=8;
		PageUtil<Providers> showPageUser = cd.showPageUser(pageNo, pageSize);
		//分页集合返回到页面
		if(showPageUser!=null){
			request.setAttribute("page", showPageUser);
			//跳转到用户列表界面
			request.getRequestDispatcher("/Backstage/providerList.jsp").forward(request, response);
		}
	}
	//带关键字
	public void showProvider(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProvidersDao  cd = new ProvidersDao();
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
			PageUtil<Providers> showPageUser = cd.pageWord(pageNo, pageSize, word);
			if(showPageUser!=null){
				request.setAttribute("page", showPageUser);
				request.setAttribute("word", word);
				//跳转到用户列表界面
				request.getRequestDispatcher("/Backstage/providerList.jsp").forward(request, response);
			}
		//全部查询
		}else{
			PageUtil<Providers> showPageUser = cd.showPageUser(pageNo, pageSize);
			ProviderTypeDao ptd = new ProviderTypeDao();
			ArrayList<ProviderType> typeArr = ptd.getTypeArr();
			HttpSession session = request.getSession();
			//分页集合返回到页面
			if(showPageUser!=null){
				request.setAttribute("page", showPageUser);
				session.setAttribute("typeArr", typeArr);
				//跳转到用户列表界面
				request.getRequestDispatcher("/Backstage/providerList.jsp").forward(request, response);
			}
		}
	}

}

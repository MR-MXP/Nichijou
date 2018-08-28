package com.mxp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mxp.beans.UserInfo;
import com.mxp.mapper.UserInfoMapper;
import com.mxp.service.UserInfoService;
@Service(value="userInfoService")
@Transactional
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UserInfoMapper UserInfoMapper;
	@Override
	public int add(UserInfo userInfo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public UserInfo get(int id) {
		UserInfo userInfo = UserInfoMapper.get(id);
		// TODO Auto-generated method stub
		return userInfo;
	}

	@Override
	public int updata(UserInfo userInfo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<UserInfo> list() {
		// TODO Auto-generated method stub
		System.out.println("½øÈëservice");
		UserInfoMapper.list();
		return null;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

}

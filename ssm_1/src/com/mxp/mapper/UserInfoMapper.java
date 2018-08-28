package com.mxp.mapper;

import java.util.List;

import com.mxp.beans.UserInfo;

public interface UserInfoMapper {
	int add(UserInfo userInfo);
	void delete(int id);
	UserInfo get(int id);
	int updata(UserInfo userInfo);
	List<UserInfo> list();
	int count();
}

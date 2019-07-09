package com.team.zip.model.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.team.zip.model.vo.PhotoCombineVO;

@Repository
public class PhotoCombineDAO extends SqlSessionDaoSupport{
	
	public List<PhotoCombineVO> getData(int num) {
		return getSqlSession().selectList("photo.getReplyData",num);
	}
	
	public List<PhotoCombineVO> getReplyData() {
		return getSqlSession().selectList("photo.photoReplyData");
	}
	
}

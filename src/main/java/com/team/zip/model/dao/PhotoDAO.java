package com.team.zip.model.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.team.zip.model.vo.PhotoVO;

@Repository
public class PhotoDAO extends SqlSessionDaoSupport {
	
	public List<PhotoVO> getList(){
		return getSqlSession().selectList("photo.photoDataList");
	}
	
	public PhotoVO getData(int num) {
		return getSqlSession().selectOne("photo.photoDetail", num);
	}
	
	public void photoUpdateReadcount(int num) {
		getSqlSession().update("photoUpdateReadcount", num);
	}
	
	public void insertPhoto(PhotoVO pvo) {
		getSqlSession().insert("photo.insertPhoto", pvo);
	}
	
	//사진 정렬
	public List<PhotoVO> photoSortByHits(){
		return getSqlSession().selectList("photo.photoSortByHits");
	}
	
	public List<PhotoVO> photoSortByNew(){
		return getSqlSession().selectList("photo.photoSortByNew");
	}
	
	public List<PhotoVO> photoSortByHomeType(){
		return getSqlSession().selectList("photo.photoSortByHomeType");
	}
	
	public List<PhotoVO> photoSortByPyeong(){
		return getSqlSession().selectList("photo.photoSortByPyeong");
	}

}

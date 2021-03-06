package com.team.zip.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.team.zip.model.vo.MainCommunityVO;
import com.team.zip.model.vo.ProductVO;

@Repository
public class MainCommunityDAO extends SqlSessionDaoSupport {
	
	public List<MainCommunityVO> selectPhotoList(){
		List<MainCommunityVO> list = new ArrayList<MainCommunityVO>();
		list = getSqlSession().selectList("MainSql.selectPhotoList");
		return list;
	}
	
	public List<MainCommunityVO> selectZipList() {
		List<MainCommunityVO> zipList = new ArrayList<MainCommunityVO>();
		zipList = getSqlSession().selectList("MainSql.selectZipList"); 
		return zipList;
	}
	
	public List<ProductVO> selectDealList() {
		List<ProductVO> dealList = new ArrayList<ProductVO>();
		dealList = getSqlSession().selectList("MainSql.selectDealList");
		return dealList;
	}
	
	public List<ProductVO> selectRankList(String categoryNm) {
		List<ProductVO> rankList = new ArrayList<ProductVO>();
		rankList = getSqlSession().selectList("MainSql.selectRankList", categoryNm);
		return rankList;
	}

}

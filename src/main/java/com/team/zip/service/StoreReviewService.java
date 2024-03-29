package com.team.zip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.zip.model.dao.StoreReviewDAO;
import com.team.zip.model.vo.MemberVO;
import com.team.zip.model.vo.StoreReviewVO;

@Service
public class StoreReviewService {

	@Autowired
	private StoreReviewDAO storeReviewDAO;
	
	public void insertReview(StoreReviewVO storeReviewVO) {
		storeReviewDAO.insertReview(storeReviewVO);
	}

	public List<StoreReviewVO> getReviewList(StoreReviewVO storeReviewVO) {
		return storeReviewDAO.getReviewList(storeReviewVO);
	}
	
	public int getReviewTotalCount(int prodNo) {
		return storeReviewDAO.getReviewTotalCount(prodNo);
	}
	
	public String selectReviewLike(String memberNo) {
		return storeReviewDAO.selectReviewLike(memberNo);
	}
	
	public int toggleReviewLike(MemberVO mvo) {
		return storeReviewDAO.toggleReviewLike(mvo);
	}
	
	public StoreReviewVO selectReviewListByRewNo(int rewNo) {
		return storeReviewDAO.selectReviewListByRewNo(rewNo);
	}
	
//	public void updateReview(StoreReviewVO storeReviewVO) {
//		storeReviewDAO.updateReview(storeReviewVO);
//	}
	
	public void deleteReview(int rewNo) {
		storeReviewDAO.deleteReview(rewNo);
	}
}

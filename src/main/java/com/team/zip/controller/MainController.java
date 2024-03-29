package com.team.zip.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.team.zip.model.vo.MainCommunityVO;
import com.team.zip.model.vo.MemberVO;
import com.team.zip.model.vo.ProductVO;
import com.team.zip.service.CartService;
import com.team.zip.service.MainService;
import com.team.zip.service.MemberService;

@Controller
public class MainController {
	
	@Autowired
	MemberService mservice;
	
	@Autowired
	private MainService mainService;
	@Autowired
	private CartService caservice;
	
	@RequestMapping("/main.do")
	public ModelAndView mainGo(HttpSession session, @RequestParam(value="where", defaultValue = "home") String where,
			@RequestParam(value="loginCondition" ,defaultValue = "0") String loginCondition)
	{
		System.out.println("loginCondition : "+loginCondition);
		ModelAndView mav = new ModelAndView();
		
		if(session.getAttribute("member_no") != null) {
			int memberNo = (Integer)session.getAttribute("member_no");
			MemberVO mvo = mservice.getMember(memberNo);
			
			if(mvo.getMember_birth() != null) {
				String temp[] = mvo.getMember_birth().split("-");
				mvo.setMember_birth_1i(temp[0]);
				mvo.setMember_birth_2i(temp[1]);
				mvo.setMember_birth_3i(temp[2]);
			}
			session.setAttribute("cartCnt", caservice.getListCnt(memberNo));
            session.setAttribute("mvo", mvo);
            session.setMaxInactiveInterval(21600);
		}
		
		String loginCondition2 = (String)session.getAttribute("loginCondition");
		if(loginCondition2 != null) {
			if(loginCondition2.equals("1")) {
				mav.addObject("loginCondition", "login");
			} else if(loginCondition2.equals("2")) {
				mav.addObject("loginCondition", "logout");
			} else if(loginCondition2.equals("3")) {
				mav.addObject("loginCondition", "signup");
			}
		}
		
		session.removeAttribute("loginCondition");
		session.removeAttribute("category");
		session.setAttribute("category", "main");
		
		
		List<MainCommunityVO> photoList = mainService.selectPhotoList();
		List<MainCommunityVO> zipList = mainService.selecZipList();
		List<ProductVO> dealList = mainService.selectDealList();
		List<ProductVO> rankListAll = mainService.selectRankLsit(null);
		List<ProductVO> rankListFurniture = mainService.selectRankLsit("가구");
		List<ProductVO> rankListElectronics = mainService.selectRankLsit("가전");
		List<ProductVO> rankListInterior = mainService.selectRankLsit("인테리어");
		
		
		mav.addObject("photoList", photoList);
		mav.addObject("where", where);
		mav.addObject("zipList", zipList);
		mav.addObject("dealList", dealList);
		mav.addObject("rankListAll", rankListAll);
		mav.addObject("rankListFurniture", rankListFurniture);
		mav.addObject("rankListElectronics", rankListElectronics);
		mav.addObject("rankListInterior", rankListInterior);
		mav.setViewName("/main/main");
		return mav;
	}
	
	
	//Team-Story로 이동
	@RequestMapping("/main/story.do")
	public String storyGo() {
			
		return "/1/main/teamstory";
	}
}

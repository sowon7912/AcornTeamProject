package com.team.zip.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.team.zip.model.vo.MainCommunityVO;
import com.team.zip.model.vo.MemberVO;
import com.team.zip.service.MainService;
import com.team.zip.service.MemberService;

@Controller
public class MainController {
	
	@Autowired
	MemberService mservice;
	
	@Autowired
	private MainService mainService;
	
	@RequestMapping("/main.do")
	public ModelAndView mainGo(HttpSession session, @RequestParam(value="where", defaultValue = "home") String where)
	{
		if(session.getAttribute("member_no") != null) {
			int memberNo = (Integer)session.getAttribute("member_no");
			MemberVO mvo = mservice.getMember(memberNo);
			
			if(mvo.getMember_birth() != null) {
				String temp[] = mvo.getMember_birth().split("-");
				mvo.setMember_birth_1i(temp[0]);
				mvo.setMember_birth_2i(temp[1]);
				mvo.setMember_birth_3i(temp[2]);
			}
			System.out.println(mvo.getMember_sex());

			session.setAttribute("mvo", mvo);
			session.setMaxInactiveInterval(10800);
		}
		
		List<MainCommunityVO> photoList = mainService.selectPhotoList();

		List<MainCommunityVO> zipList = mainService.selecZipList();
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("photoList", photoList);
		mav.addObject("where", where);
		mav.addObject("zipList", zipList);
		
		mav.setViewName("/main/main");
		return mav;
	}
}

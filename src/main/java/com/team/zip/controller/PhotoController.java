package com.team.zip.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.team.zip.model.vo.MemberVO;
import com.team.zip.model.vo.PhotoCombineVO;
import com.team.zip.model.vo.PhotoReplyVO;
import com.team.zip.model.vo.PhotoVO;
import com.team.zip.service.CombineService;
import com.team.zip.service.MemberService;
import com.team.zip.service.PhotoReplyService;
import com.team.zip.service.PhotoService;

@Controller
public class PhotoController {
	
	@Autowired
	private PhotoService service;
	@Autowired
	private MemberService mservice;
	@Autowired
	private PhotoReplyService rservice;
	@Autowired
	private CombineService cservice;
	
	@RequestMapping("photo/photolist.do")
	public ModelAndView photo(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		List<PhotoVO> list = service.getList();
		List<MemberVO> mlist = mservice.getMemberList();
		List<PhotoCombineVO> clist = cservice.getReplyData();
		
		model.addObject("list",list);
		model.addObject("mlist",mlist);
		model.addObject("clist", clist);
		model.setViewName("/2/photo/list");
		return model;
	}
	
	@RequestMapping("photo/photowrite.do")
	public String photowrite() {
		return "/1/photo/writeform";
	}
	
	@RequestMapping(value="photo/write.do", method=RequestMethod.POST)
	public String write(@ModelAttribute PhotoVO pvo, HttpServletRequest request) {
		System.out.println(request.getParameter("member_no"));
		service.photoInsert(pvo);
		return "redirect:photolist.do";
	}
	
	@RequestMapping("photo/photodetail.do")
	public String photodetail(Model model, @RequestParam int num) {
		
		//조회 1증가
		service.photoUpdateReadcount(num);
		
		//데이터 가져오기
		PhotoVO pvo = service.getData(num);
		MemberVO mvo = mservice.getData(num);
		List<PhotoCombineVO> cvo = cservice.getData(num);
		
		model.addAttribute("pvo", pvo);
		model.addAttribute("mvo", mvo);
		model.addAttribute("cvo", cvo);
		
		return "/2/photo/detail";
	}
	
}


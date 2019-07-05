package com.team.zip.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.team.zip.model.vo.BoardVO;
import com.team.zip.service.BoardService;
import com.team.zip.util.SpringFileWriter;

@Controller
public class BoardController {

	@Autowired
	private BoardService service;

	@RequestMapping("/board/list.do") 
	public ModelAndView list(
			@RequestParam(value="pageNum",defaultValue="1")
			int currentPage)
	{
		ModelAndView model=new ModelAndView();
		int totalCount;//총 데이타 갯수
		
		totalCount=service.getTotalCount();

		//페이징 복사한거
		//페이징처리에 필요한 변수들 선언
		int totalPage; //총 페이지수
		int startNum; //각페이지의시작번호
		int endNum; //각페이지의끝번호
		int startPage; //블럭의 시작페이지
		int endPage; //블럭의 끝페이지
		int no;//출력할 시작번호
		int perPage=5;//한페이지당 보여질 글의갯수
		int perBlock=5;//한블럭당 보여질 페이지의 갯수

		//총 페이지수를 구한다
		totalPage=totalCount/perPage+(totalCount%perPage>0?1:0);

		//존재하지 않는 페이지일경우 마지막 페이지로 가기
		if(currentPage>totalPage)
			currentPage=totalPage;

		//각 블럭의 시작페이지와 끝페이지를 구한다
		//perBlock 이 5일경우
		//예) 현재페이지가 3 일경우 시작페이지:1,끝페이지:5
		//예) 현재페이지가 7 일경우 시작페이지:6,끝페이지:10
		//예) 현재페이지가 11 일경우 시작페이지:11,끝페이지:15
		startPage=(currentPage-1)/perBlock*perBlock+1;
		endPage=startPage+perBlock-1;
		//마지막 블럭은 끝페이지가 총 페이지수와 같아야함
		if(endPage>totalPage)
			endPage=totalPage;

		//각 페이지의 시작번호와 끝번호를 구한다
		//perPage 가 5일경우
		//예) 1페이지 : 시작번호 : 1, 끝번호:5
		//    3페이지 :           11        15
		startNum=(currentPage-1)*perPage+1;
		endNum=startNum+perPage-1;
		//마지막 페이지의 글번호 체크하기
		if(endNum>totalCount)
			endNum=totalCount;

		//각 페이지마다 출력할 시작번호
		//총갯수가 30일경우 1페이지는 30,2페이지는 25....
		no=totalCount-(currentPage-1)*perPage;      

		//2. 리스트 가져오기
		List<BoardVO> list=service.getList(startNum, endNum);

		model.addObject("list", list);
		model.addObject("currentPage", currentPage);
		model.addObject("startPage", startPage);
		model.addObject("endPage", endPage);
		model.addObject("no", no);
		model.addObject("totalPage", totalPage);
		model.addObject("totalCount",totalCount);
		model.setViewName("/2/board/boardlist");
		return model;
	}
	@RequestMapping("/board/form.do")
	public String form()
	{
		return "/board/boardform";
	}
	
	@RequestMapping("/board/view.do")
	public String view()
	{
		return "/board/boardview";
	}

	@RequestMapping(value="/board/write.do",method=RequestMethod.POST)
	public String read(
			@ModelAttribute BoardVO vo,
			HttpServletRequest request
			)
	{
		//이미지 업로드 경로 
		String path=request.getSession().getServletContext().getRealPath("/save");
		System.out.println(path);

		//path 경로에 이미지 저장
		SpringFileWriter fileWriter=new SpringFileWriter();

		String board_image="";
		System.out.println(vo.getImagename());
		for(MultipartFile f:vo.getImagename())
		{
			//빈 문자열이 아닐경우에만 저장
			if(f.getOriginalFilename().length()>0){
				board_image+=f.getOriginalFilename()+",";
				fileWriter.writeFile(f, path, f.getOriginalFilename());
			}
		}

		//vo 에 이미지 이름들 저장
		vo.setBoard_image(board_image);
		//db에 저장
		service.boardInsert(vo);

		return "redirect:list.do";
	}
	@RequestMapping("/board/updateform.do")
	public ModelAndView updateform(
			@RequestParam int board_seq_no,
			@RequestParam String pageNum
			)
	{
		BoardVO vo=service.getData(board_seq_no);
		ModelAndView model=new ModelAndView();
		model.addObject("vo",vo);
		model.setViewName("/board/updateform");
		return model;
	}
	
	@RequestMapping(value="/board/update.do",method=RequestMethod.POST)
	public String update(
			@RequestParam int board_seq_no,
			@RequestParam String pageNum,
			@RequestParam String board_content
			)
	{
		//db 수정
		service.boardUpdate(board_seq_no, board_content);
		//목록으로 이동
		return "redirect:list.do?pageNum="+pageNum;
	}
}

package com.team.zip.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.zip.model.vo.CommonCodeVO;
import com.team.zip.model.vo.ProductVO;
import com.team.zip.model.vo.StoreReviewVO;
import com.team.zip.service.StoreCategoryService;
import com.team.zip.service.StoreProductService;
import com.team.zip.service.StoreReviewService;
import com.team.zip.util.SpringFileWriter;

@Controller
public class StoreCategoryController {
	@Autowired
	private StoreReviewService storeReviewService;
	
	@Autowired
	private StoreCategoryService categoryService;
	
	@Autowired
	private StoreProductService storeProductService;
	
	@RequestMapping("/store/category.do")
	public ModelAndView categoryGo(@ModelAttribute CommonCodeVO commonCodeVo,
			@RequestParam(value="where", defaultValue="catego") String where){
		
		ModelAndView mav = new ModelAndView();
		
		if (commonCodeVo.getCodeVal() == null) {
			commonCodeVo.setCodeVal("가구");
		}
		
		String codeVal = commonCodeVo.getCodeVal();
		String sorting = commonCodeVo.getSorting();
		int codeSeqNo = commonCodeVo.getCodeSeqNo();
		if (sorting == null) {
			sorting = "pop";
			commonCodeVo.setSorting("pop");
		}
		
		if (commonCodeVo.getCurrentPage() == null) {
			commonCodeVo.setCurrentPage("1");
		}
		
		//PAGING
		int perPage = 15;//한페이지당 보여질 글의갯수
		int currentPage = Integer.parseInt(commonCodeVo.getCurrentPage());
		int startNo = (currentPage - 1) * perPage + 1; //각 페이지 시작번호
		int endNo = startNo + perPage - 1; //각 페이지 끝번호
		
		commonCodeVo.setStartNo(String.valueOf(startNo));
		commonCodeVo.setEndNo(String.valueOf(endNo));
		List<CommonCodeVO> ctgrList = categoryService.getCategoryList(codeVal);
		List<CommonCodeVO> secondList = categoryService.getCategorySecondList(codeVal);
		List<ProductVO> prodList = storeProductService.getProductList(commonCodeVo);
		
		String totalCount = storeProductService.getProductTotalCount(commonCodeVo);
		String codeSeqNm = "";
		
		String minPrice = commonCodeVo.getMinPrice();
		String maxPrice = commonCodeVo.getMaxPrice();
		
		for(CommonCodeVO vo : secondList) {
			if (vo.getCodeSeqNo() == codeSeqNo) {
				codeSeqNm = vo.getCodeNm();
				break;
			}
		}
		
		mav.addObject("sorting", sorting);
		mav.addObject("codeSeqNo", codeSeqNo);
		mav.addObject("codeSeqNm", codeSeqNm);
		mav.addObject("where", where);
		mav.addObject("totalCount", totalCount);
		mav.addObject("ctgrList", ctgrList);
		mav.addObject("secondList", secondList);
		mav.addObject("currentCategory", codeVal);
		mav.addObject("prodList", prodList);
		mav.addObject("currentPage", currentPage);
		mav.addObject("minPrice", minPrice);
		mav.addObject("maxPrice", maxPrice);
		
		mav.setViewName("/categoryLayout/store/category");
		return mav;
	}
	
	@RequestMapping(value = "/store/categoryAjax.do", produces = "application/text; charset=utf8")
	@ResponseBody
	public String categoryGoForAjax(@ModelAttribute CommonCodeVO commonCodeVo,
			@RequestParam(value="where", defaultValue="catego") String where) {
		
		ModelAndView mav = new ModelAndView();

		if (commonCodeVo.getCodeVal() == null) {
			commonCodeVo.setCodeVal("가구");
		}
		
		String sorting = commonCodeVo.getSorting();
		if (sorting == null) {
			sorting = "pop";
			commonCodeVo.setSorting("pop");
		}
		
		if (commonCodeVo.getCurrentPage() == null) {
			commonCodeVo.setCurrentPage("1");
		}
		
		//PAGING
		int perPage = 15;//한페이지당 보여질 글의갯수
		int currentPage = Integer.parseInt(commonCodeVo.getCurrentPage());
		int startNo = (currentPage - 1) * perPage + 1; //각 페이지 시작번호
		int endNo = startNo + perPage - 1; //각 페이지 끝번호
		
		commonCodeVo.setStartNo(String.valueOf(startNo));
		commonCodeVo.setEndNo(String.valueOf(endNo));
		List<ProductVO> prodList = storeProductService.getProductList(commonCodeVo);
		String totalCount = storeProductService.getProductTotalCount(commonCodeVo);
		
		mav.addObject("totalCount", totalCount);
		mav.addObject("prodList", prodList);
		JSONObject json = new JSONObject();
		json.put("totalCount", totalCount);
		
		JSONArray jsonArray = new JSONArray();
		for (int i=0; i < prodList.size(); i++) {
			JSONObject jo = new JSONObject();
			jo.put("prodNo", prodList.get(i).getProdNo());
			jo.put("prodTitle", prodList.get(i).getProdTitle());
			jo.put("prodPrice", prodList.get(i).getProdPrice());
			jo.put("prodImage", prodList.get(i).getProdImage());
			jo.put("discountRate", prodList.get(i).getDiscountRate());
			jo.put("prodSeller", prodList.get(i).getProdSeller());
			jo.put("prodHits", prodList.get(i).getProdHits());
			jo.put("reviewCnt", prodList.get(i).getReviewCnt());
			jo.put("starGrade", prodList.get(i).getStarGrade());
			jsonArray.add(jo);
		}
		json.put("prodList", jsonArray.toJSONString());
		
		return json.toString();
	}
	
	
	@RequestMapping("/store/selling.do")
	public ModelAndView selling(@RequestParam String prodNo, HttpSession session) {
		
		//로그인 유무 CHECK
		String login = (String)session.getAttribute("loginok");
		String memberNo = String.valueOf(session.getAttribute("member_no"));
		
		ModelAndView mav = new ModelAndView();
		
		ProductVO productVO = new ProductVO();
		productVO = storeProductService.getProductDetail(prodNo);
		
		//조회수 증가 
		storeProductService.updateHits(prodNo);
		
		mav.addObject("login", login);
		mav.addObject("memberNo", memberNo);
		mav.addObject("product", productVO);
		mav.setViewName("/store/selling");
		return mav;
	}
	
	@RequestMapping(value = "/store/sellingAjax.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getReviewListForAjax(@PathVariable int prodNo,
			@ModelAttribute StoreReviewVO storeReviewVO,
			@RequestParam(value = "pageNo", defaultValue = "1") int currentPage)
	{
		
		//페이징처리에 필요한 변수들 선언
		
		int no;//출력 시작번호
		int perPage = 3; //한페이지당 출력 리뷰 개수
		int perBlock = 10; //한블럭당 보여질 페이지의 개수
		int startPage = (currentPage - 1) / perBlock * perBlock + 1; //블럭의 시작페이지
		int endPage = startPage + perBlock - 1; //블럭의 끝페이지
		int startNo = (currentPage - 1) * perPage + 1; //각페이지의시작번호
		int endNo = startNo + perPage - 1; //각페이지의끝번호

		//총 리뷰 글의 개수
		int reviewTotalCnt = storeReviewService.getReviewTotalCount(prodNo);
		//총 페이지 수
		int totalPage = reviewTotalCnt / perPage + (reviewTotalCnt % perPage > 0?1:0); 
		
		// 존재하지 않는 페이지일 경우 마지막 페이지로 가기
		if(currentPage > totalPage) {
			currentPage = totalPage;
		}
		
		//마지막 블럭은 끝페이지가 총 페이지수와 같아야 한다.
		if(endPage > totalPage) {
			endPage = totalPage;
		}

		//마지막 페이지의 글번호 체크하기
		if(endNo > reviewTotalCnt)
			endNo = reviewTotalCnt;

		// 각 블럭의 시작페이지와 끝페이지를 구한다.
		// 예) 현재페이지 3일 경우 시작페이지:1 끝페이지:5
		// 예) 현재페이지 11일 경우 시작페이지:11 끝페이지:15
		no = reviewTotalCnt - (currentPage - 1) * perPage;
		
		
		List<StoreReviewVO> reviewList = new ArrayList<StoreReviewVO>();
		reviewList = storeReviewService.getReviewList(prodNo, startNo, endNo);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonList = "";
		
		try { 
			jsonList = mapper.writeValueAsString(reviewList); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		}
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("reviewTotalCnt", reviewTotalCnt);
		jsonObj.put("currentPage", currentPage);
		jsonObj.put("startPage", startPage);
		jsonObj.put("endPage", endPage);
		jsonObj.put("totalPage", totalPage);
		jsonObj.put("jsonList", jsonList);

		return jsonObj;
	}
	
	
	@RequestMapping(value = "/store/insertReview.do", method = RequestMethod.POST)
	public String insertReview(@ModelAttribute StoreReviewVO storeReviewVO,
			@RequestParam MultipartFile image,
			HttpServletRequest request) throws Exception{
		
		//이미지 업로드 경로 1. 집  2. 학원
		//String projectPath = "C:/Users/PARKSSO/git/AcornTeamProject";
		String projectPath = "C:/Users/acorn/git/AcornTeamProject";
		
		String realPath = projectPath+"/src/main/webapp/WEB-INF/uploadImage/review";
		
		String fileName = image.getOriginalFilename();
		SpringFileWriter fileWriter = new SpringFileWriter();
		
		fileWriter.writeFile(image, realPath, fileName);
		
		storeReviewVO.setRewImg(fileName);
		storeReviewService.insertReview(storeReviewVO);
		
		return "redirect:/store/selling.do?prodNo="+storeReviewVO.getProdNo();
	}
}

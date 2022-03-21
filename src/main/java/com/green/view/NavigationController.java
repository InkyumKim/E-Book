package com.green.view;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.biz.dto.CartVO;
import com.green.biz.dto.MemberVO;
import com.green.biz.dto.NoticeVO;
import com.green.biz.dto.ProductVO;
import com.green.biz.dto.QnaVO;
import com.green.biz.dto.ReviewVO;
import com.green.biz.notice.NoticeService;
import com.green.biz.order.CartService;
import com.green.biz.product.ProductService;
import com.green.biz.product.ReviewService;
import com.green.biz.qna.QnaService;


@Controller
public class NavigationController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private CartService cartService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	QnaService qnaService;
	
	@GetMapping(value = "/best")
	   public String bestView(Model model) {
		
		// ����Ʈ ��ǰ ��ȸ ���� ȣ��
		List<ProductVO> bestProdList = productService.getBestProductList();
		
		model.addAttribute("BestProductList", bestProdList);
	     
	    return "navigation/best";
	   }
	
	@GetMapping(value = "/new")
	   public String newView(Model model) {
		
		// �Ż�ǰ ��ȸ ���� ȣ��
		List<ProductVO> newProdList =  productService.getNewProductList();
		
		model.addAttribute("NewProductList", newProdList);
	      
	      return "navigation/new";
	   }
	
	@GetMapping(value = "/free")
	   public String freeView(Model model) {
		
		// ���� ���� ��ȸ ���� ȣ��
		List<ProductVO> freeProdList = productService.getFreeProductList();
		
		model.addAttribute("FreeProductList", freeProdList);
	      
	      return "navigation/free";
	   }
	
	@GetMapping(value = "/cs_center")
	public String cs_centerView(Model model) {
		
		// notice ����� ���̺��� ��ȸ
		List<NoticeVO> noticeList = noticeService.listAllNotice();
		
		// ��ȸ ����� model ��ü�� ����
		model.addAttribute("noticeList", noticeList);
		
		// notice ȭ�� ȣ��
	      return "navigation/cs_center";
	   }

	@GetMapping(value="/category")
	public String productKindView(ProductVO vo, Model model) {
		
		List<ProductVO> listProduct = productService.getProductListByKind(vo);
		
		model.addAttribute("productKindList" , listProduct);
		
		return "product/productKind";
		}
	
	/*
	 * ȸ��ID�� �������� ��� Qna ��ȸ
	 */
	@GetMapping(value="qna_list")
	public String qnaList(HttpSession session, Model model) {
		
		// ȸ�� �α��� Ȯ��
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if (loginUser == null) {
			return "member/login";
		} else {
			List<QnaVO> qnaList = qnaService.listQna(loginUser.getId());
			
			model.addAttribute("ListQnA", qnaList);
			
			return "navigation/qnaList";
		}
	}
	
	@GetMapping(value="/qna_write_form")
	public String qnaWriteView(HttpSession session) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			return "member/login";
		} else {
			return "navigation/qnaWrite";
		}
	}
	
	@PostMapping(value="/qna_write")
	public String qnaWriteAction(QnaVO vo, HttpSession session) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			return "member/login";
		} else {
			vo.setId(loginUser.getId());
			qnaService.insertQna(vo);
			
			return "redirect:qna_list";
		}
	}
	
	@GetMapping(value="/qna_view")
	public String qnaView(QnaVO vo, HttpSession session, Model model) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			return "member/login";
		} else {
			vo.setId(loginUser.getId());
			
			QnaVO qna = qnaService.getQna(vo.getQseq());
			model.addAttribute("qnaVO", qna);
			
			return "navigation/qnaView";
		}
		
	}
	
}

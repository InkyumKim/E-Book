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

import com.green.biz.dto.MemberVO;
import com.green.biz.dto.ProductVO;
import com.green.biz.dto.ReviewVO;
import com.green.biz.product.ProductService;
import com.green.biz.product.ReviewService;


@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private ReviewService reviewService;
	
	//@RequestMapping(value="/product_detail", method=RequestMethod.GET)
	@GetMapping(value="/product-detail")
	public String productDetailAction(ProductVO vo, Model model, ReviewVO rv) {

		// ��ǰ �� ��ȸ
		ProductVO product = productService.getProduct(vo);
		
		model.addAttribute("productVO", product);
		
		List<ReviewVO> reviewList = reviewService.listReview(rv);
		 
		model.addAttribute("reviewList", reviewList);
		
		
		return "product/product-detail";
	}
	
	/* �����ۼ� */
	@RequestMapping(value="/post_review_save", method={RequestMethod.GET, RequestMethod.POST})
	public String insertReview(@RequestParam("bseq") int bseq ,ProductVO vo, ReviewVO rv, Model model,
								HttpSession session, HttpServletRequest request) {
		
		// (1) ���ǿ� ����� ����� ������ �о� �´�.
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		// (2) �α����� �ȵǾ� ������ �α���, 
		//		�α��� �Ǿ� ������, �����Ͽ� ���� ����
		if (loginUser == null) {
			model.addAttribute("msg", "�α����� �ʿ��� �����Դϴ�.");
			
			return "member/login";
			
		}else {
			rv.setId(loginUser.getId());
		}
			reviewService.insertReview(rv);

			// (3) ���� ��� ��ȸ�Ͽ� ȭ�� ǥ��
			return "redirect:"+request.getHeader("Referer");
 	}
	
	
	// ���� ����
	@PostMapping(value="/review_delete")
	public String reviewDelete(@RequestParam(value="rseq") int[] rseq, HttpServletRequest request, 
								ReviewVO rv, Model model, HttpSession session) {
		
		// (1) ���ǿ� ����� ����� ������ �о� �´�.
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		// (2) �α����� �ȵǾ� ������ �α���, 
		//		�α��� �Ǿ� ������, �����Ͽ��� ���� ����
		if (loginUser == null) {
			model.addAttribute("msg", "�α����� �ʿ��� �����Դϴ�.");
			
			return "member/login";
			
		}else {
			rv.setId(loginUser.getId());
			
		}
		for(int i=0; i<rseq.length; i++) {
			System.out.println(("������ review rseq = ") + rseq[i]);
			reviewService.deleteReview(rseq[i]);
		}
		
		return "redirect:"+request.getHeader("Referer");
	}
	
}

	
	


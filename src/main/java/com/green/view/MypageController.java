package com.green.view;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.biz.dto.CartVO;
import com.green.biz.dto.MemberVO;
import com.green.biz.dto.OrderVO;
import com.green.biz.order.CartService;
import com.green.biz.order.OrderService;

@Controller
public class MypageController {

	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	/*
	 *  ��ٱ��� ��� ��û ó��
	 */
	
	@PostMapping(value="/cart_insert")
	public String insertCart(CartVO vo, Model model, HttpSession session) {
		
		//(1) ���ǿ� ����� ����� ������ �о� �´�.
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		//(2) �α����� �ȵǾ� ������ �α���, 
	    //	    �α����� �Ǿ� ������, ��ٱ��Ͽ� �׸� ����
		if (loginUser == null) {
			return "member/login";
		} else {
			vo.setId(loginUser.getId());
			
			cartService.insertCart(vo);
			
		//(3) ��ٱ��� ��� ��ȸ�Ͽ� ȭ�� ǥ��
			return "redirect:cart_list";
		}
	}
	
	@GetMapping(value="/cart")
	public String listCart(HttpSession session, Model model) {
		
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
		} else {
			List<CartVO> cartList = cartService.listCart(loginUser.getId());
			
			// �Ѿ� ���
			int totalAmount = 0;
			for (CartVO vo : cartList) {
				totalAmount += vo.getPrice();
			}
			// ��ٱ��� ����� ���� ��ü�� ����
			model.addAttribute("cartList", cartList);
			model.addAttribute("totalPrice", totalAmount);
			
			return "mypage/cart";
		}
	}
	
	/*
	 *  ��ٱ��� �׸� ���� ��û ó��
	 */
	@PostMapping(value="/cart_delete")
	public String cartDelete(@RequestParam(value="cseq") int[] cseq) {
		
		for(int i=0; i<cseq.length; i++) {
			System.out.println(("������ cart seq = " + cseq[i]));
			cartService.deleteCart(cseq[i]);
		}
		
		return "redirect:cart";
	}
	
	/*
	 *  ��ٱ��� ������ �ֹ� ó��
	 */
	@PostMapping(value="/order_insert")
	public String orderInsert(OrderVO vo, HttpSession session, Model model) {
		
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
		} else {
			vo.setId(loginUser.getId());
			
			int oseq = orderService.insertOrder(vo);
			
			// �ֹ���ȣ ����
			model.addAttribute("oseq", oseq);
			
			return "redirect:orderlist";
		}
	}
	

	/*
	 * �������� �ֹ����� ��ȸ
	 * �Է� �Ķ����: oseq
	 *			 result = '1'
	 */			  
	@GetMapping(value="/orderlist")
	public String orderList( 
								HttpSession session, OrderVO order, Model model) {
			// (1) �α��� Ȯ��
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "member/login";
		} else {
			// (2) �ֹ���ȣ�� �������� �ֹ� ���� ��ȸ
			order.setId(loginUser.getId());
			order.setResult("y");
			List<OrderVO> orderList = orderService.listOrderById(order);
			
			// (3) �ֹ� �Ѿ� ���
			int totalAmount = 0;
			for(OrderVO vo : orderList) {
				totalAmount += (vo.getPrice());
			}
			
			// (4) ���� ��ü�� ��� ����
			model.addAttribute("orderList", orderList);
			model.addAttribute("totalPrice", totalAmount);
		
			// (5) ȭ�� ȣ��
			return "mypage/orderlist";
		}
	}
	
	@GetMapping(value="/order_detail")
	public String orderDetail(OrderVO vo, HttpSession session, Model model) {
		// ���ǰ�ü���� �α��� Ȯ��
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
		} else {
			// �ֹ���ȣ�� �������� �ֹ� ��ȸ
			vo.setId(loginUser.getId());
			//vo.setResult("1");
			vo.setResult("");
			
			List<OrderVO> orderList = orderService.listOrderById(vo);
			
			// ȭ�鿡 ����� ���� ����
			// (1) �ֹ��� ���� ����
			OrderVO orderDetail = new OrderVO();
			
			orderDetail.setOseq(orderList.get(0).getOseq());
			orderDetail.setIndate(orderList.get(0).getIndate());
			orderDetail.setMname(orderList.get(0).getMname());
			orderDetail.setResult(orderList.get(0).getResult());
			
			// �ֹ� �հ� �ݾ� ���
			int amount = 0;
			for(int i=0; i<orderList.size(); i++) {
				amount += orderList.get(i).getPrice();
			}
			
			model.addAttribute("title", "My Page(�ֹ� �� ����)");
			model.addAttribute("orderDetail", orderDetail);
			model.addAttribute("totalPrice", amount);
			model.addAttribute("orderList", orderList);
			
			return "mypage/orderDetail";
		}
	}
	
	@PostMapping(value="/wishlist_insert")
	public String insertWishlist(CartVO vo, Model model, HttpSession session) {
		
		//(1) ���ǿ� ����� ����� ������ �о� �´�.
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		//(2) �α����� �ȵǾ� ������ �α���, 
	    //	    �α����� �Ǿ� ������, ��ٱ��Ͽ� �׸� ����
		if (loginUser == null) {
			return "member/login";
		} else {
			vo.setId(loginUser.getId());
			
			cartService.insertCart(vo);
			
		//(3) ��ٱ��� ��� ��ȸ�Ͽ� ȭ�� ǥ��
			return "redirect:wishlist";
		}
	}
	
	@GetMapping(value="/wishlist")
	public String listWishlist(HttpSession session, Model model) {
		
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
		} else {
			List<CartVO> cartList = cartService.listCart(loginUser.getId());
			
			// �Ѿ� ���
			int totalAmount = 0;
			for (CartVO vo : cartList) {
				totalAmount += vo.getPrice();
			}
			// ��ٱ��� ����� ���� ��ü�� ����
			model.addAttribute("cartList", cartList);
			model.addAttribute("totalPrice", totalAmount);
			
			return "mypage/wishlist";
		}
	}
}
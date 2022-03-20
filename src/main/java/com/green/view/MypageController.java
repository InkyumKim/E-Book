package com.green.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.green.biz.dto.ProductVO;
import com.green.biz.dto.WishlistVO;
import com.green.biz.order.CartService;
import com.green.biz.order.OrderService;
import com.green.biz.product.ProductService;
import com.green.biz.wishlist.WishlistService;

@Controller
public class MypageController {

	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private WishlistService wishlistService;
	@Autowired
	private ProductService productService;
	/*
	 * ��ٱ��� ��� ��û ó��
	 */

	@PostMapping(value = "/cart_insert")
	public String insertCart(CartVO vo, Model model, HttpSession session) {

		// (1) ���ǿ� ����� ����� ������ �о� �´�.
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

		// (2) �α����� �ȵǾ� ������ �α���,
		// �α����� �Ǿ� ������, ��ٱ��Ͽ� �׸� ����
		if (loginUser == null) {
			return "member/login";
		} else {
			vo.setId(loginUser.getId());

			cartService.insertCart(vo);

			// (3) ��ٱ��� ��� ��ȸ�Ͽ� ȭ�� ǥ��
			return "redirect:cart";
		}
	}

	@GetMapping(value = "/cart")
	public String listCart(HttpSession session, Model model) {

		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

		if (loginUser == null) {
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
	 * ��ٱ��� �׸� ���� ��û ó��
	 */
	@PostMapping(value = "/cart_delete")
	public String cartDelete(@RequestParam(value = "cseq") int[] cseq) {

		for (int i = 0; i < cseq.length; i++) {
			System.out.println(("������ cart seq = " + cseq[i]));
			cartService.deleteCart(cseq[i]);
		}

		return "redirect:cart";
	}

	/*
	 * ��ٱ��� ������ �ֹ� ó��
	 */
	@PostMapping(value = "/order_insert")
	public String orderInsert(OrderVO vo, HttpSession session, Model model) {

		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "member/login";
		} else {
			vo.setId(loginUser.getId());

			int oseq = orderService.insertOrder(vo);
			
			// �ֹ���ȣ ����
			model.addAttribute("oseq", oseq);

			return "redirect:mypage";
		}
	}

	/*
	 * �������� �ֹ����� ��ȸ �Է� �Ķ����: oseq result = '1'
	 */
	@GetMapping(value = "/orderlist")
	public String orderList(HttpSession session, OrderVO order, Model model) {
		// (1) �α��� Ȯ��
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "member/login";
		} else {
			// (2) �ֹ���ȣ�� �������� �ֹ� ���� ��ȸ
			order.setId(loginUser.getId());
			order.setResult("");
			List<OrderVO> orderList = orderService.listOrderById(order);

			// (3) �ֹ� �Ѿ� ���
			int totalAmount = 0;
			for (OrderVO vo : orderList) {
				totalAmount += (vo.getPrice());
			}

			// (4) ���� ��ü�� ��� ����
			model.addAttribute("orderList", orderList);
			model.addAttribute("totalPrice", totalAmount);

			// (5) ȭ�� ȣ��
			return "mypage/mybook";
		}
	}

	@GetMapping(value = "/mypage")
	public String myPageView(HttpSession session, Model model) {

		// ���ǿ� ����� �α��� ���� �о��
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "member/login";
		} else {
			// ����ڰ� �ֹ��� ��� �ֹ���ȣ ��ȸ
			OrderVO vo = new OrderVO();
			vo.setId(loginUser.getId());
			vo.setResult("n");
			List<Integer> oseqList = orderService.selectSeqOrdering(vo);

			// �� �ֹ���ȣ�� ��ȸ�Ͽ� �ֹ�������� ����
			// (1) �ֹ� ��� ���� ���� ����
			List<OrderVO> orderList = new ArrayList<OrderVO>();

			// (2) ��� �ֹ���ȣ�� ���� ������� ����
			for (int oseq : oseqList) {
				OrderVO orderVO = new OrderVO();

				orderVO.setId(loginUser.getId());
				orderVO.setOseq(oseq);
				orderVO.setResult("n");

				// �� �ֹ��� ���� �ֹ����� ��ȸ
				List<OrderVO> listByOseq = orderService.listOrderById(orderVO);

				// ������ �ֹ��� �ֹ������� ������� ����
				OrderVO order = new OrderVO();
				order.setOseq(listByOseq.get(0).getOseq());
				order.setIndate(listByOseq.get(0).getIndate());

				if (listByOseq.size() > 1) {
					order.setTitle(listByOseq.get(0).getTitle() + " ��" + (listByOseq.size() - 1) + "��");
				} else {
					order.setTitle(listByOseq.get(0).getTitle());
				}

				// �ֹ���ȣ�� �Ѿ� ���
				int amount = 0;
				for (int i = 0; i < listByOseq.size(); i++) {
					amount += listByOseq.get(i).getPrice();
				}
				order.setPrice(amount);

				// ��������� List������ �߰�
				orderList.add(order);
			}
			model.addAttribute("title", "�������� �ֹ� ����");
			model.addAttribute("orderList", orderList);
		}

		return "mypage/mypage";
	}

	@GetMapping(value = "/order_detail")
	public String orderDetail(OrderVO vo, HttpSession session, Model model) {
		// ���ǰ�ü���� �α��� Ȯ��
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "member/login";
		} else {
			// �ֹ���ȣ�� �������� �ֹ� ��ȸ
			vo.setId(loginUser.getId());
			// vo.setResult("1");
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
			for (int i = 0; i < orderList.size(); i++) {
				amount += orderList.get(i).getPrice();
			}

			model.addAttribute("title", "My Page(�ֹ� �� ����)");
			model.addAttribute("orderDetail", orderDetail);
			model.addAttribute("totalPrice", amount);
			model.addAttribute("orderList", orderList);

			return "mypage/orderDetail";
		}
	}

	/*
	 * �� �ֹ����� ó��
	 */
	@GetMapping(value = "/order_all")
	public String orderAllView(HttpSession session, Model model, OrderVO vo) {
		// (1) ����� �α��� Ȯ��
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "member/login";
		} else {
			// (2) ������� ��ü �ֹ���ȣ Ȯ��
			vo.setId(loginUser.getId());
			vo.setResult(""); // ó����� ���� ����
			List<Integer> oseqList = orderService.selectSeqOrdering(vo);

			// (3) �� �ֹ���ȣ �� �ֹ����� ��ȸ
			List<OrderVO> orderList = new ArrayList<>(); // �ֹ�������� ���� ����
			for (int oseq : oseqList) {
				OrderVO orderVO = new OrderVO();
				orderVO.setId(loginUser.getId());
				orderVO.setOseq(oseq);
				orderVO.setResult("");

				// �ֹ���ȣ�� �ֹ����� ��ȸ
				List<OrderVO> orders = orderService.listOrderById(orderVO);

				// (4) �ֹ���� ���� ����
				OrderVO summary = new OrderVO();
				summary = orders.get(0); // �켱 ù��° ��ǰ���� ������ ����
				if (orders.size() > 1) {
					summary.setTitle(orders.get(0).getTitle() + " ��" + (orders.size() - 1 + " ��"));
				} else {
					summary.setTitle(orders.get(0).getTitle());
				}

				int amount = 0;
				for (OrderVO order : orders) {
					amount += (order.getPrice());
				}
				summary.setPrice(amount);

				orderList.add(summary);
			}

			// ����� ȭ�鿡 ����
			model.addAttribute("title", "�� �ֹ�����");
			model.addAttribute("orderlist", orderList);

			return "mypage/orderlist";
		}
	}

	@PostMapping(value = "/wishlist_insert")
	public String insertWishlist(WishlistVO vo, ProductVO vo2, Model model, HttpSession session) {
				System.out.println(vo.getBseq());
		// (1) ���ǿ� ����� ����� ������ �о� �´�.
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		// (2) �α����� �ȵǾ� ������ �α���,
		// �α����� �Ǿ� ������, ���ø���Ʈ ���� 
		if (loginUser == null) {
			return "member/login";
		} else {
			vo2.setBseq(vo.getBseq());
			ProductVO ebook = productService.getProduct(vo2);
			System.out.println(ebook);
			vo.setId(loginUser.getId());
			
			
			wishlistService.insertWishlist(vo);

			// (3) ��ٱ��� ��� ��ȸ�Ͽ� ȭ�� ǥ��
			return "redirect:wishlist";
		}
	}

	@GetMapping(value = "/wishlist")
	public String listWishlist(HttpSession session, Model model) {
		
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "member/login";
		} else {
			
			
			List<WishlistVO> wishList = wishlistService.listWishlist(loginUser.getId());
			System.out.println(wishList);
			WishlistVO wishlistDetail = new WishlistVO();
			if (wishList.size() > 0) {
				
			
			wishlistDetail.setTitle(wishList.get(0).getTitle());
			wishlistDetail.setPrice(wishList.get(0).getPrice());
			
			}
			// �Ѿ� ���
			int totalAmount = 0;
			for (WishlistVO vo : wishList) {
				totalAmount += vo.getPrice();
			}
			
			// ��ٱ��� ����� ���� ��ü�� ����
			model.addAttribute("wishlist", wishList);
			model.addAttribute("totalPrice", totalAmount);

			return "mypage/wishlist";
		}
	}
	
	@PostMapping(value = "/wishlist_delete")
	public String wishlistDelete(@RequestParam(value = "wseq") int[] wseq) {

		for (int i = 0; i < wseq.length; i++) {
			System.out.println(("������ wishlist seq = " + wseq[i]));
			wishlistService.deleteWishlist(wseq[i]);
		}

		return "redirect:wishlist";
	}
}
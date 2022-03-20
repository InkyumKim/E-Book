package com.green.biz.wishlist;

import java.util.List;

import com.green.biz.dto.WishlistVO;

public interface WishlistService {

	// ���ø���Ʈ ���
	void insertWishlist(WishlistVO vo);

	// ���ø���Ʈ ���
	List<WishlistVO> listWishlist(String userid);

	// ���ø���Ʈ �׸� ����
	void deleteWishlist(int wseq);
	
	// ���ø���Ʈ ������Ʈ
	void updateWishlist(int wseq);

}
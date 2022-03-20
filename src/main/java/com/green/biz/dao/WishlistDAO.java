package com.green.biz.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.biz.dto.WishlistVO;

@Repository
public class WishlistDAO{

	@Autowired
	private SqlSessionTemplate mybatis;
	
	// ��ٱ��� ���
	public void insertWishlist(WishlistVO vo) {
		
		mybatis.insert("mappings.wishlist-mapping.insertWishlist", vo);
	}
	
	// ��ٱ��� ���
	public List<WishlistVO> listWishlist(String userid) {
		
		return mybatis.selectList("mappings.wishlist-mapping.listWishlist", userid);
	}
	
	// ��ٱ��� �׸� ����
	public void deleteWishlist(int wseq) {
		
		mybatis.delete("mappings.wishlist-mapping.deleteWishlist", wseq);
	}
	
	// ��ٱ��� �׸��� 'ó��'�� ������Ʈ
	public void updateWishlist(int wseq) {
		
		mybatis.update("mappings.wishist-mapping.updateWishlist", wseq);
	}
}

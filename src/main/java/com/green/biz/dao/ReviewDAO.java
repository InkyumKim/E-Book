package com.green.biz.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.biz.dto.ProductVO;
import com.green.biz.dto.ReviewVO;

import utils.Criteria;

@Repository
public class ReviewDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	// ���� �ۼ�
	public void insertReview(ReviewVO rv) {
		
		mybatis.insert("mappings.review-mapping.insertReview", rv);
	}
	
	// ���� ���
	public List<ReviewVO> listReview(ReviewVO rv) {
		
		return mybatis.selectList("mappings.review-mapping.listReview", rv);
	}
	
	// �����Ͽ��� �׸� ����
	public void deleteReview(int rseq) {
		
		mybatis.delete("mappings.review-mapping.deleteReview", rseq);
	}

	
	// ��ȸ�� ��ǰ�� ���� �� ����
	public int countReviewList(String title) {
		
		return mybatis.selectOne("mappings.review-mapping.countReviewList", title);
	}
	
	// �������� ������ ��ȸ
	public List<ReviewVO> getreviewPaging(Criteria criteria, String title) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("criteria", criteria);
		map.put("title", title);
		
		return mybatis.selectList("mappings.product-mapping.reviewPaging", map);
	}
	
	/*
	 * // ���� ��� ������Ʈ public void updateReviewAvg(ProductVO vo) {
	 * 
	 * mybatis.update("mappings.product-mapping.updateReviewAvg", vo); }
	 */
	
}

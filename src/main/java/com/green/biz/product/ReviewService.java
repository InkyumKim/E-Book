package com.green.biz.product;

import java.util.List;

import com.green.biz.dto.ProductVO;
import com.green.biz.dto.ReviewVO;

import utils.Criteria;

public interface ReviewService {

	// ���� �ۼ�
	void insertReview(ReviewVO rv);

	// ���� ���
	List<ReviewVO> listReview(ReviewVO rv);

	// �����Ͽ��� �׸� ����
	void deleteReview(int rseq);
	// �����Ͽ��� �׸� ������Ʈ
	public int countReviewList(String title);
	
	public List<ReviewVO> getreviewPaging(Criteria criteria, String title);

	/*
	 * public void updateReviewAvg(ProductVO vo);
	 */}



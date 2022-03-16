package com.green.biz.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.biz.dto.ProductVO;
import com.green.biz.dto.SalesQuantity;

@Repository
public class ProductDAO {
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public ProductVO getProduct(ProductVO vo) {
		
		return mybatis.selectOne("mappings.product-mapping.getProduct", vo);
	}
	
	// ��ǰ ��� ��ȸ
	public List<ProductVO> listProduct(String name) {
			
		return mybatis.selectList("mappings.product-mapping.listbook", name);
	}
	
	// ��ǰ �߰�
	public void insertProduct(ProductVO vo) {
			
		mybatis.insert("mappings.product-mapping.insertProduct", vo);
	}
	
	// ��ǰ���� ����
	public void updateProduct(ProductVO vo) {
			
		mybatis.update("mappings.product-mapping.updateProduct", vo);
	}
	
	public void deleteProduct(ProductVO vo) {
		
		mybatis.delete("mappings.product-mapping.deleteProduct", vo);
	}
	
	// å�� �Ǹ� ���� ��ȸ
	public List<SalesQuantity> getProductSales() {
			
		return mybatis.selectList("mappings.product-mapping.getProductSales");
	}
}


	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:ProductPackPo.java 
	 * 包名:com.eeduspace.cibn.persist.po 
	 * 创建日期:2016年12月19日上午11:07:46 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.persist.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.eeduspace.cibn.interceptor.intercepterTest;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：ProductPackPo    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年12月19日 上午11:07:46    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年12月19日 上午11:07:46    
 * 修改备注：       
 * @version </pre>    
 */
@Entity
@Table(name = "cibn_price_base")
public class ProductPackPo {

	/*CREATE TABLE `cibn_price_base` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `price` varchar(20) DEFAULT NULL COMMENT '产品价格',
  `effective_time` varchar(50) DEFAULT NULL COMMENT '有效时长',
  `price_way` varchar(20) DEFAULT NULL COMMENT '产品途径  1 作业辅导 2 考试帮手',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=485 DEFAULT CHARSET=utf8 COMMENT='作业辅导和考试帮手的价格   基础数据表'*/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false)
	private Long id;
	
	@Column(name="price")
	private String price;
	
	@Column(name="effective_time")
	private String effectiveTime;
	
	@Column(name="price_way")
	private String priceWay;

	public Long getId() {
	
		return id;
	}

	public void setId(Long id) {
	
		this.id = id;
	}

	public String getPrice() {
	
		return price;
	}

	public String getEffectiveTime() {
	
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
	
		this.effectiveTime = effectiveTime;
	}

	public void setPrice(String price) {
	
		this.price = price;
	}

	
	public String getPriceWay() {
	
		return priceWay;
	}

	public void setPriceWay(String priceWay) {
	
		this.priceWay = priceWay;
	}

	@Override
	public String toString() {
		return "ProductPackPo [id=" + id + ", price=" + price
				+ ", effectiveTime=" + effectiveTime + ", priceWay="
				+ priceWay + "]";
	}
	
}

	
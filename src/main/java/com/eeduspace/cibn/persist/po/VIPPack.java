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

import com.eeduspace.cibn.persist.enumeration.VipEnum;
import com.eeduspace.uuims.comm.util.base.UIDGenerator;

@Entity
@Table(name = "cibn_vip_pack")
public class VIPPack {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false)
	private Long id;
	@Column(unique = true)
	private String uuid = UIDGenerator.getUUID().toString().replace("-", "");
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false, name = "create_time")
	private Date createDate = new Date();
	/**
	 * vip套餐类型
	 */
	@Column(name="vip_type")
	private String vipType;
	/**
	 * vip套餐描述
	 */
	@Column(name="vip_desc")
	private String vipDesc;
	/**
	 * vip套餐价格
	 */
	@Column(name="vip_price")
	private Double vipPrice;
	
	/**
	 * vip折扣
	 */
	@Column(name="vip_sale")
	private Double vipSale;
	
	/**
	 * 是否发布  true 发布  false 未发布
	 */
	@Column(name="is_release")
	private boolean isRelease=false;
	/**
	 * 	vip套餐优惠后价格（暂定）
	 */
	@Column(name="discount_price")
	private Double discountPrice;
	/**
	 * 	vip套餐价格是否优惠（暂定）
	 */
	@Column(name="discount")
	private boolean discount;
	/**
	 * 	vip优惠的起止时间（暂定）
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "discount_start_time")
	private Date discountStartDate;
	@Column(name="vip_day")
	private Integer vipDays;
	/**
	 * 	vip优惠的截止时间（暂定）
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "discount_end_time")
	private Date discountEndDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getVipDesc() {
		return vipDesc;
	}
	public void setVipDesc(String vipDesc) {
		this.vipDesc = vipDesc;
	}
	public Double getVipPrice() {
		return vipPrice;
	}
	
	public String getVipType() {
		return vipType;
	}
	public void setVipType(String vipType) {
		this.vipType = vipType;
	}
	public void setVipPrice(Double vipPrice) {
		this.vipPrice = vipPrice;
	}
	
	public Integer getVipDays() {
		return vipDays;
	}
	public void setVipDays(Integer vipDays) {
		this.vipDays = vipDays;
	}
	public Double getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}
	public boolean isDiscount() {
		return discount;
	}
	public void setDiscount(boolean discount) {
		this.discount = discount;
	}
	public Date getDiscountStartDate() {
		return discountStartDate;
	}
	public void setDiscountStartDate(Date discountStartDate) {
		this.discountStartDate = discountStartDate;
	}
	public Date getDiscountEndDate() {
		return discountEndDate;
	}
	public void setDiscountEndDate(Date discountEndDate) {
		this.discountEndDate = discountEndDate;
	}
	public Double getVipSale() {
		return vipSale;
	}
	public void setVipSale(Double vipSale) {
		this.vipSale = vipSale;
	}
	public boolean isRelease() {
		return isRelease;
	}
	public void setRelease(boolean isRelease) {
		this.isRelease = isRelease;
	}
	
	
}

package com.eeduspace.cibn.persist.po;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.eeduspace.uuims.comm.util.base.UIDGenerator;

@Entity
@Table(name = "cibn_paper_type")
public class PaperTypePo {
	/**
	 * id
	 * */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false)
	private Long id;
	/**
	 * uuid
	 * */
	@Column(unique = true)
	private String uuid = UIDGenerator.getUUID().toString().replace("-", "");
	/**
	 * 
	 * */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false, name = "date_aft")
	private Date dateAft;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false, name = "date_bef")
	private Date dateBef;
	/**
	 * 
	 * */
	@Column(name = "name")
	private String name;
	/**
	 * 
	 * */
	@Column(name = "discount")
	private double discount;
	/**
	 * 
	 * */
	@Column(name = "price")
	private String price;
	/**
	 * 
	 * */
	@Column(name = "type")
	private Integer type;
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
	public Date getDateAft() {
	
		return dateAft;
	}
	public void setDateAft(Date dateAft) {
	
		this.dateAft = dateAft;
	}
	public Date getDateBef() {
	
		return dateBef;
	}
	public void setDateBef(Date dateBef) {
	
		this.dateBef = dateBef;
	}
	public String getName() {
	
		return name;
	}
	public void setName(String name) {
	
		this.name = name;
	}
	public double getDiscount() {
	
		return discount;
	}
	public void setDiscount(double discount) {
	
		this.discount = discount;
	}
	public Integer getType() {
	
		return type;
	}
	public void setType(Integer type) {
	
		this.type = type;
	}
	
	public String getPrice() {
	
		return price;
	}
	public void setPrice(String price) {
	
		this.price = price;
	}
	@Override
	public String toString() {
		return "PaperTypePo [id=" + id + ", uuid=" + uuid + ", dateAft="
				+ dateAft + ", dateBef=" + dateBef + ", name=" + name
				+ ", discount=" + discount + ", price=" + price + ", type="
				+ type + "]";
	}
	
	
	
}

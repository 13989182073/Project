package com.java456.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="t_client")
public class Client {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length=20)
	private String bianhao;


	@Column(length=20)
	private String test;
	
	@Column(length=20)
	private String name;
	
	@Column(length=20)
	private String phone;
	
	@Column(length=200)
	private String remark;
	
	@Temporal(TemporalType.TIMESTAMP) 
	private Date  createDateTime;
	
	
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBianhao() {
		return bianhao;
	}
	
	public void setBianhao(String bianhao) {
		this.bianhao = bianhao;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

	@JsonSerialize(using=CustomDateTimeSerializer.class)
	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}


	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}
}

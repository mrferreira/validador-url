package com.mferreira.validadorurl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "GLOBAL_WHITELIST", catalog = "axr_challenge")
public class GlobalWhitelist {

	@Id
	@Column(name ="ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;
	@JsonProperty("regex")
	@Column(name = "REGEX", nullable = false)
	private String regExp;

	public GlobalWhitelist() { }

	public GlobalWhitelist(String regExp) {
		this.regExp = regExp;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRegExp() {
		return regExp;
	}
	public void setRegExp(String regExp) {
		this.regExp = regExp;
	}
}

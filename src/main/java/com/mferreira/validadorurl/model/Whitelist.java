package com.mferreira.validadorurl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "WHITELIST", catalog = "axr_challenge",
		uniqueConstraints = @UniqueConstraint(columnNames = {"client", "regex"}))
public class Whitelist {

	@Id
	@Column(name ="ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;
	@JsonProperty("client")
	@Column(name = "CLIENT")
	private String client;
	@JsonProperty("regex")
	@Column(name = "REGEX", nullable = false)
	private String regExp;
	
	public Whitelist() { }
	
	public Whitelist(String client, String regExp) {
		this.client = client;
		this.regExp = regExp;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getRegExp() {
		return regExp;
	}
	public void setRegExp(String regExp) {
		this.regExp = regExp;
	}
}

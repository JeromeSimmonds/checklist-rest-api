package com.learnvest.checklistapi.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

/**
 * @author Jerome Simmonds
 *
 */
@Entity
@Table(name = "checklists")
public class Checklist implements Serializable {

	private static final long serialVersionUID = 2133478533235942315L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Integer id;

	@Column(name = "name")
	private String name;
	
	@ManyToOne(fetch = FetchType.EAGER) // I would use LAZY and implement a fetching feature if that was not for a coding challenge
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "created", insertable = false, updatable = false)
	@Generated(GenerationTime.INSERT)
	@Convert(converter = LocalDateConverter.class)
	private LocalDate created;
	@Column(name = "modified", insertable = false, updatable = false)
	@Generated(GenerationTime.ALWAYS)
	@Convert(converter = LocalDateConverter.class)
	private LocalDate modified;

	public Checklist() {
		super();
	}
	
	public Checklist(Integer id) {
		super();
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public LocalDate getCreated() {
		return created;
	}
	public LocalDate getModified() {
		return modified;
	}
}

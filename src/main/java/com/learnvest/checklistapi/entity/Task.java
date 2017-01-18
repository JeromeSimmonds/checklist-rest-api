package com.learnvest.checklistapi.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
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
@Table(name = "tasks")
public class Task implements Serializable {

	private static final long serialVersionUID = -693580581322760041L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Integer id;

	@Column(name = "description")
	private String description;
	@Column(name = "complete")
	private Boolean complete;
	@Column(name = "due_date")
	private LocalDate dueDate;
	
	@ManyToOne(fetch = FetchType.EAGER) // I would use LAZY and implement a fetching feature if that was not for a coding challenge
	@JoinColumn(name = "checklist_id")
	private Checklist checklist;

	@Column(name = "created", insertable = false, updatable = false)
	@Generated(GenerationTime.INSERT)
	private LocalDate created;
	@Column(name = "modified", insertable = false, updatable = false)
	@Generated(GenerationTime.ALWAYS)
	private LocalDate modified;
    
	public Task() {
		super();
	}
	
	public Task(Integer id) {
		super();
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getComplete() {
		return complete;
	}
	public void setComplete(Boolean complete) {
		this.complete = complete;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public Checklist getChecklist() {
		return checklist;
	}
	public void setChecklist(Checklist checklist) {
		this.checklist = checklist;
	}
	public LocalDate getCreated() {
		return created;
	}
	public LocalDate getModified() {
		return modified;
	}
}

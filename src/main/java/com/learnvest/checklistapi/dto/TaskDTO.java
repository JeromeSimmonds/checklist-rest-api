package com.learnvest.checklistapi.dto;

import static com.learnvest.checklistapi.ChecklistApiConstants.*;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Jerome Simmonds
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class TaskDTO implements Serializable {

	private static final long serialVersionUID = 2173480944277521691L;

	@JsonProperty(ID)
	private Integer id;
	@JsonProperty(CHECKLIST_ID)
	@NotNull(message = "Checklist ID missing")
	@Min(value = 1, message = "Checklist ID invalid")
	private Integer checklistId;
	@JsonProperty(DESCRIPTION)
	@NotNull(message = "Description missing")
	private String description;
	@JsonProperty(COMPLETE)
	private Boolean complete;
	@JsonProperty(DUE_DATE)
	private String dueDate; // ISO 8601
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getChecklistId() {
		return checklistId;
	}
	public void setChecklistId(Integer checklistId) {
		this.checklistId = checklistId;
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
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
}

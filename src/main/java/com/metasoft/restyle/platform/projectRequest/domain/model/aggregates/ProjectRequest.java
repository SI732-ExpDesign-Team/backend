package com.metasoft.restyle.platform.projectRequest.domain.model.aggregates;

import com.metasoft.restyle.platform.projectRequest.domain.model.commands.CreateProjectRequestCommand;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ProjectRequest extends AbstractAggregateRoot<ProjectRequest> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer businessId;

    @Column(nullable = false)
    private Integer contractorId;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private Date deadlineDate;

    @Column(nullable = false)
    private Integer budget;

    @Column(nullable = false)
    private Integer rooms;

    @Column(nullable = false)
    private String summary;

    protected ProjectRequest(){}

    public ProjectRequest(CreateProjectRequestCommand command){
        this.name = command.name();
        this.businessId = command.businessId();
        this.contractorId = command.contractorId();
        this.surname = command.surname();
        this.email = command.email();
        this.phone = command.phone();
        this.address = command.address();
        this.city = command.city();
        this.deadlineDate = command.deadlineDate();
        this.budget = command.budget();
        this.rooms = command.rooms();
        this.summary = command.summary();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public Integer getContractorId() {
		return contractorId;
	}

	public void setContractorId(Integer contractorId) {
		this.contractorId = contractorId;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(Date deadlineDate) {
		this.deadlineDate = deadlineDate;
	}

	public Integer getBudget() {
		return budget;
	}

	public void setBudget(Integer budget) {
		this.budget = budget;
	}

	public Integer getRooms() {
		return rooms;
	}

	public void setRooms(Integer rooms) {
		this.rooms = rooms;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
    
}

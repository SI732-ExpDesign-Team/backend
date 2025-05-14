package com.metasoft.restyle.platform.project.domain.model.aggregates;

import com.metasoft.restyle.platform.project.domain.model.commands.CreateProjectCommand;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Project extends AbstractAggregateRoot<Project> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 500)
    private String image;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private Integer businessId;

    @Column(nullable = false)
    private Integer contractorId;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date finishDate;

    protected Project(){}

    public Project(CreateProjectCommand command){
        this.name = command.name();
        this.image = command.image();
        this.description = command.description();
        this.businessId = command.businessId();
        this.contractorId = command.contractorId();
        this.startDate = command.startDate();
        this.finishDate = command.finishDate();
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
    
}

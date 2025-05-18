package com.metasoft.restyle.platform.business.domain.model.aggregates;

import com.metasoft.restyle.platform.business.domain.model.commands.CreateBusinessCommand;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Business  extends AbstractAggregateRoot<Business> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String image;

    @Column(nullable = false)
    private String expertise;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer remodelerId;

    protected Business(){}

    public Business(CreateBusinessCommand command){
        this.name = command.name();
        this.image = command.image();
        this.expertise = command.expertise();
        this.address = command.address();
        this.city = command.city();
        this.description = command.description();
        this.remodelerId = command.remodelerId();
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

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getRemodelerId() {
		return remodelerId;
	}

	public void setRemodelerId(Integer remodelerId) {
		this.remodelerId = remodelerId;
	}
    
}

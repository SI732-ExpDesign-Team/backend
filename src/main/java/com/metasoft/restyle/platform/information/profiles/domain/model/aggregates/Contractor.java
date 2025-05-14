package com.metasoft.restyle.platform.information.profiles.domain.model.aggregates;

import com.metasoft.restyle.platform.profiles.domain.model.aggregates.Profile;
import com.metasoft.restyle.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
public class Contractor extends AuditableAbstractAggregateRoot<Contractor> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @OneToOne
    @JoinColumn(name = "user_id")
    private Profile profile;

    @Getter
    private String phone;


    @Getter
    private String description;


    public Contractor(String description, String phone) {
        this.description = description;
        this.phone = phone;
    }

    public Contractor() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    

}

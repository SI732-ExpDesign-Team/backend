package com.metasoft.restyle.platform.profiles.domain.model.aggregates;

import com.metasoft.restyle.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.metasoft.restyle.platform.profiles.domain.model.valueobjects.PersonName;
import com.metasoft.restyle.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class Profile extends AuditableAbstractAggregateRoot<Profile> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Embedded
    private EmailAddress email;

    @Getter
    private String password;

    @Getter
    private String type;

    @Embedded
    private PersonName name;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    public Profile(String email, String password, String type, String firstName, String paternalSurname, String maternalSurname) {
        this.email = new EmailAddress(email);
        this.password = password;
        this.type = type;
        this.name = new PersonName(firstName, paternalSurname, maternalSurname);
    }

    public Profile() {

    }

    public void updateName(String firstName, String paternalSurname, String maternalSurname) {
        this.name = new PersonName(firstName, paternalSurname, maternalSurname);
    }

    public String getFullName() {
        return this.name.getFullName();
    }

    public String getEmailAddress() {
        return this.email.address();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EmailAddress getEmail() {
		return email;
	}

	public void setEmail(EmailAddress email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public PersonName getName() {
		return name;
	}

	public void setName(PersonName name) {
		this.name = name;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
    

}

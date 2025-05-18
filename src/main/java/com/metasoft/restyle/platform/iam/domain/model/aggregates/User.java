package com.metasoft.restyle.platform.iam.domain.model.aggregates;

import com.metasoft.restyle.platform.iam.domain.model.entities.Role;
import com.metasoft.restyle.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * User aggregate root
 * This class represents the aggregate root for the User entity.
 *
 * @see AuditableAbstractAggregateRoot
 */
@Getter
@Setter
@Entity
public class User extends AuditableAbstractAggregateRoot<User> {

    @NotBlank
    @Size(max = 50)
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(max = 120)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // Eager fetch to load the roles when loading the user
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Getter
    private String email;
    @Getter
    private String firstName;
    @Getter
    private String paternalSurname;
    @Getter
    private String maternalSurname;
    @Getter
    private String description;
    @Getter
    private String phone;
    @Getter
    private String image;

    public User() {
        this.roles = new HashSet<>();
    }
    public User(String username, String password, String email, String firstName, String paternalSurname, String maternalSurname, String description, String phone, String image) {
        this.username = username;
        this.password = password;
        this.roles = new HashSet<>();
        this.email = email;
        this.firstName = firstName;
        this.paternalSurname = paternalSurname;
        this.maternalSurname = maternalSurname;
        this.description = description;
        this.phone = phone;
        this.image = image;
    }

    public User(String username, String password, List<Role> roles, String email, String firstName, String paternalSurname, String maternalSurname, String description, String phone, String image) {
        this(username, password, email, firstName, paternalSurname, maternalSurname, description, phone, image);
        addRoles(roles);
    }

    public void updateUserInfo(String email, String description, String phone, String image) {
        this.email = email;
        this.description = description;
        this.phone = phone;
        this.image = image;
    }

    /**
     * Add a role to the user
     * @param role the role to add
     * @return the user with the added role
     */
    public User addRole(Role role) {
        if(role == null || role.getId() == null) {
            this.roles.add(Role.getDefaultRole());
        } else {
            this.roles.add(role);
        }
        return this;
    }

    /**
     * Add a list of roles to the user
     * @param roles the list of roles to add
     * @return the user with the added roles
     */
    public User addRoles(List<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            this.roles.add(Role.getDefaultRole());
        } else {
            this.roles.addAll(roles);
        }
        return this;
    }
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getPaternalSurname() {
		return paternalSurname;
	}
	public void setPaternalSurname(String paternalSurname) {
		this.paternalSurname = paternalSurname;
	}
	public String getMaternalSurname() {
		return maternalSurname;
	}
	public void setMaternalSurname(String maternalSurname) {
		this.maternalSurname = maternalSurname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
    public Long getId() {
    	return super.getId();
    }

}

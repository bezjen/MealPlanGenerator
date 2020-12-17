package com.bezjen.whattoeat.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(
	name = "t_role", 
	uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "uk_role_name")}
)
public class Role implements GrantedAuthority {
	private static final long serialVersionUID = 3464791838942782181L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@Column(length = 50, nullable = false)
    private String name;
	@ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {
    }
    
    public Role(String name) {
    	this.name = name;
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getName();
    }

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]";
	}
}

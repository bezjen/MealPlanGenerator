package com.bezjen.whattoeat.entity;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(
	name = "t_user",
	uniqueConstraints = {
		@UniqueConstraint(columnNames={"username"}, name = "uk_user_username"),
		@UniqueConstraint(columnNames={"email"}, name = "uk_user_email")
	}
)
public class User implements UserDetails {
	private static final long serialVersionUID = 3846797316694666508L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@Column(length = 30, nullable = false)
    private String username;
	@Column(length = 50, nullable = false)
    private String email;
	@Column(length = 100, nullable = false)
    private String password;
    @Column(name = "is_email_confirmed", nullable = false)
    private boolean isEmailConfirmed;
    @Column(name = "date_of_creation", nullable = false)
	private Date date;
    @Column(name = "avatar_url", nullable = false, columnDefinition = "varchar(255) default '/img/user/avatar.jpg'")
    private String avatarUrl;
    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "T_USER_ROLE", 
        joinColumns = { @JoinColumn(name = "user_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "role_id") }, 
        foreignKey = @ForeignKey(name = "fk_user_role_user_id"), 
        inverseForeignKey = @ForeignKey(name = "fk_user_role_role_id")
    )
    private Set<Role> roles;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private List<Recipe> recipes;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private VerificationToken verificationToken;

    public User() {
    }
    
    public User(String username, String email, String password) {
    	this(username, email, password, "/img/user/avatar.jpg");
    }
    
    public User(String username, String email, String password, String avatarUrl) {
    	this.username = username;
    	this.email = email;
    	this.password = password;
    	this.avatarUrl = avatarUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
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

	public boolean isEmailConfirmed() {
		return isEmailConfirmed;
	}

	public void setEmailConfirmed(boolean isEmailConfirmed) {
		this.isEmailConfirmed = isEmailConfirmed;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", isEmailConfirmed=" + isEmailConfirmed + ", date=" + date + "]";
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

    public VerificationToken getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(VerificationToken verificationToken) {
        this.verificationToken = verificationToken;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}

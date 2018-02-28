package com.storefront.batch.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Praneeth.dodedu
 *
 */
@Entity
@Table(name = "user")
public class User {

	@Id
	public Long userId;

	@NotBlank
	public String userName;

	@Email
	public String email;

	public String type;

	public String profile;
	@NotBlank
	public String state;
	public String userType;
	@Column(length = 1000)
	public String avatarUrl;

	@ManyToOne(cascade = CascadeType.ALL)
	public User manager;

	@JsonIgnore
	@OneToMany(mappedBy = "manager")
	public Set<User> users = new HashSet<User>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_course", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "course_id") })
	public List<Course> courses = new ArrayList<Course>(0);

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_catalog", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "catalog_id") })
	public List<Catalog> catalogs = new ArrayList<Catalog>(0);

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_learning_program", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "learning_program_id") })
	public List<LearningProgram> learningPrograms = new ArrayList<LearningProgram>(0);

	@ElementCollection
	@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role_name")
	public List<String> roles;

	public User() {

	}

	public User(Long userId, String userName, String email, String type, String profile, String state, String userType,
			String avatarUrl) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.type = type;
		this.profile = profile;
		this.state = state;
		this.userType = userType;
		this.avatarUrl = avatarUrl;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<String> getRoles() {
		return roles;
	}

}

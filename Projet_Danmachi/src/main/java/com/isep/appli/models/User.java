package com.isep.appli.models;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(columnDefinition="tinyint(1) default 0")
	private Boolean enabled;

	@Column(columnDefinition="tinyint(1) default 0")
	private Boolean isAdmin;
	
	@Override
	public String toString() {
		return "Player [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", isAdmin=" + isAdmin + ", enabled=" + enabled
				+ "]";
	}
	
	
}

package com.isep.appli.dbModels;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "item")
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private String urlImage;
	private boolean canUse;
	private String description;
	private String useDescription;
	private Instant createdAt;
	private Instant updatedAt;
	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + "urlImage=" + urlImage + "description=" + description + "canUse=" + canUse +
				"useDescription=" + useDescription + "createdAt=" + createdAt + "updatedAt=" + updatedAt
				+ "]";
	}
}

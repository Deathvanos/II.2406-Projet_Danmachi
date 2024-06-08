package com.isep.appli.dbModels;

import com.isep.appli.models.enums.ItemCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "item")
public class Item implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String urlImage;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ItemCategory category;

	private boolean canUse;
	private String description;
	private String useDescription;
	private Instant createdAt;
	private Instant updatedAt;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Inventory> inventories;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Shop> shops;
	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + "urlImage=" + urlImage + "description=" + description + "canUse=" + canUse +
				"useDescription=" + useDescription + "createdAt=" + createdAt + "updatedAt=" + updatedAt
				+ "]";
	}
}

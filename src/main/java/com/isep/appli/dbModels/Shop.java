package com.isep.appli.dbModels;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "shop")
public class Shop implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "item_id", nullable = false)
	@JsonBackReference
	private Item item;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "seller_id", nullable = false)
	@JsonBackReference
	private Personnage seller;
	private int quantity;
	private Long price;

	@Override
	public String toString() {
		return "Item [id=" + id + ", idItem=" + item + "idPlayer=" + seller + "quantity=" + quantity + "price="+ price
				+ "]";
	}
}

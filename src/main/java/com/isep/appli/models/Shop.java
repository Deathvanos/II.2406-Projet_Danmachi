package com.isep.appli.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "shop")
public class Shop implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Long idItem;
	private Long idPlayer;
	private int quantity;
	private Long price;

	@Override
	public String toString() {
		return "Item [id=" + id + ", idItem=" + idItem + "idPlayer=" + idPlayer + "quantity=" + quantity + "price="+ price
				+ "]";
	}
}

package com.isep.appli.dbModels;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "inventory")
public class Inventory implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Long idItem;
	private Long idPlayer;
	private int quantity;

	@Override
	public String toString() {
		return "Item [id=" + id + ", idItem=" + idItem + "idPlayer=" + idPlayer + "quantity=" + quantity
				+ "]";
	}
}

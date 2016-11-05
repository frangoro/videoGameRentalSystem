package org.frangoro.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User {

	private Long id;
	private Long points;
	private String name;
	private Set<Rental> rentals = new HashSet(0);
	
	public User () {
		
	}
	
	public User (Long id, Long points, String name) {
		this.id = id;
		this.points = points;
		this.name = name;
	}
	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "POINTS")
	public Long getPoints() {
		return points;
	}
	public void setPoints(Long points) {
		this.points = points;
	}
	
	@Column(name = "NAME", length = 50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Rental> getRentals() {
		return rentals;
	}
	public void setRentals(Set<Rental> rentals) {
		this.rentals = rentals;
	}
	
}

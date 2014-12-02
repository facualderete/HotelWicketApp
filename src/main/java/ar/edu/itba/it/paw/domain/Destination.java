package ar.edu.itba.it.paw.domain;

import org.hibernate.annotations.Cascade;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Destination extends PersistentEntity {
	
	
	private String destination;
	private String details;
	
    @OneToOne
    @Cascade({org.hibernate.annotations.CascadeType.DELETE_ORPHAN, org.hibernate.annotations.CascadeType.ALL})
    private Picture picture;
    
    @OneToMany(mappedBy = "destination")
    private Set<Hotel> hotels = new HashSet<Hotel>();
    
    //Requerido por Hibernate
    public Destination(){}

    public Destination(String dest){
    	this.setDestination(dest);    	
    }
    
    public Set<Hotel> getHotels(){
    	return hotels;
    }


	public String getDetails() {
		return details;
	}


	public void setDetails(String details) {
		this.details = details;
	}
	
	public void setPicture(Picture pic){
		this.picture = pic;
	}
	
	public Picture getPicture(){
		return this.picture;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
}

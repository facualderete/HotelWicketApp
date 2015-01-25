package ar.edu.itba.it.paw.domain;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
public class Hotel extends PersistentEntity implements Comparable<Hotel> {
	private String name;
	private int category;
	private String type;
	private int price;
	private String address;
	private String phone;
	private String website;
	private boolean breakfast;
	private boolean active;
	private int accessCounter;

	@OneToMany
    @Cascade({org.hibernate.annotations.CascadeType.DELETE_ORPHAN, org.hibernate.annotations.CascadeType.ALL})
    private List<Picture> pictures;

	@OneToMany(mappedBy = "hotel")
	@Sort(type = SortType.NATURAL)
	private SortedSet<Comment> comments;
	
    @ManyToOne
	private Destination destination;

	// Requerido por Hibernate
	public Hotel() {
	}

	public Hotel(String name, int category, String type, int price,
			Destination destination, String address, String phone, String website,
			boolean breakfast) {

		if (name == null || type == null || category < 1 || price < 0
				|| destination == null || address == null || phone == null
				|| website == null) {

			throw new IllegalArgumentException();
		}
		this.name = name;
		this.category = category;
		this.type = type;
		this.price = price;
		this.address = address;
		this.phone = phone;
		this.website = website;
		this.breakfast = breakfast;
		this.active = true;
		this.accessCounter = 0;
        this.destination = destination;
	}

	public SortedSet<Comment> getComments() {
		return comments;
	}

	public void setComments(SortedSet<Comment> comments) {
		this.comments = comments;
	}

	public String getName() {
		return name;
	}

	public int getAmountOfComments() {
		return comments.size();
	}

	public int getCategory() {
		return category;
	}

	public String getType() {
		return type;
	}

	public int getPrice() {
		return price;
	}

	public Destination getDestination() {
		return destination;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	public String getWebsite() {
		return website;
	}

	public boolean getBreakfast() {
		return breakfast;
	}

	public boolean getActive() {
		return active;
	}

	public int getAccessCounter() {
		return accessCounter;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPrice(int price) {
		this.price = price;
	}


	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public boolean isBreakfast() {
		return breakfast;
	}

	public void setBreakfast(boolean breakfast) {
		this.breakfast = breakfast;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setAccessCounter(int accessCounter) {
		this.accessCounter = accessCounter;
	}

    public List<Picture> getPictures() {
        return pictures;
    }

    public void addPicture(Picture picture) {
        this.pictures.add(picture);
    }

    public void removePicture(Picture picture){
        this.pictures.remove(picture);
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Hotel))
			return false;
		Hotel other = (Hotel) obj;
		return this.name.equals(other.name)
				&& this.address.equals(other.address);
	}

	@Override
	public int hashCode() {
		return name.hashCode() + destination.getDestination().hashCode() + address.hashCode();
	}

	public int compareTo(Hotel o) {
		return ((Integer) comments.size()).compareTo(o.comments.size());
	}

	public void addComment(Comment comment) {
		comments.add(comment);
	}

	public void increaseAccessCounter() {
		accessCounter++;
	}

    public Picture getMainPic(){

        for(Picture p : this.pictures){
            if(p.getMain()){
                return p;
            }
        }

        return null;
    }

    public HotelEvaluation getEvaluation(){

        HotelEvaluation evaluation = new HotelEvaluation();

        for(Comment c : comments){
            if(c.getRating().getHygiene() != 0) evaluation.setHygiene(c.getRating().getHygiene());
            if(c.getRating().getFacilities() != 0) evaluation.setFacilities(c.getRating().getFacilities());
            if(c.getRating().getLocation() != 0) evaluation.setLocation(c.getRating().getLocation());
            if(c.getRating().getService() != 0) evaluation.setService(c.getRating().getService());
            if(c.getRating().getComfort() != 0) evaluation.setComfort(c.getRating().getComfort());
            if(c.getRating().getPrice() != 0) evaluation.setPrice(c.getRating().getPrice());
        }

        return evaluation;
    }

    public SortedSet<Comment> getFilteredComments() {

        SortedSet<Comment> filteredComments = new TreeSet<Comment>();

        for(Comment c : this.comments){
            if(!c.getForbidden()){
                filteredComments.add(c);
            }
        }
        return filteredComments;
    }
}

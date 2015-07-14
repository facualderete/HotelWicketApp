package ar.edu.itba.it.paw.domain;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.*;

@Entity
@Table(name="users")
public class User extends PersistentEntity{

    private String name;
    private String lastname;
    private String description;
    private String email;
    private String password;
    private boolean admin;
    private String token;

    @OneToOne
    @Cascade({org.hibernate.annotations.CascadeType.DELETE_ORPHAN, org.hibernate.annotations.CascadeType.ALL})
    private Picture picture;

    @OneToMany(mappedBy = "user")
    @Cascade({org.hibernate.annotations.CascadeType.DELETE_ORPHAN, org.hibernate.annotations.CascadeType.ALL})
    @Sort(type = SortType.NATURAL)
    private SortedSet<Comment> comments;

    @OneToMany
    private Set<Hotel> favourites = new HashSet<Hotel>();

    //Requerido por Hibernate
    public User(){}

    public User(String name, String lastname, String description, String email, String password) {
        this.name = name;
        this.lastname = lastname;
        this.description = description;
        this.email = email;
        this.password = password;
    }

    public SortedSet<Comment> getComments() {
        return comments;
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
    
    public Map<Hotel, List<Comment>> getCommentsByHotel(){
    	 Map<Hotel, List<Comment>> result = new HashMap<Hotel, List<Comment>>();
         Iterable<Comment> comments = getComments();

         for(Comment c : comments) {
             Hotel commentHotel = c.getHotel();
             if (!result.containsKey(commentHotel)) {
                 result.put(commentHotel, new ArrayList<Comment>());
             }
             result.get(commentHotel).add(c);
         }

         return result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean validateToken(String token) {
        return this.token != null && this.token.equals(token);
    }

    public void resetToken() {
        this.token = null;
    }

    public void setComments(SortedSet<Comment> comments) {
        this.comments = comments;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public void addFavourite(Hotel hotel){
        this.favourites.add(hotel);
    }

    public void removeFavourite(Hotel hotel){
        this.favourites.remove(hotel);
    }

    public boolean isFavourite(Hotel hotel){
        return this.favourites.contains(hotel);
    }

    public Set<Hotel> getFavourites() {
        return favourites;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean getHaspicture(){
        return this.picture != null;
    }
    
    public void setName(String name){
    	this.name = name;
    }
    
    public void setLast(String lastName){
    	this.lastname = lastName;
    }
    
    public void setDescription(String description){
    	this.description = description;
    }


    @Override
    public boolean equals(Object obj) {
    	if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		return this.email.equals(other.email);
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}

package ar.edu.itba.it.paw.domain;

import ar.edu.itba.it.paw.domain.filters.SearchHotelFilter;

import java.util.List;

public interface HotelRepo extends HibernateRepo {
	public List<Hotel> getAll();

	public Hotel get(int hotelId);

	public List<Destination> getDestinations();

	public Iterable<Hotel> getAllFiltered(SearchHotelFilter filter, boolean isAdmin);

	public Iterable<Hotel> getNHotelsOrderedByComments(int n, boolean isAdmin);
	
	public Destination getDestination(int destinationId);

	public Iterable<Comment> getCommentsOnHotel(Hotel hotel);

    public Picture getPicture(int pictureId);
<<<<<<< HEAD

	public Hotel getByName(String name);

	public Hotel getAnyOutstanding();
=======
    
    public Hotel getAnyOutstanding();
>>>>>>> d1a7a1f12129e64a61f5a6cd3b2b4f6a9b97a5e2
}

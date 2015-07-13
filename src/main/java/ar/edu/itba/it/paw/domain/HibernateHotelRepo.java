package ar.edu.itba.it.paw.domain;

import ar.edu.itba.it.paw.domain.filters.SearchHotelFilter;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HibernateHotelRepo extends AbstractHibernateRepo implements
		HotelRepo {

	@Autowired
	public HibernateHotelRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List<Hotel> getAll() {
		return find("from Hotel");
	}

	public Hotel get(int hotelId) {
		return get(Hotel.class, hotelId);
	}

	public Destination getDestination(int destinationId){
		return get(Destination.class, destinationId);
	}

	public List<Destination> getDestinations() {
		return find("from Destination");
	}

	public Iterable<Hotel> getAllFiltered(SearchHotelFilter filter, boolean isAdmin) {
		Criteria criteria = getSession().createCriteria(Hotel.class);

		String value;
		if (filter.getCity() != null) {
			if (!filter.getCity().isEmpty()) {
				value = filter.getCity().toLowerCase();
                criteria.createAlias("destination", "destination");
                criteria.add(Restrictions.ilike("destination.destination", "%" + value + "%"));
			}
		}

		if (filter.getName() != null) {
			if (!filter.getName().isEmpty()) {
				value = filter.getName().toLowerCase();
				criteria.add(Restrictions.ilike("name", "%" + value + "%"));
			}
		}

		if (filter.getType() != null) {
			if (!filter.getType().isEmpty()) {
				value = filter.getType().toLowerCase();
				criteria.add(Restrictions.ilike("type", "%" + value + "%"));
			}
		}

		if (filter.getBreakfast() != null) {
			if (!filter.getBreakfast().isEmpty()) {
				criteria.add(Restrictions.eq("breakfast",
						new Boolean(filter.getBreakfast())));
			}
		}

		if (filter.getPriceMin() != null) {
			criteria.add(Restrictions.ge("price", filter.getPriceMin()));
		}

		if (filter.getPriceMax() != null) {
			criteria.add(Restrictions.le("price", filter.getPriceMax()));
		}
		if (filter.getCategoryMin() != null) {
			criteria.add(Restrictions.ge("category", filter.getCategoryMin()));
		}

		if (filter.getCategoryMax() != null) {
			criteria.add(Restrictions.le("category", filter.getCategoryMax()));
		}

        if(!isAdmin){
            criteria.add(Restrictions.eq("active", true));
        }

		return criteria.list();
	}

	public Iterable<Hotel> getNHotelsOrderedByComments(int n, boolean isAdmin) {

        String condition = "";
        if(!isAdmin){
            condition = "where h.active = true";
        }

		Query query = getSession()
				.createQuery(
						"select h from Hotel h inner join h.comments c "+condition+" group by h order by count(*) desc")
				.setMaxResults(n);
		List<Hotel> result = query.list();
		return result;
	}

	public Iterable<Comment> getCommentsOnHotel(Hotel hotel) {
		return hotel.getComments();
	}

    public Picture getPicture(int pictureId) {
        return get(Picture.class, pictureId);
    }

	@Override
	public Hotel getAnyOutstanding() {
		List<Hotel> result = find("from Hotel where outstanding = true");
		
		return result.get((int)(Math.random()*result.size()));
	}
}
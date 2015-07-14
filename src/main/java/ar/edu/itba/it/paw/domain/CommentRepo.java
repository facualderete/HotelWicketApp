package ar.edu.itba.it.paw.domain;

import ar.edu.itba.it.paw.domain.filters.SearchHotelFilter;

import java.util.List;

public interface CommentRepo extends HibernateRepo {

    public List<Comment> getAll();

    public Comment get(int commentId);

    public Iterable<Comment> getAllFiltered(SearchHotelFilter filter);
    
	public List<Comment> getWithinRange(final String fromDate, final String toDate);
}

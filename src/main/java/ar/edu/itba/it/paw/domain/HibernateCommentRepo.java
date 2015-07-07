package ar.edu.itba.it.paw.domain;

import ar.edu.itba.it.paw.domain.filters.SearchHotelFilter;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HibernateCommentRepo extends AbstractHibernateRepo implements CommentRepo {

    @Autowired
    public HibernateCommentRepo(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Comment> getAll() {
        return find("from Comment");
    }

    @Override
    public Comment get(int commentId) {
        return get(Comment.class, commentId);
    }

    @Override
    public Iterable<Comment> getAllFiltered(SearchHotelFilter filter) {

        Criteria criteria = getSession().createCriteria(Comment.class);

//        criteria.add(Restrictions.between("commentDate", from.getTime(), to.getTime()));

        ProjectionList projectionList = Projections.projectionList();

//        projectionList.add(Projections.sqlGroupProjection(
//                "extract(YEAR from commentDate) as year",
//                "extract(YEAR from commentDate)",
//                new String[]{"year"},
//                new Type[] {Hibernate.INTEGER}));

        projectionList.add(Projections.sqlGroupProjection(
                "extract(MONTH from commentDate) as month, extract(YEAR from commentDate) as year",
                "extract(MONTH from commentDate), extract(YEAR from commentDate)",
                new String[]{"month","year"},
                new Type[] {Hibernate.INTEGER}));

        projectionList.add(Projections.count("id"));

        criteria.setProjection(projectionList);

        return criteria.list();
    }
}

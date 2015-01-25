package ar.edu.itba.it.paw.domain;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HibernateUserRepo extends AbstractHibernateRepo implements UserRepo {

	@Autowired
	public HibernateUserRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List<User> getAll() {
		return find("from User");
	}

	public User get(int userId) {
		return get(User.class, userId);
	}

	public User getByEmail(String email) {
        List<User> result = find("from User where email = ?", email);
        return result.size() > 0 ? result.get(0) : null;
	}

    public Iterable<Comment> getUserComments(User user) {
        return user.getComments();
    }

    public void addCommentOnHotel(User user, Comment comment){
        user.addComment(comment);
    }

    public Comment getComment(int commentId){
        return get(Comment.class, commentId);
    }
}

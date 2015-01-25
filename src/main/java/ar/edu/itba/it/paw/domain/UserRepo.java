package ar.edu.itba.it.paw.domain;

import java.util.List;

public interface UserRepo extends HibernateRepo{
    public List<User> getAll();
    public User get(int userId);
    public User getByEmail(String email);
    public Iterable<Comment> getUserComments(User user);
    public void addCommentOnHotel(User user, Comment comment);
    public Comment getComment(int commentId);
}


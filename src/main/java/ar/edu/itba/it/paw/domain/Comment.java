package ar.edu.itba.it.paw.domain;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Comment extends PersistentEntity implements Comparable<Comment>{

    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	private DateTime fromDate;
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	private DateTime toDate;
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	private DateTime commentDate;
	private String reason;
	private String companions;
	private String details;
    @ManyToOne
	private Hotel hotel;
    @ManyToOne
	private User user;
    @Embedded
	private Rating rating;
    private boolean forbidden;


    //Requerido por Hibernate
    public Comment(){}

	public Comment(DateTime fromDate, DateTime toDate, DateTime commentDate,
			String reason, String companions, String details, Hotel hotel,
			User user, Rating rating) {
		if (fromDate == null || toDate == null || commentDate == null || reason == null
				|| companions == null || details == null || rating == null) {
			throw new IllegalArgumentException();
		}
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.commentDate = commentDate;
		this.reason = reason;
		this.companions = companions;
		this.details = details;
		this.hotel = hotel;
		this.user = user;
		this.rating = rating;
        this.forbidden = false;
	}

    public void setFromDate(DateTime fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(DateTime toDate) {
        this.toDate = toDate;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setCompanions(String companions) {
        this.companions = companions;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public DateTime getCommentDate() {
		return commentDate;
	}

	public DateTime getFromDate() {
		return fromDate;
	}

	public DateTime getToDate() {
		return toDate;
	}

	public String getReason() {
		return reason;
	}

	public String getCompanions() {
		return companions;
	}

	public String getDetails() {
		return details;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public User getUser() {
		return user;
	}

	public Rating getRating() {
		return rating;
	}

	public double getAveragerating() {
		return rating.getAverageRating();
	}

    public boolean getForbidden() {
        return forbidden;
    }

    public void setForbidden(boolean forbidden) {
        this.forbidden = forbidden;
    }

    @Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Comment comment = (Comment) o;

		if (commentDate != null ? !commentDate.equals(comment.commentDate)
				: comment.commentDate != null)
			return false;
		if (companions != null ? !companions.equals(comment.companions)
				: comment.companions != null)
			return false;
		if (details != null ? !details.equals(comment.details)
				: comment.details != null)
			return false;
		if (fromDate != null ? !fromDate.equals(comment.fromDate) : comment.fromDate != null)
			return false;
		if (hotel != null ? !hotel.equals(comment.hotel)
				: comment.hotel != null)
			return false;
		if (rating != null ? !rating.equals(comment.rating)
				: comment.rating != null)
			return false;
		if (reason != null ? !reason.equals(comment.reason)
				: comment.reason != null)
			return false;
		if (toDate != null ? !toDate.equals(comment.toDate) : comment.toDate != null)
			return false;
		if (user != null ? !user.equals(comment.user) : comment.user != null)
			return false;

		return true;
	}

    public int compareTo(Comment o) {
		if (this.commentDate.compareTo(o.commentDate) == 0) {
			return Integer.compare(this.getId(), o.getId());
		} else {
			return this.commentDate.compareTo(o.commentDate);
		}
    }
}

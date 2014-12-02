package ar.edu.itba.it.paw.web;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.Destination;
import ar.edu.itba.it.paw.domain.Hotel;
import ar.edu.itba.it.paw.domain.Rating;
import ar.edu.itba.it.paw.domain.User;

public class CommentTest {


	private Comment comment;


	@Before
	public void setComment(){
		User user = new User("Pablo","Pauli","desc","pablo@paw.com","1234");
		Destination dest = new Destination("Cordoba");
		Hotel hotel = new Hotel("hotel", 2, "Casino", 200, dest,"falsa 123", "11113333", "hotel@paw.com", true);
		Rating rating = new Rating(1,1,1,1,1,1);
		comment = new Comment(new DateTime(1),new DateTime(2),new DateTime(),"placer","solo","lindo",hotel,user,rating);
	}


	@Test
	public void isForbiddenTest(){
		Assert.assertFalse(comment.getForbidden());
		comment.setForbidden(true);
		Assert.assertTrue(comment.getForbidden());
	}


	@Test
	public void avgRatingTest(){
		Assert.assertTrue(comment.getAveragerating()==1);
	}

	@Test
	public void equalsTest(){
		User user = new User("Pablo","Pauli","desc","pablo@paw.com","1234");
		Destination dest = new Destination("Cordoba");
		Hotel hotel = new Hotel("hotel", 2, "Casino", 200,dest,"falsa 123", "11113333", "hotel@paw.com", true);
		Rating rating = new Rating(1,1,1,1,1,1);
		Comment c1 = new Comment(new DateTime(1),new DateTime(2),new DateTime(),"placer","solo","lindo",hotel,user,rating);
		Comment c2 = new Comment(new DateTime(1),new DateTime(2),new DateTime(),"trabajo","pareja","lindo",hotel,user,rating);
		Assert.assertEquals(comment, c1);
		Assert.assertNotSame(comment, c2);
	}

}

package ar.edu.itba.it.paw.web;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.SortedSet;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.Destination;
import ar.edu.itba.it.paw.domain.Hotel;
import ar.edu.itba.it.paw.domain.Picture;
import ar.edu.itba.it.paw.domain.Rating;
import ar.edu.itba.it.paw.domain.User;

public class UserTest {


	private User user;

	@Before
	public void setUser(){
		this.user = new User("Pablo","Pauli","desc","pablo@paw.com","1234");
	}

	public void isAdmin(){
		Assert.assertNull(user.getAdmin());
		user.setAdmin(false);
		Assert.assertFalse(user.getAdmin());
		user.setAdmin(true);
		Assert.assertTrue(user.getAdmin());
	}

	@Test
	public void hasPicture(){
		Assert.assertFalse(user.getHaspicture());
		user.setPicture(new Picture());
		Assert.assertTrue(user.getHaspicture());
	}


	@Test
	public void hasFavourite(){
		Assert.assertNull(user.getFavourites());
		user.addFavourite(new Hotel());
		Assert.assertNotNull(user.getFavourites());
	}

	@Test
	public void equalUsers(){
		User u1 = new User("Pablo","Pauli","desc","pepe@paw.com","1234");
		User u2 = new User("Pablo","Pauli","desc","pablo@paw.com","1234");
		Assert.assertEquals(user, u2);
		Assert.assertNotSame(user, u1);
	}

	@Test
	public void isFavourite(){
		Destination dest = new Destination("Cordoba");
		Hotel hotel = new Hotel("hotel", 1, "", 1, dest, "cap", "1111", "h", true);
		Assert.assertFalse(user.isFavourite(hotel));
		user.addFavourite(hotel);
		Assert.assertTrue(user.isFavourite(hotel));
	}

	@Test(expected = IllegalArgumentException.class)
	public void favouriteNullTest(){
		user.addFavourite(null);
	}


	@Test(expected = IllegalArgumentException.class)
	public void setNameTest(){
		user.setName("nombremaslargodetreintaydoscaracteres");
	}
	@Test(expected = IllegalArgumentException.class)
	public void setSurnameTest(){
		user.setLast("apellidomaslargodetreintaydoscaracteres");
	}

}

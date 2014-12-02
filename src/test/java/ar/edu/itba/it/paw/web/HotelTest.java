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

public class HotelTest {
	
	private Hotel hotel;

	@Before
	public void setHotel(){
		Destination dest = new Destination("Cordoba");
		hotel = new Hotel("hotel", 2, "Casino", 200, dest,"falsa 123", "11113333", "hotel@paw.com", true);
	}

	@Test
	public void isActive(){
		Assert.assertFalse(hotel.getActive());
		hotel.setActive(true);
		Assert.assertTrue(hotel.getActive());
	}


	@Test
	public void hasBreakfastTest(){
		Assert.assertTrue(hotel.getBreakfast());
		hotel.setBreakfast(false);
		Assert.assertFalse(hotel.getBreakfast());
	}

	@Test
	public void equalsTest(){
		Destination dest = new Destination("Cordoba");
		Hotel h1 = new Hotel("hotel", 2, "Casino", 200, dest,"falsa 123", "11113333", "hotel@paw.com", true);
		Hotel h2 = new Hotel("hotel", 2, "Casino", 200, dest,"corrientes 111", "11113333", "hotel@paw.com", true);

		Assert.assertEquals(hotel, h1);
		Assert.assertNotSame(hotel,h2);
	}

	@Test
	public void counterTest(){
		Assert.assertTrue(hotel.getAccessCounter()==0);
		hotel.increaseAccessCounter();
		Assert.assertTrue(hotel.getAccessCounter()==1);
	}



}

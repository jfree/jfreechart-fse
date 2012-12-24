/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2012, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 * ----------------
 * SecondTests.java
 * ----------------
 * (C) Copyright 2002-2009, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 29-Jan-2002 : Version 1 (DG);
 * 17-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 13-Oct-2003 : Added serialization test (DG);
 * 11-Jan-2005 : Added test for non-clonability (DG);
 * 06-Oct-2006 : Added some new tests (DG);
 * 11-Jul-2007 : Fixed bad time zone assumption (DG);
 *
 */

package org.jfree.data.time;

import org.jfree.chart.date.MonthConstants;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * Tests for the {@link Second} class.
 */
public class SecondTest  {


    /**
     * Test that a Second instance is equal to itself.
     *
     * SourceForge Bug ID: 558850.
     */
    @Test
    public void testEqualsSelf() {
        Second second = new Second();
        assertEquals(second, second);
    }

    /**
     * Tests the equals method.
     */
    @Test
    public void testEquals() {
        Day day1 = new Day(29, MonthConstants.MARCH, 2002);
        Hour hour1 = new Hour(15, day1);
        Minute minute1 = new Minute(15, hour1);
        Second second1 = new Second(34, minute1);
        Day day2 = new Day(29, MonthConstants.MARCH, 2002);
        Hour hour2 = new Hour(15, day2);
        Minute minute2 = new Minute(15, hour2);
        Second second2 = new Second(34, minute2);
        assertEquals(second1, second2);
    }

    /**
     * In GMT, the 4.55:59pm on 21 Mar 2002 is java.util.Date(1016729759000L).
     * Use this to check the Second constructor.
     */
    @Test
    public void testDateConstructor1() {
        TimeZone zone = TimeZone.getTimeZone("GMT");
        Calendar c = new GregorianCalendar(zone);
        
        Locale locale = Locale.getDefault();  // locale shouldn't matter here
        Second s1 = new Second(new Date(1016729758999L), zone, locale);
        Second s2 = new Second(new Date(1016729759000L), zone, locale);

        assertEquals(58, s1.getSecond());
        assertEquals(1016729758999L, s1.getLastMillisecond(c));

        assertEquals(59, s2.getSecond());
        assertEquals(1016729759000L, s2.getFirstMillisecond(c));
    }

    /**
     * In Chicago, the 4.55:59pm on 21 Mar 2002 is
     * java.util.Date(1016751359000L). Use this to check the Second constructor.
     */
    @Test
    public void testDateConstructor2() {
        TimeZone zone = TimeZone.getTimeZone("America/Chicago");
        Calendar c = new GregorianCalendar(zone);
        Locale locale = Locale.getDefault();  // locale shouldn't matter here
        Second s1 = new Second(new Date(1016751358999L), zone, locale);
        Second s2 = new Second(new Date(1016751359000L), zone, locale);

        assertEquals(58, s1.getSecond());
        assertEquals(1016751358999L, s1.getLastMillisecond(c));

        assertEquals(59, s2.getSecond());
        assertEquals(1016751359000L, s2.getFirstMillisecond(c));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        Second s1 = new Second();

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(buffer);
        out.writeObject(s1);
        out.close();

        ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(
                buffer.toByteArray()));
        Second s2 = (Second) in.readObject();
        in.close();


        assertEquals(s1, s2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        Second s1 = new Second(13, 45, 5, 1, 2, 2003);
        Second s2 = new Second(13, 45, 5, 1, 2, 2003);
        assertEquals(s1, s2);
        int h1 = s1.hashCode();
        int h2 = s2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * The {@link Second} class is immutable, so should not be
     * {@link Cloneable}.
     */
    @Test
    public void testNotCloneable() {
        Second s = new Second(13, 45, 5, 1, 2, 2003);
        assertFalse(s instanceof Cloneable);
    }

    /**
     * Some checks for the getFirstMillisecond() method.
     */
    @Test
    public void testGetFirstMillisecond() {
        Locale saved = Locale.getDefault();
        Locale.setDefault(Locale.UK);
        TimeZone savedZone = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        Second s = new Second(15, 43, 15, 1, 4, 2006);
        assertEquals(1143902595000L, s.getFirstMillisecond());
        Locale.setDefault(saved);
        TimeZone.setDefault(savedZone);
    }

    /**
     * Some checks for the getFirstMillisecond(TimeZone) method.
     */
    @Test
    public void testGetFirstMillisecondWithTimeZone() {
        Second s = new Second(50, 59, 15, 1, 4, 1950);
        TimeZone zone = TimeZone.getTimeZone("America/Los_Angeles");
        Calendar c = new GregorianCalendar(zone);
        assertEquals(-623289610000L, s.getFirstMillisecond(c));

        // try null calendar
        try {
            s.getFirstMillisecond(null);
            fail("NullPointerException should have been thrown on null parameter");
        }
        catch (NullPointerException e) {
            //we expect to go in here
        }
    }

    /**
     * Some checks for the getFirstMillisecond(TimeZone) method.
     */
    @Test
    public void testGetFirstMillisecondWithCalendar() {
        Second s = new Second(55, 40, 2, 15, 4, 2000);
        GregorianCalendar calendar = new GregorianCalendar(Locale.GERMANY);
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Frankfurt"));
        assertEquals(955766455000L, s.getFirstMillisecond(calendar));

        // try null calendar
        try {
            s.getFirstMillisecond(null);
            fail("NullPointerException should have been thrown on null paramter");
        }
        catch (NullPointerException e) {
            //we expect to go in here
        }
    }

    /**
     * Some checks for the getLastMillisecond() method.
     */
    @Test
    public void testGetLastMillisecond() {
        Locale saved = Locale.getDefault();
        Locale.setDefault(Locale.UK);
        TimeZone savedZone = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        Second s = new Second(1, 1, 1, 1, 1, 1970);
        assertEquals(61999L, s.getLastMillisecond());
        Locale.setDefault(saved);
        TimeZone.setDefault(savedZone);
    }

    /**
     * Some checks for the getLastMillisecond(TimeZone) method.
     */
    @Test
    public void testGetLastMillisecondWithTimeZone() {
        Second s = new Second(55, 1, 2, 7, 7, 1950);
        TimeZone zone = TimeZone.getTimeZone("America/Los_Angeles");
        Calendar c = new GregorianCalendar(zone);
        assertEquals(-614962684001L, s.getLastMillisecond(c));

        // try null calendar
        try {
            s.getLastMillisecond(null);
            fail("NullPointerException should have been thrown on Null Parameter");
        }
        catch (NullPointerException e) {
            // we expect to go in here
        }
    }

    /**
     * Some checks for the getLastMillisecond(TimeZone) method.
     */
    @Test
    public void testGetLastMillisecondWithCalendar() {
        Second s = new Second(50, 45, 21, 21, 4, 2001);
        GregorianCalendar calendar = new GregorianCalendar(Locale.GERMANY);
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Frankfurt"));
        assertEquals(987889550999L, s.getLastMillisecond(calendar));

        // try null calendar
        try {
            s.getLastMillisecond(null);
            fail("NullPointerException should have been thrown on null parameter");
        }
        catch (NullPointerException e) {
            // we expect to go in here
        }
    }

    /**
     * Some checks for the getSerialIndex() method.
     */
    @Test
    public void testGetSerialIndex() {
        Second s = new Second(1, 1, 1, 1, 1, 2000);
        assertEquals(3155850061L, s.getSerialIndex());
        s = new Second(1, 1, 1, 1, 1, 1900);
        assertEquals(176461L, s.getSerialIndex());
    }

    /**
     * Some checks for the testNext() method.
     */
    @Test
    public void testNext() {
        Second s = new Second(55, 30, 1, 12, 12, 2000);
        s = (Second) s.next();
        assertEquals(2000, s.getMinute().getHour().getYear());
        assertEquals(12, s.getMinute().getHour().getMonth());
        assertEquals(12, s.getMinute().getHour().getDayOfMonth());
        assertEquals(1, s.getMinute().getHour().getHour());
        assertEquals(30, s.getMinute().getMinute());
        assertEquals(56, s.getSecond());
        s = new Second(59, 59, 23, 31, 12, 9999);
        assertNull(s.next());
    }

    /**
     * Some checks for the getStart() method.
     */
    @Test
    public void testGetStart() {
        Locale saved = Locale.getDefault();
        Locale.setDefault(Locale.ITALY);
        Calendar cal = Calendar.getInstance(Locale.ITALY);
        cal.set(2006, Calendar.JANUARY, 16, 3, 47, 55);
        cal.set(Calendar.MILLISECOND, 0);
        Second s = new Second(55, 47, 3, 16, 1, 2006);
        assertEquals(cal.getTime(), s.getStart());
        Locale.setDefault(saved);
    }

    /**
     * Some checks for the getEnd() method.
     */
    @Test
    public void testGetEnd() {
        Locale saved = Locale.getDefault();
        Locale.setDefault(Locale.ITALY);
        Calendar cal = Calendar.getInstance(Locale.ITALY);
        cal.set(2006, Calendar.JANUARY, 16, 3, 47, 55);
        cal.set(Calendar.MILLISECOND, 999);
        Second s = new Second(55, 47, 3, 16, 1, 2006);
        assertEquals(cal.getTime(), s.getEnd());
        Locale.setDefault(saved);
    }

}

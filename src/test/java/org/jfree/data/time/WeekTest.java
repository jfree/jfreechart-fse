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
 * --------------
 * WeekTests.java
 * --------------
 * (C) Copyright 2002-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 05-Apr-2002 : Version 1 (DG);
 * 26-Jun-2002 : Removed unnecessary imports (DG);
 * 17-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 13-Mar-2003 : Added serialization test (DG);
 * 21-Oct-2003 : Added hashCode test (DG);
 * 06-Apr-2006 : Added testBug1448828() method (DG);
 * 01-Jun-2006 : Added testBug1498805() method (DG);
 * 11-Jul-2007 : Fixed bad time zone assumption (DG);
 * 28-Aug-2007 : Added test for constructor problem (DG);
 * 19-Dec-2007 : Set default locale for tests that are sensitive
 *               to the locale (DG);
 *
 */

package org.jfree.data.time;

import org.junit.Before;
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
 * Tests for the {@link Week} class.
 */
public class WeekTest  {

    /** A week. */
    private Week w1Y1900;

    /** A week. */
    private Week w2Y1900;

    /** A week. */
    private Week w51Y9999;

    /** A week. */
    private Week w52Y9999;





    /**
     * Common test setup.
     */
    @Before
    public void setUp() {
        this.w1Y1900 = new Week(1, 1900);
        this.w2Y1900 = new Week(2, 1900);
        this.w51Y9999 = new Week(51, 9999);
        this.w52Y9999 = new Week(52, 9999);
    }

    /**
     * Tests the equals method.
     */
    @Test
    public void testEquals() {
        Week w1 = new Week(1, 2002);
        Week w2 = new Week(1, 2002);
        assertEquals(w1, w2);
        assertEquals(w2, w1);

        w1 = new Week(2, 2002);
        assertFalse(w1.equals(w2));
        w2 = new Week(2, 2002);
        assertEquals(w1, w2);

        w1 = new Week(2, 2003);
        assertFalse(w1.equals(w2));
        w2 = new Week(2, 2003);
        assertEquals(w1, w2);
    }

    /**
     * Request the week before week 1, 1900: it should be <code>null</code>.
     */
    @Test
    public void testW1Y1900Previous() {
        Week previous = (Week) this.w1Y1900.previous();
        assertNull(previous);
    }

    /**
     * Request the week after week 1, 1900: it should be week 2, 1900.
     */
    @Test
    public void testW1Y1900Next() {
        Week next = (Week) this.w1Y1900.next();
        assertEquals(this.w2Y1900, next);
    }

    /**
     * Request the week before w52, 9999: it should be week 51, 9999.
     */
    @Test
    public void testW52Y9999Previous() {
        Week previous = (Week) this.w52Y9999.previous();
        assertEquals(this.w51Y9999, previous);
    }

    /**
     * Request the week after w52, 9999: it should be <code>null</code>.
     */
    @Test
    public void testW52Y9999Next() {
        Week next = (Week) this.w52Y9999.next();
        assertNull(next);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {

        Week w1 = new Week(24, 1999);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(w1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
        Week w2 = (Week) in.readObject();
            in.close();

        assertEquals(w1, w2);

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        Week w1 = new Week(2, 2003);
        Week w2 = new Week(2, 2003);
        assertEquals(w1, w2);
        int h1 = w1.hashCode();
        int h2 = w2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * The {@link Week} class is immutable, so should not be {@link Cloneable}.
     */
    @Test
    public void testNotCloneable() {
        Week w = new Week(1, 1999);
        assertFalse(w instanceof Cloneable);
    }

    /**
     * The first week in 2005 should span the range:
     *
     * TimeZone         | Start Millis  | End Millis    | Start Date  | End Date
     * -----------------+---------------+---------------+-------------+------------
     * Europe/London    | 1104710400000 | 1105315199999 |  3-Jan-2005 | 9-Jan-2005
     * Europe/Paris     | 1104706800000 | 1105311599999 |  3-Jan-2005 | 2-Jan-2005
     * America/New_York | 1104037200000 | 1104641999999 | 26-Dec-2004 | 1-Jan-2005
     *
     * In London and Paris, Monday is the first day of the week, while in the
     * US it is Sunday.
     *
     * Previously, we were using these values, but see Java Bug ID 4960215:
     *
     * TimeZone         | Start Millis  | End Millis    | Start Date  | End Date
     * -----------------+---------------+---------------+-------------+------------
     * Europe/London    | 1104105600000 | 1104710399999 | 27-Dec-2004 | 2-Jan-2005
     * Europe/Paris     | 1104102000000 | 1104706799999 | 27-Dec-2004 | 2-Jan-2005
     * America/New_York | 1104037200000 | 1104641999999 | 26-Dec-2004 | 1-Jan-2005
     */
    @Test
    public void testWeek12005() {
        Week w1 = new Week(1, 2005);
        Calendar c1 = Calendar.getInstance(
                TimeZone.getTimeZone("Europe/London"), Locale.UK);
        c1.setMinimalDaysInFirstWeek(4);  // see Java Bug ID 4960215
        assertEquals(1104710400000L, w1.getFirstMillisecond(c1));
        assertEquals(1105315199999L, w1.getLastMillisecond(c1));
        Calendar c2 = Calendar.getInstance(
                TimeZone.getTimeZone("Europe/Paris"), Locale.FRANCE);
        c2.setMinimalDaysInFirstWeek(4);  // see Java Bug ID 4960215
        assertEquals(1104706800000L, w1.getFirstMillisecond(c2));
        assertEquals(1105311599999L, w1.getLastMillisecond(c2));
        Calendar c3 = Calendar.getInstance(
                TimeZone.getTimeZone("America/New_York"), Locale.US);
        assertEquals(1104037200000L, w1.getFirstMillisecond(c3));
        assertEquals(1104641999999L, w1.getLastMillisecond(c3));
    }

    /**
     * The 53rd week in 2004 in London and Paris should span the range:
     *
     * TimeZone         | Start Millis  | End Millis    | Start Date  | End Date
     * -----------------+---------------+---------------+-------------+------------
     * Europe/London    | 1104105600000 | 1104710399999 | 27-Dec-2004 | 02-Jan-2005
     * Europe/Paris     | 1104102000000 | 1104706799999 | 27-Dec-2004 | 02-Jan-2005
     *
     * The 53rd week in 2005 in New York should span the range:
     *
     * TimeZone         | Start Millis  | End Millis    | Start Date  | End Date
     * -----------------+---------------+---------------+-------------+------------
     * America/New_York | 1135486800000 | 1136091599999 | 25-Dec-2005 | 31-Dec-2005
     *
     * In London and Paris, Monday is the first day of the week, while in the
     * US it is Sunday.
     */
    @Test
    public void testWeek532005() {
        Week w1 = new Week(53, 2004);
        Calendar c1 = Calendar.getInstance(
                TimeZone.getTimeZone("Europe/London"), Locale.UK);
        c1.setMinimalDaysInFirstWeek(4);  // see Java Bug ID 4960215
        assertEquals(1104105600000L, w1.getFirstMillisecond(c1));
        assertEquals(1104710399999L, w1.getLastMillisecond(c1));
        Calendar c2 = Calendar.getInstance(
                TimeZone.getTimeZone("Europe/Paris"), Locale.FRANCE);
        c2.setMinimalDaysInFirstWeek(4);  // see Java Bug ID 4960215
        assertEquals(1104102000000L, w1.getFirstMillisecond(c2));
        assertEquals(1104706799999L, w1.getLastMillisecond(c2));
        w1 = new Week(53, 2005);
        Calendar c3 = Calendar.getInstance(
                TimeZone.getTimeZone("America/New_York"), Locale.US);
        assertEquals(1135486800000L, w1.getFirstMillisecond(c3));
        assertEquals(1136091599999L, w1.getLastMillisecond(c3));
    }

    /**
     * A test case for bug 1448828.
     */
    @Test
    public void testBug1448828() {
        Locale saved = Locale.getDefault();
        Locale.setDefault(Locale.UK);
        try {
            Week w = new Week(new Date(1136109830000l),
                    TimeZone.getTimeZone("GMT"), Locale.UK);
            assertEquals(2005, w.getYearValue());
            assertEquals(52, w.getWeek());
        }
        finally {
            Locale.setDefault(saved);
        }
    }

    /**
     * A test case for bug 1498805.
     */
    @Test
    public void testBug1498805() {
        Locale saved = Locale.getDefault();
        Locale.setDefault(Locale.UK);
        try {
            TimeZone zone = TimeZone.getTimeZone("GMT");
            GregorianCalendar gc = new GregorianCalendar(zone);
            gc.set(2005, Calendar.JANUARY, 1, 12, 0, 0);
            Week w = new Week(gc.getTime(), zone, Locale.UK);
            assertEquals(53, w.getWeek());
            assertEquals(new Year(2004), w.getYear());
        }
        finally {
            Locale.setDefault(saved);
        }
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
        Week w = new Week(3, 1970);
        assertEquals(946800000L, w.getFirstMillisecond());
        Locale.setDefault(saved);
        TimeZone.setDefault(savedZone);
    }

    /**
     * Some checks for the getFirstMillisecond(TimeZone) method.
     */
    @Test
    public void testGetFirstMillisecondWithTimeZone() {
        Week w = new Week(47, 1950);
        Locale saved = Locale.getDefault();
        Locale.setDefault(Locale.US);
        try {
            TimeZone zone = TimeZone.getTimeZone("America/Los_Angeles");
            Calendar c = new GregorianCalendar(zone);
            assertEquals(-603302400000L, w.getFirstMillisecond(c));
        }
        finally {
            Locale.setDefault(saved);
        }

        // try null calendar
        try {
            w.getFirstMillisecond(null);
            fail("NullPointerException should have been thrown");
        }
        catch (NullPointerException e) {
            //should go in here
        }
    }

    /**
     * Some checks for the getFirstMillisecond(TimeZone) method.
     */
    @Test
    public void testGetFirstMillisecondWithCalendar() {
        Week w = new Week(1, 2001);
        GregorianCalendar calendar = new GregorianCalendar(Locale.GERMANY);
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Frankfurt"));
        assertEquals(978307200000L, w.getFirstMillisecond(calendar));

        // try null calendar
        try {
            w.getFirstMillisecond(null);
            fail("NullPointerException should have been thrown");
        }
        catch (NullPointerException e) {
            //we should go in here
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
        Week w = new Week(31, 1970);
        assertEquals(18485999999L, w.getLastMillisecond());
        Locale.setDefault(saved);
        TimeZone.setDefault(savedZone);
    }

    /**
     * Some checks for the getLastMillisecond(TimeZone) method.
     */
    @Test
    public void testGetLastMillisecondWithTimeZone() {
        Week w = new Week(2, 1950);
        Locale saved = Locale.getDefault();
        Locale.setDefault(Locale.US);
        try {
            TimeZone zone = TimeZone.getTimeZone("America/Los_Angeles");
            Calendar c = new GregorianCalendar(zone);
            assertEquals(-629913600001L, w.getLastMillisecond(c));
        }
        finally {
            Locale.setDefault(saved);
        }

        // try null zone
        try {
            w.getLastMillisecond(null);
            fail("NullPointerException should have been thrown");
        }
        catch (NullPointerException e) {
            //we should go in here
        }
    }

    /**
     * Some checks for the getLastMillisecond(TimeZone) method.
     */
    @Test
    public void testGetLastMillisecondWithCalendar() {
        Week w = new Week(52, 2001);
        GregorianCalendar calendar = new GregorianCalendar(Locale.GERMANY);
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Frankfurt"));
        assertEquals(1009756799999L, w.getLastMillisecond(calendar));

        // try null calendar
        try {
            w.getLastMillisecond(null);
            fail("NullPointerException should have been thrown");
        }
        catch (NullPointerException e) {
            //we should go in here
        }
    }

    /**
     * Some checks for the getSerialIndex() method.
     */
    @Test
    public void testGetSerialIndex() {
        Week w = new Week(1, 2000);
        assertEquals(106001L, w.getSerialIndex());
        w = new Week(1, 1900);
        assertEquals(100701L, w.getSerialIndex());
    }

    /**
     * Some checks for the testNext() method.
     */
    @Test
    public void testNext() {
        Week w = new Week(12, 2000);
        w = (Week) w.next();
        assertEquals(new Year(2000), w.getYear());
        assertEquals(13, w.getWeek());
        w = new Week(53, 9999);
        assertNull(w.next());
    }

    /**
     * Some checks for the getStart() method.
     */
    @Test
    public void testGetStart() {
        Locale saved = Locale.getDefault();
        Locale.setDefault(Locale.ITALY);
        Calendar cal = Calendar.getInstance(Locale.ITALY);
        cal.set(2006, Calendar.JANUARY, 16, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Week w = new Week(3, 2006);
        assertEquals(cal.getTime(), w.getStart());
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
        cal.set(2006, Calendar.JANUARY, 8, 23, 59, 59);
        cal.set(Calendar.MILLISECOND, 999);
        Week w = new Week(1, 2006);
        assertEquals(cal.getTime(), w.getEnd());
        Locale.setDefault(saved);
    }

    /**
     * A test for a problem in constructing a new Week instance.
     */
    @Test
    public void testConstructor() {
        Locale savedLocale = Locale.getDefault();
        TimeZone savedZone = TimeZone.getDefault();
        Locale.setDefault(new Locale("da", "DK"));
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Copenhagen"));
        GregorianCalendar cal = (GregorianCalendar) Calendar.getInstance(
                TimeZone.getDefault(), Locale.getDefault());

        // first day of week is monday
        assertEquals(Calendar.MONDAY, cal.getFirstDayOfWeek());
        cal.set(2007, Calendar.AUGUST, 26, 1, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date t = cal.getTime();
        Week w = new Week(t, TimeZone.getTimeZone("Europe/Copenhagen"), Locale.getDefault());
        assertEquals(34, w.getWeek());

        Locale.setDefault(Locale.US);
        TimeZone.setDefault(TimeZone.getTimeZone("US/Detroit"));
        cal = (GregorianCalendar) Calendar.getInstance(TimeZone.getDefault());
        // first day of week is Sunday
        assertEquals(Calendar.SUNDAY, cal.getFirstDayOfWeek());
        cal.set(2007, Calendar.AUGUST, 26, 1, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);

        t = cal.getTime();
        w = new Week(t, TimeZone.getTimeZone("Europe/Copenhagen"), Locale.getDefault());
        assertEquals(35, w.getWeek());
        w = new Week(t, TimeZone.getTimeZone("Europe/Copenhagen"),
                new Locale("da", "DK"));
        assertEquals(34, w.getWeek());

        Locale.setDefault(savedLocale);
        TimeZone.setDefault(savedZone);
    }

}

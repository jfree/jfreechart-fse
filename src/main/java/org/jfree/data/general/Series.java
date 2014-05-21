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
 * -----------
 * Series.java
 * -----------
 * (C) Copyright 2001-2012, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 15-Nov-2001 : Version 1 (DG);
 * 29-Nov-2001 : Added cloning and property change support (DG);
 * 30-Jan-2002 : Added a description attribute and changed the constructors to
 *               protected (DG);
 * 07-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 13-Mar-2003 : Implemented Serializable (DG);
 * 01-May-2003 : Added equals() method (DG);
 * 26-Jun-2003 : Changed listener list to use EventListenerList - see bug
 *               757027 (DG);
 * 15-Oct-2003 : Added a flag to control whether or not change events are sent
 *               to registered listeners (DG);
 * 19-May-2005 : Made abstract (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 04-May-2006 : Updated API docs (DG);
 * 26-Sep-2007 : Added isEmpty() and getItemCount() methods (DG);
 * 16-Oct-2011 : Added vetoable property change support for series name (DG);
 * 17-Jun-2012 : Removed JCommon dependencies (DG);
 * 
 */

package org.jfree.data.general;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;

import javax.swing.event.EventListenerList;

import org.jfree.chart.util.ObjectUtils;
import org.jfree.chart.util.ParamChecks;

/**
 * Base class representing a data series.  Subclasses are left to implement the
 * actual data structures.
 * <P>
 * The series has two properties ("Key" and "Description") for which you can
 * register a <code>PropertyChangeListener</code>.
 * <P>
 * You can also register a {@link SeriesChangeListener} to receive notification
 * of changes to the series data.
 */
public abstract class Series implements Cloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -6906561437538683581L;

    /** The key for the series. */
    private Comparable key;

    /** A description of the series. */
    private String description;

    /** Storage for registered change listeners. */
    private EventListenerList listeners;

    /** Object to support property change notification. */
    private PropertyChangeSupport propertyChangeSupport;

    /** Object to support property change notification. */
    private VetoableChangeSupport vetoableChangeSupport;

    /** A flag that controls whether or not changes are notified. */
    private boolean notify;

    /**
     * Creates a new series with the specified key.
     *
     * @param key  the series key (<code>null</code> not permitted).
     */
    protected Series(Comparable key) {
        this(key, null);
    }

    /**
     * Creates a new series with the specified key and description.
     *
     * @param key  the series key (<code>null</code> NOT permitted).
     * @param description  the series description (<code>null</code> permitted).
     */
    protected Series(Comparable key, String description) {
        ParamChecks.nullNotPermitted(key, "key");       
        this.key = key;
        this.description = description;
        this.listeners = new EventListenerList();
        this.propertyChangeSupport = new PropertyChangeSupport(this);
        this.vetoableChangeSupport = new VetoableChangeSupport(this);
        this.notify = true;
    }

    /**
     * Returns the key for the series.
     *
     * @return The series key (never <code>null</code>).
     *
     * @see #setKey(Comparable)
     */
    public Comparable getKey() {
        return this.key;
    }

    /**
     * Sets the key for the series and sends a <code>VetoableChangeEvent</code>
     * (with the property name "Key") to all registered listeners.  If the
     * change is vetoed by a listener, this method will throw an 
     * IllegalArgumentException.
     *
     * @param key  the key (<code>null</code> not permitted).
     *
     * @see #getKey()
     */
    public void setKey(Comparable key) {
        ParamChecks.nullNotPermitted(key, "key");
        Comparable old = this.key;
        try {
            // if this series belongs to a dataset, the dataset might veto the
            // change if it results in two series within the dataset having the
            // same key
            this.vetoableChangeSupport.fireVetoableChange("Key", old, key);
            this.key = key;
        } catch (PropertyVetoException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Returns a description of the series.
     *
     * @return The series description (possibly <code>null</code>).
     *
     * @see #setDescription(String)
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description of the series and sends a
     * <code>PropertyChangeEvent</code> to all registered listeners.
     *
     * @param description  the description (<code>null</code> permitted).
     *
     * @see #getDescription()
     */
    public void setDescription(String description) {
        String old = this.description;
        this.description = description;
        this.propertyChangeSupport.firePropertyChange("Description", old,
                description);
    }

    /**
     * Returns the flag that controls whether or not change events are sent to
     * registered listeners.
     *
     * @return A boolean.
     *
     * @see #setNotify(boolean)
     */
    public boolean getNotify() {
        return this.notify;
    }

    /**
     * Sets the flag that controls whether or not change events are sent to
     * registered listeners.
     *
     * @param notify  the new value of the flag.
     *
     * @see #getNotify()
     */
    public void setNotify(boolean notify) {
        if (this.notify != notify) {
            this.notify = notify;
            fireSeriesChanged();
        }
    }

    /**
     * Returns <code>true</code> if the series contains no data items, and
     * <code>false</code> otherwise.
     *
     * @return A boolean.
     *
     * @since 1.0.7
     */
    public boolean isEmpty() {
        return (getItemCount() == 0);
    }

    /**
     * Returns the number of data items in the series.
     *
     * @return The number of data items in the series.
     */
    public abstract int getItemCount();

    /**
     * Returns a clone of the series.
     * <P>
     * Notes:
     * <ul>
     * <li>No need to clone the name or description, since String object is
     * immutable.</li>
     * <li>We set the listener list to empty, since the listeners did not
     * register with the clone.</li>
     * <li>Same applies to the PropertyChangeSupport instance.</li>
     * </ul>
     *
     * @return A clone of the series.
     *
     * @throws CloneNotSupportedException  not thrown by this class, but
     *         subclasses may differ.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        Series clone = (Series) super.clone();
        clone.listeners = new EventListenerList();
        clone.propertyChangeSupport = new PropertyChangeSupport(clone);
        clone.vetoableChangeSupport = new VetoableChangeSupport(clone);
        return clone;
    }

    /**
     * Tests the series for equality with another object.
     *
     * @param obj  the object (<code>null</code> permitted).
     *
     * @return <code>true</code> or <code>false</code>.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Series)) {
            return false;
        }
        Series that = (Series) obj;
        if (!getKey().equals(that.getKey())) {
            return false;
        }
        if (!ObjectUtils.equal(getDescription(), that.getDescription())) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result;
        result = this.key.hashCode();
        result = 29 * result + (this.description != null
                ? this.description.hashCode() : 0);
        return result;
    }

    /**
     * Registers an object with this series, to receive notification whenever
     * the series changes.
     * <P>
     * Objects being registered must implement the {@link SeriesChangeListener}
     * interface.
     *
     * @param listener  the listener to register.
     */
    public void addChangeListener(SeriesChangeListener listener) {
        this.listeners.add(SeriesChangeListener.class, listener);
    }

    /**
     * Deregisters an object, so that it not longer receives notification
     * whenever the series changes.
     *
     * @param listener  the listener to deregister.
     */
    public void removeChangeListener(SeriesChangeListener listener) {
        this.listeners.remove(SeriesChangeListener.class, listener);
    }

    /**
     * General method for signalling to registered listeners that the series
     * has been changed.
     */
    public void fireSeriesChanged() {
        if (this.notify) {
            notifyListeners(new SeriesChangeEvent(this));
        }
    }

    /**
     * Sends a change event to all registered listeners.
     *
     * @param event  contains information about the event that triggered the
     *               notification.
     */
    protected void notifyListeners(SeriesChangeEvent event) {

        Object[] listenerList = this.listeners.getListenerList();
        for (int i = listenerList.length - 2; i >= 0; i -= 2) {
            if (listenerList[i] == SeriesChangeListener.class) {
                ((SeriesChangeListener) listenerList[i + 1]).seriesChanged(
                        event);
            }
        }

    }

    /**
     * Adds a property change listener to the series.
     *
     * @param listener  the listener.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Removes a property change listener from the series.
     *
     * @param listener  the listener.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Fires a property change event.
     *
     * @param property  the property key.
     * @param oldValue  the old value.
     * @param newValue  the new value.
     */
    protected void firePropertyChange(String property, Object oldValue,
            Object newValue) {
        this.propertyChangeSupport.firePropertyChange(property, oldValue,
                newValue);
    }
    
    /**
     * Adds a vetoable property change listener to the series.
     *
     * @param listener  the listener.
     * 
     * @since 1.0.14
     */
    public void addVetoableChangeListener(VetoableChangeListener listener) {
        this.vetoableChangeSupport.addVetoableChangeListener(listener);
    }

    /**
     * Removes a vetoable property change listener from the series.
     *
     * @param listener  the listener.
     * 
     * @since 1.0.14 
     */
    public void removeVetoableChangeListener(VetoableChangeListener listener) {
        this.vetoableChangeSupport.removeVetoableChangeListener(listener);
    }    

    /**
     * Fires a vetoable property change event.
     *
     * @param property  the property key.
     * @param oldValue  the old value.
     * @param newValue  the new value.
     */
    protected void fireVetoableChange(String property, Object oldValue,
            Object newValue) throws PropertyVetoException {
        this.vetoableChangeSupport.fireVetoableChange(property, oldValue,
                newValue);
    }

}

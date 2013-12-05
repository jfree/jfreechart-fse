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
 * -----------------
 * KeyedObjects.java
 * -----------------
 * (C) Copyright 2003-2012, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 31-Oct-2002 : Version 1 (DG);
 * 11-Jan-2005 : Minor tidy up (DG);
 * 28-Sep-2007 : Clean up equals() method (DG);
 * 03-Oct-2007 : Make method behaviour consistent with DefaultKeyedValues (DG);
 * 17-Jun-2012 : Removed JCommon dependencies (DG);
 *
 */

package org.jfree.data;

import java.io.Serializable;
import java.util.List;

import org.jfree.chart.util.PublicCloneable;

/**
 * A collection of (key, object) pairs.
 */
public class KeyedObjects implements Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 1321582394193530984L;

    /** Storage for the data. */
    private List<KeyedObject> data;

    /**
     * Creates a new collection (initially empty).
     */
    public KeyedObjects() {
        this.data = new java.util.ArrayList<KeyedObject>();
    }

    /**
     * Returns the number of items (values) in the collection.
     *
     * @return The item count.
     */
    public int getItemCount() {
        return this.data.size();
    }

    /**
     * Returns an object from the list.
     *
     * @param item  the item index (zero-based).
     *
     * @return The object (possibly <code>null</code>).
     *
     * @throws IndexOutOfBoundsException if <code>item</code> is out of bounds.
     */
    public Object getObject(int item) {
        Object result = null;
        KeyedObject kobj = this.data.get(item);
        if (kobj != null) {
            result = kobj.getObject();
        }
        return result;
    }

    /**
     * Returns the key at the specified position in the list.
     *
     * @param index  the item index (zero-based).
     *
     * @return The row key.
     *
     * @throws IndexOutOfBoundsException if <code>item</code> is out of bounds.
     *
     * @see #getIndex(Comparable)
     */
    public Comparable getKey(int index) {
        Comparable result = null;
        KeyedObject item = this.data.get(index);
        if (item != null) {
            result = item.getKey();
        }
        return result;
    }

    /**
     * Returns the index for a given key, or <code>-1</code>.
     *
     * @param key  the key (<code>null</code> not permitted).
     *
     * @return The index, or <code>-1</code> if the key is unrecognised.
     *
     * @see #getKey(int)
     */
    public int getIndex(Comparable key) {
        if (key == null) {
            throw new IllegalArgumentException("Null 'key' argument.");
        }
        int i = 0;
        for (KeyedObject ko : this.data) {
            if (ko.getKey().equals(key)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * Returns a list containing all the keys in the list.
     *
     * @return The keys (never <code>null</code>).
     */
    public List<Comparable> getKeys() {
        List<Comparable> result = new java.util.ArrayList<Comparable>();
        for (KeyedObject ko : this.data) {
            result.add(ko.getKey());
        }
        return result;
    }

    /**
     * Returns the object for a given key. If the key is not recognised, the
     * method should return <code>null</code>.
     *
     * @param key  the key.
     *
     * @return The object (possibly <code>null</code>).
     *
     * @see #addObject(Comparable, Object)
     */
    public Object getObject(Comparable key) {
        int index = getIndex(key);
        if (index < 0) {
            throw new UnknownKeyException("The key (" + key
                    + ") is not recognised.");
        }
        return getObject(index);
    }

    /**
     * Adds a new object to the collection, or overwrites an existing object.
     * This is the same as the {@link #setObject(Comparable, Object)} method.
     *
     * @param key  the key.
     * @param object  the object.
     *
     * @see #getObject(Comparable)
     */
    public void addObject(Comparable key, Object object) {
        setObject(key, object);
    }

    /**
     * Replaces an existing object, or adds a new object to the collection.
     * This is the same as the {@link #addObject(Comparable, Object)}
     * method.
     *
     * @param key  the key (<code>null</code> not permitted).
     * @param object  the object.
     *
     * @see #getObject(Comparable)
     */
    public void setObject(Comparable key, Object object) {
        int keyIndex = getIndex(key);
        if (keyIndex >= 0) {
            KeyedObject ko = this.data.get(keyIndex);
            ko.setObject(object);
        }
        else {
            KeyedObject ko = new KeyedObject(key, object);
            this.data.add(ko);
        }
    }

    /**
     * Inserts a new value at the specified position in the dataset or, if
     * there is an existing item with the specified key, updates the value
     * for that item and moves it to the specified position.
     *
     * @param position  the position (in the range <code>0</code> to
     *                  <code>getItemCount()</code>).
     * @param key  the key (<code>null</code> not permitted).
     * @param value  the value (<code>null</code> permitted).
     *
     * @since 1.0.7
     */
    public void insertValue(int position, Comparable key, Object value) {
        if (position < 0 || position > this.data.size()) {
            throw new IllegalArgumentException("'position' out of bounds.");
        }
        if (key == null) {
            throw new IllegalArgumentException("Null 'key' argument.");
        }
        int pos = getIndex(key);
        if (pos >= 0) {
            this.data.remove(pos);
        }
        KeyedObject item = new KeyedObject(key, value);
        if (position <= this.data.size()) {
            this.data.add(position, item);
        }
        else {
            this.data.add(item);
        }
    }

    /**
     * Removes a value from the collection.
     *
     * @param index  the index of the item to remove.
     *
     * @see #removeValue(Comparable)
     */
    public void removeValue(int index) {
        this.data.remove(index);
    }

    /**
     * Removes a value from the collection.
     *
     * @param key  the key (<code>null</code> not permitted).
     *
     * @see #removeValue(int)
     *
     * @throws UnknownKeyException if the key is not recognised.
     */
    public void removeValue(Comparable key) {
        // defer argument checking
        int index = getIndex(key);
        if (index < 0) {
            throw new UnknownKeyException("The key (" + key.toString()
                    + ") is not recognised.");
        }
        removeValue(index);
    }

    /**
     * Clears all values from the collection.
     *
     * @since 1.0.7
     */
    public void clear() {
        this.data.clear();
    }

    /**
     * Returns a clone of this object.  Keys in the list should be immutable
     * and are not cloned.  Objects in the list are cloned only if they
     * implement {@link PublicCloneable}.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if there is a problem cloning.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        KeyedObjects clone = (KeyedObjects) super.clone();
        clone.data = new java.util.ArrayList<KeyedObject>();
        for (KeyedObject ko : this.data) {
            clone.data.add((KeyedObject) ko.clone());
        }
        return clone;
    }

    /**
     * Tests this object for equality with an arbitrary object.
     *
     * @param obj  the object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }
        if (!(obj instanceof KeyedObjects)) {
            return false;
        }
        KeyedObjects that = (KeyedObjects) obj;
        int count = getItemCount();
        if (count != that.getItemCount()) {
            return false;
        }

        for (int i = 0; i < count; i++) {
            Comparable k1 = getKey(i);
            Comparable k2 = that.getKey(i);
            if (!k1.equals(k2)) {
                return false;
            }
            Object o1 = getObject(i);
            Object o2 = that.getObject(i);
            if (o1 == null) {
                if (o2 != null) {
                    return false;
                }
            }
            else {
                if (!o1.equals(o2)) {
                    return false;
                }
            }
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
        return (this.data != null ? this.data.hashCode() : 0);
    }

}

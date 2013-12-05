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
 * KeyedObject.java
 * ----------------
 * (C) Copyright 2003-2012, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 05-Feb-2003 : Version 1 (DG);
 * 27-Jan-2003 : Implemented Cloneable and Serializable, and added an equals()
 *               method (DG);
 * 16-Jun-2012 : Removed JCommon dependencies (DG);
 *
 */

package org.jfree.data;

import org.jfree.chart.util.ObjectUtilities;
import org.jfree.chart.util.PublicCloneable;

import java.io.Serializable;

/**
 * A (key, object) pair.
 */
public class KeyedObject<Key extends Comparable, Value>
        implements Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 2677930479256885863L;

    /** The key. */
    private Key key;

    /** The object. */
    private Value object;

    /**
     * Creates a new (key, object) pair.
     *
     * @param key  the key.
     * @param object  the object (<code>null</code> permitted).
     */
    public KeyedObject(Key key, Value object) {
        this.key = key;
        this.object = object;
    }

    /**
     * Returns the key.
     *
     * @return The key.
     */
    public Key getKey() {
        return this.key;
    }

    /**
     * Returns the object.
     *
     * @return The object (possibly <code>null</code>).
     */
    public Value getObject() {
        return this.object;
    }

    /**
     * Sets the object.
     *
     * @param object  the object (<code>null</code> permitted).
     */
    public void setObject(Value object) {
        this.object = object;
    }

    /**
     * Returns a clone of this object.  It is assumed that the key is an
     * immutable object, so it is not deep-cloned.  The object is deep-cloned
     * if it implements {@link PublicCloneable}, otherwise a shallow clone is
     * made.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if there is a problem cloning.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        KeyedObject clone = (KeyedObject) super.clone();
        if (this.object instanceof PublicCloneable) {
            PublicCloneable pc = (PublicCloneable) this.object;
            clone.object = pc.clone();
        }
        return clone;
    }

    /**
     * Tests if this object is equal to another.
     *
     * @param obj  the other object.
     *
     * @return A boolean.
     */
    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof KeyedObject)) {
            return false;
        }
        KeyedObject that = (KeyedObject) obj;
        if (!ObjectUtilities.equal(this.key, that.key)) {
            return false;
        }

        return ObjectUtilities.equal(this.object, that.object);

    }

}

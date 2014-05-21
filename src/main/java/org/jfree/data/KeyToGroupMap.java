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
 * ------------------
 * KeyToGroupMap.java
 * ------------------
 * (C) Copyright 2004-2012, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 29-Apr-2004 : Version 1 (DG);
 * 07-Jul-2004 : Added a group list to ensure group index is consistent, fixed
 *               cloning problem (DG);
 * 18-Aug-2005 : Added casts in clone() method to suppress 1.5 compiler
 *               warnings - see patch 1260587 (DG);
 * 17-Jun-2012 : Removed JCommon dependencies (DG);
 *
 */

package org.jfree.data;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.util.ObjectUtils;
import org.jfree.chart.util.PublicCloneable;

/**
 * A class that maps keys (instances of <code>Comparable</code>) to groups.
 */
public class KeyToGroupMap implements Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -2228169345475318082L;

    /** The default group. */
    private Comparable defaultGroup;

    /** The groups. */
    private List<Comparable> groups;

    /** A mapping between keys and groups. */
    private Map<Comparable, Comparable> keyToGroupMap;

    /**
     * Creates a new map with a default group named 'Default Group'.
     */
    public KeyToGroupMap() {
        this("Default Group");
    }

    /**
     * Creates a new map with the specified default group.
     *
     * @param defaultGroup  the default group (<code>null</code> not permitted).
     */
    public KeyToGroupMap(Comparable defaultGroup) {
        if (defaultGroup == null) {
            throw new IllegalArgumentException("Null 'defaultGroup' argument.");
        }
        this.defaultGroup = defaultGroup;
        this.groups = new ArrayList<Comparable>();
        this.keyToGroupMap = new HashMap<Comparable, Comparable>();
    }

    /**
     * Returns the number of groups in the map.
     *
     * @return The number of groups in the map.
     */
    public int getGroupCount() {
        return this.groups.size() + 1;
    }

    /**
     * Returns a list of the groups (always including the default group) in the
     * map.  The returned list is independent of the map, so altering the list
     * will have no effect.
     *
     * @return The groups (never <code>null</code>).
     */
    public List<Comparable> getGroups() {
        List<Comparable> result = new ArrayList<Comparable>();
        result.add(this.defaultGroup);
        for (Comparable group : this.groups) {
            if (!result.contains(group)) {
                result.add(group);
            }
        }
        return result;
    }

    /**
     * Returns the index for the group.
     *
     * @param group  the group.
     *
     * @return The group index (or -1 if the group is not represented within
     *         the map).
     */
    public int getGroupIndex(Comparable group) {
        int result = this.groups.indexOf(group);
        if (result < 0) {
            if (this.defaultGroup.equals(group)) {
                result = 0;
            }
        }
        else {
            result = result + 1;
        }
        return result;
    }

    /**
     * Returns the group that a key is mapped to.
     *
     * @param key  the key (<code>null</code> not permitted).
     *
     * @return The group (never <code>null</code>, returns the default group if
     *         there is no mapping for the specified key).
     */
    public Comparable getGroup(Comparable key) {
        if (key == null) {
            throw new IllegalArgumentException("Null 'key' argument.");
        }
        Comparable result = this.defaultGroup;
        Comparable group = this.keyToGroupMap.get(key);
        if (group != null) {
            result = group;
        }
        return result;
    }

    /**
     * Maps a key to a group.
     *
     * @param key  the key (<code>null</code> not permitted).
     * @param group  the group (<code>null</code> permitted, clears any
     *               existing mapping).
     */
    public void mapKeyToGroup(Comparable key, Comparable group) {
        if (key == null) {
            throw new IllegalArgumentException("Null 'key' argument.");
        }
        Comparable currentGroup = getGroup(key);
        if (!currentGroup.equals(this.defaultGroup)) {
            if (!currentGroup.equals(group)) {
                int count = getKeyCount(currentGroup);
                if (count == 1) {
                    this.groups.remove(currentGroup);
                }
            }
        }
        if (group == null) {
            this.keyToGroupMap.remove(key);
        }
        else {
            if (!this.groups.contains(group)) {
                if (!this.defaultGroup.equals(group)) {
                    this.groups.add(group);
                }
            }
            this.keyToGroupMap.put(key, group);
        }
    }

    /**
     * Returns the number of keys mapped to the specified group.  This method
     * won't always return an accurate result for the default group, since
     * explicit mappings are not required for this group.
     *
     * @param group  the group (<code>null</code> not permitted).
     *
     * @return The key count.
     */
    public int getKeyCount(Comparable group) {
        if (group == null) {
            throw new IllegalArgumentException("Null 'group' argument.");
        }
        int result = 0;
        for (Comparable g : this.keyToGroupMap.values()) {
            if (group.equals(g)) {
                result++;
            }
        }
        return result;
    }

    /**
     * Tests the map for equality against an arbitrary object.
     *
     * @param obj  the object to test against (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof KeyToGroupMap)) {
            return false;
        }
        KeyToGroupMap that = (KeyToGroupMap) obj;
        if (!ObjectUtils.equal(this.defaultGroup, that.defaultGroup)) {
            return false;
        }
        if (!this.keyToGroupMap.equals(that.keyToGroupMap)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a clone of the map.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException  if there is a problem cloning the
     *                                     map.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        KeyToGroupMap result = (KeyToGroupMap) super.clone();
        result.defaultGroup
            = KeyToGroupMap.clone(this.defaultGroup);
        result.groups = (List<Comparable>) KeyToGroupMap.clone(this.groups);
        result.keyToGroupMap = KeyToGroupMap.clone(this.keyToGroupMap);
        return result;
    }

    /**
     * Attempts to clone the specified object using reflection.
     *
     * @param object  the object (<code>null</code> permitted).
     *
     * @return The cloned object, or the original object if cloning failed.
     */
    private static <T> T clone(T object) {
        if (object == null) {
            return null;
        }
        Class c = object.getClass();
        T result = null;
        try {
            Method m = c.getMethod("clone", (Class[]) null);
            if (Modifier.isPublic(m.getModifiers())) {
                try {
                    result = (T) m.invoke(object, (Object[]) null);
                }
                catch (InvocationTargetException e) {
                    throw new RuntimeException("Could not clone object", e);
                }
                catch (IllegalAccessException e) {
                    throw new RuntimeException("Could not clone object", e);
                }
            }
        }
        catch (NoSuchMethodException e) {
            result = object;
        }
        return result;
    }

    /**
     * Returns a clone of the list.
     *
     * @param list  the list.
     *
     * @return A clone of the list.
     *
     * @throws CloneNotSupportedException if the list could not be cloned.
     */
    private static <T> Collection<T> clone(Collection<T> list)
        throws CloneNotSupportedException {
        Collection<T> result = null;
        if (list != null) {
            try {
                List<T> clone = (List<T>) list.getClass().newInstance();
                for (T aList : list) {
                    clone.add(KeyToGroupMap.clone(aList));
                }
                result = clone;
            }
            catch (Exception e) {
                throw new CloneNotSupportedException("Exception.");
            }
        }
        return result;
    }

}

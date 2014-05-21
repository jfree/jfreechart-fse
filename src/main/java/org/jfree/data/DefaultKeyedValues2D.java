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
 * -------------------------
 * DefaultKeyedValues2D.java
 * -------------------------
 * (C) Copyright 2002-2012, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Andreas Schroeder;
 *
 * Changes
 * -------
 * 28-Oct-2002 : Version 1 (DG);
 * 21-Jan-2003 : Updated Javadocs (DG);
 * 13-Mar-2003 : Implemented Serializable (DG);
 * 18-Aug-2003 : Implemented Cloneable (DG);
 * 31-Mar-2004 : Made the rows optionally sortable by a flag (AS);
 * 01-Apr-2004 : Implemented remove method (AS);
 * 05-Apr-2004 : Added clear() method (DG);
 * 15-Sep-2004 : Fixed clone() method (DG);
 * 12-Jan-2005 : Fixed bug in getValue() method (DG);
 * 23-Mar-2005 : Implemented PublicCloneable (DG);
 * 09-Jun-2005 : Modified getValue() method to throw exception for unknown
 *               keys (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 18-Jan-2007 : Fixed bug in getValue() method (DG);
 * 30-Mar-2007 : Fixed bug 1690654, problem with removeValue() (DG);
 * 21-Nov-2007 : Fixed bug (1835955) in removeColumn(Comparable) method (DG);
 * 23-Nov-2007 : Added argument checks to removeRow(Comparable) to make it
 *               consistent with the removeRow(Comparable) method (DG);
 * 17-Jun-2012 : Removed JCommon dependencies (DG);
 *
 */

package org.jfree.data;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jfree.chart.util.ObjectUtils;
import org.jfree.chart.util.PublicCloneable;

/**
 * A data structure that stores zero, one or many values, where each value
 * is associated with two keys (a 'row' key and a 'column' key).  The keys
 * should be (a) instances of {@link Comparable} and (b) immutable.
 */
public class DefaultKeyedValues2D implements KeyedValues2D, PublicCloneable,
        Cloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -5514169970951994748L;

    /** The row keys. */
    private List<Comparable> rowKeys;

    /** The column keys. */
    private List<Comparable> columnKeys;

    /** The row data. */
    private List<DefaultKeyedValues> rows;

    /** If the row keys should be sorted by their comparable order. */
    private boolean sortRowKeys;

    /**
     * Creates a new instance (initially empty).
     */
    public DefaultKeyedValues2D() {
        this(false);
    }

    /**
     * Creates a new instance (initially empty).
     *
     * @param sortRowKeys  if the row keys should be sorted.
     */
    public DefaultKeyedValues2D(boolean sortRowKeys) {
        this.rowKeys = new java.util.ArrayList<Comparable>();
        this.columnKeys = new java.util.ArrayList<Comparable>();
        this.rows = new java.util.ArrayList<DefaultKeyedValues>();
        this.sortRowKeys = sortRowKeys;
    }

    /**
     * Returns the row count.
     *
     * @return The row count.
     *
     * @see #getColumnCount()
     */
    @Override
    public int getRowCount() {
        return this.rowKeys.size();
    }

    /**
     * Returns the column count.
     *
     * @return The column count.
     *
     * @see #getRowCount()
     */
    @Override
    public int getColumnCount() {
        return this.columnKeys.size();
    }

    /**
     * Returns the value for a given row and column.
     *
     * @param row  the row index.
     * @param column  the column index.
     *
     * @return The value.
     *
     * @see #getValue(Comparable, Comparable)
     */
    @Override
    public Number getValue(int row, int column) {
        Number result = null;
        DefaultKeyedValues rowData = this.rows.get(row);
        if (rowData != null) {
            Comparable columnKey = this.columnKeys.get(column);
            // the row may not have an entry for this key, in which case the
            // return value is null
            int index = rowData.getIndex(columnKey);
            if (index >= 0) {
                result = rowData.getValue(index);
            }
        }
        return result;
    }

    /**
     * Returns the key for a given row.
     *
     * @param row  the row index (in the range 0 to {@link #getRowCount()} - 1).
     *
     * @return The row key.
     *
     * @see #getRowIndex(Comparable)
     * @see #getColumnKey(int)
     */
    @Override
    public Comparable getRowKey(int row) {
        return this.rowKeys.get(row);
    }

    /**
     * Returns the row index for a given key.
     *
     * @param key  the key (<code>null</code> not permitted).
     *
     * @return The row index.
     *
     * @see #getRowKey(int)
     * @see #getColumnIndex(Comparable)
     */
    @Override
    public int getRowIndex(Comparable key) {
        if (key == null) {
            throw new IllegalArgumentException("Null 'key' argument.");
        }
        if (this.sortRowKeys) {
            return Collections.binarySearch(this.rowKeys, key, new Comparator<Comparable>() {
                @Override
                public int compare(Comparable o1, Comparable o2) {
                    return o1.compareTo(o2);
                }
            }); //FIXME MMC remove this comparator
        }
        else {
            return this.rowKeys.indexOf(key);
        }
    }

    /**
     * Returns the row keys in an unmodifiable list.
     *
     * @return The row keys.
     *
     * @see #getColumnKeys()
     */
    @Override
    public List<Comparable> getRowKeys() {
        return Collections.unmodifiableList(this.rowKeys);
    }

    /**
     * Returns the key for a given column.
     *
     * @param column  the column (in the range 0 to {@link #getColumnCount()}
     *     - 1).
     *
     * @return The key.
     *
     * @see #getColumnIndex(Comparable)
     * @see #getRowKey(int)
     */
    @Override
    public Comparable getColumnKey(int column) {
        return this.columnKeys.get(column);
    }

    /**
     * Returns the column index for a given key.
     *
     * @param key  the key (<code>null</code> not permitted).
     *
     * @return The column index.
     *
     * @see #getColumnKey(int)
     * @see #getRowIndex(Comparable)
     */
    @Override
    public int getColumnIndex(Comparable key) {
        if (key == null) {
            throw new IllegalArgumentException("Null 'key' argument.");
        }
        return this.columnKeys.indexOf(key);
    }

    /**
     * Returns the column keys in an unmodifiable list.
     *
     * @return The column keys.
     *
     * @see #getRowKeys()
     */
    @Override
    public List<Comparable> getColumnKeys() {
        return Collections.unmodifiableList(this.columnKeys);
    }

    /**
     * Returns the value for the given row and column keys.  This method will
     * throw an {@link UnknownKeyException} if either key is not defined in the
     * data structure.
     *
     * @param rowKey  the row key (<code>null</code> not permitted).
     * @param columnKey  the column key (<code>null</code> not permitted).
     *
     * @return The value (possibly <code>null</code>).
     *
     * @see #addValue(Number, Comparable, Comparable)
     * @see #removeValue(Comparable, Comparable)
     */
    @Override
    public Number getValue(Comparable rowKey, Comparable columnKey) {
        if (rowKey == null) {
            throw new IllegalArgumentException("Null 'rowKey' argument.");
        }
        if (columnKey == null) {
            throw new IllegalArgumentException("Null 'columnKey' argument.");
        }

        // check that the column key is defined in the 2D structure
        if (!(this.columnKeys.contains(columnKey))) {
            throw new UnknownKeyException("Unrecognised columnKey: "
                    + columnKey);
        }

        // now fetch the row data - need to bear in mind that the row
        // structure may not have an entry for the column key, but that we
        // have already checked that the key is valid for the 2D structure
        int row = getRowIndex(rowKey);
        if (row >= 0) {
            DefaultKeyedValues rowData
                = this.rows.get(row);
            int col = rowData.getIndex(columnKey);
            return (col >= 0 ? rowData.getValue(col) : null);
        }
        else {
            throw new UnknownKeyException("Unrecognised rowKey: " + rowKey);
        }
    }

    /**
     * Adds a value to the table.  Performs the same function as
     * #setValue(Number, Comparable, Comparable).
     *
     * @param value  the value (<code>null</code> permitted).
     * @param rowKey  the row key (<code>null</code> not permitted).
     * @param columnKey  the column key (<code>null</code> not permitted).
     *
     * @see #setValue(Number, Comparable, Comparable)
     * @see #removeValue(Comparable, Comparable)
     */
    public void addValue(Number value, Comparable rowKey,
                         Comparable columnKey) {
        // defer argument checking
        setValue(value, rowKey, columnKey);
    }

    /**
     * Adds or updates a value.
     *
     * @param value  the value (<code>null</code> permitted).
     * @param rowKey  the row key (<code>null</code> not permitted).
     * @param columnKey  the column key (<code>null</code> not permitted).
     *
     * @see #addValue(Number, Comparable, Comparable)
     * @see #removeValue(Comparable, Comparable)
     */
    public void setValue(Number value, Comparable rowKey,
                         Comparable columnKey) {

        DefaultKeyedValues row;
        int rowIndex = getRowIndex(rowKey);

        if (rowIndex >= 0) {
            row = this.rows.get(rowIndex);
        }
        else {
            row = new DefaultKeyedValues();
            if (this.sortRowKeys) {
                rowIndex = -rowIndex - 1;
                this.rowKeys.add(rowIndex, rowKey);
                this.rows.add(rowIndex, row);
            }
            else {
                this.rowKeys.add(rowKey);
                this.rows.add(row);
            }
        }
        row.setValue(columnKey, value);

        int columnIndex = this.columnKeys.indexOf(columnKey);
        if (columnIndex < 0) {
            this.columnKeys.add(columnKey);
        }
    }

    /**
     * Removes a value from the table by setting it to <code>null</code>.  If
     * all the values in the specified row and/or column are now
     * <code>null</code>, the row and/or column is removed from the table.
     *
     * @param rowKey  the row key (<code>null</code> not permitted).
     * @param columnKey  the column key (<code>null</code> not permitted).
     *
     * @see #addValue(Number, Comparable, Comparable)
     */
    public void removeValue(Comparable rowKey, Comparable columnKey) {
        setValue(null, rowKey, columnKey);

        // 1. check whether the row is now empty.
        boolean allNull = true;
        int rowIndex = getRowIndex(rowKey);
        DefaultKeyedValues row = this.rows.get(rowIndex);

        for (int item = 0, itemCount = row.getItemCount(); item < itemCount;
             item++) {
            if (row.getValue(item) != null) {
                allNull = false;
                break;
            }
        }

        if (allNull) {
            this.rowKeys.remove(rowIndex);
            this.rows.remove(rowIndex);
        }

        // 2. check whether the column is now empty.
        allNull = true;
        //int columnIndex = getColumnIndex(columnKey);

        for (DefaultKeyedValues rowItem : this.rows) {
            int columnIndex = rowItem.getIndex(columnKey);
            if (columnIndex >= 0 && rowItem.getValue(columnIndex) != null) {
                allNull = false;
                break;
            }
        }

        if (allNull) {
            for (DefaultKeyedValues rowItem : this.rows) {
                int columnIndex = rowItem.getIndex(columnKey);
                if (columnIndex >= 0) {
                    rowItem.removeValue(columnIndex);
                }
            }
            this.columnKeys.remove(columnKey);
        }
    }

    /**
     * Removes a row.
     *
     * @param rowIndex  the row index.
     *
     * @see #removeRow(Comparable)
     * @see #removeColumn(int)
     */
    public void removeRow(int rowIndex) {
        this.rowKeys.remove(rowIndex);
        this.rows.remove(rowIndex);
    }

    /**
     * Removes a row from the table.
     *
     * @param rowKey  the row key (<code>null</code> not permitted).
     *
     * @see #removeRow(int)
     * @see #removeColumn(Comparable)
     *
     * @throws UnknownKeyException if <code>rowKey</code> is not defined in the
     *         table.
     */
    public void removeRow(Comparable rowKey) {
        if (rowKey == null) {
            throw new IllegalArgumentException("Null 'rowKey' argument.");
        }
        int index = getRowIndex(rowKey);
        if (index >= 0) {
            removeRow(index);
        }
        else {
            throw new UnknownKeyException("Unknown key: " + rowKey);
        }
    }

    /**
     * Removes a column.
     *
     * @param columnIndex  the column index.
     *
     * @see #removeColumn(Comparable)
     * @see #removeRow(int)
     */
    public void removeColumn(int columnIndex) {
        Comparable columnKey = getColumnKey(columnIndex);
        removeColumn(columnKey);
    }

    /**
     * Removes a column from the table.
     *
     * @param columnKey  the column key (<code>null</code> not permitted).
     *
     * @throws UnknownKeyException if the table does not contain a column with
     *     the specified key.
     * @throws IllegalArgumentException if <code>columnKey</code> is
     *     <code>null</code>.
     *
     * @see #removeColumn(int)
     * @see #removeRow(Comparable)
     */
    public void removeColumn(Comparable columnKey) {
        if (columnKey == null) {
            throw new IllegalArgumentException("Null 'columnKey' argument.");
        }
        if (!this.columnKeys.contains(columnKey)) {
            throw new UnknownKeyException("Unknown key: " + columnKey);
        }
        for (DefaultKeyedValues rowData : this.rows) {
            int index = rowData.getIndex(columnKey);
            if (index >= 0) {
                rowData.removeValue(columnKey);
            }
        }
        this.columnKeys.remove(columnKey);
    }

    /**
     * Clears all the data and associated keys.
     */
    public void clear() {
        this.rowKeys.clear();
        this.columnKeys.clear();
        this.rows.clear();
    }

    /**
     * Tests if this object is equal to another.
     *
     * @param o  the other object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object o) {

        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }

        if (!(o instanceof KeyedValues2D)) {
            return false;
        }
        KeyedValues2D kv2D = (KeyedValues2D) o;
        if (!getRowKeys().equals(kv2D.getRowKeys())) {
            return false;
        }
        if (!getColumnKeys().equals(kv2D.getColumnKeys())) {
            return false;
        }
        int rowCount = getRowCount();
        if (rowCount != kv2D.getRowCount()) {
            return false;
        }

        int colCount = getColumnCount();
        if (colCount != kv2D.getColumnCount()) {
            return false;
        }

        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < colCount; c++) {
                Number v1 = getValue(r, c);
                Number v2 = kv2D.getValue(r, c);
                if (v1 == null) {
                    if (v2 != null) {
                        return false;
                    }
                }
                else {
                    if (!v1.equals(v2)) {
                        return false;
                    }
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
        int result;
        result = this.rowKeys.hashCode();
        result = 29 * result + this.columnKeys.hashCode();
        result = 29 * result + this.rows.hashCode();
        return result;
    }

    /**
     * Returns a clone.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException  this class will not throw this
     *         exception, but subclasses (if any) might.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        DefaultKeyedValues2D clone = (DefaultKeyedValues2D) super.clone();
        // for the keys, a shallow copy should be fine because keys
        // should be immutable...
        clone.columnKeys = new java.util.ArrayList<Comparable>(this.columnKeys);
        clone.rowKeys = new java.util.ArrayList<Comparable>(this.rowKeys);

        // but the row data requires a deep copy
        clone.rows = ObjectUtils.deepClone(this.rows);
        return clone;
    }

}

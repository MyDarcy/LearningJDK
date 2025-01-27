/*
 * Copyright (c) 1997, 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package java.util;

/**
 * This class provides a skeletal implementation of the {@code Collection}
 * interface, to minimize the effort required to implement this interface. <p>
 *
 * To implement an unmodifiable collection, the programmer needs only to
 * extend this class and provide implementations for the {@code iterator} and
 * {@code size} methods.  (The iterator returned by the {@code iterator}
 * method must implement {@code hasNext} and {@code next}.)<p>
 *
 * To implement a modifiable collection, the programmer must additionally
 * override this class's {@code add} method (which otherwise throws an
 * {@code UnsupportedOperationException}), and the iterator returned by the
 * {@code iterator} method must additionally implement its {@code remove}
 * method.<p>
 *
 * The programmer should generally provide a void (no argument) and
 * {@code Collection} constructor, as per the recommendation in the
 * {@code Collection} interface specification.<p>
 *
 * The documentation for each non-abstract method in this class describes its
 * implementation in detail.  Each of these methods may be overridden if
 * the collection being implemented admits a more efficient implementation.<p>
 *
 * This class is a member of the
 * <a href="{@docRoot}/java.base/java/util/package-summary.html#CollectionsFramework">
 * Java Collections Framework</a>.
 *
 * @author Josh Bloch
 * @author Neal Gafter
 * @see Collection
 * @since 1.2
 */
// 一元容器的抽象实现
/**
 * AbstractCollection基本上就是依赖iterator和size两个方法实现的抽象容器
 * 1. 实现不可变collection，extends AbstractCollection 并且 实现 iterator()方法和size()方法，iterator()方法
 *    返回的迭代器要实现 hasNext()和next()方法
 * 2. 实现可变collection，extends AbstractCollection 并且 额外实现 add()方法，iterator()方法要额外实现remove()方法
 */
public abstract class AbstractCollection<E> implements Collection<E> {
    
    /**
     * The maximum size of array to allocate.
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     */
    // 最大大小 （Max - 8）
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    
    
    
    /*▼ 构造器 ████████████████████████████████████████████████████████████████████████████████┓ */
    
    /**
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)
     */
    protected AbstractCollection() {
    }
    
    /*▲ 构造器 ████████████████████████████████████████████████████████████████████████████████┛ */
    
    
    
    /*▼ 存值 ████████████████████████████████████████████████████████████████████████████████┓ */
    
    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     * @throws IllegalStateException         {@inheritDoc}
     * @implSpec This implementation always throws an
     * {@code UnsupportedOperationException}.
     */
    // 向当前容器中添加元素
    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     * @throws IllegalStateException         {@inheritDoc}
     * @implSpec This implementation iterates over the specified collection, and adds
     * each object returned by the iterator to this collection, in turn.
     *
     * <p>Note that this implementation will throw an
     * {@code UnsupportedOperationException} unless {@code add} is
     * overridden (assuming the specified collection is non-empty).
     * @see #add(Object)
     */
    // 将指定容器中的元素添加到当前容器中
    // 添加成功，则修改成功
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        
        for(E e : c) {
            if(add(e)) {
                modified = true;
            }
        }
        
        return modified;
    }
    
    /*▲ 存值 ████████████████████████████████████████████████████████████████████████████████┛ */
    
    
    
    /*▼ 移除 ████████████████████████████████████████████████████████████████████████████████┓ */
    
    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @implSpec This implementation iterates over the collection looking for the
     * specified element.  If it finds the element, it removes the element
     * from the collection using the iterator's remove method.
     *
     * <p>Note that this implementation throws an
     * {@code UnsupportedOperationException} if the iterator returned by this
     * collection's iterator method does not implement the {@code remove}
     * method and this collection contains the specified object.
     */
    // 移除指定的元素，返回值指示是否移除成功
    // 通过迭代器实现，== null 和 not null需要分别处理，true代表移除了目标元素
    public boolean remove(Object o) {
        Iterator<E> it = iterator();
        if(o == null) {
            while(it.hasNext()) {
                if(it.next() == null) {
                    it.remove();
                    return true;
                }
            }
        } else {
            while(it.hasNext()) {
                if(o.equals(it.next())) {
                    it.remove();
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @implSpec This implementation iterates over this collection, checking each
     * element returned by the iterator in turn to see if it's contained
     * in the specified collection.  If it's so contained, it's removed from
     * this collection with the iterator's {@code remove} method.
     *
     * <p>Note that this implementation will throw an
     * {@code UnsupportedOperationException} if the iterator returned by the
     * {@code iterator} method does not implement the {@code remove} method
     * and this collection contains one or more elements in common with the
     * specified collection.
     * @see #remove(Object)
     * @see #contains(Object)
     */
    // (匹配则移除)移除当前容器中所有与给定容器中的元素匹配的元素
    // a-b，依赖iter实现，如果移除成功过，返回true
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c); // throw NPE if c is null
        boolean modified = false;
        Iterator<?> it = iterator();
        while(it.hasNext()) {
            if(c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @implSpec This implementation iterates over this collection, checking each
     * element returned by the iterator in turn to see if it's contained
     * in the specified collection.  If it's not so contained, it's removed
     * from this collection with the iterator's {@code remove} method.
     *
     * <p>Note that this implementation will throw an
     * {@code UnsupportedOperationException} if the iterator returned by the
     * {@code iterator} method does not implement the {@code remove} method
     * and this collection contains one or more elements not present in the
     * specified collection.
     * @see #remove(Object)
     * @see #contains(Object)
     */
    // (不匹配则移除)移除当前容器中所有与给定容器中的元素不匹配的元素
    // a&b, 依赖iter实现，如果移除成功过，返回true
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<E> it = iterator();
        while(it.hasNext()) {
            if(!c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @implSpec This implementation iterates over this collection, removing each
     * element using the {@code Iterator.remove} operation.  Most
     * implementations will probably choose to override this method for
     * efficiency.
     *
     * <p>Note that this implementation will throw an
     * {@code UnsupportedOperationException} if the iterator returned by this
     * collection's {@code iterator} method does not implement the
     * {@code remove} method and this collection is non-empty.
     */
    // 清空当前容器中所有元素
    // 依赖iter实现
    public void clear() {
        Iterator<E> it = iterator();
        while(it.hasNext()) {
            it.next();
            it.remove();
        }
    }
    
    /*▲ 移除 ████████████████████████████████████████████████████████████████████████████████┛ */
    
    
    
    /*▼ 包含查询 ████████████████████████████████████████████████████████████████████████████████┓ */
    
    /**
     * {@inheritDoc}
     *
     * @throws ClassCastException   {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @implSpec This implementation iterates over the elements in the collection,
     * checking each element in turn for equality with the specified element.
     */
    // 判断当前容器中是否包含元素o
    // 和remove类似，需要分为null何非null分别处理
    public boolean contains(Object o) {
        Iterator<E> it = iterator();
        if(o == null) {
            while(it.hasNext()) {
                if(it.next() == null) {
                    return true;
                }
            }
        } else {
            while(it.hasNext()) {
                if(o.equals(it.next())) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * {@inheritDoc}
     *
     * @throws ClassCastException   {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @implSpec This implementation iterates over the specified collection,
     * checking each element returned by the iterator in turn to see
     * if it's contained in this collection.  If all elements are so
     * contained {@code true} is returned, otherwise {@code false}.
     * @see #contains(Object)
     */
    // 判读指定容器中的元素是否都包含在当前容器中
    // 任一不包含，返回false
    public boolean containsAll(Collection<?> c) {
        for(Object e : c) {
            if(!contains(e)) {
                return false;
            }
        }
        
        return true;
    }
    
    /*▲ 包含查询 ████████████████████████████████████████████████████████████████████████████████┛ */
    
    
    
    /*▼ 视图 ████████████████████████████████████████████████████████████████████████████████┓ */
    
    /**
     * {@inheritDoc}
     *
     * @implSpec This implementation returns an array containing all the elements
     * returned by this collection's iterator, in the same order, stored in
     * consecutive elements of the array, starting with index {@code 0}.
     * The length of the returned array is equal to the number of elements
     * returned by the iterator, even if the size of this collection changes
     * during iteration, as might happen if the collection permits
     * concurrent modification during iteration.  The {@code size} method is
     * called only as an optimization hint; the correct result is returned
     * even if the iterator returns a different number of elements.
     *
     * <p>This method is equivalent to:
     *
     * <pre> {@code
     * List<E> list = new ArrayList<E>(size());
     * for (E e : this)
     *     list.add(e);
     * return list.toArray();
     * }</pre>
     */
    // 以数组形式返回当前顺序表
    public Object[] toArray() {
        // Estimate size of array; be prepared to see more or fewer elements
        Object[] r = new Object[size()];
        Iterator<E> it = iterator();
        for(int i = 0; i<r.length; i++) {
            if(!it.hasNext()) {
                // fewer elements than expected
                return Arrays.copyOf(r, i); // 没有size个元素，copy已有的元素
            }
            r[i] = it.next();
        }
    
        return it.hasNext() ? finishToArray(r, it) : r;
    }
    
    /**
     * {@inheritDoc}
     *
     * @throws ArrayStoreException  {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @implSpec This implementation returns an array containing all the elements
     * returned by this collection's iterator in the same order, stored in
     * consecutive elements of the array, starting with index {@code 0}.
     * If the number of elements returned by the iterator is too large to
     * fit into the specified array, then the elements are returned in a
     * newly allocated array with length equal to the number of elements
     * returned by the iterator, even if the size of this collection
     * changes during iteration, as might happen if the collection permits
     * concurrent modification during iteration.  The {@code size} method is
     * called only as an optimization hint; the correct result is returned
     * even if the iterator returns a different number of elements.
     *
     * <p>This method is equivalent to:
     *
     * <pre> {@code
     * List<E> list = new ArrayList<E>(size());
     * for (E e : this)
     *     list.add(e);
     * return list.toArray(a);
     * }</pre>
     */
    // 将当前顺序表中的元素存入数组a后返回，需要将链表中的元素转换为T类型
    // 如果传入数组size < 当前list.size(), 那么利用 Array.newInstance() 创建一个新数组，再进行元素复制 （迭代器可能返回更多的元素）
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        // Estimate size of array; be prepared to see more or fewer elements
        int size = size();
        T[] r = a.length >= size ? a : (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        Iterator<E> it = iterator();
        
        for(int i = 0; i<r.length; i++) {
            // 如果已经不存在下个元素了
            if(!it.hasNext()) { // fewer elements than expected
                if(a == r) {
                    r[i] = null; // null-terminate
                } else if(a.length<i) {
                    return Arrays.copyOf(r, i);
                } else {
                    System.arraycopy(r, 0, a, 0, i);
                    if(a.length>i) {
                        a[i] = null;
                    }
                }
                return a;
            }
    
            r[i] = (T) it.next();
        }
    
        // more elements than expected
        return it.hasNext() ? finishToArray(r, it) : r;
    }
    
    /*▲ 视图 ████████████████████████████████████████████████████████████████████████████████┛ */
    
    
    
    /*▼ 迭代 ████████████████████████████████████████████████████████████████████████████████┓ */
    
    /**
     * Returns an iterator over the elements contained in this collection.
     *
     * @return an iterator over the elements contained in this collection
     */
    // 返回当前容器的迭代器
    public abstract Iterator<E> iterator();
    
    /*▲ 迭代 ████████████████████████████████████████████████████████████████████████████████┛ */
    
    
    
    /*▼ 杂项 ████████████████████████████████████████████████████████████████████████████████┓ */
    
    // 返回当前容器的元素数量
    public abstract int size();
    
    /**
     * {@inheritDoc}
     *
     * @implSpec This implementation returns {@code size() == 0}.
     */
    // 判断当前容器是否为空
    public boolean isEmpty() {
        return size() == 0;
    }
    
    /*▲ 杂项 ████████████████████████████████████████████████████████████████████████████████┛ */
    
    
    
    /**
     * Reallocates the array being used within toArray when the iterator
     * returned more elements than expected, and finishes filling it from
     * the iterator.
     *
     * @param r  the array, replete with previously stored elements
     * @param it the in-progress iterator over this collection
     *
     * @return array containing the elements in the given array, plus any
     * further elements returned by the iterator, trimmed to size
     */
    // 
    @SuppressWarnings("unchecked")
    private static <T> T[] finishToArray(T[] r, Iterator<?> it) {
        int i = r.length;
    
        while(it.hasNext()) {
            int cap = r.length;
            if(i == cap) {
                int newCap = cap + (cap >> 1) + 1; // 1.5N + 1
                // overflow-conscious code
                if(newCap - MAX_ARRAY_SIZE>0) {
                    newCap = hugeCapacity(cap + 1);
                }
                r = Arrays.copyOf(r, newCap);
            }
        
            r[i++] = (T) it.next();
        }
    
        // trim if overallocated
        return (i == r.length) ? r : Arrays.copyOf(r, i);
    }
    
    private static int hugeCapacity(int minCapacity) {
        if(minCapacity<0) {
            throw new OutOfMemoryError("Required array size too large");
        }
        return (minCapacity>MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }
    
    
    
    /**
     * Returns a string representation of this collection.  The string
     * representation consists of a list of the collection's elements in the
     * order they are returned by its iterator, enclosed in square brackets
     * ({@code "[]"}).  Adjacent elements are separated by the characters
     * {@code ", "} (comma and space).  Elements are converted to strings as
     * by {@link String#valueOf(Object)}.
     *
     * @return a string representation of this collection
     */
    public String toString() {
        Iterator<E> it = iterator();
        if(!it.hasNext())
            return "[]";
        
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for(; ; ) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if(!it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }
    
}

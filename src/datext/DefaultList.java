/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datext;

import datext.util.BinaryConverter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author cybergnome
 */
public class DefaultList extends DaTextList{
	
	/** This is the internal List data structure for DaTextLists. If you 
	 * do not use this object, you will need to override all of the 
	 * java.util.List implementation methods.*/
	protected ArrayList<DaTextVariable> listData = new ArrayList<>();
	
	
	
	///// LIST METHODS /////
	// <editor-fold defaultstate="collapsed" desc="Implementation of java.util.List">
	@Override
	public int size() {
		readLock.lock();
		try {
			return listData.size();
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public boolean isEmpty() {
		readLock.lock();
		try {
			return listData.isEmpty();
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public boolean contains(Object o) {
		readLock.lock();
		try {
			return listData.contains(o);
		} finally {
			readLock.unlock();
		}
	}
	/**
	 * WARNING: THIS METHOD IS NOT THREAD SAFE!!!
	 * @return 
	 */
	@Override
	public Iterator iterator() {
		return listData.iterator();
	}

	@Override
	public DaTextVariable[] toArray() {
		readLock.lock();
		try {
			return listData.toArray(new DaTextVariable[listData.size()]);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public Object[] toArray(Object[] a) {
		readLock.lock();
		try {
			return listData.toArray(a);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public boolean add(DaTextVariable e) {
		writeLock.lock();
		try {
			return listData.add(e);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean remove(Object o) {
		writeLock.lock();
		try {
			return listData.remove(o);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean containsAll(Collection c) {
		readLock.lock();
		try {
			return listData.containsAll(c);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public boolean addAll(Collection c) {
		writeLock.lock();
		try {
			return listData.addAll(c);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean addAll(int index, Collection c) {
		writeLock.lock();
		try {
			return listData.addAll(index, c);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean removeAll(Collection c) {
		writeLock.lock();
		try {
			return listData.removeAll(c);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean retainAll(Collection c) {
		writeLock.lock();
		try {
			return listData.retainAll(c);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public void clear() {
		writeLock.lock();
		try {
			listData.clear();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public DaTextVariable get(int index) {
		readLock.lock();
		try {
			return listData.get(index);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public DaTextVariable set(int index, DaTextVariable element) {
		writeLock.lock();
		try {
			return listData.set(index, element);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public void add(int index, DaTextVariable element) {
		writeLock.lock();
		try {
			listData.add(index, element);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public DaTextVariable remove(int index) {
		writeLock.lock();
		try {
			return listData.remove(index);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public int indexOf(Object o) {
		readLock.lock();
		try {
			return listData.indexOf(o);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public int lastIndexOf(Object o) {
		readLock.lock();
		try {
			return listData.lastIndexOf(o);
		} finally {
			readLock.unlock();
		}
	}
	/**
	 * WARNING: THIS METHOD IS NOT THREAD SAFE!!!
	 * @return 
	 */
	@Override
	public ListIterator listIterator() {
		return listData.listIterator();
	}

	/**
	 * WARNING: THIS METHOD IS NOT THREAD SAFE!!!
	 * @param index
	 * @return 
	 */
	@Override
	public ListIterator listIterator(int index) {
		return listData.listIterator(index);
	}

	@Override
	public List subList(int fromIndex, int toIndex) {
		readLock.lock();
		try {
			return listData.subList(fromIndex, toIndex);
		} finally {
			readLock.unlock();
		}
		
	}
	// </editor-fold>
	///// END OF LIST METHODS /////

	@Override
	public void add(String value, String annotation) {
		this.add(new DefaultVariable(value,annotation));
	}

	@Override
	public void add(byte[] value, String annotation) {
		this.add(new DefaultVariable(BinaryConverter.bytesToString(value),annotation));
	}

	@Override
	public void add(double value, String annotation) {
		this.add(new DefaultVariable(formatNumber(value),annotation));
	}

	@Override
	public void add(long value, String annotation) {
		this.add(new DefaultVariable(((Number)value).toString(),annotation));
	}

	@Override
	public void add(int value, String annotation) {
		this.add(new DefaultVariable(((Number)value).toString(),annotation));
	}

	@Override
	public String asText() {
		// TODO: serialize
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	
//  TODO document this method
	@Override
	public void set(DaTextList value) {
		writeLock.lock();
		try{
			this.clear();
			this.addAll(value);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public DaTextVariable clone() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}

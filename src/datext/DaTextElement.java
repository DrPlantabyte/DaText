/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datext;

import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * This is the abstract superclass for all DaText implementations. It provides 
 * get and set methods for all possible subtypes, even if the underlying data 
 * doesn't match that type (throwing an UnsupportedOperationException). This is 
 * to simplify the use of this class instead of casting every element to the 
 * appropriate subtype.
 * @author Christopher Collin Hall
 */
public abstract class DaTextElement implements Cloneable{
	/** Lock to synchronize reading and writing */
	protected final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	/** All getter methods must use this lock (or their own Read/Write Lock) to be thread-safe */
	protected final ReadLock readLock = readWriteLock.readLock();
	/** All setter methods must use this lock (or their own Read/Write Lock) to be thread-safe */
	protected final WriteLock writeLock = readWriteLock.writeLock();
	
	/** Points to the root element of the DaText data tree. If this is the 
	 * root element, then this is a self-pointer. */
	private DaTextElement root = null;
	/** Points to the DaText above this one. If this is the root element, 
	 * then this will be null. */
	private DaTextElement parent = null;
	/** Annotation content */
	private String annotation = null;
	/** Locale for parsing data, default to English (programming standard) */
	private java.util.Locale locale = java.util.Locale.ENGLISH;
	/** Name of this element (null if root) */
	private String name = null;
	
	
	/**
	 * Returns the annotation for this variable.
	 * <p/>This method is thread-safe.
	 * @return Returns this element's annotation coment, or null if there is 
	 * none.
	 */
	public String getAnnotation(){
		readLock.lock();
		try{
			return annotation;
		}finally {
			readLock.unlock();
		}
	}
	/**
	 * Sets the annotation comment for this element.
	 * <p/>This method is thread-safe.
	 * @param content The comment to go with this element.
	 */
	public void setAnnotation(String content){
		writeLock.lock();
		try{
			annotation = content;
		}finally {
			writeLock.unlock();
		}
	}
	/**
	 * Gets the variable name of this element.
	 * <p/>This method is thread-safe.
	 * @return Name of this element, or null if this is a root element.
	 */
	public String getName(){
		readLock.lock();
		try{
			return name;
		}finally {
			readLock.unlock();
		}
	}
	/**
	 * Sets the variable name of this element.
	 * <p/>This method is thread-safe.
	 * @param newName Name of this element, or null if this is a root element.
	 */
	public void setName(String newName){
		writeLock.lock();
		try{
			name = newName;
		}finally {
			writeLock.unlock();
		}
	}
	
	/**
	 * Gets the locale being used by this element for parsing numbers.
	 * <p/>This method is thread-safe.
	 * @return This element's locale
	 */
	public java.util.Locale getLocale(){
		readLock.lock();
		try{
			return locale;
		}finally {
			readLock.unlock();
		}
	}
	/**
	 * Sets the locale used by this element (but not necessarily child 
	 * elements) for parsing numbers.
	 * <p/>This method is thread-safe.
	 * @param newLocale The new locale to use.
	 */
	public void setLocale(java.util.Locale newLocale){
		writeLock.lock();
		try{
			locale = newLocale;
		}finally {
			writeLock.unlock();
		}
	}
	/**
	 * Sets the locale used by this element and all child 
	 * elements for parsing numbers.
	 * <p/>This method is thread-safe.
	 * @param newLocale The new locale to use.
	 */
	public void setLocaleForAllChildren(java.util.Locale newLocale){
		writeLock.lock();
		try{
			locale = newLocale;
			for(DaTextElement child : getChildElements()){
				child.setLocaleForAllChildren(newLocale);
			}
		}finally {
			writeLock.unlock();
		}
	}
	/**
	 * Sets the locale used by all children of the same root element as 
	 * this element's root.
	 * <p/>This method is thread-safe.
	 * @param newLocale The new locale to use.
	 */
	public void setLocaleForWholeDocument(java.util.Locale newLocale){
		writeLock.lock();
		try{
			if(getRoot() != this){
				getRoot().setLocaleForAllChildren(newLocale);
			} else {
				this.setLocaleForAllChildren(newLocale);
			}
		}finally {
			writeLock.unlock();
		}
	}
	
	/**
	 * Gets the root (aka Document) element that this element belongs to.
	 * <p/>This method is thread-safe.
	 * @return The top of the DaText tree, or this element if it is the root.
	 */
	public DaTextElement getRoot(){
		readLock.lock();
		try{
			return root;
		}finally {
			readLock.unlock();
		}
	}
	/** 
	 * Gets the element that owns this element.
	 * <p/>This method is thread-safe.
	 * @return The element above this one, or null if this element is the root.
	 */
	public DaTextElement getParent(){
		readLock.lock();
		try{
			return parent;
		}finally {
			readLock.unlock();
		}
	}
	/**
	 * Sets the root element. This method alone is not sufficient to transfer 
	 * an element from one tree to another.
	 * <p/>This method is thread-safe.
	 * @param newRoot New root element
	 */
	protected void setRoot(DaTextElement newRoot){
		writeLock.lock();
		try{
			root = newRoot;
		}finally {
			writeLock.unlock();
		}
	}
	/**
	 * Sets the owning element. This method alone is not sufficient to transfer 
	 * an element from one owner to another.
	 * <p/>This method is thread-safe.
	 * @param newParent New owning element
	 */
	protected void setParent(DaTextElement newParent){
		writeLock.lock();
		try{
			parent = newParent;
		}finally {
			writeLock.unlock();
		}
	}
	/**
	 * Gets the child element by the given variable name, if it exists. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextElement docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>long lot = docRoot.getElement("properties").getLong("lotNumber");</b><br/>
	 * System.out.println("Analyzing lot #" + lot);<br/>
	 * </code>
	 * @param key Variable name that identifies the element being referenced.
	 * @return Returns a DaTextElement representing the indicated element, 
	 * or <code>null</code> if no such element exists.
	 * @throws UnsupportedOperationException Thrown if the indicated element is not 
	 * an element type.
	 */
	public abstract DaTextElement getElement(String key) throws UnsupportedOperationException;
	/**
	 * Gets a field by the given field name, if it exists, 
	 * and treats it as a list. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextElement docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>List&lt;String&gt; colors = docRoot.getList("availableColors");</b><br/>
	 * System.out.println("Colors:");<br/>
	 * for(String color : colors){<br/>
	 * &nbsp;&nbsp;System.out.println("\t" + color);<br/>
	 * }<br/>
	 * </code>
	 * @param key Variable name that identifies the element being referenced.
	 * @return Returns a list of Strings representing the indicated element, 
	 * or <code>null</code> if no such element exists.
	 * @throws UnsupportedOperationException Thrown if the indicated element is not 
	 * a list type.
	 */
	public abstract List<String> getList(String key) throws UnsupportedOperationException;
	/**
	 * Gets a field by the given field name, if it exists. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextElement docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>String flv = docRoot.getElement("properties").getText("flavor");</b><br/>
	 * System.out.println("Flavor profile: " + flv);<br/>
	 * </code>
	 * @param key Variable name that identifies the element being referenced.
	 * @return Returns the text value of the indicated field, 
	 * or <code>null</code> if no such element exists.
	 * @throws UnsupportedOperationException Thrown if the indicated element is not 
	 * a element type.
	 */
	public abstract String getText(String key) throws UnsupportedOperationException;
	/**
	 * Gets a field by the given field name, if it exists, and parses it 
	 * as an integer. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextElement docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>int n = docRoot.getInt("numJellyBeans");</b><br/>
	 * System.out.println("There are " + n + " jelly beans");<br/>
	 * </code>
	 * @param key Variable name that identifies the element being referenced.
	 * @return Returns the text value of the indicated field, 
	 * or <code>null</code> if no such element exists.
	 * @throws UnsupportedOperationException Thrown if the indicated element is not 
	 * a element type.
	 * @throws NumberFormatException Thrown if the field could not be parsed as 
	 * a number (may be due to locale mismatch)
	 */
	public abstract int getInt(String key) throws UnsupportedOperationException, NumberFormatException;
	/**
	 * Gets a field by the given field name, if it exists, and parses it 
	 * as an integer.
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextElement docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>long lot = docRoot.getElement("properties").getLong("lotNumber");</b><br/>
	 * System.out.println("Analyzing lot #" + lot);<br/>
	 * </code>
	 * @param key Variable name that identifies the element being referenced.
	 * @return Returns a DaTextElement representing the indicated element, 
	 * or <code>null</code> if no such element exists.
	 * @throws UnsupportedOperationException Thrown if the indicated element is not 
	 * an element type.
	 * @throws NumberFormatException Thrown if the field could not be parsed as 
	 * a number (may be due to locale mismatch)
	 */
	public abstract long getLong(String key) throws UnsupportedOperationException, NumberFormatException;
	/**
	 * Gets a field by the given field name, if it exists, and parses it 
	 * as a decimal number. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextElement docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>Double grams = docRoot.getNumber("weight");</b><br/>
	 * System.out.println("Mass: " + grams + "g");<br/>
	 * </code>
	 * @param key Variable name that identifies the element being referenced.
	 * @return Returns the text value of the indicated field, 
	 * or <code>null</code> if no such element exists.
	 * @throws UnsupportedOperationException Thrown if the indicated element is not 
	 * a element type.
	 * @throws NumberFormatException Thrown if the field could not be parsed as 
	 * a number (may be due to locale mismatch)
	 */
	public abstract Double getNumber(String key) throws UnsupportedOperationException, NumberFormatException;
	/**
	 * Gets a field by the given field name, if it exists, and parses it 
	 * as an array of bytes. The bytes are given as a stream of hexadecimal 
	 * numbers read 2 at a time (whitespace is optional).
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextElement docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>long lot = docRoot.getElement("properties").getLong("lotNumber");</b><br/>
	 * System.out.println("Analyzing lot #" + lot);<br/>
	 * </code>
	 * @param key Variable name that identifies the element being referenced.
	 * @return Returns a DaTextElement representing the indicated element, 
	 * or <code>null</code> if no such element exists.
	 * @throws UnsupportedOperationException Thrown if the indicated element is not 
	 * an element type.
	 * @throws NumberFormatException Thrown if the hexadecimal stream is not 
	 * well-formatted
	 */
	public abstract byte[] getBinary(String key) throws UnsupportedOperationException, NumberFormatException;
	
	/**
	 * Gets the child element by the given element name, if it exists, 
	 * or adds a new element if it doesn't already exist. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextElement docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>String title = docRoot.getOrSetElement("label").getOrSetText("title", "Magical Beans");</b><br/>
	 * System.out.println("Title: " + title);<br/>
	 * </code>
	 * @param key Variable name that identifies the element being referenced.
	 * @return Returns the element, if it exists, or the newly created 
	 * one (with default value) it it didn't exist.
	 * @throws UnsupportedOperationException Thrown if the indicated element exists 
	 * and is not an element type.
	 */
	public abstract DaTextElement getOrSetElement(String key) throws UnsupportedOperationException;
	/**
	 * Gets a field by the given field name, if it exists, 
	 * or adds a new one if it doesn't already exist. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextElement docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>List&lt;String&gt; reviews = docRoot.getOrSetElement("label").getOrSetList("reviews", defaultReviews);</b><br/>
	 * for(String s : reviews){<br/>
	 * &nbsp;&nbsp;System.out.println(s + "!");<br/>
	 * }<br/>
	 * </code>
	 * @param key Variable name that identifies the element being referenced.
	 * @param defaultValue The value to initialize the field to if is doesn't 
	 * already exist (ignored if it does already exist).
	 * @return Returns the referenced field, if it exists, or the newly created 
	 * one (with the provided default value) it it didn't exist.
	 * @throws UnsupportedOperationException Thrown if the indicated element exists 
	 * but is not the correct type.
	 */
	public abstract List<String> getOrSetList(String key, List<String> defaultValue) throws UnsupportedOperationException;
	/**
	 * Gets a field by the given field name, if it exists, 
	 * or adds a new one if it doesn't already exist. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextElement docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>String title = docRoot.getOrSetElement("label").getOrSetText("title", "Magical Beans");</b><br/>
	 * System.out.println("Title: " + title);<br/>
	 * </code>
	 * @param key Variable name that identifies the element being referenced.
	 * @param defaultValue The value to initialize the field to if is doesn't 
	 * already exist (ignored if it does already exist).
	 * @return Returns the referenced field, if it exists, or the newly created 
	 * one (with the provided default value) it it didn't exist.
	 * @throws UnsupportedOperationException Thrown if the indicated element exists 
	 * but is not the correct type.
	 */
	public abstract String getOrSetText(String key, String defaultValue) throws UnsupportedOperationException;
	/**
	 * Gets a field by the given field name, if it exists, 
	 * or adds a new one if it doesn't already exist. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextElement docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>int revision = docRoot.getOrSetElement("label").getOrSetInt("version", 1);</b><br/>
	 * System.out.println("Revision " + revision);<br/>
	 * </code>
	 * @param key Variable name that identifies the element being referenced.
	 * @param defaultValue The value to initialize the field to if is doesn't 
	 * already exist (ignored if it does already exist).
	 * @return Returns the referenced field, if it exists, or the newly created 
	 * one (with the provided default value) it it didn't exist.
	 * @throws UnsupportedOperationException Thrown if the indicated field exists 
	 * but is not the correct type.
	 * @throws NumberFormatException Thrown if the indicated field exists 
	 * but is not correctly formatted.
	 */
	public abstract int getOrSetInt(String key, int defaultValue) throws UnsupportedOperationException, NumberFormatException;
	/**
	 * Gets a field by the given field name, if it exists, 
	 * or adds a new one if it doesn't already exist. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextElement docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>long time = docRoot.getOrSetElement("label").getOrSetInt("timestamp", System.currentTimeMillis());</b><br/>
	 * System.out.println("Last edited on " + (new Date(time)).toString());<br/>
	 * </code>
	 * @param key Variable name that identifies the element being referenced.
	 * @param defaultValue The value to initialize the field to if is doesn't 
	 * already exist (ignored if it does already exist).
	 * @return Returns the referenced field, if it exists, or the newly created 
	 * one (with the provided default value) it it didn't exist.
	 * @throws UnsupportedOperationException Thrown if the indicated field exists 
	 * but is not the correct type.
	 * @throws NumberFormatException Thrown if the indicated field exists 
	 * but is not correctly formatted.
	 */
	public abstract long getOrSetLong(String key, long defaultValue) throws UnsupportedOperationException, NumberFormatException;
	/**
	 * Gets a field by the given field name, if it exists, 
	 * or adds a new one if it doesn't already exist. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextElement docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>Double aspect = docRoot.getOrSetElement("label").getOrSetNumber("aspect ratio", 1.5);</b><br/>
	 * System.out.println("Aspect ratio =  " + aspect);<br/>
	 * </code>
	 * @param key Variable name that identifies the element being referenced.
	 * @param defaultValue The value to initialize the field to if is doesn't 
	 * already exist (ignored if it does already exist).
	 * @return Returns the referenced field, if it exists, or the newly created 
	 * one (with the provided default value) it it didn't exist.
	 * @throws UnsupportedOperationException Thrown if the indicated field exists 
	 * but is not the correct type.
	 * @throws NumberFormatException Thrown if the indicated field exists 
	 * but is not correctly formatted.
	 */
	public abstract Double getOrSetNumber(String key, Number defaultValue) throws UnsupportedOperationException, NumberFormatException;
	/**
	 * Gets a field by the given field name, if it exists, 
	 * or adds a new one if it doesn't already exist. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextElement docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * ByteArrayOutputStream stream = new ByteArrayOutputStream();<br/>
	 * ImageIO.write(newLabelImg, "png", stream);<br/>
	 * <b>byte[] imageStream = docRoot.getOrSetElement("label").getOrSetBinary("icon", stream.toByteArray());</b><br/>
	 * </code>
	 * @param key Variable name that identifies the element being referenced.
	 * @param defaultValue The value to initialize the field to if is doesn't 
	 * already exist (ignored if it does already exist).
	 * @return Returns the referenced field, if it exists, or the newly created 
	 * one (with the provided default value) it it didn't exist.
	 * @throws UnsupportedOperationException Thrown if the indicated field exists 
	 * but is not the correct type.
	 * @throws NumberFormatException Thrown if the indicated field exists 
	 * but is not correctly formatted.
	 */
	public abstract byte[] getOrSetBinary(String key, byte[] defaultValue) throws UnsupportedOperationException, NumberFormatException;
	
	/**
	 * Adds a new child element to this element, overwriting any 
	 * pre-existing field or element with the same name.
	 * @param key Name of the element for use in the corresponding get method.
	 */
	public abstract void putElement(String key);
	/**
	 * Clones the other element and adds it to this element.
	 * @param key Name of the element for use in the corresponding get method.
	 * @param other Element to clone and add.
	 */
	public abstract void putCopyOf(String key, DaTextElement other);
	/**
	 * Stores a value in this element, overwriting any previous element 
	 * or value that may have previously been stored under the same 
	 * field name.
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 */
	public abstract void putValue(String key, List value);
	/**
	 * Stores a value in this element, overwriting any previous element 
	 * or value that may have previously been stored under the same 
	 * field name.
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 */
	public abstract void putValue(String key, Number[] value);
	/**
	 * Stores a value in this element, overwriting any previous element 
	 * or value that may have previously been stored under the same 
	 * field name.
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 */
	public abstract void putValue(String key, String value);
	/**
	 * Stores a value in this element, overwriting any previous element 
	 * or value that may have previously been stored under the same 
	 * field name.
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 */
	public abstract void putValue(String key, Number value);
	/**
	 * Stores a value in this element, overwriting any previous element 
	 * or value that may have previously been stored under the same 
	 * field name.
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 */
	public abstract void putValue(String key, byte[] value);
	
	/**
	 * Deletes a stored field or element.
	 * @param key Name of the field or element to remove.
	 */
	public abstract void remove(String key);
	/**
	 * Enumerates all child elements of this element. If there are no 
	 * child elements, an empty list must be returned.
	 * @return A list of all child elements (list will have size of 0 if there 
	 * are no child elements). Fields are not included in this list.
	 */
	public abstract List<DaTextElement> getChildElements();
	/**
	 * Implementations must override the <code>clonde()</code> method 
	 * to perform a deep copy, treating the copy at the root of a new 
	 * tree.
	 * @return A copy of this element that is an independant root whose 
	 * children elements and fields are all copies of the those of this element.
	 */
	@Override public abstract DaTextElement clone();
}

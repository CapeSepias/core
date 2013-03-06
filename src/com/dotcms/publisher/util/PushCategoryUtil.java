package com.dotcms.publisher.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dotcms.publisher.pusher.wrapper.CategoryWrapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class PushCategoryUtil {
	
	private Collection<File> categories;
	private XStream xstream;
	
	public PushCategoryUtil(Collection<File> categories){
		this.categories = categories;
		xstream=new XStream(new DomDriver());
	}
	
	/**
	 * Find all top level categories. Returns all the Wrapper.
	 * 
	 * Mar 6, 2013 - 9:53:45 AM
	 */
	public List<CategoryWrapper> findTopLevelWrappers() throws FileNotFoundException {
		List<CategoryWrapper> topLevels = new ArrayList<CategoryWrapper>();
		for(File category : categories){
			if(category.isDirectory()) continue;
			CategoryWrapper wrapper = getCategoryWrapperFromFile(category);
			if(wrapper.isTopLevel())
				topLevels.add(wrapper);
		}
		return topLevels;
	}
	
	public CategoryWrapper getCategoryWrapperFromInode(String inode) throws FileNotFoundException {
		CategoryWrapper wrapper = null;
		for(File category : categories){
			if(category.isDirectory()) continue;
			wrapper = getCategoryWrapperFromFile(category);
			if(inode.equals(wrapper.getCategory().getInode()))
				return wrapper;
		}
		return wrapper;
	}
	
	private CategoryWrapper getCategoryWrapperFromFile(File category) throws FileNotFoundException {
		return (CategoryWrapper)xstream.fromXML(new FileInputStream(category));
	}
}

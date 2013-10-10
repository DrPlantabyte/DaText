/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datext.util;

import datext.*;
import java.util.*;

/**
 *
 * @author Christopher Collin Hall
 */
public class ListHandler {
    
    /**
     * 
     * @param listContent The text content of a list (including [ and ])
     * @return 
     */
    public static List<DaTextVariable> parseListString(String listString){
		String listContent = listString.substring(listString.indexOf("[")+1,listString.lastIndexOf("]"));
		List<DaTextVariable> list = new ArrayList<>();
		int start = 0;
		int end;
		int braceDepth = 0; // used for keeping track of nested objects
		int bracketDepth = 0; // used for keeping track of nested lists
		while(start < listContent.length()){
			end = start;
			while(end < listContent.length()){
				if(end > 0 && listContent.charAt(end-1) == '\\'){
					end++;
					continue;
				}
				// TODO: skip nested objects and lists
				// skip nested objects/lists
				if(listContent.charAt(end) == '{' ){
					braceDepth++;
				} else if(listContent.charAt(end) == '[' ){
					bracketDepth++;
				} else if(listContent.charAt(end) == '}' && braceDepth > 0 && (end == 0 || listContent.charAt(end-1) != '\\')){
					braceDepth--;
				} else if(listContent.charAt(end) == ']' && bracketDepth > 0 && (end == 0 || listContent.charAt(end-1) != '\\')){
					bracketDepth--;
				}
				// skip escaped commas
				if(listContent.charAt(end) == ','){
					if((end > 0 && listContent.charAt(end-1) == '\\') == false){
						break;
					}
				}
				end++;
			}
			String listItem = listContent.substring(start, end).trim();
			if(listItem.length() > 0){// skip empty list entries
				list.add(new DefaultVariable(listItem));
			}
			start = end + 1;
		}
		return list;
	}
	
	public static String listToString(List<DaTextVariable> datextList){
		StringBuilder postProcessed = new StringBuilder();
		boolean first = true;
		postProcessed.append("[");
		for(DaTextVariable v : datextList){
			if(!first){
				postProcessed.append(", ");
			}
			postProcessed.append(v.asText());
			first = false;
		}
		postProcessed.append("]\n");
		return postProcessed.toString();
	}
}

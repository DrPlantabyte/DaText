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
		while(start < listContent.length()){
			end = start;
			while(end < listContent.length()){
				// TODO: skip nested objects and lists
				// skip escaped commas
				if(listContent.charAt(end) == ','){
					if((end > 0 && listContent.charAt(end-1) == '\\') == false){
						end++;
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

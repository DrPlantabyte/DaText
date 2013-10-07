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
     * @param listContent The text content of a list (excluding [ and ])
     * @return 
     */
    public static List<DaTextVariable> parseListString(String listContent){
		List<DaTextVariable> list = new ArrayList<>();
		int start = 0;
		int end;
		while(start < listContent.length()){
			end = start;
			while(end < listContent.length()){
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
}

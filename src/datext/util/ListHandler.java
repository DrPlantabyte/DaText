/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datext.util;

import datext.DaTextVariable;
import java.util.List;

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
		int start = 0;
		int end = 0;
		while( end < listContent.length()){
			if(listContent.charAt(end) == ','){
				if((end > 0 && listContent.charAt(end-1) == '\\') == false){
					break;
				}
			}
			end++;
		}
		String listItem = listContent.substring(start, end);
		
	}
}

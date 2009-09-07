/*
 * JavaWars - browser game that teaches Java
 * Copyright (C) 2008-2009  Bartlomiej N (nbartlomiej@gmail.com)
 * 
 * This file is part of JavaWars. JavaWars is free software: you can 
 * redistribute it and/or modify it under the terms of the GNU General 
 * Public License as published by the Free Software Foundation, either 
 * version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 * 
 */

package javawars.client;

import javawars.client.ui.exceptions.PageNotFoundException;
import javawars.client.ui.Page;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.Window;

/**
 * A class that takes care of the history-related issues
 * @author bartlomiej
 */
public class HistoryManager implements HistoryListener{
    
    /**
     * From this page the HistoryManager will start while 
     * resolving a token
     */
    private final Page rootPage;

    public HistoryManager(Page rootPage) {
        this.rootPage = rootPage;
    }
    
    public void onHistoryChanged(String token) {
        Page page = rootPage;
        
        try{
            // Maybe it is the root page we
            // want do display...
            if (token.equals("")){
                
                page.showInitialContent();
            
            // History token is different than "", so... 
            } else {
                for ( String s : token.split("/") ){
                    if (page.getDisplayedUrl()!=null && page.getDisplayedUrl().equals(s) ){
                        // nothing; we don't need to redisplay the visible page.
                    } else {
                        page.showChildPage(s);
                    }
                    page = page.getChildPage(s);
                }
                
                page.showInitialContent();
                
            }
        } catch (PageNotFoundException p){
            page.showComposite(Page.PAGE_NOT_FOUND);
        }
    }

}

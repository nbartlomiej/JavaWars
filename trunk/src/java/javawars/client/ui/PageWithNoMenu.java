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


package javawars.client.ui;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.Iterator;

/**
 *
 * @author bartlomiej
 */




public abstract class PageWithNoMenu extends Page{

    
    /**
     * VerticalPanel that hosts all the widgets displayed by
     * this page
     */
    private final VerticalPanel pageVP = new VerticalPanel();
    
    

    
    /**
     * Creates a page object; since Page is an abstract class,
     * this constructor is supposed to be evoked from overriding class
     * if form of a super(arguments) expression
     * @param url url of this page; should be unique; if not, a random 
     * text will be appended to guarantee its uniqueness
     */
    public PageWithNoMenu( String url ){
        
        // invoking the constructor of the superclass 
        // which takes care of assuring the uniqueness 
        // of the url
        super ( url );
        
        initWidget(pageVP);
        
        pageVP.add(contentWrapper);
        
        contentWrapper.add(contentProxy);
                
        // setting the widths of the page's widgets
        this.setWidth("100%");
        
    }
    
    protected final void updateStyle(){
        
        // calculation of page's level
        int level = 0;
        Page page = this;
        while (page.getParentPage()!=null){
            level++;
            page = page.getParentPage();
        }
        
        // updating the names of the styles
        this.setStyleName("page page-nomenu page-level-"+level + " " + getUrl() +"-page");
        contentWrapper.setStyleName("content content-nomenu content-level-"+level + " " + getUrl() +"-content");
        
        Iterator<Page> iterator = getChildrenIterator();
        while(iterator.hasNext() ){
            iterator.next().updateStyle();
        }
        
        trashFocus();
        
    }

    @Override
    protected void onChildAdd(Page child) {
       removeAllChildrenPages();
       throw new RuntimeException("This page should never have any children added! ");
    }

    @Override
    protected void onChildRemove(Page child, int childIndex) {        
        //empty
        return;
    }
    

}

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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javawars.client.ui;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author bartlomiej
 */
public class SpacerFactory {
    /**
     * Generates a HTML widget of a specified size; it may serve as a spacer
     * to tune layout;
     * @param width
     * @param height
     * @return
     */
    public static Widget getHTMLSpacer(int width, int height){
        HTML spacer = new HTML("&nbsp");
        spacer.setWidth(""+width+"px");
        spacer.setHeight(""+height+"px");
        return spacer;
    }
    
    /**
     * Puts a number of HTML spacers with given width into the first row 
     * of the FlexTable; this method may serve as a way to beautify the 
     * layout of FlexTables.
     * @param table
     * @param widths
     */
    public static void adjustFlexTable(FlexTable table, int ... widths){
        for (int index = 0; index < widths.length; index++){
            table.setWidget(0, index, getHTMLSpacer(widths[index], 0));
        }
    }
}

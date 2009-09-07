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

package javawars.client.pages.labels;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * A label that contains a SimplePanel with one gwt.user.client.ui.Label
 * widget.
 * @author bartlomiej
 */
public class SimpleLabel extends Composite{
    
    private final SimplePanel simplePanel = new SimplePanel();
    private final String label;

    /**
     * Creates a SimpleLabel object; this is a label that contains 
     * one SimplePanel and one gwt.user.client.ui.Label with the given
     * text within it.
     * @param label - the text to be displayed in the label;
     */
    public SimpleLabel(String label) {
        this.label = label;
        
        simplePanel.add(new Label(label));

        initWidget(simplePanel);
    }
    
    

}

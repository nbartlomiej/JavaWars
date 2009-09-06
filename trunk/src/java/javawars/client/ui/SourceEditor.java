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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
//import jwsgwt.client.OUT;

/**
 *
 * @author bartek
 */
public class SourceEditor extends VerticalPanel{

    public void setText(String source, int width, int height){

        clear();

        String codePress = "<textarea id='editSource' class='codepress java' style='width: "+width+"px; height: "+height+"px;' wrap='off'> "+ source +" </textarea>";

        add(new HTML(codePress));

        try {
            initializeCodePress();
        } catch (Exception e){

        }
        
    }
    
    public void setText(String source){

        clear();

        String codePress = "<textarea id='editSource' class='codepress java' wrap='off'> "+ source +" </textarea>";

        add(new HTML(codePress));

        try {
            initializeCodePress();
        } catch (Exception e){

        }
        
    }
    
    public native void initializeCodePress() /*-{
        $wnd.CodePress.run();
    }-*/;
    
    public native String getTextAreaText() /*-{
        return $wnd.editSource.getCode();
    }-*/;
    
    public String getText(){
            try {
                Element textarea = DOM.getElementById("editSource");
                String result = DOM.getInnerText(textarea);
                return result;
            } catch (Exception e){
                return getTextAreaText();
            }
    }
    
}


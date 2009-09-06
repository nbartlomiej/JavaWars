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

package javawars;

import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author bartek
 */
public class JWXML {

    private LinkedList<String> content = new LinkedList<String>();
    private LinkedList<String> stack = new LinkedList<String>();
    int leftMargin = 0;
    
    public JWXML(){}
    
    private String getSpace() {
        String result = "";
        int space = leftMargin;
        while (space-- > 0) {
            result += "    ";
        }
        return result;
    }

    public void openTag(String tag) {
        content.add("" + getSpace() + "<" + tag + ">\n");
        if (tag.indexOf(" ") > 0) {
            stack.add(tag.substring(0, tag.indexOf(" ")));
        } else {
            stack.add(tag);
        }
        leftMargin++;
    }

    public void closeTag() {
        if (stack.size() <= 0) return;
        leftMargin--;
        String tag = stack.removeLast();
        content.add("" + getSpace() + "</" + tag + ">\n");
    }

    public void writeTag(String text) {
        content.add("" + getSpace() + "<" + text + "/>\n");
    }

    public String getContent() {
        String result = "";
        while (result.length() < 999999 && content.size()>0 ) result += content.removeFirst();
        return result;
    }

    public static void main(String[] args) {

        JWXML j = new JWXML();
        j.openTag("jwgame id='234'");
        j.openTag("jwmatch");
        j.writeTag("time id='392'");
        j.closeTag();
        j.closeTag();
        System.out.println("" + j.getContent());

    }
}

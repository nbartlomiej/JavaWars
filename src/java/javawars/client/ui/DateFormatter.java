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

import java.util.Date;

/**
 *
 * @author bartlomiej
 */
public class DateFormatter {

    public static String extractDate(Date date){
        String result = (date.getDate()>=10?""+date.getDate():"0"+date.getDate()) 
                + "." + (date.getMonth()+1>=10?""+(date.getMonth()+1):"0"
                +(date.getMonth()+1)) + "." + (1900+date.getYear());
        return result;
    }
    public static String extractHour(Date date){
        String result = (date.getHours()>=10?""+date.getHours():"0"+date.getHours()) 
                + ":" + (date.getMinutes()>=10?""+(date.getMinutes()):"0" +(date.getMinutes()));
        return result;
    }
    
    public static String extractDateAndHour(Date date) {
        return extractDate(date) + ", " + extractHour(date);
    }
}

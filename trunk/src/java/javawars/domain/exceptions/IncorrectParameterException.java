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

package javawars.domain.exceptions;



import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import javawars.domain.exceptions.Enum.Error;
import javawars.domain.exceptions.Enum.Parameter;

/**
 *
 * @author bartlomiej
 */
public class IncorrectParameterException extends Exception implements Serializable, IsSerializable{


    private Parameter parameter;
    private Error error;

    
    public IncorrectParameterException() {
    }
    
    public IncorrectParameterException( Parameter parameter, Error error) {
        this.parameter = parameter;
        this.error = error;
    }

    public Error getError() {
        return error;
    }

    public Parameter getParameter() {
        return parameter;
    }
    
    
    
}

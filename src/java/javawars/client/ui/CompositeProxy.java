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

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 *
 * @author bartlomiej
 */
public abstract class CompositeProxy extends Composite {
    public abstract Composite getComposite();
    
    private FlowPanel compositeProxyVP = new FlowPanel();

    public CompositeProxy() {
        initWidget( compositeProxyVP );
    }

    private boolean initialized = false;
    @Override
    protected void onLoad() {
        if (initialized == false ){
            compositeProxyVP.add(getComposite());
            initialized = true;
        }
    }
    
}

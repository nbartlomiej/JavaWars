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

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import java.util.Iterator;

/**
 *
 * @author bartlomiej
 */
public abstract class PageWithVerticalMenu extends Page {

    /**
     * TabBar that contains proxies that lead to labels of child pages.
     * Should not be visible when not containing any tabs - to prevent 
     * from creating a gap in layout.
     */
    private final VerticalTabBar tabBar = new VerticalTabBar();
    /**
     * Hiding the tabBar; will become visible when 'addChild' method will
     * be evoked.
     */
    

    {
        tabBar.setVisible(false);
    }
    /**
     * VerticalPanel that hosts all the widgets displayed by
     * this page
     */
    private final FlowPanel pageVP = new FlowPanel();

    /**
     * Creates a page object; since Page is an abstract class,
     * this constructor is supposed to be evoked from overriding class
     * if form of a super(arguments) expression
     * @param url url of this page; should be unique; if not, a random 
     * text will be appended to guarantee its uniqueness
     */
    public PageWithVerticalMenu(String url) {

        // invoking the constructor of the superclass 
        // which takes care of assuring the uniqueness 
        // of the url
        super(url);

        initWidget(pageVP);

        HorizontalPanel contentDivider = new HorizontalPanel();
        contentDivider.add(tabBar);
        contentDivider.add(contentWrapper);

        pageVP.add(contentDivider);

        contentWrapper.add(contentProxy);

        tabBar.addTabListener(historyUpdater);

        // setting the widths of the page's widgets
        this.setWidth("100%");

    }
    /**
     * TabListener that triggers placing new item on the history
     * stack upon clicking on a tab
     */
    TabListener historyUpdater = new TabListener() {

        public boolean onBeforeTabSelected(SourcesTabEvents sender, int tabIndex) {
            if (getChildPage(tabIndex).isDisabled() == true) {
                return false;
            } else {
                return true;
            }
        }

        public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
            History.newItem(getChildPage(tabIndex).getHistoryToken());
        }
    };

    protected final void updateStyle() {

        // calculation of page's level
        int level = 0;
        Page page = this;
        while (page.getParentPage() != null) {
            level++;
            page = page.getParentPage();
        }

        // updating the names of the styles
        this.setStyleName("page page-vertical page-level-" + level + " " + getUrl() + "-page");
        tabBar.setStyleName("menu menu-vertical menu-level-" + level + " " + getUrl() + "-menu");
        contentWrapper.setStyleName("content content-vertical content-level-" + level + " " + getUrl() + "-content");

        Iterator<Page> iterator = getChildrenIterator();
        while (iterator.hasNext()) {
            iterator.next().updateStyle();
        }

        trashFocus();

    }

    /**
     * If user programmatically selects a child page to be displayed the tabBar
     * must be updated; we're using the onContentChange to implement that; therefore
     * each onContentChange sub-implementation should invoke the super-one.
     */
    @Override
    protected void onContentChange() {
        if ( getSelectedChild() != tabBar.getSelectedTab()) {
            // removing the tabListener just for a while to prevent 
            // relaunching the loading sequence leading to an endless loop
            tabBar.removeTabListener(historyUpdater);
            tabBar.selectTab(getSelectedChild());
            tabBar.addTabListener(historyUpdater);
        }
    }

    @Override
    protected void onChildAdd(Page child) {
        tabBar.addTab(child.getLabelProxy());

        // updating visibility of the tabBar
        if (tabBar.isVisible() == false) {
            tabBar.setVisible(true);
        }
    }

    @Override
    protected void onChildRemove(Page child, int childIndex) {

        tabBar.removeTab(childIndex);

        // updating visibility of the tabBar
        if (tabBar.getTabCount() == 0) {
            tabBar.setVisible(false);
        }
    }
}

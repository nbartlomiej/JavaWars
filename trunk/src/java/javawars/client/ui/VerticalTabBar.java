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
 * Copyright 2007 Hilbrand Bouwkamp, hs@bouwkamp.com
 *
 * This file is a derivative work of the file:
 *   com.google.gwt.user.client.ui.TabBar.java
 * The original file is available from:
 *   http://code.google.com/webtoolkit/
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * License of original work
 *
 * Copyright 2006 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package javawars.client.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabListenerCollection;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A vertical bar of folder-style tabs, most commonly used as part of a
 * {@link com.bouwkamp.gwt.user.client.ui.VerticalTabPanel}.
 *
 * The vertical bar is a derivate of the TabBar
 * {@link com.google.gwt.user.client.ui.TabBar}. This class uses the same
 * CSS style names as the TabBar class to minimize differences between
 * that class because the only difference is the orientation of the TabBar.
 *
 * <p>
 * <img class='gallery' src='VerticalTabBar.png'/>
 * </p>
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.gwt-TabBar { the tab bar itself }</li>
 * <li>.gwt-TabBar .gwt-TabBarFirst { the top edge of the bar }</li>
 * <li>.gwt-TabBar .gwt-TabBarRest { the bottom edge of the bar }</li>
 * <li>.gwt-TabBar .gwt-TabBarItem { unselected tabs }</li>
 * <li>.gwt-TabBar .gwt-TabBarItem-selected { additional style for selected
 * tabs } </li>
 * </ul>
 * <p>
 * <h3>Example</h3>
 * {@example com.bouwkamp.gwt..examples.VerticalTabBarExample}
 * </p>
 */
public class VerticalTabBar extends Composite implements SourcesTabEvents,
    ClickListener {

  /**
   * <code>ClickDecoratorPanel</code> decorates any widget with the minimal
   * amount of machinery to receive clicks for delegation to the parent.
   * {@link SourcesClickEvents} is not implemented due to the fact that only a
   * single observer is needed.
   */
  private static final class ClickDecoratorPanel extends SimplePanel {
    ClickListener delegate;

    ClickDecoratorPanel(Widget child, ClickListener delegate) {
      this.delegate = delegate;
      setWidget(child);
      sinkEvents(Event.ONCLICK);
    }

    public void onBrowserEvent(Event event) {
      // No need for call to super.
      switch (DOM.eventGetType(event)) {
      case Event.ONCLICK:
        delegate.onClick(this);
      }
    }
  }

  private static final String STYLENAME_DEFAULT = "gwt-TabBarItem";
  private VerticalPanel panel = new VerticalPanel();
  private Widget selectedTab;
  private TabListenerCollection tabListeners;

  /**
   * Creates an empty tab bar.
   */
  public VerticalTabBar() {
    initWidget(panel);
    sinkEvents(Event.ONCLICK);
    setStyleName("gwt-TabBar");

    panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

    HTML first = new HTML("&nbsp;", true), rest = new HTML("&nbsp;", true);

    rest.setStyleName("gwt-TabBarRest");
    panel.add(first);
    //set gwt-TabBarFirst on TR otherwise a 1px line remains visible in IE6 when display:none
    DOM.setAttribute(DOM.getParent(DOM.getParent(first.getElement())), "className", "gwt-TabBarFirst");
    panel.add(rest);
    panel.setCellWidth(rest, "100%");
  }

  /**
   * Adds a new tab with the specified text.
   *
   * @param text the new tab's text
   */
  public void addTab(String text) {
    insertTab(text, getTabCount());
  }

  /**
   * Adds a new tab with the specified text.
   *
   * @param text the new tab's text
   * @param asHTML <code>true</code> to treat the specified text as html
   */
  public void addTab(String text, boolean asHTML) {
    insertTab(text, asHTML, getTabCount());
  }

  /**
   * Adds a new tab with the specified widget.
   *
   * @param widget the new tab's widget.
   */
  public void addTab(Widget widget) {
    insertTab(widget, getTabCount());
  }

  public void addTabListener(TabListener listener) {
    if (tabListeners == null) {
      tabListeners = new TabListenerCollection();
    }
    tabListeners.add(listener);
  }

  /**
   * Gets the tab that is currently selected.
   *
   * @return the selected tab
   */
  public int getSelectedTab() {
    if (selectedTab == null) {
      return -1;
    }
    return panel.getWidgetIndex(selectedTab) - 1;
  }

  /**
   * Gets the widget of the tab that is currently selected.
   *
   * @return the selected tab widget
   */
  public Widget getSelectedTabWidget() {
    return null != selectedTab && selectedTab instanceof ClickDecoratorPanel
        ? ((ClickDecoratorPanel) selectedTab).getWidget()
        : selectedTab;
  }

  /**
   * Gets the number of tabs present.
   *
   * @return the tab count
   */
  public int getTabCount() {
    return panel.getWidgetCount() - 2;
  }

  /**
   * Gets the specified tab's HTML.
   *
   * @param index the index of the tab whose HTML is to be retrieved
   * @return the tab's HTML
   */
  public String getTabHTML(int index) {
    if (index >= getTabCount()) {
      return null;
    }
    Widget widget = panel.getWidget(index + 1);

    if (widget instanceof HTML) {
      return ((HTML) widget).getHTML();
    } else if (widget instanceof Label) {
      return ((Label) widget).getText();
    } else {
      // This will be a ClickDecorator holding a user-supplied widget.
      return DOM.getInnerHTML(widget.getElement());
    }
  }

  /**
   * Inserts a new tab at the specified index.
   *
   * @param text the new tab's text
   * @param asHTML <code>true</code> to treat the specified text as HTML
   * @param beforeIndex the index before which this tab will be inserted
   */
  public void insertTab(String text, boolean asHTML, int beforeIndex) {
    checkInsertBeforeTabIndex(beforeIndex);

    Label item;

    if (asHTML) {
      item = new HTML(text);
    } else {
      item = new Label(text);
    }

    item.setWordWrap(false);
    item.addClickListener(this);
    item.setStyleName(STYLENAME_DEFAULT);
    panel.insert(item, beforeIndex + 1);
  }

  /**
   * Inserts a new tab at the specified index.
   *
   * @param text the new tab's text
   * @param beforeIndex the index before which this tab will be inserted
   */
  public void insertTab(String text, int beforeIndex) {
    insertTab(text, false, beforeIndex);
  }

  /**
   * Inserts a new tab at the specified index.
   *
   * @param widget widget to be used in the new tab.
   * @param beforeIndex the index before which this tab will be inserted.
   */
  public void insertTab(Widget widget, int beforeIndex) {
    checkInsertBeforeTabIndex(beforeIndex);

    ClickDecoratorPanel decWidget = new ClickDecoratorPanel(widget, this);

    decWidget.addStyleName(STYLENAME_DEFAULT);
    panel.insert(decWidget, beforeIndex + 1);
  }

  public void onClick(Widget sender) {
    for (int i = 1; i < panel.getWidgetCount() - 1; ++i) {
      if (panel.getWidget(i) == sender) {
        selectTab(i - 1);
        return;
      }
    }
  }

  /**
   * Removes the tab at the specified index.
   *
   * @param index the index of the tab to be removed
   */
  public void removeTab(int index) {
    checkTabIndex(index);

    // (index + 1) to account for 'first' placeholder widget.
    Widget toRemove = panel.getWidget(index + 1);

    if (toRemove == selectedTab) {
      selectedTab = null;
    }
    panel.remove(toRemove);
  }

  public void removeTabListener(TabListener listener) {
    if (tabListeners != null) {
      tabListeners.remove(listener);
    }
  }

  /**
   * Programmatically selects the specified tab. Use index -1 to specify that no
   * tab should be selected.
   *
   * @param index the index of the tab to be selected.
   * @return <code>true</code> if successful, <code>false</code> if the
   *         change is denied by the {@link TabListener}.
   */
  public boolean selectTab(int index) {
    checkTabIndex(index);

    if (tabListeners != null) {
      if (!tabListeners.fireBeforeTabSelected(this, index)) {
        return false;
      }
    }

    // Check for -1.
    setSelectionStyle(selectedTab, false);
    if (index == -1) {
      selectedTab = null;
      return true;
    }

    selectedTab = panel.getWidget(index + 1);
    setSelectionStyle(selectedTab, true);

    if (tabListeners != null) {
      tabListeners.fireTabSelected(this, index);
    }
    return true;
  }

  private void checkInsertBeforeTabIndex(int beforeIndex) {
    if ((beforeIndex < 0) || (beforeIndex > getTabCount())) {
      throw new IndexOutOfBoundsException();
    }
  }

  private void checkTabIndex(int index) {
    if ((index < -1) || (index >= getTabCount())) {
      throw new IndexOutOfBoundsException();
    }
  }

  private void setSelectionStyle(Widget item, boolean selected) {
    if (item != null) {
      if (selected) {
        item.addStyleName("gwt-TabBarItem-selected");
      } else {
        item.removeStyleName("gwt-TabBarItem-selected");
      }
    }
  }
}

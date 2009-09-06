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

import com.google.gwt.user.client.Window;
import javawars.client.ui.exceptions.PageNotFoundException;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bartlomiej
 */
public abstract class Page extends Composite {

    /**
     * A composite that is displayed when this object is requested to
     * show a child page that it cannot find.
     */
    public static final Composite PAGE_NOT_FOUND = new Composite() {

        private VerticalPanel pageNotFoundVP = new VerticalPanel();
        

        {
            initWidget(pageNotFoundVP);
            pageNotFoundVP.add(new Label("ERROR 404"));
            pageNotFoundVP.add(new Label("Page not found."));
        }
    };
    private final String url;
    /**
     * List of all child Pages; may be empty.
     * Upon each update of this list, the TabBar containing links to
     * labels of all child pages should be updated too.
     */
    private final List<Page> children = new ArrayList<Page>();
    /**
     * The page that is above this one in the menu hierarchy
     */
    private Page parentPage = null;

    private static final Map<String, Integer> urls = new HashMap<String, Integer>();

    /**
     * Returns the content that will be displayed when this page
     * is being viewed.
     * @return page content
     */
    public abstract Composite getContent();

    /**
     * Returns a label, shown in the parent's TabBar
     * @return page label
     */
    public abstract Composite getLabel();
    /**
     * Proxy that delays the creation of the content; the Page class
     * operates solely on this proxy and doesn't evoke getContent()
     * method. By this fact initialization of the content is delayed until
     * the moment when content is being shown.
     */
    protected final CompositeProxy contentProxy = new CompositeProxy() {

        @Override
        public Composite getComposite() {
            return getContent();
        }
    };
    /**
     * Proxy that delays the creation of the label; the Page class
     * operates solely on this proxy and doesn't evoke getLabel()
     * method. By this fact initialization of the label is delayed until
     * the moment when label is being shown.
     */
    protected final CompositeProxy labelProxy = new CompositeProxy() {

        @Override
        public Composite getComposite() {
            return getLabel();
        }
    };
    

    /**
     * Method that grants use of the label proxy outside the 
     * class, to parent classes that want to add this proxy to 
     * their tabbars.
     * @return label proxy
     */
    protected final Composite getLabelProxy() {
        return labelProxy;
    }
    /**
     * Link to the current content - for the purpose of 
     * detatching it from parent later on.
     */
    private Composite currentContent;
    
    /**
     * Wrapper that will enable to style the content of this page.
     */
    protected final FlowPanel contentWrapper = new FlowPanel();

    /**
     * This constructor (obligatory to evoke by subclasses) only registers the url
     * of the page.
     * @param url url unique for each page; if not unique - a random one wil be
     * generated; attention, upon each refresh of the webpage the random values 
     * will be different! (no seeding - it is not yet implemented in gwt).
     */
    public Page(String url) {

        // making sure that the url is unique 
        // to avoid problems while resolving urls

        // temporarily turning off the mechanism 
        // of altering urls
            
//        int occurenceCount = 0;
//        if (urls.containsKey(url) == true) {
//             occurenceCount = urls.remove(url);
//             urls.put(url, ++occurenceCount);
//             url += "-" + occurenceCount;
//        }

        this.url = url;

        // registering the url to prevent other pages 
        // be assigned the same url
//        urls.put(url, 0);

        // setting the content of this page as the current content
        currentContent = contentProxy;
    }

    /**
     * A method for accessing a checked (unique) url
     * @return unique url
     */
    public final String getUrl() {
        return url;
    }

    /**
     * 
     * @return the history token required to view the content of this page
     */
    protected final String getHistoryToken() {
        String prefix = "";
        if (getParentPage() != null) {
            Page page = getParentPage();
            prefix += page.getUrl() + "/";
            while (page.getParentPage() != null) {
                page = page.getParentPage();
                prefix = page.getUrl() + "/" + prefix;
            }
        }
        String historyToken = prefix + getUrl();

        // Deleting the root page's name; with this we transform the "root/child1/child-child-2"
        // to "child1/child-child2".
        historyToken = historyToken.substring(historyToken.indexOf("/") + 1);
        return historyToken;

    }
    private boolean initialized = false;

    @Override
    protected final void onLoad() {
        if (initialized == false) {
            // updating the style of the page
            updateStyle();

            initialized = true;
        }
        showInitialContent();
    }
    
    private boolean ommitDefaultContent = true;

    /**
     * If true, the page will not show its default (embbeded) content
     * but display the first child; when childless - the default content
     * will be shown. Default: true.
     * @param ommitDefaultContent
     */
    public void setOmmitDefaultContent(boolean ommitDefaultContent) {
        this.ommitDefaultContent = ommitDefaultContent;
    }

    /**
     * Displays the default content of this page or the first child (if it has 
     * chidren and the ommitDefaultContent variable is set as 'true').
     */
    public final void showInitialContent() {
        if (ommitDefaultContent == true && getChildPageCount() > 0) {
            // updating the history token
            // History.newItem( getChildPage(0).getHistoryToken(), false);
            showChildPage(0);
           
        // if the proper content is already attached
        // we don't need to redisplay it
        } else if (currentContent != contentProxy){
            if (currentContent.isAttached()) {
                currentContent.removeFromParent();
            }
            currentContent = contentProxy;
            contentWrapper.add(currentContent);
            
            // no child is selected
            selectedChild = -1;
            onContentChange();
        }
    }

    /**
     * Displays the content of the child page (of specified index)
     * @param index index of the child to be displayed, acceptable values:
     * [0 - getChildPageCount() )
     */
    public final void showChildPage(int index) {
        if (getChildPage(index).isDisabled()==true){
            // This child has been disabled and should not be shown
            return;
        }
        
        if (index == selectedChild){
            // we don't want to redisplay the already visible child.
            children.get(index).showInitialContent();
            return;
        }
        
        if (index < 0 || index >= children.size()) {
            throw new PageNotFoundException();
        }
        if (currentContent.isAttached()) {
            currentContent.removeFromParent();
        }
        currentContent = children.get(index);
        contentWrapper.add(currentContent);
        
        // noting that the content displayed is the Page's child 
        // with a certain index
        selectedChild = index;
        onContentChange();
    }

    /**
     * Displays the content of the child page (of specified url)
     * @param url url of the child to be displayed
     */
    public final void showChildPage(String url) {
        for (int index = 0; index < children.size(); index++) {
            if (children.get(index).getUrl().equals(url) == true) {
                showChildPage(index);
                return;
            }
        }
        throw new PageNotFoundException();
    }

    /**
     * A method that allows to display any composite as the page's
     * content.
     * @param composite
     */
    public final void showComposite(Composite composite) {
        currentContent.removeFromParent();
        currentContent = composite;
        contentWrapper.add(currentContent);
        
        // no child is selected
        selectedChild = -1;
        onContentChange();
    }

    /**
     * Returns parent page of this object.
     * @return page that is higher in menu hierarchy
     */
    public final Page getParentPage() {
        return parentPage;
    }

    /**
     * A method that assigns the given Page object as this page's 
     * parent. Also updates the style of this page.
     * @param parentPage
     */
    private final void setParentPage(Page parentPage) {
        this.parentPage = parentPage;
        updateStyle();

    }
    
    /**
     * An integer value for storing the index of the selected (visible) child;
     * is modified by such methods as: showChildPage(...) or showComposite(...)
     * and similar.
     */
    private int selectedChild = -1;
    
    /**
     * Returns the index of the selected (visible) child page, or -1 if none of
     * the children is visible (i.e. the default or custom content is displayed)
     * @return
     */
    protected final int getSelectedChild(){
        return selectedChild;
    }

    /**
     * A method that forces the class to update its style - is
     * evoked by 'addparent' method and should be implemented by
     * classes that extend Page class.
     */
    protected abstract void updateStyle();

    /**
     * Invoked right after a child page is added to this page's instance
     * @param child
     */
    protected abstract void onChildAdd(Page child);

    /**
     * Invoked right before a child page is removed from this page's instance
     * @param child
     */
    protected abstract void onChildRemove(Page child, int childIndex);

    /**
     * Invoked right after a content of this page has been changed.
     */
    protected void onContentChange(){
        
    }
    
    /**
     * If set to true, the label of this page should not be clickable and the
     * content of this page should not be visible. Although the label itself 
     * should remain seen.
     */
    private boolean disabled = false;

    /**
     * If set to true, the label of this page should not be clickable and the
     * content of this page should not be visible. Although the label itself 
     * should remain seen. This behaviour should be implemented by subclasses.
     * Default: false.
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    /** 
     * Informs whether the label of this page is clickable and the content is 
     * visible.
     * @return
     */
    public boolean isDisabled() {
        return disabled;
    }
        
    /**
     * Adds a child page - it will appear in the tab bar of this page.
     * @param child A child page
     */
    public final void addChild(Page child) {
        children.add(child);
        child.setParentPage(this);

        onChildAdd(child);
    }

    public final Page getChildPage(int index) {
        if (index < 0 || index >= children.size()) {
            throw new PageNotFoundException();
        }
        return children.get(index);
    }

    public final Page getChildPage(String url) {
        for (int index = 0; index < children.size(); index++) {
            if (children.get(index).getUrl().equals(url) == true) {
                return getChildPage(index);
            }
        }
        throw new PageNotFoundException();
    }

    public final Page removeChildPage(int index) {
        if (index < 0 || index >= children.size()) {
            throw new PageNotFoundException();
        }
        children.get(index).setParentPage(null);

        onChildRemove(children.get(index), index);
        if (index == selectedChild ) showInitialContent();
        return children.remove(index);
    }

    public final Page removeChildPage(String url) {
        for (int index = 0; index < children.size(); index++) {
            if (children.get(index).getUrl().equals(url) == true) {
                return removeChildPage(index);
            }
        }
        throw new PageNotFoundException();
    }

    /**
     * Method created to access the children of this page
     * @return iterator of the child pages
     */
    public final Iterator<Page> getChildrenIterator() {
        return children.iterator();
    }

    public final void removeAllChildrenPages() {
        while (children.size() > 0) {
            removeChildPage(0);
        }
    }

    /**
     * Returns the number of child Pages attached to this object (by the 
     * addChildPage( Page p ) method )
     * @return
     */
    public final int getChildPageCount() {
        return children.size();
    }

    @Override
    public final int hashCode() {
        return getUrl().hashCode();
    }

    @Override
    public final boolean equals(Object object) {
        if (object instanceof Page) {
            Page page = (Page) object;
            if (this.getUrl().equals(page.getUrl())) {
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * A text area that should remain hidden - it is used to
     * transfer the focus from widgets that should lost it due
     * to any bugs
     */
    private static final TextArea hidden = new TextArea();
    private static boolean hiddenFieldInitializes = false;
    /**
     * This block makes sure that the hidden text area is added
     * to the RootPanel.
     */
    

    {
        if (hiddenFieldInitializes == false) {
            hidden.setVisible(false);
            RootPanel.get().add(hidden);
            hiddenFieldInitializes = true;
        }
    }

    /**
     * This function assigns focus to the hidden widget - it
     * is a way of getting rid of the unwanted focus.
     */
    protected static final void trashFocus() {
        hidden.setFocus(true);
    }
    
    /**
     * Returns the url of the child page that is being displayed or 
     * null - if none.
     * @return
     */
    public String getDisplayedUrl(){
        if (selectedChild>=0){
            return children.get(selectedChild).getUrl();
        } else {
            return null;
        }
    }
    
}







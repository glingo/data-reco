//WebOnSwing - Web Application Framework
//Copyright (C) 2003 Fernando Damian Petrola
//	
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//	
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//Lesser General Public License for more details.
//	
//You should have received a copy of the GNU Lesser General Public
//License along with this library; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

package net.ar.webonswing.toolkit;

import java.awt.*;
import java.awt.List;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.dnd.peer.*;
import java.awt.im.*;
import java.awt.image.*;
import java.awt.peer.*;
import java.net.*;
import java.util.*;

import net.ar.webonswing.toolkit.peers.*;
import sun.awt.font.*;

public class WosToolkit extends Toolkit
{
	protected WosBufferedImage imageUrl= new WosBufferedImage(100, 100, 1);
	protected String[] fontList= new String[0];
	protected WosColorModel colorModel= new WosColorModel();
	protected Dimension screenSize= new Dimension(640, 480);
	protected WosFontPeer fontPeer= new WosFontPeer();
	protected WosFileDialogPeer filedialogPeer= new WosFileDialogPeer();
	protected WosPopupMenuPeer popupmenuPeer= new WosPopupMenuPeer();
	protected WosMenuPeer menuPeer= new WosMenuPeer();
	protected WosMenuBarPeer menubarPeer= new WosMenuBarPeer();
	protected WosDialogPeer dialogPeer= new WosDialogPeer();
	protected WosWindowPeer windowPeer= new WosWindowPeer();
	protected WosPanelPeer panelPeer= new WosPanelPeer();
	protected WosCanvasPeer canvasPeer= new WosCanvasPeer();
	protected WosFramePeer framePeer= new WosFramePeer();
	protected WosChoicePeer choicePeer= new WosChoicePeer();
	protected WosTextAreaPeer textareaPeer= new WosTextAreaPeer();
	protected WosScrollPanePeer scrollpanePeer= new WosScrollPanePeer();
	protected WosScrollbarPeer scrollbarPeer= new WosScrollbarPeer();
	protected WosCheckboxPeer checkboxPeer= new WosCheckboxPeer();
	protected WosListPeer listPeer= new WosListPeer();
	protected WosLabelPeer labelPeer= new WosLabelPeer();
	protected WosTextFieldPeer textfieldPeer= new WosTextFieldPeer();
	protected EventQueue theEventQueue= new WosEventQueue();
	protected WosImage image= new WosImage();
	protected Insets screenInsets= new Insets(0, 0, 0, 0);
	protected WosButtonPeer buttonPeer= new WosButtonPeer();

	protected EventQueue getSystemEventQueueImpl()
	{
		return theEventQueue;
	}

	public Image createImage(ImageProducer producer)
	{
		return image;
	}

	public FontMetrics getFontMetrics(Font arg0)
	{
		return new FontDesignMetrics(arg0);
	}

	public Image getImage(String arg0)
	{
		return image;
	}

	public Insets getScreenInsets(GraphicsConfiguration gc)
	{
		return screenInsets;
	}

	protected ButtonPeer createButton(Button target)
	{
		return buttonPeer;
	}

	protected TextFieldPeer createTextField(TextField target)
	{
		return textfieldPeer;
	}

	protected LabelPeer createLabel(Label target)
	{
		return labelPeer;
	}

	protected ListPeer createList(List target)
	{
		return listPeer;
	}

	protected CheckboxPeer createCheckbox(Checkbox target)
	{
		return checkboxPeer;
	}

	protected ScrollbarPeer createScrollbar(Scrollbar target)
	{
		return scrollbarPeer;
	}

	protected ScrollPanePeer createScrollPane(ScrollPane target)
	{
		return scrollpanePeer;
	}

	protected TextAreaPeer createTextArea(TextArea target)
	{
		return textareaPeer;
	}

	protected ChoicePeer createChoice(Choice target)
	{

		return choicePeer;
	}

	protected FramePeer createFrame(Frame target)
	{

		return framePeer;
	}

	protected CanvasPeer createCanvas(Canvas target)
	{
		return canvasPeer;
	}

	protected PanelPeer createPanel(Panel target)
	{

		return panelPeer;
	}

	protected WindowPeer createWindow(Window target)
	{

		return windowPeer;
	}

	protected DialogPeer createDialog(Dialog target)
	{

		return dialogPeer;
	}

	protected MenuBarPeer createMenuBar(MenuBar target)
	{

		return menubarPeer;
	}

	protected MenuPeer createMenu(Menu target)
	{

		return menuPeer;
	}

	protected PopupMenuPeer createPopupMenu(PopupMenu target)
	{

		return popupmenuPeer;
	}

	protected MenuItemPeer createMenuItem(MenuItem target)
	{

		WosMenuItemPeer menuitemPeer= new WosMenuItemPeer();
		return menuitemPeer;
	}

	protected FileDialogPeer createFileDialog(FileDialog target)
	{

		return filedialogPeer;
	}

	protected CheckboxMenuItemPeer createCheckboxMenuItem(CheckboxMenuItem target)
	{

		WosCheckboxMenuItemPeer checkboxmenuitemPeer= new WosCheckboxMenuItemPeer();
		return checkboxmenuitemPeer;
	}

	protected FontPeer getFontPeer(String name, int style)
	{

		return fontPeer;
	}

	public Dimension getScreenSize()
	{
		return screenSize;
	}

	public int getScreenResolution()
	{
		return 0;
	}

	public ColorModel getColorModel()
	{
		return colorModel;
	}

	public String[] getFontList()
	{
		return fontList;
	}

	public void sync()
	{

	}

	public Image getImage(URL url)
	{
		return imageUrl;
	}

	public Image createImage(String filename)
	{
		return imageUrl;
	}

	public Image createImage(URL url)
	{
		return imageUrl;
	}

	public boolean prepareImage(Image anImage, int width, int height, ImageObserver observer)
	{
		return true;
	}

	public int checkImage(Image anImage, int width, int height, ImageObserver observer)
	{
		return 0;
	}

	public Image createImage(byte[] imagedata, int imageoffset, int imagelength)
	{
		return imageUrl;
	}

	public PrintJob getPrintJob(Frame frame, String jobtitle, Properties props)
	{
		return null;
	}

	public void beep()
	{

	}

	public Clipboard getSystemClipboard()
	{

		return null;
	}

	public DragSourceContextPeer createDragSourceContextPeer(DragGestureEvent dge) throws InvalidDnDOperationException
	{

		return null;
	}

	public Map mapInputMethodHighlight(InputMethodHighlight highlight)
	{

		return null;
	}

}

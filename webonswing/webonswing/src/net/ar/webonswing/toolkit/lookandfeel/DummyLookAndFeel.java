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

package net.ar.webonswing.toolkit.lookandfeel;

import java.io.*;

import javax.swing.*;
import javax.swing.plaf.basic.*;

/**
 * Este look&feel sirve para que Swing no haga operaciones de mas cuando tiene que usar los UI de cada componente
 * 
 * @author Fernando Damian Petrola
 */
public class DummyLookAndFeel extends BasicLookAndFeel implements Serializable
{
	static Object[] uiDefaults= { 
		"RootPaneUI", "net.ar.webonswing.toolkit.lookandfeel.DummyRootPaneUI",
		"ComponentUI", "net.ar.webonswing.toolkit.lookandfeel.DummyUI",
		"LabelUI", "net.ar.webonswing.toolkit.lookandfeel.DummyLabelUI", 
		"ButtonUI", "net.ar.webonswing.toolkit.lookandfeel.DummyButtonUI", 
		"PanelUI", "net.ar.webonswing.toolkit.lookandfeel.DummyPanelUI", 
		"ScrollPaneUI", "net.ar.webonswing.toolkit.lookandfeel.DummyScrollPaneUI", 
		"ViewportUI", "net.ar.webonswing.toolkit.lookandfeel.DummyViewportUI", 
		"TextAreaUI", "net.ar.webonswing.toolkit.lookandfeel.DummyTextAreaUI", 
		"ScrollBarUI", "net.ar.webonswing.toolkit.lookandfeel.DummyScrollBarUI",
		"TextFieldUI", "net.ar.webonswing.toolkit.lookandfeel.DummyTextFieldUI",
		"SplitPaneUI", "net.ar.webonswing.toolkit.lookandfeel.DummySplitPaneUI",
		"ListUI", "net.ar.webonswing.toolkit.lookandfeel.DummyListUI",
		"CheckBoxUI", "net.ar.webonswing.toolkit.lookandfeel.DummyButtonUI",
		"RadioButtonUI", "net.ar.webonswing.toolkit.lookandfeel.DummyButtonUI",
		"ToggleButtonUI", "net.ar.webonswing.toolkit.lookandfeel.DummyButtonUI", 
		"ComboBoxUI", "net.ar.webonswing.toolkit.lookandfeel.DummyComboBoxUI", 
		"LinkUI", "net.ar.webonswing.toolkit.lookandfeel.DummyUI", 
		"TabbedPaneUI", "net.ar.webonswing.toolkit.lookandfeel.DummyTabbedPaneUI", 
		"MenuBarUI", "net.ar.webonswing.toolkit.lookandfeel.DummyMenuBarUI", 
		"MenuUI", "net.ar.webonswing.toolkit.lookandfeel.DummyMenuItemUI", 
		"MenuItemUI", "net.ar.webonswing.toolkit.lookandfeel.DummyMenuItemUI",
		"PopupMenuUI", "net.ar.webonswing.toolkit.lookandfeel.DummyPopupMenuUI",
		"PopupMenuSeparatorUI", "net.ar.webonswing.toolkit.lookandfeel.DummySeparatorUI",
		"RadioButtonMenuItemUI", "net.ar.webonswing.toolkit.lookandfeel.DummyMenuItemUI",
		"CheckBoxMenuItemUI", "net.ar.webonswing.toolkit.lookandfeel.DummyMenuItemUI",
		"TreeUI", "net.ar.webonswing.toolkit.lookandfeel.DummyTreeUI",
		"TableUI", "net.ar.webonswing.toolkit.lookandfeel.DummyTableUI",
		"FileChooserUI", "net.ar.webonswing.toolkit.lookandfeel.DummyFileChooserUI"
	};

	public String getName()
	{
		return "WebOnSwing";
	}

	public String getID()
	{
		return "WebOnSwing";
	}

	public String getDescription()
	{
		return "WebOnSwing Look and Feel";
	}

	public boolean isNativeLookAndFeel()
	{
		return false;
	}

	public boolean isSupportedLookAndFeel()
	{
		return true;
	}

	protected void initClassDefaults(UIDefaults table)
	{
		super.initClassDefaults(table);
		table.putDefaults(uiDefaults);
	}
	
	
	public static UIDefaults getStaticDefaults ()
	{
		UIDefaults table= new UIDefaults();
		table.putDefaults(uiDefaults);
		return table;
	}
}

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

package net.ar.webonswing.managers.script;

import net.ar.webonswing.*;
import net.ar.webonswing.render.*;
import net.ar.webonswing.render.markup.*;

public class ScriptContributionContainer extends CodeAndIncludeContributionContainer
{
	private StringBuffer codeBeforeForm= new StringBuffer();

	protected String getIncludeOperation(String anIncludedPath)
	{
		return new ContentRenderer(new Tag("script").addAttribute("src", anIncludedPath).addAttribute("type", "text/javascript")).getResult() + "\n";
	}
	
	public void addCodeBeforeForm(String aCode)
	{
		codeBeforeForm.append(aCode);
	}
	
	
	public StringBuffer getCodeBeforeForm()
	{
		return codeBeforeForm;
	}
	public void setCodeBeforeForm(StringBuffer codeBeforeForm)
	{
		this.codeBeforeForm= codeBeforeForm;
	}

	public void addDynApiInitCode()
	{
		addInclude(WosFramework.getInstance().getCompleteResourcePath() + "/dynapi/dynapi.js");
		addCodeOnce("dynapi.library.setPath('" + WosFramework.getInstance().getCompleteResourcePath() + "/dynapi/');");
		addCodeOnce("dynapi.library.addPackage('dynapi.widgets', dynapi.library.path + 'widgets/');");
		addCodeOnce("dynapi.library.include('dynapi.library');");
		addCodeOnce("dynapi.library.include('dynapi.api');");
		addCodeOnce("dynapi.library.include('DynLayerInline');");
		addCodeOnce("dynapi.library.include('DragEvent');");
	}
}

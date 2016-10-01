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

package examples;

import javax.servlet.http.*;

import net.ar.webonswing.*;
import net.ar.webonswing.pages.*;

public class WebOnSwingDemo2Page extends DefaultHtmlPage
{
	public void prepareResponse(HttpServletResponse aResponse) throws Exception
	{
		theContributionContainer.getScriptManager().addInclude(WosFramework.getInstance().getCompleteResourcePath() + "/demo/js/CheckBoxDemo.js");
		theContributionContainer.getScriptManager().addInclude(WosFramework.getInstance().getCompleteResourcePath() + "/demo/js/RadioButtonDemo.js");
		theContributionContainer.getScriptManager().addInclude(WosFramework.getInstance().getCompleteResourcePath() + "/demo/js/WebOnSwingDemo.js");
		super.prepareResponse(aResponse);
	}
}

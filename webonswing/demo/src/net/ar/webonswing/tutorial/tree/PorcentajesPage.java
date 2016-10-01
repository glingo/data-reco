package net.ar.webonswing.tutorial.tree;

import javax.servlet.http.*;

import net.ar.webonswing.*;
import net.ar.webonswing.pages.*;

public class PorcentajesPage extends SaveContextDefaultHtmlPage
{
	public void prepareResponse(HttpServletResponse aResponse) throws Exception
	{
		theContributionContainer.getScriptManager().addInclude(WosFramework.getInstance().getCompleteResourcePath() + "/js/Porcentajes.js");
		super.prepareResponse(aResponse);
	}
}

package net.ar.webonswing.tutorial;

import net.ar.webonswing.WosFramework;
import net.ar.webonswing.managers.script.ScriptContributionContainer;
import net.ar.webonswing.ui.RootPaneUIContributor;

public class PopupExampleContributor extends RootPaneUIContributor
{
	public void doScriptContribution(ScriptContributionContainer aContributionManager)
	{
		aContributionManager.addInclude(WosFramework.getInstance().getCompleteResourcePath() + "/js/OpenDialogActionListener.js");
	}
}
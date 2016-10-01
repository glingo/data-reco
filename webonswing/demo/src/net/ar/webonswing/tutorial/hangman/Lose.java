package net.ar.webonswing.tutorial.hangman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import net.ar.webonswing.WosFramework;

public class Lose extends JDialog
{
	protected Visit visit;

	public Lose()
	{
		visit= Guess.getVisit();

		init();
	}

	protected void init()
	{
		JPanel contentPane= new JPanel();
		
		JButton playAgain= new JButton("Play again");
		playAgain.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				visit.startGame();
				WosFramework.showChildWindow(Lose.this, new Guess());
			}
		});
		
		contentPane.add(Guess.getLettersPanel()).setName("lettersPanel");
		contentPane.add(playAgain).setName("playAgainButton");
		contentPane.setLayout(WosFramework.getPropagateTemplateLayoutByNameFor("Lose.main"));
		setContentPane(contentPane);
	}
}
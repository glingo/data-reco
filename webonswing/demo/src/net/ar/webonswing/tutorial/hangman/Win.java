package net.ar.webonswing.tutorial.hangman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.ar.webonswing.WosFramework;

public class Win extends JDialog
{
	protected Visit visit;

	public Win()
	{
		visit= Guess.getVisit();
		init();
	}

	protected void init()
	{
		JPanel contentPane= new JPanel();

		JLabel guessLeft= new JLabel(new ImageIcon(Guess.guessesLeftImages[visit.getGame().getIncorrectGuessesLeft()]));
		JLabel scaffold= new JLabel(new ImageIcon(Guess.scaffoldImages[visit.getGame().getIncorrectGuessesLeft()]));

		JButton playAgain= new JButton("Play again");
		playAgain.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				visit.startGame();
				WosFramework.showChildWindow(Win.this, new Guess());
			}
		});

		contentPane.add(guessLeft).setName("guessesLeft");
		contentPane.add(scaffold).setName("scaffold");
		contentPane.add(Guess.getLettersPanel()).setName("lettersPanel");
		contentPane.add(playAgain).setName("playAgainButton");
		contentPane.setLayout(WosFramework.getPropagateTemplateLayoutByNameFor("Win.main"));
		setContentPane(contentPane);
	}
}
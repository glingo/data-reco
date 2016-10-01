package net.ar.webonswing.tutorial.hangman;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.ar.webonswing.WosFramework;
import net.ar.webonswing.tutorial.hangman.Visit.*;

public class Guess extends JDialog
{
	protected static Visit visit= new Visit();

	protected static String[] guessesLeftImages= new String[] { "images/Chalkboard_1x7.png", "images/Chalkboard_1x8.png", "images/Chalkboard_2x7.png", "images/Chalkboard_2x8.png", "images/Chalkboard_3x7.png", "images/Chalkboard_3x8.png"};
	protected static String[] scaffoldImages= new String[] { "images/scaffold-5.png", "images/scaffold-4.png", "images/scaffold-3.png", "images/scaffold-2.png", "images/scaffold-1.png", "images/scaffold.png"};
	protected static String[] letterImages= new String[] { "images/Chalkboard_5x3.png", "images/letter-spacer.png", "images/Chalkboard_1x1.png", "images/Chalkboard_1x2.png", "images/Chalkboard_1x3.png", "images/Chalkboard_1x4.png", "images/Chalkboard_1x5.png", "images/Chalkboard_1x6.png", "images/Chalkboard_2x1.png", "images/Chalkboard_2x2.png", "images/Chalkboard_2x3.png", "images/Chalkboard_2x4.png", "images/Chalkboard_2x5.png", "images/Chalkboard_2x6.png", "images/Chalkboard_3x1.png", "images/Chalkboard_3x2.png", "images/Chalkboard_3x3.png", "images/Chalkboard_3x4.png", "images/Chalkboard_3x5.png", "images/Chalkboard_3x6.png", "images/Chalkboard_4x1.png", "images/Chalkboard_4x2.png", "images/Chalkboard_4x3.png", "images/Chalkboard_4x4.png", "images/Chalkboard_4x5.png", "images/Chalkboard_4x6.png", "images/Chalkboard_5x1.png", "images/Chalkboard_5x2.png"};

	public Guess()
	{
		visit= getVisit();
		init();
	}

	protected void init()
	{
		JLabel guessLeft= new JLabel(new ImageIcon(guessesLeftImages[visit.getGame().getIncorrectGuessesLeft()]));
		JLabel scaffold= new JLabel(new ImageIcon(scaffoldImages[visit.getGame().getIncorrectGuessesLeft()]));

		JPanel contentPane= new JPanel();
		contentPane.add(guessLeft).setName("guessesLeft");
		contentPane.add(scaffold).setName("scaffold");
		contentPane.add(getLettersPanel()).setName("lettersPanel");
		contentPane.add(getGuessesPanel()).setName("guessesPanel");

		contentPane.setLayout(WosFramework.getPropagateTemplateLayoutByNameFor("Guess.main"));
		setContentPane(contentPane);
	}

	protected static JPanel getLettersPanel()
	{
		JPanel lettersPanel= new JPanel();
		lettersPanel.setLayout(new GridLayout(0, 1));
		for (int i= 0; i < visit.getLetters().length; i++)
		{
			char letter= visit.getLetters()[i].charAt(0);
			JLabel letterLabel= new JLabel(new ImageIcon(letterImages[letter - 95]));
			lettersPanel.add(letterLabel).setName("letterLabel");
		}

		return lettersPanel;
	}

	protected JPanel getGuessesPanel()
	{
		JPanel guessesPanel= new JPanel();
		guessesPanel.setLayout(new GridLayout(0, 1));
		GuessBean[] guesses= visit.getGuesses();
		for (int i= 0; i < guesses.length; i++)
		{
			final Visit.GuessBean guessBean= guesses[i];
			final char letter= guessBean.getLetter().charAt(0);

			JLabel guessLabel= new JLabel(new ImageIcon(letterImages[letter - 95]));
			guessLabel.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					String result= visit.makeGuess(letter);

					if (result.equals("lose"))
						WosFramework.showChildWindow(Guess.this, new Lose());

					if (result.equals("win"))
						WosFramework.showChildWindow(Guess.this, new Win());

					init();
				}
			});

			guessesPanel.add(guessLabel).setName("guessLabel");
		}

		return guessesPanel;
	}

	protected static Visit getVisit()
	{
		Visit visit= (Visit)WosFramework.getSessionContext().get("game");
		if (visit == null)
		{
			visit= new Visit();
			visit.startGame();
			WosFramework.getSessionContext().put("game", visit);
		}

		return visit;
	}
}
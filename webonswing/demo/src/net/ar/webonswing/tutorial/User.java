package net.ar.webonswing.tutorial;

public class User
{
	public static User[] users= new User[] { new User("Diego", "Maradona", "diego@maradona.com", 0), new User("Carlitos", "Tevez", "carlos@tevez.com", 1), new User("Andres", "D'Alessandro", "andres@dalessandro.com", 2), new User("Pablo", "Aimar", "pablo@aimar.com", 3), new User("Javier", "Saviola", "javier@saviola.com", 4)};

	protected int id;
	protected String firstName;
	protected String lastName;
	protected String email;

	public User()
	{
		this("", "", "", -1);
	}

	public User(String aFirstName, String aLastName, String anEmail, int anId)
	{
		firstName= aFirstName;
		lastName= aLastName;
		id= anId;
		email= anEmail;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setFirstName(String aString)
	{
		firstName= aString;
	}

	public void setLastName(String aString)
	{
		lastName= aString;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String aString)
	{
		email= aString;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int aL)
	{
		id= aL;
	}
}

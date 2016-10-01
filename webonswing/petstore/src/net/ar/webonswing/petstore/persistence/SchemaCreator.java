package net.ar.webonswing.petstore.persistence;

import net.ar.webonswing.petstore.model.*;
import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import net.sf.hibernate.tool.hbm2ddl.*;

public class SchemaCreator
{
	public static void main(String[] args) throws HibernateException
	{
		Configuration cfg= new Configuration().configure("/hibernate/config/hibernate.cfg.xml");
		SessionFactory sessionFactory= cfg.buildSessionFactory();
		new SchemaExport(cfg).create(true, true);
		Session session= sessionFactory.openSession();
		Transaction transaction= session.beginTransaction();

		session.save(createFishCategory());
		session.save(createDogsCategory());
		session.save(createReptilesCategory());
		session.save(createCatsCategory());
		session.save(createBirdsCategory());

		transaction.commit();

		session.close();
		sessionFactory.close();
	}

	protected static Category createBirdsCategory()
	{
		Category category5= new Category("Birds", "description of BIRDS");
		Product product15= new Product("AV-CB-01", "Amazon Parrot", "Great companion for up to 75 years");
		product15.addItem(new Item("EST-25", "Male Adult", 348.50, 120.00, "bird1"));
		product15.addItem(new Item("EST-26", "Female Adult", 348.50, 120.00, "bird2"));
		Product product16= new Product("AV-SB-02", "Finch", "Great stress reliever");
		category5.addProduct(product15);
		category5.addProduct(product16);
		return category5;
	}

	protected static Category createCatsCategory()
	{
		Category category4= new Category("Cats", "description of CATS");
		Product product13= new Product("FL-DSH-01", "Manx", "Great for reducing mouse populations");
		product13.addItem(new Item("EST-23", "Male Adult", 348.50, 120.00, "cat1"));
		product13.addItem(new Item("EST-24", "Female Adult", 348.50, 120.00, "cat2"));
		Product product14= new Product("FL-DLH-02", "Persian", "Friendly house cat, doubles as a princess");
		category4.addProduct(product13);
		category4.addProduct(product14);
		return category4;
	}

	protected static Category createReptilesCategory()
	{
		Category category3= new Category("Reptiles", "description of REPTILES");
		Product product11= new Product("RP-SN-01", "Rattlesnake", "Doubles as a watch dog");
		product11.addItem(new Item("EST-21", "Female Adult", 48.50, 20.00, "lizard1"));
		product11.addItem(new Item("EST-22", "Male Adult", 48.50, 20.00, "lizard2"));
		Product product12= new Product("RP-LI-02", "Iguana", "Friendly green friend");
		product12.addItem(new Item("EST-221", "Female Adult", 48.50, 20.00, "lizard3"));
		category3.addProduct(product11);
		category3.addProduct(product12);
		return category3;
	}

	protected static Category createDogsCategory()
	{
		Category category2= new Category("Dogs", "description of DOGS");
		Product product5= new Product("K9-BD-01", "Bulldog", "Friendly dog from England");
		product5.addItem(new Item("EST-9", "Spotless Male Puppy", 28.50, 22.00, "dog1"));
		product5.addItem(new Item("EST-10", "Spotless Female Puppy", 28.50, 22.00, "dog2"));
		Product product6= new Product("K9-PO-02", "Poodle", "Cute dog from France");
		product6.addItem(new Item("EST-11", "Spotted Male Puppy", 48.50, 32.00, "dog3"));
		product6.addItem(new Item("EST-12", "Spotted Female Puppy", 58.50, 32.00, "dog4"));
		Product product7= new Product("K9-DL-01", "Dalmation", "Great dog for a fire station");
		product7.addItem(new Item("EST-13", "Tailed", 108.50, 62.00, "dog5"));
		product7.addItem(new Item("EST-14", "Tailless", 108.50, 62.00, "dog6"));
		Product product8= new Product("K9-RT-01", "Golden Retriever", "Great family dog");
		product8.addItem(new Item("EST-15", "Tailed", 158.50, 82.00, "dog4"));
		product8.addItem(new Item("EST-16", "Tailless", 158.50, 82.00, "dog4"));
		Product product9= new Product("K9-RT-02", "Labrador Retriever", "Great hunting dog");
		product9.addItem(new Item("EST-17", "Tailed", 258.50, 100.00, "dog5"));
		product9.addItem(new Item("EST-18", "Tailless", 258.50, 100.00, "dog5"));
		Product product10= new Product("K9-CW-01", "Chihuahua", "Great companion dog");
		product10.addItem(new Item("EST-19", "Female Adult", 208.50, 100.00, "dog6"));
		product10.addItem(new Item("EST-20", "Female Adult", 208.50, 100.00, "dog6"));
		category2.addProduct(product5);
		category2.addProduct(product6);
		category2.addProduct(product7);
		category2.addProduct(product8);
		category2.addProduct(product9);
		category2.addProduct(product10);
		return category2;
	}

	protected static Category createFishCategory()
	{
		Category category1= new Category("Fish", "description of FISH");
		Product product1= new Product("FI-SW-01", "Angelfish", "Saltwater fish from Australia");
		product1.addItem(new Item("EST-1", "Large", 16.50, 10.00, "fish1"));
		product1.addItem(new Item("EST-2", "Thootless", 16.50, 10.00, "fish2"));
		Product product2= new Product("FI-SW-02", "Tiger Shark", "Saltwater fish from Australia");
		product2.addItem(new Item("EST-3", "Spotted", 18.50, 12.00, "fish3"));
		product2.addItem(new Item("EST-4", "Spotless", 18.50, 12.00, "fish4"));
		Product product3= new Product("FI-FW-01", "Koi", "Freshwater fish from Japan");
		product3.addItem(new Item("EST-5", "Male Adult", 18.50, 12.00, "fish3"));
		product3.addItem(new Item("EST-6", "Female Adult", 18.50, 12.00, "fish3"));
		Product product4= new Product("FI-FW-02", "Goldfish", "Freshwater fish from China");
		product4.addItem(new Item("EST-7", "Male Puppy", 18.50, 12.00, "fish4"));
		product4.addItem(new Item("EST-8", "Female Puppy", 18.50, 12.00, "fish4"));
		category1.addProduct(product1);
		category1.addProduct(product2);
		category1.addProduct(product3);
		category1.addProduct(product4);

		return category1;
	}
}
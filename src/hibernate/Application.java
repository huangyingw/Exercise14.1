package hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Application {
	private static SessionFactory sessionFactory;

	static {
		sessionFactory = new Configuration().configure()
				.buildSessionFactory();
	}

	public static void main(String[] args) {
		// Hibernate placeholders
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			// Create new instance of Car and set values in it
			Car car1 = new Car("BMW", "SDA231", 30221.00);
			Owner owner1 = new Owner("Frank Brown", "Chicago");
			car1.setOwner(owner1);
			// save the car
			session.persist(car1);
			
			// Create new instance of Car and set values in it
			Car car2 = new Car("Mercedes", "HOO100", 4088.00);
			Owner owner2 = new Owner("Jim Delows", "San Fransico");
			car2.setOwner(owner2);
			// save the car
			session.persist(car2);

			tx.commit();

		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			// retieve all cars
			@SuppressWarnings("unchecked")
			List<Car> carList = session.createQuery("from Car").list();
			for (Car car : carList) {
				System.out.println("brand= " + car.getBrand() + ", year= "
						+ car.getYear() + ", price= " + car.getPrice());
				System.out.println("Owner= " + car.getOwner().getName());
			}
			tx.commit();

		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		// Close the SessionFactory (not mandatory)
		sessionFactory.close();
	}
}

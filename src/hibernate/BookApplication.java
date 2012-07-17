package hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class BookApplication {
	private static SessionFactory sessionFactory;
	private static int bookid1 = 0;
	private static int bookid2 = 0;

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

			Book book1 = new Book("book1", "1234", "author1", 12.35, new Date());
			Book book2 = new Book("book2", "1345", "author2", 14.35, new Date());
			Book book3 = new Book("book3", "1543", "author3", 16.35, new Date());

			session.persist(book1);
			session.persist(book2);
			session.persist(book3);
			bookid1 = book1.getId();
			bookid2 = book2.getId();
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

			// retieve all books
			@SuppressWarnings("unchecked")
			List<Book> bookList = session.createQuery("from Book").list();				
			for (Book book : bookList) {
				System.out.println("title= " + book.getTitle() + ", isbn= "
						+ book.getISBN() + ", author= " + book.getAuthor()
						+ " , price =" + book.getPrice() + " , publish date= "
						+ book.getPublish_date());
			}
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

			Book book1 = (Book) session.get(Book.class, bookid1);
			Book book2 = (Book) session.get(Book.class, bookid2);
			book2.setAuthor("newAuthor");
			book2.setPrice(56.75);
			session.delete(book1);
			session.update(book2);

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

			// retieve all books
			@SuppressWarnings("unchecked")
			List<Book> bookList = session.createQuery("from Book").list();
			for (Book book : bookList) {
				System.out.println("title= " + book.getTitle() + ", isbn= "
						+ book.getISBN() + ", author= " + book.getAuthor()
						+ " , price =" + book.getPrice() + " , publish date= "
						+ book.getPublish_date());
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

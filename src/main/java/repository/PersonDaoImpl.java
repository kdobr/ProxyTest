package repository;

import anotations.Cacheable;
import model.Address;
import model.Person;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

public class PersonDaoImpl implements PersonDao {

    public void addPerson(Person person) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(person);
        transaction.commit();
        session.close();
    }

    @Cacheable(source = "local")
    public Person getPerson(int passNumber) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Person person = session.get(Person.class, passNumber);
        transaction.commit();
        session.close();
        return person;
    }

    public boolean changeAddress(int passNumber, Address address) {
        return false;
    }
}

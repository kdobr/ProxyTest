package repository;

import anotations.Cacheable;
import model.Address;
import model.Person;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import javax.persistence.Query;
import java.util.List;

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

    @Override
    public List<Person> getPersonsByAddressId(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Person where address_id = :id ");
        query.setParameter("id", id);
        List personList = query.getResultList();
        transaction.commit();
        session.close();
        return personList;
    }

    @Override
    public boolean addOrUpdateAddressToPerson(int passNumber, int addressId) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Person person = session.get(Person.class, passNumber);
        Address address = session.get(Address.class, addressId);
        person.setAddress(address);
        transaction.commit();
        session.close();
        return true;
    }
}

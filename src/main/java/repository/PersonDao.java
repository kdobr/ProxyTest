package repository;

import model.Person;

import java.util.List;

public interface PersonDao {

    public void addPerson(Person person);

    public Person getPerson(int passNumber);

    public List<Person> getPersonsByAddressId(int id);

    public boolean addOrUpdateAddressToPerson(int passNumber, int addressId);


}

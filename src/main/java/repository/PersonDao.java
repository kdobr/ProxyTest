package repository;

import model.Address;
import model.Person;

public interface PersonDao {

    public void addPerson(Person person);

    public Person getPerson(int passNumber);

    public boolean changeAddress(int passNumber, Address adress);

}

package controller;

import model.Person;
import proxy_factory.PersonProxyFactory;
import repository.PersonDao;
import repository.PersonDaoImpl;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        PersonDaoImpl personDao = new PersonDaoImpl();
        PersonDao personDaoProxy = PersonProxyFactory.getProxy(new Class[]{PersonDao.class}, personDao);
        personDaoProxy.addPerson(Person.builder()
                .passNumber(200000)
                .firstName("Semen")
                .lastName("Altov")
                .build());
        System.out.println(personDaoProxy.getPerson(200000));
        System.out.println(personDaoProxy.getPerson(200000));
        personDaoProxy.addOrUpdateAddressToPerson(200000, 2);
        personDao.addOrUpdateAddressToPerson(202020, 3);
        List<Person> personList = (personDaoProxy.getPersonsByAddressId(3));
        System.out.println(Arrays.toString(personList.toArray()));
    }
}

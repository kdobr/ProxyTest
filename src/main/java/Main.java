import repository.PersonDao;
import repository.PersonDaoImpl;

public class Main {

    public static void main(String[] args) {

        PersonDaoImpl personDao = new PersonDaoImpl();
        System.out.println(
                PersonProxyFactory.getProxy(new Class[]{PersonDao.class}, personDao).getPerson(101010));
    }
}

package proxy_factory;

import anotations.Cacheable;
import model.Person;
import repository.PersonDao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PersonProxyFactory {

    public static PersonDao getProxy(Class<PersonDao>[] interfaces, PersonDao obj) {
        return (PersonDao) Proxy.newProxyInstance(obj.getClass().getClassLoader(), interfaces,
                new DynamicInvocationHandler(obj));
    }

    private static class DynamicInvocationHandler implements InvocationHandler {
        private Object object;
        private Map<String, Method> cachedMethods = new HashMap<>();
        private Map<List<Object>, Person> personMap = new HashMap<>();

        public DynamicInvocationHandler(Object object) {
            this.object = object;
            cachedMethods = Arrays.stream(object.getClass().getMethods()).filter(m -> m.isAnnotationPresent(Cacheable.class))
                    .collect(Collectors.toMap(Method::getName, Function.identity()));
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            if (cachedMethods.containsKey(methodName) && methodName.equals("getPerson")) {
                Person person = personMap.get(Arrays.asList(args));
                if (person == null) {
                    person = (Person) method.invoke(object, args);
                    personMap.put(Collections.unmodifiableList(Arrays.asList(args)), person);
                }
                return person;
            }
            return method.invoke(object, args);
        }
    }
}
import anotations.Cacheable;
import lombok.AllArgsConstructor;
import repository.PersonDao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class PersonProxyFactory {

    public static PersonDao getProxy(Class<PersonDao>[] interfaces, PersonDao obj) {
        return (PersonDao) Proxy.newProxyInstance(obj.getClass().getClassLoader(), interfaces,
                new DynamicInvocationHandler(obj));
    }

    @AllArgsConstructor
    private static class DynamicInvocationHandler implements InvocationHandler {
        Object object;

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (!method.isAnnotationPresent(Cacheable.class)) {
                return method.invoke(object, args);
            }
            return method.getDeclaredAnnotation(Cacheable.class).source();
        }
    }
}
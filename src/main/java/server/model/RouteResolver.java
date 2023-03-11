package server.model;

import framework.Framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RouteResolver {

    private Object bean;

    private Method method;

    private Map<String, String> parameters;

    public RouteResolver(Class clazz, Method method) {
        this.method = method;
        this.parameters = new HashMap<>();
        this.bean = Framework.getInstance().getDependencyContainer().getBean(clazz);
    }

    public Object callMethod(Map<String, String> parameters) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(bean);
    }

    @Override
    public String toString() {
        return "RouteResolver{" +
                "bean=" + bean +
                ", method=" + method +
                ", parameters=" + parameters +
                '}';
    }
}

package framework.IoC;

import framework.Framework;
import framework.annotations.spring.Bean;
import framework.annotations.spring.Component;
import framework.annotations.spring.Controller;
import framework.annotations.spring.Service;
import framework.exceptions.DataAccessException;

public class DiEngine {

    public Object inject(Class clazz) throws DataAccessException, InstantiationException, IllegalAccessException {
        if(isClassInjectable(clazz)){
            return getOrCreateBean(clazz);
        } else {
            throw new DataAccessException("Class can't be injected: " + clazz.getName());
        }
    }

    private Object getOrCreateBean(Class clazz) throws DataAccessException, InstantiationException, IllegalAccessException {
        DependencyContainer dependencyContainer = Framework.getInstance().getDependencyContainer();
        if(dependencyContainer.contains(clazz)){
            return dependencyContainer.getBean(clazz);
        } else {
            return Framework.getInstance().getBeanFactory().createBean(clazz);
        }
    }

    private boolean isClassInjectable(Class clazz) {
        return clazz.isAnnotationPresent(Component.class) ||
                clazz.isAnnotationPresent(Service.class) ||
                clazz.isAnnotationPresent(Controller.class) ||
                clazz.isAnnotationPresent(Bean.class);
    }
}

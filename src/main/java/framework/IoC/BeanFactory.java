package framework.IoC;

import framework.Framework;
import framework.annotations.spring.*;
import framework.exceptions.DataAccessException;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class BeanFactory {
    public Object createBean(Class clazz) throws InstantiationException, IllegalAccessException, DataAccessException {
        Field[] fields = clazz.getDeclaredFields();
        Object object = clazz.newInstance();

        for(Field currField : fields){
            if(currField.isAnnotationPresent(Autowired.class)){
                recursiveInject(currField, object);
                doVerbose(currField);
            }
        }
        saveBeanToDependencyContainer(clazz, object);
        return object;
    }

    private void recursiveInject(Field currField, Object object) throws DataAccessException, IllegalAccessException, InstantiationException {
            currField.setAccessible(true);
            Class fieldsClass = currField.getType();

            if(fieldsClass.isInterface()){
                injectQualifierForInterface(object, currField);
            }else {
                injectQualifierForClass(currField, object, fieldsClass);
            }
        }

    private void injectQualifierForClass(Field currField, Object object, Class fieldsClass) throws DataAccessException, IllegalAccessException, InstantiationException {
        DiEngine diEngine = Framework.getInstance().getDiEngine();
        currField.set(object, diEngine.inject(fieldsClass));
    }

    private void injectQualifierForInterface(Object object, Field currField) throws DataAccessException, IllegalAccessException, InstantiationException {
        DependencyContainer dependencyContainer = Framework.getInstance().getDependencyContainer();
        DiEngine diEngine = Framework.getInstance().getDiEngine();
        if(!currField.isAnnotationPresent(Qualifier.class)){
            throw new RuntimeException("No qualifier!");
    }
        String qualifier = currField.getAnnotation(Qualifier.class).value();
        currField.set(object, diEngine.inject(dependencyContainer.getImplForInterface(qualifier)));

}

    private void saveBeanToDependencyContainer(Class clazz, Object object) {
        DependencyContainer dependencyContainer = Framework.getInstance().getDependencyContainer();
        if(clazz.isAnnotationPresent(Service.class) || clazz.isAnnotationPresent(Controller.class)){
            dependencyContainer.saveBean(clazz, object);
        }
        if(clazz.isAnnotationPresent(Bean.class) && ((Bean) clazz.getAnnotation(Bean.class)).scope() == Scope.SINGLETON){
            dependencyContainer.saveBean(clazz, object);
        }
    }
    private void doVerbose(Field field) {
        if(field.getAnnotation(Autowired.class).verbose()){
            System.out.println(String.format("Initialized %s %s in %s on %s with %s",
                    field.getType(),
                    field.getName(),
                    field.getDeclaringClass(),
                    LocalDateTime.now(),
                    field.hashCode()));
        }
    }
}

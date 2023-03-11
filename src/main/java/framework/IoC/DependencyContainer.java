package framework.IoC;

import framework.exceptions.DataAccessException;

import java.util.HashMap;
import java.util.Map;

public class DependencyContainer {
    private Map<String, Object> beans;
    private Map<String, Class> qualifiers;

    public DependencyContainer(Map<String, Class> qualifiers){
        this.beans = new HashMap<>();
        this.qualifiers = qualifiers;
    }

    public void saveBean(Class clazz, Object object){
        beans.put(clazz.getName(), object);
    }

    public Object getBean(Class clazz){
        return beans.get(clazz.getName());
    }

    public boolean contains(Class clazz){
        return beans.containsKey(clazz.getName());
    }

    public Class getImplForInterface(String qualifier) throws DataAccessException {
        if(qualifiers.containsKey(qualifier)){
            return qualifiers.get(qualifier);
        }else {
            throw new DataAccessException("No qualifier for " + qualifier);
        }
    }
}

package framework;

import framework.IoC.BeanFactory;
import framework.IoC.DependencyContainer;
import framework.IoC.DiEngine;
import framework.IoC.DiscoveryUtil;
import framework.exceptions.DataAccessException;

import java.util.List;
import java.util.Map;

public class Framework {
    private static Framework instance;
    private DependencyContainer dependencyContainer;
    private DiEngine diEngine;
    private BeanFactory beanFactory;

    private Framework(){

    }
    public static Framework getInstance(){
        if(instance == null){
            instance = new Framework();
        }
        return instance;
    }

    public void init(String packageName){
        try{
            this.beanFactory = new BeanFactory();
            this.diEngine = new DiEngine();
            Map<String, Class> qualifiers = DiscoveryUtil.discoverQualifiers(packageName);
            this.dependencyContainer = new DependencyContainer(qualifiers);
            registerAllControllers(packageName);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void registerAllControllers(String packageName) throws DataAccessException, InstantiationException, IllegalAccessException {
        List<Class> controllers = DiscoveryUtil.discoverControllers(packageName);
        for(Class clazz : controllers){
            diEngine.inject(clazz);
        }
    }

    public DependencyContainer getDependencyContainer() {
        return dependencyContainer;
    }

    public DiEngine getDiEngine() {
        return diEngine;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}

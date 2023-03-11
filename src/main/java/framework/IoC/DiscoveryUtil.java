package framework.IoC;

import framework.annotations.http.POST;
import framework.annotations.http.Path;
import framework.annotations.spring.Controller;
import framework.annotations.spring.Qualifier;
import server.dto.RequestMethod;
import server.model.Route;
import server.model.RouteResolver;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DiscoveryUtil {

    public static Map<String, Class> discoverQualifiers(String packageName){
        List<Class> classes = findAllClassesUsingClassLoader(packageName);
        return classes.stream().filter(clazz -> clazz.isAnnotationPresent(Qualifier.class)).collect(Collectors.toMap(clazz -> ((Qualifier) clazz.getAnnotation(Qualifier.class)).value(), clazz -> clazz));
    }

    public static List<Class> discoverControllers(String packageName) {
        List<Class> classes = findAllClassesUsingClassLoader(packageName);
        return classes.stream().filter(clazz -> clazz.isAnnotationPresent(Controller.class)).collect(Collectors.toList());
    }

    public static Map<Route, RouteResolver> discoverRoutes(String packageName){
        List<Class> classes = findAllClassesUsingClassLoader(packageName);
        List<java.lang.reflect.Method> methodsWithPath = classes.stream().filter(clazz -> clazz.isAnnotationPresent(Controller.class)).map(clazz -> List.of(clazz.getMethods())).reduce(new ArrayList<>(), DiscoveryUtil::mergeLists).stream().filter(method -> method.isAnnotationPresent(Path.class)).collect(Collectors.toList());
        Map<Route, RouteResolver> routeMapping = new HashMap<>();

        for(java.lang.reflect.Method method : methodsWithPath){
            Route route = new Route(method.getAnnotation(Path.class).value(), getRequestMethod(method));
            RouteResolver routeResolver = new RouteResolver(method.getDeclaringClass(), method);
            routeMapping.put(route, routeResolver);
        }
        return routeMapping;
    }

    private static List<Method> mergeLists(List<Method> baseMethods, List<Method> appendMethods) {
        baseMethods.addAll(appendMethods);
        return baseMethods;
    }

    private static RequestMethod getRequestMethod(java.lang.reflect.Method method){
        if(method.isAnnotationPresent(POST.class)){
            return RequestMethod.POST;
        }
        return RequestMethod.GET;
    }

    private static List<Class> findAllClassesUsingClassLoader(String packageName){
        List<Class> classes = new ArrayList<>();
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("[.]","/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        reader.lines().forEach(curr -> {
            if(curr.endsWith(".class")){
                classes.add(getClass(curr, packageName));
            }
            else{
                classes.addAll(findAllClassesUsingClassLoader(packageName + "." + curr));
            }
        });
        return classes;
    }

    private static Class getClass(String className, String packageName){
        try{
            return Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e){
            //handle the exception
        }
        return null;
    }
}

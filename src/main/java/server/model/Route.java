package server.model;

import server.dto.RequestMethod;

import java.util.Objects;

public class Route {

    private String path;

    private RequestMethod requestMethod;

    public Route(String path, RequestMethod requestMethod) {
        this.path = path;
        this.requestMethod = requestMethod;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Route route = (Route) o;
        return Objects.equals(path, route.path) && requestMethod == route.requestMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, requestMethod);
    }

    @Override
    public String toString() {
        return "Route{" +
                "path='" + path + '\'' +
                ", requestMethod=" + requestMethod +
                '}';
    }
}

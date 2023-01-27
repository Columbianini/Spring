# Chapter 1 Getting started with Spring
- Spring offers a `container`(called `Spring application context`) that creates and manages application components (called `beans`). This is a pattern called `dependency injection`
- How to guide the `Spring application context` to wire `beans` together    
    - XML-based configuration
    - Java-based configuration
    - automatic configuration
        - `autowiring`： Spring automatically inject the components with the other beans that they depend on
        - `component scanning`：Spring can autmatically discover components from an application's classpath and create them as beans in the `Spring application context`
        - `Spring Boot`：an extension of Spring Framework that offer many productivity enhancements
            - `autoconfiguration`: based on entries in the classpath, env variables, and other factors, Spring Boot can make reasonable guess of what components need to be wired and configured together
- start a project
    - starter dependencies: DevTools, Thymeleaf, Web

# Chapter 2 Developing Web applications
- job of MVC:
    - controller: fetch and process data
      - view: render data to HTML
- `lombok`: [`@Data`](https://projectlombok.org/features/Data) will automatically add getter/setter/toString/constructor to the class + you need to install lombok extension to the IDE (otherwise a lot error)
- 

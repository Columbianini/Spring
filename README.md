# Chapter 1 Getting started with Spring
- Spring offers a `container`(called `Spring application context`) that creates and manages application components (called `beans`). This is a pattern called `dependency injection`
- How to guide the `Spring application context` to wire `beans` together    
    - XML-based configuration
    - Java-based configuration
    - automatic configuration
        - `autowiring`： Spring automatically inject the components with the other beans that they depend on
        - `component scanning`：Spring can automatically discover components from an application's classpath and create them as beans in the `Spring application context`
        - `Spring Boot`：an extension of Spring Framework that offer many productivity enhancements
            - `autoconfiguration`: based on entries in the classpath, env variables, and other factors, Spring Boot can make reasonable guess of what components need to be wired and configured together
- start a project
    - starter dependencies: DevTools, Thymeleaf, Web

# Chapter 2 Developing Web applications
- job of MVC:
  - Model (controller's member function): defined in Controller class with `@org.springframework.web.bind.annotation.ModelAttribute`
  - view (html file under `/static`): render data to HTML using template such as avaServer Pages (JSP), Thymeleaf, FreeMarker, Mustache, and Groovy-based templates
    - template: HTML with some additional element attributes that guide a template in rendering request data.
  - controller (java class): handle HTTP requests
    - hand off a request to a view to render HTML (browser-displayed)
    - write data directly to the body of a response (RESTful) 
- Controller
  - Class-Level annotation
    - `lombok`: 
      - [`@Data`](https://projectlombok.org/features/Data) will automatically add getter/setter/toString/constructor to the class + you need to install lombok extension to the IDE (otherwise a lot error)
      - [`@Slf4j`](https://www.slf4j.org/) <=> `private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DesignTacoController.class);`
    - `@org.springframework.stereotype.Controller`: Spring will discover it through component scanning and automatically create an instance of `DesignTacoController` as a bean in the Spring application context (aka container)
    - `@org.springframework.web.bind.annotation.RequestMapping("/design")`: In this case, it specifies that `DesignTacoController` will handle requests whose path begins with `/design`
    - `org.springframework.web.bind.annotation.SessionAttributes("tacoOrder)`: the `TacoOrder` object that is put into the `model attribute` in the `DesignTacoController` should be maintained in session
  - Method-Level annotation (the methods are `controller`'s methods)
    - `org.springframework.web.bind.annotation.GetMapping`: when `GET` request happen, the function will activate 
    - `@org.springframework.web.bind.annotation.ModelAttribute`: when a request happen, the function will activate and it want to add tuple (data name, data value) to `org.springframework.ui.Model`
      - add many attributes at same time: `model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type))`
      - add one attribute at one time: `@ModelAttribute(name="tacoOrder")` + the decorated function return model value
- View
  - [`th:object`](https://www.thymeleaf.org/doc/tutorials/3.1/thymeleafspring.html#handling-the-command-object): (i.e. the name of form-backing bean) the name Spring MVC gives to objects that model a form’s fields and provide getter and setter methods that will be used by the framework for establishing and obtaining the values input by the user at the browser side
  - [`*{...}`](https://www.thymeleaf.org/doc/articles/standarddialect5minutes.html): selection expressions, just like variable expressions, except they will be executed on a nearest `th:object` instead of the whole context variables map (i.e. `Model Attributes`)
  - [`th:text`]: replace the text value inside the tag
  - [`th:each="ingredient : ${sauce}"`]: replicate the tag and its children in a for loop. In this case, we create a `ingredient` variable for the children tag, and it is one element of `Model["sauce"]`

ToDo
- I do not quite understand templates/design.html's ` <input th:field="*{ingredients}" type="checkbox" th:value="${ingredient.id}"/>` The part of `th:field` make me confusing. Let me know how it is rendered to html when continuing reading
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
- client send a request with a specific path, Spring will associate the specific path with a specific handler class. The handler class will run all `@MethodAttribute` function to get a `org.springframework.ui.Model` object and run the specific request's function (e.g. `@PostMapping`), which will return the view's name and bring `org.springframeworkui.Model` to the view
- For request handler,
  - return a string without prefix, this string is a filename under the `resources\templates` and the browser will render the **view**
  - return a string with prefix `redirect: ... `, it will run the code of `...`  related **handler** method
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
    - `org.springframework.web.bind.annotation.PostMapping`: when `POST` request happen, the function will activate
      - For `Post` handler method, you could have parameters
        - Form object parameter (without annotation `@ModelAttribute`) : When the form is submitted, the fields in the form are bound to properties of a Taco object (whose class is shown in the next listing) that’s passed as a parameter into processTaco()
        - Model Attribute parameter (with annotation `@ModelAttribute`): refer to the object (usually `@SessionAttributes(...)`) inside the `org.springframework.ui.Model` container
        - `org.springframework.web.bind.support.SessionStatus`: by setting `sessionStatus.setComplete()`, we close the session and clear the cached SessionAttributes, for this case, `TacoOrder`
      - when you want to redirect to another handler, pls return with prefix `redirect:...`, otherwise it will try to locate the file under `resources\templates` with filename equal to the return value
    - `@org.springframework.web.bind.annotation.ModelAttribute`: when a request happen, the function will activate and it want to add tuple (data name, data value) to `org.springframework.ui.Model`
      - add many attributes at same time: `model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type))`
      - add one attribute at one time: `@ModelAttribute(name="tacoOrder")` + the decorated function return model value
- View
  - [`th:object`](https://www.thymeleaf.org/doc/tutorials/3.1/thymeleafspring.html#handling-the-command-object): (i.e. the name of form-backing bean) the name Spring MVC gives to objects that model a form’s fields and provide getter and setter methods that will be used by the framework for establishing and obtaining the values input by the user at the browser side
  - [`th:field`]: (could be private) field of the above form object
  - [`*{...}`](https://www.thymeleaf.org/doc/articles/standarddialect5minutes.html): selection expressions, just like variable expressions, except they will be executed on a nearest `th:object` instead of the whole context variables map (i.e. `Model Attributes`)
  - [`th:text`]: replace the text value inside the tag
  - [`th:each="ingredient : ${sauce}"`]: replicate the tag and its children in a for loop. In this case, we create a `ingredient` variable for the children tag, and it is one element of `Model["sauce"]`
  - If there is no `action` inside the `<form>`, when the form is submitted, the browser will gather all the data in the form and send it to the server in an HTTP POST request to the same path for which a `GET` request displayed the form—the `/design` path
  - When the form is submitted, the fields in the form are bound to properties of a `Taco` object (whose class is shown in the next listing and without annotation `ModelAttribute`) that’s passed as a parameter into `@PostMapping processTaco()`
- Converter: 
  - a class 
    - that implements Spring's `Converter` interface and implements its `convert()` method to take one value and convert it to another
    - that is annotated with `@Component`: Spring will automatically use the converter when the conversion of request parameters to bound properties is needed
    - used when converting `<form>`'s `th:field` from type `String` to another type of the form object's field **with the same name**
- Validate:
  - 1st: import dependencies: `spring-boot-starter-validation`
  - 2nd: add annotation on domain objects, e.g. (`javax.validation.constraints.NotNull`, ` javax.validation.constraints.Size` , `javax.validation.constraints.NotBlank`)[https://jakarta.ee/specifications/bean-validation/3.0/apidocs/], (`org.hibernate.validator.constraints.CreditCardNumber`)[https://docs.jboss.org/hibernate/validator/5.1/api/org/hibernate/validator/constraints/package-summary.html]
  - 3rd: prefix `@Valid` on POST request handler function's form parameter
    - normally, you will also add `org.springframework.validation.Errors` parameter
  - 4th: in Thymeleaf html, add [`<span th:if="${#fields.hasErrors('ccNumber')}" th:errors="*{ccNumber}">cc Num Error</span>`](https://stackoverflow.com/questions/73597622/what-does-this-expression-mean-in-thymeleaf-form-validation)


# Chapter 3 Working with data
- Reading and Writing data to JDBC database
  - 1st: add `Id` and `Timestamp` to domain object
  - 2nd: add dependencies for `JdbcTemplate`: `spring-boot-starter-jdbc`
  - 3rd: add dependencies for embedded/external database: e.g. `com.h2database`
  - 4th: rename `resources\application.properties` to `resources\application.yml`, and add
    - spring:
      datasource:
      generate-unique-name: false
      name: tacocloud
    - the database URL will be `jdbc:h2:mem:tacocloud`
    - Spring Boot DevTools enables the database at http://localhost:8080/h2-console
  - 5th: create new repo interface `IngredientRepository`
  - 6th: implement above interface with class `JdbcIngredientRepository`
    - `@Repository` will make Spring auto discover it 
    - `@Autowired` on constructor: enables you to inject the object (`jdbcTemplate`) dependency implicitly
    - [`@Transactional`](https://spring.io/guides/gs/managing-transactions/): any failure causes the entire operation to roll back to its previous state and to re-throw the original exception
    - READ: `jdbcTemplate.query(...,...)`
    - UPDATE/INSERT: `jdbcTemplate.update(...)`
  - 7th: create `src/main/resources/schema.sql` to define schema of table with name same as the domain object
    -  If there’s a file named schema.sql in the root of the application’s classpath, then
       the SQL in that file will be executed against the database when the application starts
  - 8th: create `src/main/resources/data.sql`
    - Spring Boot will also execute a file named data.sql from the root of the classpath when
      the application starts
  - Here jdbcTemplate is not ORM. **The domain object fields do not have relationship with the schema of database** If you check the database `Taco`, the schema has no relationship with `Taco` class
  - `jdbcTemplate` and `jdbcOperation` worked as db connection.
    - `jdbcTemplate`: call `query(String, ...)`
    - `jdbcOperation`: call `update(PreparedStatementCreator, KeyHolder)` and you can get the automatic generated value for the `Identity` field in sql table
- Working with Spring Data JDBC (one of the Spring Data projects - I guess it is ORM. to be confirmed)
  - 1st: add Spring Data JDBC to the build  
  - 2nd: define repository interfaces
    - extend `org.springframework.data.repository.CrudRepository< param1, param2 >`
      - `param1`: object to be persisted 
      - `param2`: type of ID
  - 3rd: annotate the domain object
    - annotate the class with `@Table`
    - annotate the properties with `@Id`
    - preload data with `CommandLineRunner`
      - this is a SAM interface and a lambda function could be the interface's object
      - How to implement in Spring? As the return value of a function annotated with `@Bean` under `TacocloudApplication.java`
      - How it works? At application start, Spring will wire up (i.e. create) all the Beans. For `@Bean` function, Spring will run it and store its returned value in application context. Then in the application context, it will first run the `CommandLineRunner` object's `run` function
    - solve issue [`Caused by: org.springframework.dao.IncorrectUpdateSemanticsDataAccessException: Failed to update entity [Ingredient(id=FLTO, name=Flour Tortilla, type=WRAP)]; Id [FLTO] not found in database`](https://stackoverflow.com/questions/64030718/spring-does-update-instead-of-save)
- Working with Spring Data JPA (no need to write schema.sql)
  - add dependencies: `spring-boot-starter-data-jpa`
  - annotate the domain as entities:
    - annotations over domain class
      - `@jakarta.persistence.Entity`
      - `@lombok.NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)`
        - force = true: the Lombok-generated constructor setting properties to a default value of null, 0, or false
      - `@lombok.AllArgsConstructor`
      - [`@lombok.RequiredArgsConstructor`](https://javabydeveloper.com/lombok-requiredargsconstructor-examples/)
    - annotations over ID property:
      - `@jakarta.persistence.GeneratedValue(strategy = GenerationType.AUTO)`
      - `@jakarta.persistence.Id`
    - annotations over List<...> properties:
      - `@ManyToMany()`: declare the relationship between an object (e.g. Taco) and its List property (e.g. List<Ingredient>), a Taco can have many Ingredient, and one Ingredient can be in many Taco
      - `@OneToMany(cascade=CascadeType.ALL)`: TacoOrder::List<Taco>, one TacoOrder object can have many Taco objects, but one Taco object belong to only one TacoOrder. For the Cascade, if CascadeType.All, then if you delete one TacoOrder, you will delete all the Tacos related
    - declare JPA repositories
      - similar to JDBC
      - define interface extends `CrudRepository< object type, ID type >`
    - customize repositories
      - [naming Convention](https://www.baeldung.com/spring-data-derived-queries): < verb, e.g. find >By< field name e.g. deliveryZip >< predicate e.g. Between, IsNotNull... >And< field name >< predicate > (args...)
      - [`@Query( {JPA Query} )`] over a customized function name
- Working with MongoDB
  - Step 1: add dependencies for mongoDB: `spring-boot-starter-data-mongodb`
  - Step 2: add dependencies for embedded db for testing/developing purpose: `de.flapdoodle.embed.mongo`
    - H2 for relational data and Flapdoodle for non-relational data
    - embedded db = in-memory db, you won’t need to have a separate database running, but all data will be wiped clean when you restart the application
  - Step 3: Mapping domain types to documents
    - `@Id`: Designates a property as the document ID (from Spring Data Commons)
    - `@Document`: Declares a domain type as a document to be persisted to MongoDB
    - `@Field`: Specifies the field name (and, optionally, the order) for storing a property in the persisted document
    - `@Transient`: Specifies that a property is not to be persisted
  - not working 🤣


- Tips:
- Solved Error: `Failed to transfer...` by [link](https://stackoverflow.com/questions/5074063/maven-error-failure-to-transfer)
- You can always using method reference or lambda function to implement SAM(Single Abstract Method) interface
- you can use `orElse(null)` under `Optional` object
- Persist: Data persistence is the collective set of mechanisms that allow you to save ("persist") your data somewhere before it evaporates from memory when you turn the power off
- we `autowired`(DI) the interface rather than implementation [why?](https://stackoverflow.com/questions/12899372/spring-why-do-we-autowire-the-interface-and-not-the-implemented-class)


ToDo
- I do not quite understand templates/design.html's ` <input th:field="*{ingredients}" type="checkbox" th:value="${ingredient.id}"/>` The part of `th:field` make me confusing. Let me know how it is rendered to html when continuing reading
- How to do live reload debug?
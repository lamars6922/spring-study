# spring-study
교재 : 코드로 배우는 스프링 웹 프로젝트
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
PART 1 : 스프링 개발 환경 구축
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
JDK 1.8

STS(Spring Tool Suite) 플러그인

Tomcat 9

Maven 기반

Lombok 라이브러리 : Java 개발 시 자주 사용하는 getter/setter, toString(), 생성자 등을 자동으로 생성해 줌.

Oracle Database 연동 (11g Express 버전)

SQL Developer 설치

JDBC 연결

커넥션 풀(여러 명의 사용자를 동시에 처리해야 하는 웹 애플리케이션의 경우 사용) 설정

MyBatis 연동

ex00 : XML 기반

jex00 : JAVA 기반
(@Configuration 어노테이션을 이용해서 해당 클래스의 인스턴스를 이용해서 설정 파일을 대신함.)
(root-context.xml -> RootConfig.java, web.xml -> WebConfig.java)

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
스프링의 주요 특징
1. POJO 기반의 구성
2. 의존성 주입(DI)을 통한 객체 간의 관계 구성
3. AOP(Aspect-Oriented-Programming) 지원
4. 편리한 MVC 구조
5. WAS의 종속적이지 않은 개발 
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
사용된 어노테이션
Lombok 관련 :
1. @Setter : setter 메서드를 생성해주는 역할
2. @Data : @ToString, @EqualsAndHashCode, @Getter/@Setter, @RequiredArgsConstructor를 모두 결합한 형태로 한번에 자주 사용되는 모든 메서드들을 생성할 수 있다.
3. @Log4j : 로그 객체럴 생성

Spring 관련 :
1. @Component : 해당 클래스가 스프링에서 객체로 만들어서 관리하는 대상임을 명시함.(@ComponentScan : @Component가 있는 클래스를 스프링이 읽음)
2. @Autowired : 스프링 내부에서 자신이 특정한 객체의 의존적이므로 자신에게 해당 타입의 빈을 주입해주라는 표시.

Test 관련 :
1. @ContextConfiguration : 스프링이 실행되면서 어떤 설정 정보를 읽어 들여야 하는지를 명시합니다.
2. @Runwith : 테스트 시 필요한 클래스를 지정합니다.(스프링은 SpringJUnit4ClassRunner 클래스가 대상)
3. @Test : junit에서 해당 메서드가 jUnit 상에서 단위 테스트의 대상인지 알려줍니다.
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
PART 2 : 스프링 MVC 설정
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
스프링 MVC 프로젝트를 구성해서 사용한다는 의미는 내부적으로는 root-context.xml로 사용하는 일반 Java 영역(흔히 POJO(Plain Old Java Object))과 servlet-context.xml로 설정하는 Web 관련 영역을 같이 연동해서 구동하게 됩니다. WebApplicationContext라는 존재는 기존의 구조에 MVC 설정을 포함하는 구조로 만들어짐.

XML 기반 : ex01, Java 기반 : jex01

jex01 : ServletConfig 클래스는 기존의 servlet-context.xml에 설정된 모든 내용을 담아야하는데, 
1. @EnableWebMvc 어노테이션과 WebMvcConfigurer 인터페이스를 구현하는 방식
2. @Configuration과 WebMvcConfigurationSupport 클래스를 상속하는 방식-일반 @Configuration 우선 순위가 구분되지 않는 경우에 사용

예제는 1번 방식으로 제작함.
WebMvcConfigurer는 스프링 MVC와 관련된 설정을 메서드로 오버라이드 하는 형태를 이용할 때 사용합니다.

예제 프로젝트의 로딩 구조
프로젝트 구동 시 관여하는 XML은 web.xml, root-context.xml, servlet-context.xml 파일입니다. web.xml은 Tomcat 구동과 관련된 설정이고, 나머지 두 파일은 스프링과 관련된 설정입니다.

Web.xml : 가장 먼저 구동되는 Context Listener가 등록되어 있습니다. <context-param>에는 root-context.xml의 경로가 설정되어 있고, <listener>에는 스프링 MVC의 ContextLoaderListener가 등록되어 있는데 해당 웹 애플리케이션 구동 시 같이 동작함.
  
root-context.xml : 파일에 있는 빈(Bean) 설정들이 동작하게 됩니다. 정의된 객체(Bean)들은 스프링의 영역(context) 안에 생성되고 객체들 간의 의존성이 처리됩니다. 처리 후에는 스프링 MVC에서 사용하는 DispatcherServlet이라는 서블릿과 관련된 설정이 동작합니다.
  
servlet-context.xml : DispatcherServlet에서 XmlWebApplicationContext를 이용해서 servlet-context.xml를 로딩하고 해석하기 시작함. 이 과정에서 등록된 객체(Bean)들은 기존에 만들어진 객체(Bean)들과 같이 연동하게 됩니다.
  
스프링 MVC의 기본 사상 : Servlet/JSP에서는 HttpServletRequest/HttpServletResponse라는 타입의 객체를 이용해 브라우저에서 전송한 정보를 처리하는 방식입니다.
스프링 MVC의 경우 하나의 계층을 더하는 형태가 됩니다.
ㅁ 개발자의 코드 영역 <-> Spring MVC <-> Servlet/JSP
스프링 MVC를 이용하게 되면 개발자들은 직접적으로 HttpServletRequest/HttpServletResponse 등과 같이 Servlet/JSP의 API를 사용할 필요성이 현저하게 줄어듭니다.
스프링은 중간에 연결 역할을 하기 때문에 이러한 코드를 작성하지 않고도 원하는 기능을 구현할 수 있게 됩니다.
  
스프링 MVC의 특정한 클래스를 상속하거나 인터페이스를 구현하는 형태로 개발할 수 있었지만, 스프링 2.5버전부터 등장한 어노테이션 방식으로 최근 개발에는 어노테이션이나 XML 등의 설정만으로 개발이 가능하게 되었습니다.
  
모델2와 스프링 MVC : 스프링 MVC는 '모델 2'라는 방식으로 처리되는 구조입니다. 모델 2방식은 쉽게 말해서 '로직과 화면을 분리'하는 스타일의 개발 방식입니다.
  
  ![11](https://user-images.githubusercontent.com/57030114/122504823-070c7380-d036-11eb-9bbf-a8161e02d5a5.PNG)

  모델 2방식에서 사용자의 Request는 특별한 상황이 아닌 이상 먼저 Controller를 호출하게 됩니다. 설계의 가장 중요한 이유는 나중에 View를 교체하더라도 사용자가 호출하는 URL 자체에 변화가 없게 만들어 주기 때문입니다. 컨트롤러는 데이터를 처리하는 존재를 이용해서 데이터(Model)를 처리하고 Response 할 때 필요한 데이터(Model)를 View 쪽으로 전달하게 됩니다. Servlet을 이용하는 경우 개발자들은 Servlet API의 RequestDispatcher 등을 이용해서 이를 직접 처리해 왔지만 스프링 MVC는 내부에서 이러한 처리를 하고, 개발자들은 스프링 MVC의 API를 이용해서 코드를 작성하게 됩니다.
  
  스프링 MVC의 기본 구조는 아래 그림과 같이 표현할 수 있습니다.
  
  ![22](https://user-images.githubusercontent.com/57030114/122505282-fc061300-d036-11eb-9c42-7a5a70417385.PNG)
  
  1. 사용자의 Request는 Front-Controller인 DispatcherServlet을 통해 처리합니다.
  
  2, 3. HandlerMapping은 Request의 처리를 담당하는 컨트롤러를 찾기 위해서 존재합니다. HandlerMapping 인터페이스를 구현한 여러 객체들 중 RequestMappingHandlerMap-ping 같은 경우는 개발자가 @RequestMapping 어노테이션이 적용된 것을 기준으로 판단하게 됩니다. 적절한 컨트롤러가 찾아졌다면 HandlerAdapter를 이용해서 해당 컨트롤러를 동작시킵니다.
  
  4. Controller는 개발자가 작성하는 클래스로 실제 Request를 처리하는 로직을 작성하게 됩니다. View에 전달해야 하는 데이터는 주로 Model이라는 객체에 담아서 전달. 다양한 타입의 결과를 반환하는데 이에 대한 처리는 ViewResolver를 이용하게 됩니다. 
  
  5. ViewResolver는 Controller가 반환한 결과를 어떤 View를 통해서 처리하는 것이 좋을지 해석하는 역할을 합니다. 가장 흔한 설정은 servlet-context.xml에 정의된 Inter-nalResourceViewResolver입니다.
  
  6, 7. View는 실제로 응답을 보내야 하는 데이터를 Jsp 등을 이용해서 생성하는 역할을 하게 됩니다. 만들어진 응답은 DispathcherServlet을 통해서 전달됩니다.
  
  Request는 DispatcherServlet을 통하도록 설계되는데, 이런 방식을 Front-Controller 패턴, 이 패턴을 이용하는 경우에는 모든 Request의 처리에 대한 분배가 정해진 방식대로만 동작하기 때문에 좀 더 엄격한 구조를 만들어 낼 수 있습니다.
  
  스프링 MVC의 Controller의 특징
  1. HttpServletRequest, HttpServletResponse를 거의 사용할 필요 없이 필요한 기능 구현
  2. 다양한 타입의 파라미터 처리, 다양한 타입의 리턴 타입 사용 가능
  3. GET 방식, POST 방식 등 전송 방식에 대한 처리를 어노테이션으로 처리 가능
  4. 상속/인터페이스 방식 대신에 어노테이션만으로도 필요한 설정 가능
  
  @Controller, @RequestMapping
  @Controller : 자동으로 스프링의 객체로 등록
  @RequestMapping : 현재 클래스의 모든 메서드들의 기본적인 URL 경로가 됨. (클래스의 선언, 메서드 선언 사용 가능)
                    @RequestMapping의 경우 몇 가지의 속성을 추가할 수 있음. 주로 method 속성을 사용하는데 흔히 GET 방식, POST 방식을 구분해서 사용할 때 이용.
                    축약형으로 @GetMapping, @PostMapping, GET, POST 방식 모두를 지원해야 하는 경우 배열로 처리해서 지정할 수 있음.
  
  Controller를 작성할 때 가장 편리한 기능은 파라미터가 자동으로 수집되는 기능입니다. 예를 들어 SampleDTO에는 int 타입으로 선언된 age가 자동으로 숫자로 변환되는 것을 볼수 있음.
  만일 기본 자료형이나 문자열 등을 이용한다면 파라미터의 타입만을 맞게 선언해주는 방식을 사용할 수 있습니다.
  @RequestParam은 파라미터로 사용된 변수의 이름과 전달되는 파라미터의 이름이 다른 경우에 유용하게 사용됩니다.
  
  동일한 이름의 파라미터가 여러 개 전달되는 경우에는 ArrayList<> 등을 이용해서 처리가 가능합니다. 배열(예:String[])의 경우도 동일하게 처리할 수 있습니다. 
  
  만일 전달하는 데이터가 객체 타입이고 여러 개를 처리해야 한다면 약간의 작업을 통해서 한 번에 처리를 할 수 있습니다.
  
  @InitBinder : 변환이 가능한 데이터는 자동으로 변환되지만 경우에 따라서는 파라미터를 변환해서 처리해야 하는 경우도 존재합니다. (예 : '2018-01-01'과 같은 문자열로 전달된 데이터를 java.util.Date) 타입으로 변환하는 작업이 그러함. 스프링 Controller에서는 파라미터를 바인딩할 때 자동으로 호출되는 @InitBinder를 이용해서 이러한 변환을 처리할 수 있습니다.
  
  @DateTimeFormat : @InitBinder를 이용해서 날짜를 변환할 수도 있지만, 파라미터로 사용되는 인스턴스 변수에 @DateTimeFormat을 적용해도 변환이 가능합니다.
  
  Controller의 메서드를 작성할 때는 특별하게 Model이라는 타입을 파라미터로 지정할 수 있습니다. Model 객체는 JSP에 컨트롤러에서 생성된 데이터를 담아서 전달하는 역할을 하는 존재입니다.
  메서트의 파라미터에 Model 타입이 지정된 경우에는 스프링은 특별하게 Model 타입의 객체를 만들어서 메서드에 주입하게 됩니다.
  
  ![11](https://user-images.githubusercontent.com/57030114/124385581-4531b480-dd11-11eb-9e4e-87bfb731553e.PNG)

  Model을 사용해야 하는 경우는 주로 Controller에 전달된 데이터를 이용해서 추가적인 데이터를 가져와야 하는 상황입니다.
  
  1. 리스트 페이지 번호를 파라미터로 전달받고, 실제 데이터를 View로 전달해야 하는 경우
  2. 파라미터들에 대한 처리 후 결과를 전달해야 하는 


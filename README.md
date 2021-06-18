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

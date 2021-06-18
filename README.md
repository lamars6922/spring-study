# spring-study
교재 : 코드로 배우는 스프링 웹 프로젝트

PART 1 : 스프링 개발 환경 구축

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

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
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

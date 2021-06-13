# spring-study
교재 : 코드로 배우는 스프링 웹 프로젝트

PART 1 : 스프링 개발 환경 구축

JDK 1.8
STS(Spring Tool Suite) 플러그인
Tomcat 9
Maven 기반
Lombok 라이브러리 : Java 개발 시 자주 사용하는 getter/setter, toString(), 생성자 등을 자동으로 생성해 줌.

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

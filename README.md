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
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
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
스프링 MVC 프로젝트를 구성해서 사용한다는 의미는 내부적으로는 root-context.xml로 사용하는 일반 Java 영역(흔히 POJO(Plain Old Java Object))과 
servlet-context.xml로 설정하는 Web 관련 영역을 같이 연동해서 구동하게 됩니다. WebApplicationContext라는 존재는 기존의 구조에 MVC 설정을 포함하는 구조로 만들어짐.

XML 기반 : ex01, Java 기반 : jex01

jex01 : ServletConfig 클래스는 기존의 servlet-context.xml에 설정된 모든 내용을 담아야하는데, 
1. @EnableWebMvc 어노테이션과 WebMvcConfigurer 인터페이스를 구현하는 방식
2. @Configuration과 WebMvcConfigurationSupport 클래스를 상속하는 방식-일반 @Configuration 우선 순위가 구분되지 않는 경우에 사용

예제는 1번 방식으로 제작함.
WebMvcConfigurer는 스프링 MVC와 관련된 설정을 메서드로 오버라이드 하는 형태를 이용할 때 사용합니다.

예제 프로젝트의 로딩 구조
프로젝트 구동 시 관여하는 XML은 web.xml, root-context.xml, servlet-context.xml 파일입니다. web.xml은 Tomcat 구동과 관련된 설정이고, 나머지 두 파일은 스프링과 관련된 설정입니다.

Web.xml : 가장 먼저 구동되는 Context Listener가 등록되어 있습니다. <context-param>에는 root-context.xml의 경로가 설정되어 있고, 
<listener>에는 스프링 MVC의 ContextLoaderListener가 등록되어 있는데 해당 웹 애플리케이션 구동 시 같이 동작함.
  
root-context.xml : 파일에 있는 빈(Bean) 설정들이 동작하게 됩니다. 정의된 객체(Bean)들은 스프링의 영역(context) 안에 생성되고 객체들 간의 의존성이 처리됩니다. 
처리 후에는 스프링 MVC에서 사용하는 DispatcherServlet이라는 서블릿과 관련된 설정이 동작합니다.
  
servlet-context.xml : DispatcherServlet에서 XmlWebApplicationContext를 이용해서 servlet-context.xml를 로딩하고 해석하기 시작함. 
이 과정에서 등록된 객체(Bean)들은 기존에 만들어진 객체(Bean)들과 같이 연동하게 됩니다.
  
스프링 MVC의 기본 사상 : Servlet/JSP에서는 HttpServletRequest/HttpServletResponse라는 타입의 객체를 이용해 브라우저에서 전송한 정보를 처리하는 방식입니다.
스프링 MVC의 경우 하나의 계층을 더하는 형태가 됩니다.
ㅁ 개발자의 코드 영역 <-> Spring MVC <-> Servlet/JSP
스프링 MVC를 이용하게 되면 개발자들은 직접적으로 HttpServletRequest/HttpServletResponse 등과 같이 Servlet/JSP의 API를 사용할 필요성이 현저하게 줄어듭니다.
스프링은 중간에 연결 역할을 하기 때문에 이러한 코드를 작성하지 않고도 원하는 기능을 구현할 수 있게 됩니다.
  
스프링 MVC의 특정한 클래스를 상속하거나 인터페이스를 구현하는 형태로 개발할 수 있었지만, 스프링 2.5버전부터 등장한 어노테이션 방식으로 최근 개발에는 
어노테이션이나 XML 등의 설정만으로 개발이 가능하게 되었습니다.
  
모델2와 스프링 MVC : 스프링 MVC는 '모델 2'라는 방식으로 처리되는 구조입니다. 모델 2방식은 쉽게 말해서 '로직과 화면을 분리'하는 스타일의 개발 방식입니다.
  
  ![11](https://user-images.githubusercontent.com/57030114/122504823-070c7380-d036-11eb-9bbf-a8161e02d5a5.PNG)

  모델 2방식에서 사용자의 Request는 특별한 상황이 아닌 이상 먼저 Controller를 호출하게 됩니다. 
  설계의 가장 중요한 이유는 나중에 View를 교체하더라도 사용자가 호출하는 URL 자체에 변화가 없게 만들어 주기 때문입니다. 
  컨트롤러는 데이터를 처리하는 존재를 이용해서 데이터(Model)를 처리하고 Response 할 때 필요한 데이터(Model)를 View 쪽으로 전달하게 됩니다. 
  Servlet을 이용하는 경우 개발자들은 Servlet API의 RequestDispatcher 등을 이용해서 이를 직접 처리해 왔지만 스프링 MVC는 내부에서 이러한 처리를 하고, 
  개발자들은 스프링 MVC의 API를 이용해서 코드를 작성하게 됩니다.
  
  스프링 MVC의 기본 구조는 아래 그림과 같이 표현할 수 있습니다.
  
  ![22](https://user-images.githubusercontent.com/57030114/122505282-fc061300-d036-11eb-9c42-7a5a70417385.PNG)
  
  1. 사용자의 Request는 Front-Controller인 DispatcherServlet을 통해 처리합니다.
  
  2, 3. HandlerMapping은 Request의 처리를 담당하는 컨트롤러를 찾기 위해서 존재합니다. 
  HandlerMapping 인터페이스를 구현한 여러 객체들 중 RequestMappingHandlerMap-ping 같은 경우는 개발자가 @RequestMapping 어노테이션이 적용된 것을 기준으로 판단하게 됩니다. 
  적절한 컨트롤러가 찾아졌다면 HandlerAdapter를 이용해서 해당 컨트롤러를 동작시킵니다.
  
  4. Controller는 개발자가 작성하는 클래스로 실제 Request를 처리하는 로직을 작성하게 됩니다. View에 전달해야 하는 데이터는 주로 Model이라는 객체에 담아서 전달. 
  다양한 타입의 결과를 반환하는데 이에 대한 처리는 ViewResolver를 이용하게 됩니다. 
  
  5. ViewResolver는 Controller가 반환한 결과를 어떤 View를 통해서 처리하는 것이 좋을지 해석하는 역할을 합니다. 
  가장 흔한 설정은 servlet-context.xml에 정의된 Inter-nalResourceViewResolver입니다.
  
  6, 7. View는 실제로 응답을 보내야 하는 데이터를 Jsp 등을 이용해서 생성하는 역할을 하게 됩니다. 만들어진 응답은 DispathcherServlet을 통해서 전달됩니다.
  
  Request는 DispatcherServlet을 통하도록 설계되는데, 이런 방식을 Front-Controller 패턴, 이 패턴을 이용하는 경우에는 모든 Request의 처리에 대한 분배가 정해진 방식대로만 동작하기 때문에 좀 
  더 엄격한 구조를 만들어 낼 수 있습니다.
  
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
  
  @InitBinder : 변환이 가능한 데이터는 자동으로 변환되지만 경우에 따라서는 파라미터를 변환해서 처리해야 하는 경우도 존재합니다. 
  (예 : '2018-01-01'과 같은 문자열로 전달된 데이터를 java.util.Date) 타입으로 변환하는 작업이 그러함. 스프링 Controller에서는 파라미터를 바인딩할 때 자동으로 호출되는 
  @InitBinder를 이용해서 이러한 변환을 처리할 수 있습니다.
  
  @DateTimeFormat : @InitBinder를 이용해서 날짜를 변환할 수도 있지만, 파라미터로 사용되는 인스턴스 변수에 @DateTimeFormat을 적용해도 변환이 가능합니다.
  
  Controller의 메서드를 작성할 때는 특별하게 Model이라는 타입을 파라미터로 지정할 수 있습니다. Model 객체는 JSP에 컨트롤러에서 생성된 데이터를 담아서 전달하는 역할을 하는 존재입니다.
  메서트의 파라미터에 Model 타입이 지정된 경우에는 스프링은 특별하게 Model 타입의 객체를 만들어서 메서드에 주입하게 됩니다.

![11](https://user-images.githubusercontent.com/57030114/124386034-3f3cd300-dd13-11eb-900d-c84940f7dfe4.PNG)

  Model을 사용해야 하는 경우는 주로 Controller에 전달된 데이터를 이용해서 추가적인 데이터를 가져와야 하는 상황입니다.
  
  1. 리스트 페이지 번호를 파라미터로 전달받고, 실제 데이터를 View로 전달해야 하는 경우
  2. 파라미터들에 대한 처리 후 결과를 전달해야 하는 경우
  
  @ModelAttribute 어노테이션
  SampleDTO의 경우는 Java Bean의 규칙에 맞기 때문에 자동으로 다시 화면까지 전달됩니다. 전달될 때에는 클래스명의 앞글자는 소문자로 처리됩니다. 
  반면에 기본 자료형의 경우는 파라미터로 선언하더라도 기본적으로 화면까지 전달되지는 않습니다. 예를 들어 int 타입으로 선언된 변수는 전달되지 않습니다. 
  @ModelAttribute는 강제로 전달받은 파라미터를 Model에 담아서 전달하도록 할 때 필요한 어노테이션입니다. 
  @ModelAttribute가 걸린 파라미터는 타입에 관계없이 무조건 Model에 담아서 전달되므로, 파라미터로 전달된 데이터를 다시 화면에서 사용해야 할 경우에 유용하게 사용됩니다.
(기본 자료형에 @ModelAttribute를 적용할 경우에는 반드시 @ModelAttribute("page")와 같이 값(value)을 지정하도록 합니다.)
  
  Model 타입과 더불어서 스프링 MVC가 자동으로 전달해 주는 타입 중에는 RedirectAttributes 타입이 존재합니다. RedirectAttributes는 조금 특별하게도 일회성으로 데이터를 전달하는 용도로 
  사용됩니다. RedirectAttributes는 기존에 Servlet에서는 response.sendRedircet()를 사용할 때와 동일한 용도로 사용됩니다.
  
  ![11](https://user-images.githubusercontent.com/57030114/124612625-734bfb80-dead-11eb-8c4d-f22181a5628a.PNG)

  RedirectAttributes는 Model과 같이 파라미터로 선언해서 사용하고, addFlashAttribute(이름,값) 메서드를 이용해서 화면에 한 번만 사용하고 다음에는 사용되지 않는 데이터를 전달하기 위해서 
  사용합니다.
  
  스프링 MVC의 구조가 기존의 상속과 인터페이스에서 어노테이션을 사용하는 방식으로 변한 이후에 가장 큰 변화 중 하나는 리턴 타입이 자유로워 졌다는 점입니다.
  
  String : jsp를 이용하는 경우에는 jsp 파일의 경로와 파일이름을 나타내기 위해 사용합니다. 상황에 따라 다른 화면을 보여줄 필요가 있을 경우에 유용함.
  redirect, forward 키워드를 붙여서 사용가능.
  void : 호출하는 URL과 동일한 이름의 jsp를 의미합니다.
  VO, DTO 타입 : 주로 JSON 타입의 데이터를 만들어서 반환하는 용도로 사용합니다.
  ResponseEntity 타입 : response 할 때 Http 헤더 정보와 내용을 가공하는 용도로 사용합니다.
  Model, ModelAndView : Model로 데이터를 반환하거나 화면까지 같이 지정하는 경우에 사용합니다.
  HttpHeaders : 응답에 내용 없이 Http 헤더 메시지만 전달하는 용도로 사용합니다.
  
  commons-fileupload 파일 업로드 방식을 이용할 때, 파일 업로드의 경우에는 반드시 id 속성의 값을 multipartResolver로 정확하게 지정해야 함.(ex07)
  
  Controller를 작성할 때 예외 상황을 고려하면 처리해야 하는 작업이 너무 많아질 수밖에 없습니다.
  스프링 MVC에서는 이러한 작업을 다음과같은 방식으로 처리할 수 있습니다.
  @ExceptionHandler와 @ControllerAdvice를 이용한 처리
  @ResponseEntity를 이용하는 예외 메시지 구성

  @ControllerAdvice는 AOP를 이용하는 방식입니다. Controller를 작성할 때는 메소드의 모든 예외상황을 전부 핸들링해야 한다면 많은 양의 코드가 중복되겠지만, 
  AOP 방식을 이용하면 공통적인 예외상   황에 대해서는 별도로 @ControllerAdvice를 이용해서 분리합니다.
  
  @ControllerAdvice는 해당 객체가 스프링의 컨트롤러에서 발생하는 예외를 처리하는 존재임을 명시합니다.
  @ExceptionHandler는 어노테이션의 속성으로는 Exception 클래스 타입을 지정할 수 있습니다.

  WAS 구동 중에 에러과 관련된 HTTP 상태 코드 중 가장 흔한 코드는 '404'와 '500' 에러 코드입니다. 500 메시지는 'Internal Server Error' 이므로 @ExceptionHandler를 이용해서 처리되지만, 
  잘못된 URL을 호출했을 때 발생하는 404 에러 메시지는 다르게 처리하는 것이 좋습니다. 서블릿이나 JSP를 이용했던 개발 시에는 web.xml을 이용해서 별도의 에러 페이지를 지정할 수 있습니다. 
  에러 발생 시 추가적인 작업을 하기는 어렵기 때문에 스프링을 이용해서 404와 같이 WAS 내부에서 발생하는 에러를 처리하는 방식을 알아두는 것이 좋습니다.
 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
PART 3 : 기본적인 웹 게시물 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 스프링 MVC 프로젝트의 기본 
  
  웹 프로젝트는 3-tier 방식으로 구성합니다.
  
  1. Presentation Tier(화면 계층)는 화면에 보여주는 기술을 사용하는 영역입니다. 프로젝트의 성격에 맞춰 앱으로 제작하거나, CS로 구성되는 경우도 있습니다. 
  2. Business Tier(비즈니스 계층)는 순수한 비즈니스 로직을 담고 있는 영역입니다. 이 영역이 중요한 이유는 고객이 원하는 요구 사항을 반영하는 계층이기 때문입니다.
  3. Persistence Tier(영속 계층 혹은 데이터 계층)는 데이터를 어떤 방식으로 보관하고, 사용하는가에 대한 설계가 들어가는 계층입니다. 일반적인 경우에는 데이터베이스를 많이 이용하지만, 경우에 
  따라서 네트워크 호출이나 원격 호출 등의 기술이 접목될 수 있습니다.
  ![KakaoTalk_20210207_141026062](https://user-images.githubusercontent.com/57030114/133594025-7afcae4a-fc07-4b8c-849b-ff884f7109f5.jpg)
  
  스프링 MVC 영역은 Presentation Tier를 구성하게 되는데, 각 영역은 사실 별도의 설정을 가지는 단위로 볼 수 있습니다. root-context.xml, servlet-context.xml 등의 설정 파일이 해당 영역의 설
  정을 담당하였습니다. 스프링 Core 영역은 POJO의 영역입니다. 스프링의 의존성 주입을 이용해서 객체 간의 연관구조를 완성해서 사용합니다. MyBatis 영역은 현실적으로는 mybatis-spring을 이용해서 
  구성하는 영역입니다. SQL에 대한 처리를 담당하는 구조입니다.
  
  3-tier로 구성하는 가장 일반적인 설명은 '유지 보수'에 대한 필요성 때문이다. 각 영역은 설계 당시부터 영역을 구분하고, 해당 연결 부위는 인터페이스를 이용해서 설계하는 것이 일반적인 구성방식입
  니다.
   네이밍 규칙 : 
  1. xxxController : 스프링 MVC에서 동작하는 Controller 클래스를 설계할 때 사용합니다.
  2. xxxSerivce, xxxServiceImpl : 비즈니스 영역을 담당하는 인터페이스는 'xxxService'라는 방식을 사용하고, 인터페이스를 구현한 클래스는 'xxxServiceImpl'이라는 이름을 사용합니다.
  3. xxxDAO, xxxRepository: DAO나 Repository라는 이름으로 영역을 따로 구성하는 것이 보편적입니다. 
  4. VO, DTO : VO와 DTO는 일반적으로 유사한 의미로 사용하는 용어로, 데이터를 담고 있는 객체를 의미한다는 공통점이 있습니다. 다만, VO의 경우는 주로 Read Only의 목적이 강하고, 데이터 자체도 
  불변하게 설계하는 것이 정석입니다. DTO는 주로 데이터 수집의 용도가 좀 더 강합니다. 
  
  패키지 구성은 규모가 작은 프로젝트는 Controller 영역을 별도의 패키지로 설계하고, Service 영역 등을 하나의 패키지로 설계할 수 있습니다. 반면에, 프로젝트의 규모가 커져서 많은 Service 클래스
  와 Controller들이 혼재할 수 있다면 비즈니스를 단위별로 구분하고 다시 내부에서 Controller 패키지, Serivce 패키지 등으로 다시 나누는 방식을 이용합니다.
  
![a](https://user-images.githubusercontent.com/57030114/133598380-f792a787-4647-4c67-8060-f6a4fac9a398.jpg)

  프로젝트를 진행하기 전에 고객의 요구사항을 인식하고, 이를 설계하는 과정이 필요합니다.
  요구사항은 실제로 상당히 방대해 질 수 있으므로, 프로젝트에서는 단계를 정확히 구분해 주는 것이 좋습니다.
  요구사항은 온전한 문장으로 정리하는 것이 좋습니다. 
  
  각 화면을 설계하는 단계에서는 사용자가 입력해야 하는 값과 함께 전체 페이지의 흐름이 설계됩니다. 이 화면의 흐름을 URL로 구성하게 되는데 이 경우 GET/POST 방식에 대해서 같이 언급해두는 것이  
  좋습니다.

  영속/비즈니스 계층의 CRUD 구현
  
  영속 계층의 작업은 항상 다음과 같은 순서로 진행합니다.
  1. 테이블의 컬럼 구조를 반영하는 VO 클래스의 생성
  2. MyBatis의 Mapper 인터페이스의 작성/XML 처리
  3. 작성한 Mapper 인터페이스의 테스트
  
  간단한 SQL이라면 어노테이션을 이용해서 처리하는 것이 무난하지만, SQL이 점점 복잡해지고 검색과 같이 상황에 따라 다른 SQL문이 처리되는 경우에는 어노테이션은 그다지 유용하지 못한다는 단점이 있
  습니다. XML의 경우 단순 텍스트를 수정하는 과정만으로 처리가 끝나지만, 어노테이션의 경우 코드를 수정하고 다시 빌드하는 등의 유지 보수성이 떨어지는 이유로 기피하는 경우도 종종 있습니다.
  
  Mapper 인터페이스를 작성할 때는 리스트(select)와 등록(insert) 작업을 우선해서 작성합니다.
  
  SQL를 작성할 때, SQL Developer에서 먼저 실행해서 결과를 확인해야한다. 이유는 
  1. SQL이 문제가 없이 실행 가능한지를 확인하기 위한 용도와 
  2. 데이터베이스의 commit을 하지 않았다면 나중에 테스트 결과가 달라지기 때문에 이를 먼저 비교할 수 있도록 하기 위함입니다.
  
  XML을 작성할 때는 반드시 태그mapper의 namespace 속성값을 Mapper 인터페이스와 동일한 이름을 주는 것에 주의하고, 태그select 태그의 id 속성값은 메서드의 이름과 일치하게 작성합니다.
  resultType 속성의 값은 select 쿼리의 결과를 특정 클래스의 객체로 만들기 위해서 설정합니다.
  자동으로 PK 값이 정해지는 경우에는
  1. insert만 처리되고 생성된 PK 값을 알 필요가 없는 경우 
  2. insert문이 실행되고 생성된 PK 값을 알아야 하는 경우
  
  insert문의 몇 건의 데이터가 변경되었는지만을 알려주기 때문에 추가된 데이터의 PK 값을 알 수는 없지만, 1번의 SQL 처리만으로 작업이 완료되는 장점이 있습니다.
  @SelectKey는 주로 PK 값을 미리 SQL을 통해서 처리해 두고 특정한 이름으로 결과를 보관하는 방식입니다. @SelectKey를 이용하는 방식은 SQL을 한 번 더 실행하는 부담이 있기는 하지만 자동으로 
  추가되는 PK 값을 확인해야 하는 상황에서는 유용하게 사용될 수 있습니다.
  등록, 삭제, 수정과 같은 DML 작업은 '몇 건의 데이터가 삭제(혹은 수정) 되었는지'를 반환할 수 있습니다. Update는 delete와 마찬가지로 int 타입으로 메서드를 설계할 수 있습니다.
  
  비즈니스 계층
  
  비즈니스 계층은 고객의 요구사항을 반영하는 계층으로 프레젠테이션 계층과 영속 계층의 중간 다리 역할을 하게 됩니다. 영속 계층은 데이터베이스를 기준으로 해서 설계를 나눠 구현하지만, 비즈니스
  계층은 로직을 기준으로 해서 처리하게 됩니다. 일반적으로는 비즈니스 영역에 있는 객체들은 '서비스(Service)'라는 용어를 많이 사용합니다.
  설계를 할 때 각 계층 간의 연결은 인터페이스를 이용해서 느슨한(loose) 연결(결합)을 합니다. 인터페이스와 인터페이스를 구현한 클래스를 선언합니다.
  인터페이스를 구현한 클래스에 가장 중요한 부분은 @Service라는 어노테이션입니다. @Service는 계층 구조상 주로 비즈니스 영역을 담당하는 객체임을 표시하기 위해 사용합니다.
  
  프레젼테이션(웹) 계층의 CRUD 구현
  스프링 MVC의 Controller는 하나의 클래스 내에서 여러 메서드를 작성하고, @RequestMapping 등을 이용해서 URL을 분기하는 구조로 작성할 수 있기 때문에 하나의 클래스에서 필요한
  만큼 메서드의 분기를 이용하는 구조로 작성합니다.
  
  ![11](https://user-images.githubusercontent.com/57030114/134005244-2310a2bf-89c2-4c5b-86dd-4133ff746faf.jpg)

  화면 처리
  
  화면을 개발하기 전에는 반드시 화면의 전체 레이아웃이나 디자인이 반영된 상태에서 개발하는 것을 추천합니다.
  
  JSP 페이지를 작성하다 보면 JavaScript로 브라우저 내에서의 조작이 필요한 경우가 많습니다. 그래서 예제는 jQuery를 이용합니다.
  
  등록, 수정, 삭제 작업은 처리가 완료된 후 다시 동일한 내용을 전송할 수 없도록 아예 브라우저의 URL을 이동하는 방식을 이용합니다. 이러한 과정에서 하나 더 신경 써야 하는 것은 브
  라우저에 등록, 수정, 삭제의 결과를 바로 알 수 있게 피드백을 줘야 한다는 점입니다. redirect 처리를 할 때 RedirectAttributes라는 특별한 타입의 객체를 이용했습니다. 
  addFlashAttribute()의 경우 이러한 처리에 적합한데, 그 이유는 일회성으로만 데이터를 전달하기 때문입니다. 
  
  모달창은 활성화된 <div>를 선택하지 않고는 다시는 원래의 화면을 볼 수 없도록 막기 때문에 메시지를 보여주는데 효과적인 방식입니다.
  
  목록 페이지와 뒤로 가기 문제. 목록 페이지에서 각 게시물 제목에 a태그를 적용해서 조회 페이지로 이동하게 처리합니다. 최근에 웹페이지들은 사용자들의 트래픽을 고려해 목록 페이지
  에서 새창을 띄워서 조회 페이지로 이동하는 방식을 선호한다.
  조회 페이지의 이동은 JavaScript를 이용해서 처리할 수도 있고, 직접 a 태그를 이용해서도 처리가 가능합니다. 만일 조회 페이지를 이동하는 방식이 아니라 '새창'을 통해서 보고 싶다
  면, a 태그의 속성으로 target='_blank'를 지정하면 됩니다.
  
  뒤로 가기의 문제. '등록 - 목록 - 조회'까지는 순조롭지만 브라우저의 '뒤로 가기'를 선택하는 순간 다시 게시물의 등록 결과를 확인하는 방식으로 동작한다는 것입니다.

  ![11](https://user-images.githubusercontent.com/57030114/134284848-7ae44473-5625-4971-b1f8-3e6ee59054df.jpg)

  이러한 문제가 생기는 원인은 브라우저에서 '뒤로 가기'나 '앞으로 가기'를 하면 서버를 다시 호출하는 게 아니라 과거에 자신이 가진 모든 데이터를 활용하기 때문입니다.
  브라우저에서 조회 페이지와 목록 페이지를 여러 번 앞으로 혹은 뒤로 이동해도 서버에서는 처음에 호출을 제외하고 별다른 변화가 없는 것을 확인할 수 있습니다.
  
  이 문제를 해결하려면 window의 history 객체를 이용해서 현제 페이지는 모달창을 띄울 필요가 없다고 표시를 해 두는 방식을 이용해야 합니다. window의 history 객체는 스택 구조로
  동작합니다. history.replaceState() 를 활용합니다.
  
  첫번째 인자는 데이터 객체를 전달 가능하며 history.state에 저장되어 사용할 수 있습니다. 두번째 인자는 해당 페이지의 제목 변경이 가능한데 브라우저에서 기능이 구현되어 있지 않아 null을 전달하
  면 됩니다. 세번째 인자는 변경할 url 주소를 넣어주면 됩니다. 단순한 경로 이동도 가능하고 querystring도 추가할 수 있기 때문에 유용하게 사용가능 합니다.
  
  게시물의 수정 작업은 일반적으로 1. 조회 페이지에서 직접 처리하는 방식이나 2. 별도의 수정/삭제 페이지를 만들어서 해당 페이지에서 수정 삭제를 처리하는 방식을 많이 사용합니다.
  
  오라클 데이터베이스 페이징 처리
  페이징 처리는 크게 번호를 이용하거나 '계속 보기'의 형태로 구현됩니다. 번호를 이용한 페이징 처리는 과거 웹 초기부터 이어오던 방식이고, '계속 보기'는 Ajax와 앱이 등장한 이후에 '무한 스크롤'
  이나 '더 보기'와 같은 형태로 구현됩니다.
  
  order by의 문제. 프로그램을 이용해서 정렬을 해 본 적이 있다면 데이터의 양이 많을수록 정렬이라는 작업이 얼마나 많은 리소스를 소모하는지 알 수 있습니다. 데이터베이스는 경우에 따라서 수 백만
  혹은 천 만개 이상의 데이터를 처리하기 때문에 이 경우 정렬을 하게 되면 엄청나게 많은 시간과 리소스를 소모하게 됩니다.    
  데이터베이스를 이용할 때 웹이나 애플리케이션에 가장 신경 쓰는 부분은 1. 빠르게 처리되는것, 2. 필요한 양만큼만 데이터를 가져오는 것입니다. 거의 모든 웹페이지에서 페이징을 하는 이유는 최소한
  의 필요한 데이터만을 가져와서 빠르게 화면에 보여주기 위함입니다.
  만일 수백 만개의 데이터를 매번 정렬을 해야 하는 상황이라면 사용자는 정렬된 결과를 볼 때까지 오랜 시간을 기다려야만 하고, 특히 웹에서 동시에 여러 명의 사용자가 정렬이 필요한 데이터를 요청
  하게 된다면 시스템에는 많은 부하가 걸리게 되고 연결 가능한 커넥션의 개수가 점점 줄어서 서비스가 멈추는 상황을 초래하게 됩니다. 빠르게 동작하는 SQL을 위해서는 먼저 order by를 이용하는 작업
  을 가능하면 하지 말아야 합니다. order by는 데이터가 많은 경우에 엄청난 성능의 저하를 가져오기 때문에 1. 데이터가 적은 경우와 2. 정렬을 빠르게 할 수 있는 방법이 있는 경우가 아니라면 order
  by는 주의해야만 합니다.   
  
  데이터베이스에 전달된 SQL문은 SQL 파싱 - SQL 최적화 - SQL 실행과 같은 과정을 거쳐서 처리합니다. SQL 파싱 단계에서는 SQL 구문에 오류가 있는지 SQL을 실행해야 하는 대상 객체가 존재하는지를
  검사합니다. SQL 최적화 단계에서는 SQL이 실행되는데 필요한 비용을 계산하게 됩니다. 이 계산된 값을 기초로 해서 어떤 방식으로 실행하는 것이 가장 좋다는 것을 판단하는 '실행 계획'을 세우게 됩
  니다. SQL 실행 단계에서는 세워진 실행 계획을 통해서 메모리상에서 데이터를 읽거나 물리적인 공간에서 데이터를 로딩하는 등의 작업을 하게 됩니다. 가장 간단하게 실행 계획을 보는 방법은 
  '안쪽에서 바깥쪽으로, 위에서 아래로' 봐주면 됩니다. 'FULL'이라는 의미는 테이블 내의 모든 데이터를 스캔했다는 의미입니다. 실행 계획을 세우는 것은 데이터베이스에서 하는 역할이기 때문에
  데이터의 양이나 제약 조건 등의 여러 상황에 따라서 데이터베이스는 실행 계획을 다르게 작성합니다.
  
  order by 보다는 인덱스를 이용해서 정렬을 생략하는 방법입니다. 결론부터 말하자면 '인덱스'라는 존재가 이미 정렬된 구조이므로 이를 이용해서 별도의 정렬을 하지 않는 방법입니다.
  데이터베이스에서 PK는 상당히 중요한 의미를 가지는데, 흔히 말하는 '식별자'의 의미와 '인덱스'의 의미를 가집니다. 인덱스는 말 그대로 색인입니다. 색인을 이용하면 사용자들은 책 전체를 살펴볼
  필요 없이 색인을 통해서 자신이 원하는 내용이 책의 어디에 있는지 알 수 있습니다. 데이터베이스에 테이블을 만들 때 PK를 부여하면 지금까지 얘기한 인덱스라는 것이 만들어집니다. 인덱스와 실제
  데이터을 연결하는 고리는 ROWID라는 존재합니다. ROWID는 데이터베이스 내의 주소에 해당하는데 모든 데이터는 자신만의 주소를 가지고 있습니다.   
  인덱스에서 가장 중요한 개념 중 하나는 정렬이 되어 있다는 점입니다. 정렬이 이미 되어 있는 상태이므로 데이터를 찾아내서 이들을 SORT 하는 과정을 생략할 수 있습니다.
  
  오라클은 select문을 전달할 때 힌트라는 것을 사용할 수 있습니다. 힌트는 말 그래도 데이터베이스에 지금 내가 전달한 select문을 이렇게 실행해 주면 좋겠습니다. 라는 힌트입니다. 힌트는 개발자
  가 데이터베이스에 어떤 방식으로 실행해 줘야 하는지를 명시하기 때문에 조금 강제성이 부여되는 방식입니다.   
  힌트 사용 문법 : SELECT /*+ Hint name ( param...) */ column name, .... from table name ....   
 
  오라클 데이터베이스는 페이지 처리를 위해서 ROWNUM이라는 특별한 키워드를 사용해서 데이터에 순번을 붙여 사용합니다. ROWNUM은 실제 데이터가 아니라 테이블에서 데이터를 추출한 후에 처리되는
  변수이므로 상황에 따라서 그 값이 매번 달라질 수 있습니다. ROWNUM이라는 것은 데이터를 가져올 때 적용되는 것이고, 이 후에 정렬되는 과정에서는 ROWNUM이 변경되지 않는다는 것입니다.
  다른 말로는 정렬은 나중에 처리된다는 의미이기도 합니다.
  
  페이지 번호 1, 2의 데이터
  1. 필요한 순서로 정렬된 데이터에 ROWNUM을 붙인다.   
  2. 처음부터 해당 페이지의 데이터를 'ROWNUM <= 30'과 같은 조건으로 이용해서 구한다. (ROWNUM 조건에 반드시 1이 포함되어야 합니다.)   
  3. 구해놓은 데이터를 하나의 테이블처럼 간주하고 인라인뷰(SELECT문 안쪽 FROM에 다시 SELECT문)로 처리한다.   
  4. 인라인뷰에서 필요한 데이터만을 남긴다.
    
  페이징 처리를 위해서는 필요한 파라미터가 있는데 그것은 페이지 번호, 한 페이지당 몇개의 데이터를 보여줄 것인지가 결정되어야만 합니다.
  CDATA 섹션은 XML에서 사용할 수 없는 부등호를 사용하기 위함인데, XML을 사용할 경우에는 '<,>'는 태그로 인식하는데, 이로 인해 생기는 문제를 막기 위함입니다.
  
  페이징 화면 처리. 페이지를 보여주는 작업은   
  1. 브라우저 주소창에서 페이지 번호를 전달해서 결과를 확인하는 단계
  2. JSP에서 페이지 번호를 출력하는 단계
  3. 각 페이지 번호에 클릭 이벤트 처리
  4. 전체 데이터 개수를 반영해서 페이지 번호 조절
  
  페이징 처리할 때 필요한 정보들 현재 페이지 번호, 이전과 다음으로 이동 가능한 링크의 표시 여부, 화면에서 보여지는 페이지의 시작 번호와 끝 번호 페이지를 계산할 때 시작 번호를 먼저 하려고 하지만, 오히려 끝 번호를 먼저 계산해 두는 것이 수월합니다.   
   페이징의 끝 번호 계산 : this.endPage = (int)(Math.ceil(페이지번호/10.0)) * 10   
   끝 번호를 먼저 계산하는 이유는 시작 번호를 계산하기 수월하기 때문입니다.   
   페이징의 시작 번호 계산 : this.startPage = this.endPage - 9   
   끝 번호와 한 페이지당 출력되는 데이터 수의 곱이 전체 데이터 수보다 크다면 끝 번호는 다시 total을 이용해서 다시 계산되어야합니다.   
  realEnd = (int)(Math.ceil((total * 1.0) / amount))   
  if(realEnd < this.endPage) {   
    this.endPage = realEnd   
  }   
  먼저 전체 데이터 수를 이용해서 진짜 끝 페이지가 몇 번까지 되는지를 계산합니다. 만일 진짜 끝 페이지가 구해둔 끝 번호보다 작다면 끝 번호는 작은 값이 되어야만 합니다.   
  
  이전의 경우는 시작 번호가 1보다 큰 경우라면 존재하게 됩니다. this.prev = this.startPage > 1;
  다음으로 가는 링크의 경우 realEnd가 끝 번호보다 큰 경우에만 존재하게 됩니다. this.next = this.endPage < realEnd;
  
  검색 처리
  검색 조건은 일반저긍로 select 태그를 이용해서 작성하거나 checkbox를 이용하는 경우가 많습니다.
   
  게시물의 검색 기능은 다음과 같이 분류가 가능합니다.
   1. 제목/내용/작성자와 같이 단일 항목 검색
   2. 제목 or 내용, 제목 or 작성자, 내용 or 작성자, 제목 or 내용 or 작성자와 같은 다중 항목 검색
                                                                                     
  오라클은 페이징 처리에 인라인뷰를 이용하기 때문에 실제로 검색 조건에 대한 처리는 인라인뷰의 내부에서 이루어져야 합니다.   
  다중 항목 검색의 경우 AND 와 OR 같은 연산자의 우선 순위를 잘 생각해서 ()를 활용해야 한다.   
  MyBatis는 동적 태그 기능을 통해서 SQL을 파라미터들의 조건에 맞게 조정할 수 있는 기능을 제공합니다.   
  if 태그는 test라는 속성과 함께 특정한 조건이 true가 되었을 때 포함된 SQL을 사용하고자 할 때 사용합니다.   
  choose 태그 여러 상황들 중 하나의 상황에서만 동작합니다. 자바의 if ~ else와 유사합니다.   
  trim, where, set 태그는 단독으로 사용되지 않고 if태그, choose태그와 같은 태그들을 내포하여 SQL들을 연결해 주고, 앞 뒤에 필요한 구문들(AND, OR, WHERE 등)을 추가하거나 생략하는 역할을
  합니다. where 태그의 경우 태그 안쪽에서 SQL이 생성될 때는 WHERE 구문을 붙이고, 그렇지 않는 경우에는 생성되지 않습니다.   
  trim 태그는 태그의 내용을 앞의 내용과 관련되어 원하는 접두/접미를 처리할 수 있습니다. prefix, suffix, prefixOverrides, suffixOverrides 속성을 지정할 수 있습니다.   
  foreach 태그는 List, 배열, 맵 등을 이용해서 루프를 처리할 수 있습니다. 주로 IN 조건에서 많이 사용하지만, 경우에 따라서 복잡한 WHERE 조건을 만들때에도 사용할 수 있습니다.   
  foreach 태그를 배열이나 List를 이용하는 경우에는 item 속성만을 이용하면 되고, Map의 형태로 key와 value를 이용해야 할 때는 index와 item 속성을 둘 다 이용합니다.   
  sql태그를 이용해서 SQL의 일부를 별도로 보관하고, 필요한 경우 include태그로 include시키는 형태로 사용할 수 있습니다.   
  
  화면에서 검색은 페이지 번호가 파라미터로 우지되었던 것처럼 검색 조건과 키워드 역시 항상 화면 이동 시 같이 전송되어야합니다.   
  화면에서 검색 버튼을 클릭하면 새로 검색을 한다는 의미이므로 1페이지로 이동합니다.   
  한글의 경우 GET 방식으로 이동하는 경우 문제가 생길 수 있으므로 주의해야 합니다.   
  
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
PART 4 : REST 방식과 Ajax를 이용하는 댓글 처리 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
REST 방식으로 전환
                                                                                     
모바일 시대가 되면서 WEB 분야의 가장 큰 변화는 서버 역할의 변화라고 할 수 있습니다. 서버의 데이터를 소비하는 주체가 '브라우저'라는 특정한 애플리케이션으로 제한적이었다면, 모바일의 시대가 되면서 앱이나 웹은 서버에서 제공하는 데이터를 소비하게 됩니다. 앱에서 서버에 기대하는 것은 브라우저가 소화 가능한 HTML 데이터가 아닌 순수한 데이터만을 요구하게 되었습니다. 이처럼 서버의 역할은 점점 더 순수하게 데이터에 대한 처리를 목적으로 하는 형태로 진화하고 있습니다. 또한, 브라우저와 앱은 서버에서 전달하는 데이터를 이용해서 앱 혹은 브라우저 내부에서 별도의 방식을 통해서 이를 소비하는 형태로 전환하고 있습니다. 이러한 변화 속에서 최근의 웹페이지들은 대부분 페이지를 이동하면 브라우저 내의 주소 역시 같이 이동하는 방식을 사용하게 됩니다.

REST는 하나의 URI는 하나의 고유한 리소스를 대표하도록 설계된다는 개념에 전송방식을 결합해서 원하는 작업을 지정합니다. 예) URI + GET/POST/PUT/DELETE/...
스프링은 @RequestMapping이나 @ResponseBody와 같이 REST 방식의 데이터 처리를 위한 여러 종류의 어노테이션과 기능이 있습니다.

@RestController : 메서드의 리턴 타입으로 사용자가 정의한 클래스 타입(객체, 컬렉션, ResponseEntity)을 사용할 수 있고, 이를 JSON이나 XML로 자동으로 처리할 수 있습니다. 추가로 
@PathVariable(URL 경로의 일부를 파라미터로 사용할 때 이용), @RequestBody(JSON 데이터를 원하는 타입의 객체로 변환해야 하는 경우에 주로 사용) 같은 어노테이션을 이용하는 경우가 있습니다.
                                                                                     
Ajax 댓글 처리

REST 방식을 가장많이 사용하는 형태는 역시 브라우저나 모바일 App 등에서 Ajax를 이용해서 호출하는 것입니다. 하나의 게시물에 여러 개의 댓글을 추가하는 형태로 구성하고, 화면은 조회 화면상에서 별도의 화면 이동 없이 처리하기 때문에 Ajax를 이용해서 호출합니다.
                                                                                     
MyBatis는 두 개 이상이 데이터를 파라미터로 전달하기 위해서는
1. 별도의 객체로 구성하거나, 2. Map을 이용하는 방식, 3. @Param을 이용해서 이름을 사용하는 방식입니다. @Param의 속성값은 MyBatis에서 SQL을 이용할 때 '#{}'의 이름으로 사용이 가능합니다.

Rest 방식으로 동작하는 URL을 설계 할 때는 PK를 기준으로 작성하는 것이 좋습니다. REST 방식으로 처리할 때 주의해야 하는 점은 브라우저나 외부에서 서버를 호출할 때 데이터의 포맷과 서버에서 보내주는 데이터의 타입을 명확히 설계해야 하는 것입니다.

화면에서 사용되는 jQuery는 막강한 기능과 다양한 플러그인을 통해서 많은 프로젝트에서 기본으로 사용됩니다. 특히 Ajax를 이용하는 경우에는 jQuery의 함수를 이용해서 너무나 쉽게 처리할 수 있기 때문에 많이 사용합니다. javaScript 처리를 하다 보면 어느 순간 이벤트 처리와 DOM 처리 등 마구 섞여서 유지보수 하기 힘든 코드를 만드는 경우가 있어 모듈처럼 구성하는 방식을 이용하는 것이 좋습니다.
                                                                                     
DOM에서 이벤트 리스너를 등록하는 것은 반드시 해당 DOM 요소가 존재해야만 가능합니다. 동적으로 Ajax를 통해서 <li> 태그들이 만들어지면서 이후에 이벤트를 등록해야 하기 때문에 일반적인 방식이 아니라 '이벤트 위임'의 형태로 작성해야 합니다. 이벤트를 동적으로 생성되는 요소가 아닌 이미 존재하는 요소에 이벤트를 걸어주고, 나중에 이벤트의 대상을 변경해 주는 방식입니다.
  
  댓글의 화면 처리는 1. 게시물을 조회하는 페이지에 들어오면 기본적으로 가장 오래된 댓글들을 가져와서 1페이지에 보여줍니다.
  2. 1페이지의 게시물을 가져올 때 해당 게시물의 댓글의 숫자를 파악해서 댓글의 페이지 번호를 출력합니다.
  3. 댓글이 추가되면 댓글의 숫자만을 가져와서 최종 페이지를 찾아서 이동합니다.
  4. 댓글의 수정과 삭제 후에는 다시 동일 페이지를 호출합니다.
  
  
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
PART 5 : AOP와 트랜잭션
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
AOP는 흔히 '관점 지향 프로그래밍'이라는 용어로 번역된다. '관점'이라는 용어는 개발자들에게는 '관심사'라는 말로 통용됩니다. '관심사'는 개발 시 필요한 고민이나 염두에 두어야 하는 일이라고 생각할 수 있는데, 코드를 작성하면서 염두에 두는 일들은 주로 다음과 같습니다.
  1. 파라미터가 올바르게 들어왔을까?
  2. 이 작업을 하는 사용자가 적절한 권한을 가진 사용자인가?
  3. 이 작업에서 발생할 수 있는 모든 예외는 어떻게 처리해야 하는가?
위와 같은 고민들은 '핵심 로직'은 아니지만, 코드를 온전하게 만들기 위해서 필요한 고민들인데 전통적인 방식에서는 개발자가 반복적으로 이러한 고민을 코드에 반영하게 됩니다. AOP는 이러한 고민에 대한 문제를 조금 다른 방식으로 접근합니다. AOP가 추구하는 것은 '관심사의 분리'입니다. AOP는 개발자가 염두에 두어야 하는 일들은 별도의 '관심사'로 분리하고, 핵심 비즈니스 로직만을 작성할 것을 권장합니다.
 AOP는 과거에 개발자가 작성했던 '관심사 + 비즈니스 로직'을 분리해서 별도의 코드로 작성하도록 하고, 실행할 때 이를 결합하는 방식으로 접근합니다. 개발자가 작성한 코드와 분리된 관심사를 구현한 코드를 컴파일 혹은 실행시점에 결합시킵니다. 실제 실행은 결합된 상태의 코드가 실행되기 때문에 개발자들은 핵심 비즈니스 로직에만 근거해서 코드를 작성하고, 나머지는 어떤 관심사들과 결합할 것인지를 설정하는 것 만으로 모든 개발을 마칠 수 있게 됩니다.

 AOP 용어들 : 
    Target : 순수한 비즈니스 로직을 의미하고, 어떠한 관심사들과도 관계를 맺지 않습니다. 순수한 코어라고 볼수 있습니다.
    Proxy : Target을 전체적으로 감싸고 있는 존재. 내부적으로 Target을 호출하지만, 중간에 필요한 관심사들을 거쳐서 Target을 호출하도록 자동 혹은 수동으로 작성됩니다.
    JoinPoint : Target 객체가 가진 메서드입니다. 외부에서의 호출은 Proxy 객체를 통해서 Target 객체의 JoinPoint를 호출하는 방식이라고 이해할 수 있습니다.
  
  ![11](https://user-images.githubusercontent.com/57030114/143771204-d89f8c3c-02a7-4bbf-8b03-caec9b4b5735.PNG)

  Advice와 JoinPoint의 관계를 좀 더 상세하게 표현하자면 다음과 같습니다.
  
  ![22](https://user-images.githubusercontent.com/57030114/143770740-9557ee03-790f-4e2b-b742-9e864eac784c.PNG)
  
    Pointcut : 관심사와 비즈니스 로직이 결합되는 지점을 결정하는 것입니다. 관심사는 Aspect와 Advice라는 용어로 표현되어 있습니다.
    Advice : 실제 걱정거리를 분리해 놓은 코드를 의미합니다. 
             Before Advice - Target의 JoinPoint를 호출하기 전에 실행되는 코드입니다. 코드의 실행자체에는 관여할 수 없습니다.
             After Returning Advice

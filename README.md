## Http 프로토콜
  프로토콜 규약에 맞게 split 하여 Request 객체 생성, Response 객체 생성하여 클라이언트와 통신.

HttpRequest
- RequestLine(GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1)
    - HttpMethod
    - path
    - queryString
- Header
- Body

HttpResponse
- StatusLine
- Header
- Body

![page4image25038080](https://github.com/park-soo/was-practice/assets/127409329/7d0ce20d-3b4e-41b9-8f60-f9a9fa94d06e)


---------------------

## 톰켓(WAS) 통신을 직접 구현해보기..

Step1 - 사용자 요청을 메인 Thread 가 처리하도록 한다.

Step2 - 사용자 요청이 들어올 때마다 Thread를 새로 생성해서 사용자 요청을 처리하도록 한다.
- 이렇게만 했을 때 문제가 발생
- Thread는 생성될때마다 독립적인 stick 메모리 공간을 할당 (메모리를 할당 받는 작업 = 비싼 작업)
- 사용자 요청마다 Thread가 생성된다면 성능이 상당이 저하된다.
- 포퍼먼스 측면에서도 좋지 않다.
- 동시접속자 수가 많아 지면 많은 Thread가 생성 될텐데 Thread가 많아지게 되면
- cpu context switching 횟수도 증가, cpu와 메모리 사용량이 많이 증가
- 최악의 경우에는 서버에 리소스가 감당하지 못해서 서버가 다운될 가능성도 있다.
- Thread를 이미 고정된 개수 만큼 생성해 두고 이를 재활용하는 스레드 풀 개념을 적용하여 안정적인 서비스를 구현해야함.
  
Step3 - Thread Pool을 적용해 안정적인 서비스가 가능하도록 한다.


---------------------

## 서블릿 프로그래밍

![page7image55181936](https://github.com/park-soo/was-practice/assets/127409329/52de274a-a787-490e-ae39-81d6f5367f4a)

Servlet (Server + Applet의 합성어)
- 자바에서 웹 애플리케이션을 만드는 기술
- 자바에서 동적인 웹 페이지를 구현하기 위한 표준

ServletContainer
- 서블릿의 생성부터 소멸까지의 라이프사이클을 관리하는 역할
- 서블릿 컨테이너는 웹 서버와 소켓을 만들고 통신하는 과정을 대신 처리해준다. 개발자는 비즈니스 로직에만 집중하면 된다.
- 서블릿 객체를 싱글톤으로 관리 (인스턴스 하나만 생성하여 공유하는 방식)
- 상태를 유지(stateful)하게 설계하면 안됨
- Thread safety 하지 않음                 



* 상태를 유지(stateful)하게 설계하면 안됨!!
싱글톤 객체 멀티 쓰레드 환경에서 하나의 객체를 공유하게 되면, 우리가 뜻하지않는 레이스 컨디션 즉, 원치않는 결과가 나올 수 있다.

※ 레이스 컨디션 :  여러 프로세스, 혹은 쓰레드 동시에 하나의 자원에 접근하기 위해 경쟁하는 상태.




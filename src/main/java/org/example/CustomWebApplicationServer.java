package org.example;

import org.example.calculator.domain.Calculator;
import org.example.calculator.domain.PositiveNumber;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomWebApplicationServer {

    private final int port;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private static final Logger logger = LoggerFactory.getLogger(CustomWebApplicationServer.class);

    public CustomWebApplicationServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("[CustomWebApplicationServer] started {} port.", port);

            Socket clientSocket;
            logger.info("[CustomWebApplicationServer] waiting for client");

            while ((clientSocket = serverSocket.accept()) != null) {
                logger.info("[CustomWebApplicationServer] client connected!");

                 /**
                 * Step1 - 사용자 요청을 메인 Thread 가 처리하도록 한다.
                 */
//
//                try(InputStream in = clientSocket.getInputStream(); OutputStream out = clientSocket.getOutputStream()) {
//                    BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
//                    // line by line 으로 읽기 위해 변경함.
//
//                    DataOutputStream dos = new DataOutputStream(out);

                    /**
                    String line;
                    while ((line = br.readLine()) != "") {
                        System.out.println(line);
                    }

                    http 프로토콜
                    GET / HTTP/1.1
                    Host: localhost:8080
                    Connection: Keep-Alive
                    User-Agent: Apache-HttpClient/4.5.14 (Java/17.0.6)
                    Accept-Encoding: br,deflate,gzip,x-gzip
                    */


//                    HttpRequest httpRequest = new HttpRequest(br);
//
//                    // GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1
//                    if (httpRequest.isGetRequest() && httpRequest.matchPath("/calculate")) {
//                        QueryStrings queryStrings = httpRequest.getQueryStrings();
//
//                        int operand1 = Integer.parseInt(queryStrings.getValue("operand1"));
//                        String operator = queryStrings.getValue("operator");
//                        int operand2 = Integer.parseInt(queryStrings.getValue("operand2"));
//
//                        int result = Calculator.calculate(new PositiveNumber(operand1), operator, new PositiveNumber(operand2));
//                        byte[] body = String.valueOf(result).getBytes();
//
//                        HttpResponse response = new HttpResponse(dos);
//                        response.response200Header("application/json",body.length);
//                        response.responseBody(body);
//                    }
//                }

                /**
                 * Step2 - 사용자 요청이 들어올 때마다 Thread를 새로 생성해서 사용자 요청을 처리하도록 한다.
                 * 이렇게만 했을 때 문제가 발생
                 * Thread는 생성될때마다 독립적인 stick 메모리 공간을 할당 (메모리를 할당 받는 작업 = 비싼 작업)
                 * 사용자 요청마다 Thread가 생성된다면 성능이 상당이 저하된다.
                 * 포퍼먼스 측면에서도 좋지 않다.
                 * 동시접속자 수가 많아 지면 많은 Thread가 생성 될텐데 Thread가 많아지게 되면
                 * cpu context switching 횟수도 증가, cpu와 메모리 사용량이 많이 증가
                 * 최악의 경우에는 서버에 리소스가 감당하지 못해서 서버가 다운될 가능성도 있다.
                 *
                 * Thread를 이미 고정된 개수 만큼 생성해 두고 이를 재활용하는 스레드 풀 개념을 적용하여 안정적인 서비스를 구현해야함.
                 */
                //new Thread(new ClientRequestHandler(clientSocket)).start();

                /**
                 * Step3 - Thread Pool을 적용해 안정적인 서비스가 가능하도록 한다.
                 */
                executorService.execute(new ClientRequestHandler(clientSocket));
            }
        }
    }

}

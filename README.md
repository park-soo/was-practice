## Http 프로토콜
  프로토콜 규약에 맞게 split 하여 Request 객체 생성, Response 객체 생성하여 클라이언트와 통신.

HttpRequest
ㄴ RequestLine(GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1)
   ㄴ HttpMethod
   ㄴ path
   ㄴ queryString
ㄴ Header
ㄴ Body

HttpResponse
ㄴ StatusLine
ㄴ Header
ㄴ Body

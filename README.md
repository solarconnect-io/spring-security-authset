# spring-security-business

# 소개

SpringSecurity 기반으로 SSO를 구축 

세가지 인증 방법 지원
* 아이디/비밀번호
* OAuth
* JWT

세가지 Provider 서버
* Identity Provider : 인증 제공
* Service Provider : 사용자가 쓰는 사이트
* Resource Provider : 파일/이미지, 메시징 등 제공


## UsernamePassword Authentication

아이디/패스워드 인증


## OAuth Authentication

OAuth 클라이언트 역할
서버는 없음


## JWT Authentication

앱이나 서버에서 리소스에 접근할 때 권한 확인용도


# 권한 관리

## JWT Token의 권한

(global, local), (open, close), (machine, human)

global : 한 서비스 집단 전체에 사용되는 권한\
local : 한개의 서비스에서만 사용되는 권한

open : 공개\
close : 폐쇄, 나만보기, 내가 공유한 사람만 보기

machine : 서버에서 접근하는 리소스, 한 서비스에 소유권\
human : 사용자가 접근하는 리소스, 사용자에게 소유권

요약표현 예시
 * g.o.m
 * l.c.h
 * ...

## Resource 의 권한

### 1단계

Resource 별로 Previlege를 갖고 있음.
없는 경우에는 Role별 권한만 제공

### 2단계
리소스에 대한 권한은 다음과 같이 저장(Resource Provider에 따라 변경 가능)
<pre>
ALL     -1
NONE     0
VIEW     1
READ     2
WRITE    4
MODIFY   8+
DELETE   16
EXECUTE  32
</pre>

예) Article.0.15.5
<pre>
Article이라는 Resource는
Master -1
Owner   0  권한없음
Group   15 보기, 읽기, 쓰기, 수정
Public  5  보기, 쓰기
</pre>

ex)
웹페이지에서도 권한 개념을 사용하는경우
글쓰기 권한 - 글쓰기 버튼에 같은 권한 표시
VIEW가 없으면 안보임.
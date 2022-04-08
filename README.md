# 프로젝트 소개
책을 판매하는 쇼핑몰 서비스입니다. 

참여인원 : 3명 

기간 : 2022.02~2022.03(5주) 

내가 맡은 역할 : 관리자 페이지 


# 기술 스택
운영체제 : Windows 

개발 도구 : 이클립스, oracle database 

front-end : HTML, css, JS 

back-end : java, spring, mybatis 

서버 : apache tomcat 

그 외 : Github



# 주요 기능
●  사용자 페이지-> 김형주/김민진(다른 팀원들)
  1) 로그인 / 회원가입 (유효성 검사)![회원가입로그인](https://user-images.githubusercontent.com/98381560/162413585-bbc13ab4-1271-4f78-b57a-514057e9bd3e.gif) 
  2) 책 카테고리별 조회 / 베스트 도서 조회 / 신작 도서 조회 / 무료 도서 조회
  ![책상세보기](https://user-images.githubusercontent.com/98381560/162413493-a7f9e5a8-f50d-4e92-9358-a570622462ec.gif)
  3) 마이페이지 ( 위시리스트 / 장바구니 / 내 서재(구매목록) / 회원정보 변경
  4) 리뷰 작성 및 삭제(별점)
  5) 게시판 ( 공지사항 / q&a)





●  관리자 페이지 -> 김인겸(내가 구현한 부분)
  1) 관리자 로그인 (유효성 검사)
  2) e-book 데이터(CRUD) -> 등록 삭제 수정시 유효성 검사 ![책수정](https://user-images.githubusercontent.com/98381560/162413544-e28ff619-79fc-4f45-80dd-da636ed4d7a1.gif)
   ![책삭제](https://user-images.githubusercontent.com/98381560/162413472-36167121-8537-44e4-a7e7-6a6d345ecee6.gif)
  3) e-book 데이터 페이징 처리 ![페이징](https://user-images.githubusercontent.com/98381560/162413578-a40a9c7d-3975-4757-be5c-4013f3f5e23a.gif)
  4) q&a 답변하기
  5) 공지사항 (CRUD) -> 등록 삭제 수정시 유효성 검사
  6) 회원 조회  ![회원관리](https://user-images.githubusercontent.com/98381560/162413602-ad625178-4954-497b-aad9-6ff92121526b.gif)
  7) 주문상태 변경
  8) 책 데이터별 판매 실적(AJAX 활용하여 파이차트 / 바차트) ![판매실적](https://user-images.githubusercontent.com/98381560/162413567-1107403d-3452-4bae-af86-c346cc793636.gif)


●  데이터베이스    -> 김인겸(내가 구현한 부분)
https://github.com/InkyumKim/ebookDB
![db](https://user-images.githubusercontent.com/98381560/162378855-b72c3b91-b74a-4b4e-8135-400334d2319c.PNG)


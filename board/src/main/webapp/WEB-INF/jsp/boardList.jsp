<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>boardList</title>

</head>
<body>
	<!-- 검색 -->
    <div class="container search-container">
            <div class="search-box">
                <select name="searchType">
                    <option value="search_title" selected="${searchType=='search_title'}">제목</option>
                    <option value="search_writer" selected="${searchType=='search_writer'}">작성자</option>
                    <option value="search_content" selected="${searchType=='search_content'}">내용</option>
                </select>
                <input type="text" name="searchKeyword" value="${searchKeyword}" placeholder="검색어를 입력하세요" >
                <button class="btn btn-primary" type="submit">검색</button>
            </div>
    </div>
    <!-- 게시판 -->
        <table class="table">
            <tbody>
            <c:forEach var="board" items="${boardList}">
             <tr>
             	<c:if test="${isAdmin}">
        	    	<th><input type="checkbox" name="boardNo" value="${board.boardNo}"></th>
             	</c:if>
                <th ${board.boardType}>
                    <span>주문/결제</span>
                    <span>취소/반품/교환</span>
                </th>
                <td ${board.boardTitle}></td>
                <td ${board.userName}></td>
                <td ${board.boardUpdateDate}></td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="row">
		    <div class="col">
		        <button class="w-100 btn btn-primary btn-lg" type="submit" id="loginBtn">로그인</button>
		    </div>
		    <div class="col">
		        <button class="w-100 btn btn-secondary btn-lg" type="submit" id="logOutBtn">로그아웃</button>
		    </div>
		</div>
<script>
    $(document).ready(function() {
        $('#logOutBtn').on('click', function() {
        	
            // 서버에 로그인 요청 (AJAX)
            $.ajax({
                url: '/api/user/logout', // 로그인 요청 URL
                type: 'POST',
                contentType: 'application/json',
                success: function(response) {
                    // 로그인 성공 시, JWT 토큰을 받아 처리
                    if (response.body.resultCode === "200") {
                        alert("로그아웃 성공했습니다.");
                        window.location.href = '/api/user/login';
                    } else {
                        alert("로그인 실패: " + response.body.resultMsg);
                    }
                },
                error: function(xhr, status, error) {
                    // 상태 코드 확인 후 적절한 메시지 출력
                    if (xhr.status === 404) {
                        alert("잘못된 아이디나 비밀번호입니다.");
                    } else {
                        alert("로그인 실패: 서버 오류 발생");
                    }
                }
            });
        });
    });
</script>		
</body>
</html>
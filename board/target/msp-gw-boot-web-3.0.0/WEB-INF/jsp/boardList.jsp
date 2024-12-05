<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
</body>
</html>
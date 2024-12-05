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
	<!-- �˻� -->
    <div class="container search-container">
            <div class="search-box">
                <select name="searchType">
                    <option value="search_title" selected="${searchType=='search_title'}">����</option>
                    <option value="search_writer" selected="${searchType=='search_writer'}">�ۼ���</option>
                    <option value="search_content" selected="${searchType=='search_content'}">����</option>
                </select>
                <input type="text" name="searchKeyword" value="${searchKeyword}" placeholder="�˻�� �Է��ϼ���" >
                <button class="btn btn-primary" type="submit">�˻�</button>
            </div>
    </div>
    <!-- �Խ��� -->
        <table class="table">
            <tbody>
            <c:forEach var="board" items="${boardList}">
             <tr>
             	<c:if test="${isAdmin}">
        	    	<th><input type="checkbox" name="boardNo" value="${board.boardNo}"></th>
             	</c:if>
                <th ${board.boardType}>
                    <span>�ֹ�/����</span>
                    <span>���/��ǰ/��ȯ</span>
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
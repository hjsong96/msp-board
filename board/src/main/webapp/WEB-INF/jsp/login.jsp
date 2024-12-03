<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<html>
<head>
<title>Login</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="container">
    <div class="py-5 text-center">
        <h2>로그인 페이지</h2>
    </div>
    <h4 class="mb-3">로그인</h4>
        <div> <label for="userID">아이디</label>
            <input type="text" id="userID" name="userID" placeholder="아이디를 입력하세요">
        </div>
        <div>
            <label for="userPW">비밀번호</label>
            <input type="password" id="userPW" name="userPW" placeholder="비밀번호를 입력하세요">
        </div>
        <hr class="my-4">
        <div class="row">
            <div class="col">
                <button class="w-100 btn btn-primary btn-lg" type="submit" id="loginBtn">로그인</button>
            </div>
            <div class="col">
                <button class="w-100 btn btn-secondary btn-lg" type="button" id="joinBtn">회원가입</button>
            </div>
        </div>
</div> <!-- /container -->
<script>
    $(document).ready(function() {
        $('#loginBtn').on('click', function() {
            // 사용자 입력 값 가져오기
            const userID = $('#userID').val();
            const userPW = $('#userPW').val();

            // 서버에 로그인 요청 (AJAX)
            $.ajax({
                url: '/api/auth/login', // 로그인 요청 URL
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({ userID: userID, userPW: userPW }), // JSON 형태로 데이터 전송
                success: function(response) {
                    // 로그인 성공 시, JWT 토큰을 받아 처리
                    if (response.head.result_code === "200" && response.body.token) {
                        alert("로그인 성공했습니다.");
                        document.cookie = "token=" + response.body.token + "; path=/;";
                        window.location.href = "/"; 
                    } else {
                        alert("로그인 실패: " + response.head.result_msg);
                    }
                },
                error: function(xhr, status, error) {
                    // 상태 코드 확인 후 적절한 메시지 출력
                    if (xhr.status === 401) {
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

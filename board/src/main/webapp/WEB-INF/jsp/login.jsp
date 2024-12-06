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
                url: '/api/user/login', // 로그인 요청 URL
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({ userID: userID, userPW: userPW }), // JSON 형태로 데이터 전송
                success: function(response) {
                    // 로그인 성공 시, JWT 토큰을 받아 처리
                    if (response.body && response.body.resultCode === "200") {
                        alert("로그인 성공했습니다.");
                    } else {
                        alert("로그인 실패: " + (response.body ? response.body.resultMsg : "알 수 없는 오류"));
                    }
                },
                error: function(xhr, status, error) {
                    let resultMsg = "로그인 실패: 서버 오류 발생";

                    // 응답이 JSON 형식인지 확인하고 resultMsg를 가져옴
                    if (xhr.responseJSON) {
                        const body = xhr.responseJSON.body || {};
                        resultMsg = body.resultMsg || resultMsg;
                    }

                    alert(resultMsg); // resultMsg는 "요청 리소스를 찾을 수 없음" 등의 메시지
                }
            });
        });
    });
</script>
</body>
</html>

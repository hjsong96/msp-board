<%--
  Created by IntelliJ IDEA.
  User: uracle
  Date: 3/15/24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script>
        $(function() {
            $("#encodeSubmit").click(function () {
                if ($("#dbcpUrl").val() === '' || $("#dbcpName").val() === '' || $("#dbcpPasswd").val() === '') {
                    alert("Please fill in all fields (URL, NAME, PASSWORD).");
                    return;
                }

                const formData = $("#encodeForm").serialize();
                $.ajax({
                    url: "/dbcpEncode",                     //Send URL
                    type: "POST",                           //Send Type(HTTP Method)
                    data: formData,                         //Send Data
                    dataType: "json",                       //Return Type
                    success: function (data) {
                        console.info(data);
                        $("#encUrl").text(data.encURL);
                        $("#encName").text(data.encName);
                        $("#encPass").text(data.encPass);
                    },
                    error: function () {
                        alert("Error");
                    }
                });
            });
        });
    </script>
    <title>Title</title>
</head>
<body>
<form id="encodeForm">
    <table style="width: 80%" class="FormTable">
        <colgroup>
            <col width="20%"/>
            <col width="auto"/>
        </colgroup>
        <tbody>
        <tr>
            <td>URL</td>
            <td><input type="text" id="dbcpUrl" name="dbcpUrl" style="width: 300px" value=""></td>
        </tr>
        <tr>
            <td>NAME</td>
            <td><input type="text" id="dbcpName" name="dbcpName" value=""></td>
        </tr>
        <tr>
            <td>PASSWORD</td>
            <td><input type="text" id="dbcpPasswd" name="dbcpPasswd" value=""></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="button" id="encodeSubmit" value="암호화">
            </td>
        </tr>
        </tbody>
    </table>
    <br><br><br>
    <table class="FormTable" style="margin-top: 30px;width: 80%">
        <colgroup>
            <col width="20%"/>
            <col width="auto"/>
        </colgroup>
        <thead>
        <tr>
            <td colspan="2" style="height: 30px; background-color: #999999;"> 암호화 된 값</td>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>URL</td>
            <td id="encUrl"></td>
        </tr>
        <tr>
            <td>NAME</td>
            <td id="encName"></td>
        </tr>
        <tr>
            <td>PASSWORD</td>
            <td id="encPass"></td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>

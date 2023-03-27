<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <style type="text/css">
        * {
            margin: 0;
        }

        .content {
            display: flex;
            flex-flow: column;
            width: 1200px;
            margin: auto;
            text-align: center;
            /* 	border: 1px solid; */
        }

        form {
            float: right;
        }

        select {
            border: 1px solid gray;
        }

        input {
            width: 200px;
            height: 25px;
            border: 1px solid gray;
        }

    </style>
</head>
<body>
<c:import url="../default/header.jsp"/>
<div class="content">
    <h3>N O T I C E</h3>
    <br>

    <div class="notice">
        <table class="table table-hover"> <!-- class="table table-warning table-hover" -->
            <thead style="background:#f5f5f5;">
            <tr>
                <th colspan="1" style="width: 150px;">No.1</th>
                <th style="width: 150px;">아이디</th>
                <th style="width: 400px;">제목</th>
                <th style="width: 300px;">작성일</th>
                <th style="width: 150px;">조회수</th>

                <!-- <th>img</th> -->
            </tr>
            </thead>

            <c:if test="${noticeList.size() == 0 }">
                <tr>
                    <th colspan="6">저장 데이터 없음</th>
                </tr>
            </c:if>
            <c:forEach var="dto" items="${noticeList }">
                <tr>
                    <td>${dto.noticeId }</td>
                    <td>${dto.noticeWriterId }</td>
                    <td>
                        <a href="${contextPath }/notice/contentView?noticeId=${dto.noticeId }">${dto.noticeTitle }</a>
                    </td>
                    <td>${dto.noticeCreateDate }</td>
                    <td>${dto.noticeViews }</td>
                </tr>
            </c:forEach>
        </table>

        <tr>
            <td colspan="6">
                <c:forEach var="num" begin="1" end="${repeat }">
                    <a href="${contextPath }/notice/noticeAllList?num=${num}">${num } &nbsp;</a>
                </c:forEach>
                <a href="${contextPath }/notice/writeForm">글작성</a>
            </td>
        </tr>

    </div>
</div>
<c:import url="../default/footer.jsp"/>
</body>
</html>
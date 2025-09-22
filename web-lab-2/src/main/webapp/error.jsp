<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ошибка</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<header>
    <div id="student-info" class="container">
        <h1>Веб-программирование</h1>
        <h2>ФИО: Чэнь Жохань</h2>
        <h2>Вариант: 413107</h2>
    </div>
</header>

<div class="container">
    <div class="card results-section" id="point-table-card">
        <div class="card-header">
            <h3 class="card-title">
                Ошибка проверки
            </h3>
        </div>
        <div class="card-body">
            <h3><%= request.getAttribute("error") %></h3>
            <p><a href="${pageContext.request.contextPath}/controller"><button>Вернуть в главное</button></a></p>
        </div>
    </div>
</div>
</body>
</html>

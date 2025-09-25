<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Результат</title>
    <link rel="stylesheet" href="style.css">
    <style>
        #student-info {
            margin-bottom: 10px;
        }
        #resultTable {
            margin: 0 auto;
            text-align: center;
        }
        #resultTable td {
            text-align: center;
        }
        button {
            display: block;
            margin: 10px auto 0;
        }
        a {
            text-decoration: none;
        }
    </style>
</head>
<body>
    <header>
        <div id="student-info" class="container">
            <h1>Веб-программирование</h1>
            <h2>ФИО: Чэнь Жохань</h2>
            <h2>Вариант: 413107</h2>
        </div>
    </header>
<main>
    <div class="container">
        <div class="card results-section" id="point-table-card">
            <div class="card-header">
                <h3 class="card-title">
                    Результат проверки
                </h3>
            </div>
            <div class="card-body">
                <table id="resultTable" style="height: auto; width: 300px; padding: 20px;">
                    <tr>
                        <td>x :</td>
                        <td><%= request.getAttribute("x") %></td>
                    </tr>
                    <tr>
                        <td>y :</td>
                        <td><%= request.getAttribute("y") %></td>
                    </tr>
                    <tr>
                        <td>r :</td>
                        <td><%= request.getAttribute("r") %></td>
                    </tr>
                    <tr>
                        <td>Result :</td>
                        <td><%= request.getAttribute("check") %></td>
                    </tr>
                    <tr>
                        <td>CurTime :</td>
                        <td><%= request.getAttribute("clickTime") %></td>
                    </tr>
                    <tr>
                        <td>Execution time :</td>
                        <td><%= request.getAttribute("executionTime") %></td>
                    </tr>
                </table>
                <p>
                    <a href="${pageContext.request.contextPath}/controller">
                        <button type="reset">Вернуть в главное</button>
                    </a>
                </p>
            </div>
        </div>
    </div>
</main>
</body>
</html>

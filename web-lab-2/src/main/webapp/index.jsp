<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List"%>
<%@ page import="web.data.Point"%>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>web-lab-1</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/big.js/6.1.1/big.min.js"></script>
    <link rel="stylesheet" href="style.css">
    <%
        List<Point> results = (List<Point>) session.getAttribute("results");
        if (results == null) {
            results = new ArrayList<>();
            session.setAttribute("results", results);
        }
    %>
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

        <div class="top-grid">

            <div class="card" id="canvas">
                <div class="card-header">
                    <h3 class="card-title">Координатная область</h3>
                    <p class="card-subtitle">Изменяйте R для изменения размера фигур.</p>
                </div>
                <div class="card-body">
                    <canvas id="graph" width="500" height="500"></canvas>
                </div>
            </div>

            <div class="card" id="point-form-card">
                <div class="card-header">
                    <h3 class="card-title">Ввод координат точки</h3>
                    <p class="card-subtitle">Введите значения для проверки попадания точки</p>
                </div>
                <div class="card-body">
                    <form id="coordinateForm" action="controller" method="get" onsubmit="return handleFormSubmit();">
                        <div id="point-form">
                            <div class="form-group">
                                <label for="x">X:</label>
                                <select id="x" name="x" required>
                                    <option value="-4">-4</option>
                                    <option value="-3">-3</option>
                                    <option value="-2">-2</option>
                                    <option value="-1">-1</option>
                                    <option value="0">0</option>
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="y">Y:</label>
                                <input id="y" name="y" placeholder="от -3 до 3" required value="0">
                            </div>

                            <div class="form-group">
                                <label for="r">R:</label>
                                <input id="r" name="r" placeholder="от 1 до 4" required value="1">
                                <p class="form-hint">Изменяет размер фигур на графике</p>
                            </div>

                            <div class="form-actions">
                                <button type="reset">Сбросить</button>
                                <button type="submit">Проверить</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="card results-section" id="point-table-card">
            <div class="card-header">
                <h3 class="card-title">
                    Результаты проверки
                </h3>
                <p class="card-subtitle">История проверок попадания точки в область</p>
            </div>
            <div class="card-body">
                <div id="point-table">
                    <div class="table-header">
                        <h3>Последние результаты</h3>
                        <button id="clearTable">
                            <i class="fa fa-trash-o"></i>Очистить
                        </button>
                    </div>
                    <div id="empty-table" style="display: <%= results.isEmpty() ? "block" : "none" %>;">
                        <p>Нет данных</p>
                    </div>

                    <div class="table-container" id="tableContainer" style="display: <%= results.isEmpty() ? "none" : "block" %>;">
                        <table id="result-table">
                            <thead>
                            <tr>
                                <th scope="col">id</th>
                                <th scope="col">X</th>
                                <th scope="col">Y</th>
                                <th scope="col">R</th>
                                <th scope="col">Result</th>
                                <th scope="col">Click time</th>
                                <th scope="col">Execution time</th>
                            </tr>
                            </thead>
                            <tbody id="results">
                            <% int id = 1;
                                for (Point point : results) {
                            %>
                            <tr>
                                <td><%= id++ %></td>
                                <td><%= point.getX() %></td>
                                <td><%= point.getY() %></td>
                                <td><%= point.getR() %></td>
                                <td><%= point.isCheck() %></td>
                                <td><%= point.getClickTime() %></td>
                                <td><%= point.getExecutionTime() %></td>
                            </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        draw();

        document.getElementById('clearTable').addEventListener('click', clearTable);

        document.getElementById('r').addEventListener('change', function() {
            const rText = this.value;
            const r_num = new Big(rText);

            if (r_num.lt(1) || r_num.gt(4)) {
                alert('R должно быть в диапазоне от 1 до 4');
            } else {
                const isTableEmpty = document.getElementById('results').innerHTML.trim() === '';

                if (isTableEmpty){
                    const ctx = document.getElementById('graph').getContext('2d');
                    drawCoordinateSystem(ctx);
                    drawRegions(ctx);
                    return;
                }
                draw();
            }
        });

        document.getElementById('graph').addEventListener('click', function(e) {
            const rect = this.getBoundingClientRect();
            const x = e.clientX - rect.left;
            const y = e.clientY - rect.top;

            handleCanvasClick(x, y);
        });

    });

    function draw() {
        const canvas = document.getElementById('graph');
        const ctx = canvas.getContext('2d');

        drawCoordinateSystem(ctx);
        drawRegions(ctx);

        <%
        if (!results.isEmpty()) {
            for (Point point : results) {
        %>
            drawPoint(<%=point.getX()%>, <%=point.getY()%>, <%=point.getR()%>, <%=point.isCheck()%>);
        <%
            }
        }
        %>
    }

    function drawCoordinateSystem(ctx) {
        const width = ctx.canvas.width;
        const height = ctx.canvas.height;
        const centerX = width / 2;
        const centerY = height / 2;
        const maxValue = 6;
        const gridSize = width / (2 * maxValue);

        ctx.clearRect(0, 0, width, height);

        ctx.fillStyle = '#fafafa';
        ctx.fillRect(0, 0, width, height);

        ctx.strokeStyle = '#e2e8f0';
        ctx.lineWidth = 1;

        for (let x = 0; x < width; x += gridSize) {
            ctx.beginPath();
            ctx.moveTo(x, 0);
            ctx.lineTo(x, height);
            ctx.stroke();
        }

        for (let y = 0; y < height; y += gridSize) {
            ctx.beginPath();
            ctx.moveTo(0, y);
            ctx.lineTo(width, y);
            ctx.stroke();
        }

        ctx.strokeStyle = '#1e293b';
        ctx.lineWidth = 2;

        ctx.beginPath();
        ctx.moveTo(0, centerY);
        ctx.lineTo(width, centerY);
        ctx.stroke();

        ctx.beginPath();
        ctx.moveTo(centerX, 0);
        ctx.lineTo(centerX, height);
        ctx.stroke();

        ctx.fillStyle = '#1e293b';
        ctx.beginPath();
        ctx.arc(centerX, centerY, 4, 0, Math.PI * 2);
        ctx.fill();

        ctx.font = '10px Inter, sans-serif';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'top';

        for (let i = -maxValue; i <= maxValue; i++) {
            if (i === 0) continue;
            const x = centerX + i * gridSize;
            ctx.fillText(i, x, centerY + 5);
            drawTick(ctx, x, centerY);
        }

        ctx.textAlign = 'right';
        ctx.textBaseline = 'middle';

        for (let i = -maxValue; i <= maxValue; i++) {
            if (i === 0) continue;
            const y = centerY - i * gridSize;
            ctx.fillText(i, centerX - 5, y);
            drawTick(ctx, centerX, y);
        }

        ctx.font = '12px Inter, sans-serif';
        ctx.fillStyle = '#1e293b';
        ctx.textAlign = 'left';
        ctx.fillText('X', width - 10, centerY + 5);
        ctx.textAlign = 'right';
        ctx.fillText('Y', centerX - 5, 15);
    }

    function drawTick(ctx, x, y) {
        ctx.beginPath();
        ctx.moveTo(x, y - 3);
        ctx.lineTo(x, y + 3);
        ctx.stroke();
    }

    function drawRegions(ctx) {
        const canvas = document.getElementById('graph');
        const centerX = canvas.width / 2;
        const centerY = canvas.height / 2;
        const maxValue = 6;
        const gridSize = canvas.width / (2 * maxValue);
        const r = parseFloat(document.getElementById('r').value) || 2;

        drawCurrentRegions(ctx, r, centerX, centerY, gridSize);
    }

    function drawCurrentRegions(ctx, r, centerX, centerY, gridSize) {
        // 第三象限：矩形
        ctx.fillStyle = 'rgba(79, 70, 229, 0.15)';
        ctx.strokeStyle = 'rgba(79, 70, 229, 0.7)';
        ctx.lineWidth = 2;

        ctx.beginPath();
        ctx.rect(
            centerX - r * gridSize,
            centerY,
            r * gridSize,
            r * gridSize
        );
        ctx.fill();
        ctx.stroke();

        // 第一象限：三角形
        ctx.fillStyle = 'rgba(124, 58, 237, 0.15)';
        ctx.strokeStyle = 'rgba(124, 58, 237, 0.7)';

        ctx.beginPath();
        ctx.moveTo(centerX, centerY);
        ctx.lineTo(centerX + (r / 2) * gridSize, centerY);
        ctx.lineTo(centerX, centerY - r * gridSize);
        ctx.closePath();
        ctx.fill();
        ctx.stroke();

        // 第四象限：1/4圆
        ctx.fillStyle = 'rgba(63, 112, 219, 0.15)';
        ctx.strokeStyle = 'rgba(63, 112, 219, 0.7)';

        ctx.beginPath();
        ctx.moveTo(centerX, centerY);
        ctx.arc(centerX, centerY, r * gridSize / 2, 0, Math.PI / 2, false);
        ctx.closePath();
        ctx.fill();
        ctx.stroke();
    }


    function drawPoint(x, y, r, isInside) {
        const canvas = document.getElementById('graph');
        const ctx = canvas.getContext('2d');
        const centerX = canvas.width / 2;
        const centerY = canvas.height / 2;
        const maxValue = 6;
        const gridSize = canvas.width / (2 * maxValue);

        const pointX = centerX + x * gridSize;
        const pointY = centerY - y * gridSize;

        ctx.save();

        ctx.fillStyle = isInside ? 'rgba(16, 185, 129, 0.7)' : 'rgba(239, 68, 68, 0.7)';
        ctx.strokeStyle = isInside ? 'rgb(16, 185, 129)' : 'rgb(239, 68, 68)';
        ctx.lineWidth = 2;

        ctx.beginPath();
        ctx.arc(pointX, pointY, 4, 0, Math.PI * 2);
        ctx.fill();
        ctx.stroke();

        ctx.restore();
    }

    function handleCanvasClick(canvasX, canvasY) {
        const canvas = document.getElementById('graph');
        const centerX = canvas.width / 2;
        const centerY = canvas.height / 2;
        const maxValue = 6;
        const gridSize = canvas.width / (2 * maxValue);

        const x = (canvasX - centerX) / gridSize;
        const y = (centerY - canvasY) / gridSize;

        const xRounded = Math.round(x);

        if (xRounded < -4 || xRounded > 4 || y < -3 || y > 5) {
            alert('Точка вне допустимого диапазона');
            return;
        }

        document.getElementById('x').value = xRounded;
        document.getElementById('y').value = y.toFixed(2);

        console.log(xRounded, y.toFixed(2))

        document.getElementById('coordinateForm').submit();
    }

    function handleFormSubmit() {
        const x = parseFloat(document.getElementById('x').value);
        const yText = document.getElementById('y').value;
        const rText = document.getElementById('r').value;

        const numberRegex = /^-?\d+(\.\d+)?$/;

        const expressionRegex = /^-?\d+(\.\d+)?([+*/]-?\d+(\.\d+)?)*(-[+*/]?\d+(\.\d+)?)*$/;

        let y_num = new Big(0);

        if (numberRegex.test(yText)){
            y_num = new Big(yText);
        }else{
            if (expressionRegex.test(yText)){
                let expression = yText.match(/(\d+(\.\d+)?)|([+\-*/])/g);
                let finalExpressionString = expression.join('');
                let result = eval(finalExpressionString);
                y_num = new Big(result);
            }else{
                alert('Y должно быть число или выражение');
                return false;
            }
        }

        let yValue =  new Big(0);

        if (y_num.lt(-3) || y_num.gt(5)) {
            alert('Y должно быть в диапазоне от -3 до 5');
            return false;
        } else {
            yValue = y_num;
        }

        let rValue = new Big(0);

        if (numberRegex.test(rText)){
            rValue = new Big(rText);
        }else{
            alert('R должно быть числом');
            return false;
        }

        if (rValue.lt(1) || rValue.gt(4)) {
            alert('R должно быть в диапазоне от 1 до 4');
            return false;
        }

        console.log(x, yValue, rValue)

        return true;

    }

    function clearTable() {
        fetch('clear', { method: 'GET' })
            .then(response => {
                if (!response.ok) {
                    throw new Error('error');
                }
                return response;
            })
            .then(() => {
                const resultsBody = document.getElementById('results');
                const emptyTable = document.getElementById('empty-table');
                const tableContainer = document.getElementById('tableContainer');

                resultsBody.innerHTML = '';
                tableContainer.style.display = 'none';
                emptyTable.style.display = 'block';

                const canvas = document.getElementById('graph');
                const ctx = canvas.getContext('2d');
                ctx.clearRect(0, 0, canvas.width, canvas.height);
                drawCoordinateSystem(ctx);
                drawRegions(ctx);
            })
            .catch(error => console.error('clear error:', error));
    }

</script>

</body>

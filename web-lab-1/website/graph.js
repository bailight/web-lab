document.addEventListener('DOMContentLoaded', function() {
    initCanvas();

    document.getElementById('coordinateForm').addEventListener('submit', function(e) {
        e.preventDefault();
        handleFormSubmit();
    });

    document.getElementById('clearTable').addEventListener('click', clearTable);


    document.getElementById('R').addEventListener('change', function() {
        const canvas = document.getElementById('graph');
        const ctx = canvas.getContext('2d');

        drawCoordinateSystem(ctx);
        drawRegions(ctx);
    });


});

let lengthData = 1;

function initCanvas() {
    const canvas = document.getElementById('graph');
    const ctx = canvas.getContext('2d');

    drawCoordinateSystem(ctx);
    drawRegions(ctx);

    let data = getResponsesFromLocalStorage();
    for (let i = 0; i < data.length; i++) {
        console.log("Loaded from local storage:", data[i]);
        lengthData = i + 1;
        addToTable(data[i].x, data[i].y, data[i].r, data[i].result, data[i].clickTime, data[i].executionTime);
    }

    canvas.addEventListener('click', function(e) {
        const rect = canvas.getBoundingClientRect();
        const x = e.clientX - rect.left;
        const y = e.clientY - rect.top;

        handleCanvasClick(x, y);
    });
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
    const r = parseFloat(document.getElementById('R').value) || 2;

    drawCurrentRegions(ctx, r, centerX, centerY, gridSize);
}

function drawCurrentRegions(ctx, r, centerX, centerY, gridSize) {
    // 第二象限：长方形
    ctx.fillStyle = 'rgba(79, 70, 229, 0.15)';
    ctx.strokeStyle = 'rgba(79, 70, 229, 0.7)';
    ctx.lineWidth = 2;

    ctx.beginPath();
    ctx.rect(
        centerX - r * gridSize,
        centerY - (r/2) * gridSize,
        r * gridSize,
        (r/2) * gridSize
    );
    ctx.fill();
    ctx.stroke();

    // 第一象限：三角形
    ctx.fillStyle = 'rgba(124, 58, 237, 0.15)';
    ctx.strokeStyle = 'rgba(124, 58, 237, 0.7)';

    ctx.beginPath();
    ctx.moveTo(centerX, centerY);
    ctx.lineTo(centerX + r * gridSize, centerY);
    ctx.lineTo(centerX, centerY - r * gridSize);
    ctx.closePath();
    ctx.fill();
    ctx.stroke();

    // 第四象限：1/4圆
    ctx.fillStyle = 'rgba(63, 112, 219, 0.15)';
    ctx.strokeStyle = 'rgba(63, 112, 219, 0.7)';

    ctx.beginPath();
    ctx.moveTo(centerX, centerY);
    ctx.arc(centerX, centerY, r * gridSize, 0, Math.PI / 2, false);
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

    const animationSteps = 20;
    let currentStep = 0;

    function animatePoint() {
        if (currentStep < animationSteps) {
            currentStep++;

            ctx.save();

            drawCoordinateSystem(ctx);
            drawRegions(ctx);

            ctx.fillStyle = isInside ? 'rgba(16, 185, 129, 0.7)' : 'rgba(239, 68, 68, 0.7)';
            ctx.strokeStyle = isInside ? 'rgb(16, 185, 129)' : 'rgb(239, 68, 68)';
            ctx.lineWidth = 2;

            ctx.beginPath();
            ctx.arc(pointX, pointY, 4, 0, Math.PI * 2);
            ctx.fill();
            ctx.stroke();

            if (currentStep === animationSteps) {
                ctx.font = '12px Inter, sans-serif';
                ctx.fillStyle = isInside ? 'rgb(16, 185, 129)' : 'rgb(239, 68, 68)';
                ctx.textAlign = 'left';
                ctx.textBaseline = 'bottom';
                ctx.fillText(`(${x}, ${y.toFixed(2)})`, pointX + 10, pointY - 10);
            }

            ctx.restore();
            requestAnimationFrame(animatePoint);
        }
    }

    animatePoint();
}

function handleCanvasClick(canvasX, canvasY) {
    const canvas = document.getElementById('graph');
    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;
    const maxValue = 6;
    const gridSize = canvas.width / (2 * maxValue);

    const x = (canvasX - centerX) / gridSize;
    const y = (centerY - canvasY) / gridSize;

    console.log(x, y)

    const xRounded = Math.round(x * 2) / 2;

    if (xRounded < -2 || xRounded > 2 || y < -3 || y > 5) {
        alert('Точка вне допустимого диапазона');
        return;
    }

    document.getElementById('X').value = xRounded;
    document.getElementById('Y').value = y.toFixed(2);

    console.log(xRounded, y.toFixed(2))

    document.getElementById('coordinateForm').dispatchEvent(
        new Event('submit')
    );
}

function handleFormSubmit() {
    const x = parseFloat(document.getElementById('X').value);
    const yText = document.getElementById('Y').value;
    const r = parseFloat(document.getElementById('R').value);

    let num = new Big(yText);
    let yValue =  new Big(0);

    if (!/^-?\d+(\.\d+)?$/.test(yText)|| num.lt(-3) || num.gt(5)) {
        alert('Y должно быть в диапазоне от -3 до 5');
        return;
    } else {
        console.log(yText);
        yValue = new Big((yText));
    }

    const data = JSON.stringify({x: x, y: yValue, r: r});

    fetch('/fcgi-bin/server.jar', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: data
    })
        .then(response => response.json())
        .then(response => {
            addToTable(response.x, response.y, response.r, response.result, response.clickTime, response.executionTime)
            saveResponseToLocalStorage(response);
            drawPoint(response.x, response.y, response.r, response.result);
        })
        .catch(error => console.error('Error:', error));

}

function getResponsesFromLocalStorage() {
    let data = localStorage.getItem("data");
    if (data == null) {
        data = '[]';
    }
    const obj = JSON.parse(data);
    return Object.keys(obj).map((key) => obj[key]);
}

function saveResponseToLocalStorage(response) {
    let responses = getResponsesFromLocalStorage();
    responses.push(response);
    localStorage.setItem("data", JSON.stringify(responses));
}

function addToTable(x, y, r, result, clickTime, executionTime) {
    const resultsBody = document.getElementById('results');
    const emptyState = document.getElementById('empty-table');
    const tableContainer = document.getElementById('tableContainer');

    // 显示表格，隐藏空状态
    emptyState.style.display = 'none';
    tableContainer.style.display = 'block';

    const row = document.createElement('tr');
    row.className = 'table-row-animate';

    const id = lengthData++;

    row.innerHTML = `
        <td>${id}</td>
        <td>${x}</td>
        <td>${y.toFixed(2)}</td>
        <td>${r}</td>
        <td>
            <span class="check ${result ? 'check-success' : 'check-error'}">
                ${result ? 'Попал' : 'Не попал'}
            </span>
        </td>
        <td>${clickTime}</td>
        <td>${executionTime}</td>
    `;

    resultsBody.appendChild(row);

}

function clearTable() {
    const resultsBody = document.getElementById('results');
    const emptyState = document.getElementById('empty-table');
    const tableContainer = document.getElementById('tableContainer');

    setTimeout(() => {
        resultsBody.innerHTML = '';
        emptyState.style.display = 'block';
        tableContainer.style.display = 'none';

        localStorage.removeItem("data");

        lengthData = 1;

        drawCoordinateSystem(document.getElementById('graph').getContext('2d'));
        drawRegions(document.getElementById('graph').getContext('2d'));

    }, 500);

}

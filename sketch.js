const canvas = document.getElementById('canvas');
const ctx = canvas.getContext('2d');

const resetButton = document.getElementById('resetButton');
resetButton.addEventListener('click', () => {
  grid = makeArray(cols, rows);
});

let resolution = 40;
let width = 10 * resolution;
let height = width;
let rectW = 10;
let cols = width / rectW;
let rows = height / rectW;
let grid;

const inputSize = document.getElementById('size');
inputSize.addEventListener('change', (e) => {
  resolution = e.target.value;
  width = 10 * resolution;
  height = width;
  rectW = 10;
  cols = width / rectW;
  rows = height / rectW;

  canvas.width = width;
  canvas.height = height;
  grid = makeArray(cols, rows);
});

function makeArray(cols, rows) {
  let arr = new Array(cols);
  for (let i = 0; i < arr.length; i++) {
    arr[i] = new Array(rows);
    for (let j = 0; j < arr[i].length; j++) {
      arr[i][j] = 0;
    }
  }
  return arr;
}

canvas.width = width;
canvas.height = height;
function setup() {
  grid = makeArray(cols, rows);
}

const mouseEvent = (e) => {
  const x = e.clientX - canvas.offsetLeft;
  const y = e.clientY - canvas.offsetTop;
  const i = Math.floor(x / rectW);
  const j = Math.floor(y / rectW);
  grid[i][j] = 1;
};
canvas.addEventListener('mousemove', mouseEvent);

// Check if a row is within the bounds
function withinCols(i) {
  return i >= 0 && i <= cols - 1;
}

// Check if a column is within the bounds
function withinRows(j) {
  return j >= 0 && j <= rows - 1;
}

function draw() {
  for (let i = 0; i < cols; i++) {
    for (let j = 0; j < rows; j++) {
      let x = i * rectW;
      let y = j * rectW;
      ctx.fillStyle = 'white';
      ctx.fillRect(x, y, rectW, rectW);
      if (grid[i][j] === 1) {
        ctx.fillStyle = 'hsl( 40 ,80%, 30%)';
        ctx.fillRect(x, y, rectW, rectW);
      }
    }
  }

  let nextGrid = makeArray(cols, rows);

  for (let i = 0; i < cols; i++) {
    for (let j = 0; j < rows; j++) {
      let state = grid[i][j];
      if (state === 1) {
        let below = grid[i][j + 1] ?? 1;
        let randomDir = Math.floor(Math.random() < 0.5) ? 1 : -1;
        let belowA = withinCols(i + randomDir)
          ? grid[i + randomDir][j + 1]
          : -1;
        let belowB = withinCols(i - randomDir)
          ? grid[i - randomDir][j + 1]
          : -1;
        if (below === 0) {
          nextGrid[i][j + 1] = state;
        } else if (belowA === 0) {
          nextGrid[i + randomDir][j + 1] = state;
        } else if (belowB === 0) {
          nextGrid[i - randomDir][j + 1] = state;
        } else {
          nextGrid[i][j] = state;
        }
      }
    }
  }

  grid = nextGrid;
}

function animate() {
  // Zeichne das Raster
  draw();

  // Fordere den Browser an, die Funktion erneut aufzurufen
  requestAnimationFrame(animate);
}

// Starte die Animation
setup();
animate();

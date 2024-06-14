<template>
    <div class="sb-logo">
        <canvas
            width="400"
            height="200"
            class="canvas"
            ref="canvas" 
        />
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, nextTick } from 'vue';
// import { PROCESS } from '@/lib/Async';
// import { Api } from '@/lib/Api';
// import { dateToApiDateStr } from '@/lib/DateUtil';
// import { DayAccount } from '@/types/AccountTypes';
// import { compare } from '@/lib/StrUtil';
// import { cloneDeep } from 'lodash';
// import AccountDetailCategoryList from '@/components/AccountBookDetail/AccountDetailCategoryList.vue';
// import AccountDetailChart from '@/components/AccountBookDetail/AccountDetailChart.vue';
// import AccountDetailDateList from '@/components/AccountBookDetail/AccountDetailDateList.vue';

const canvas = ref<HTMLCanvasElement  | null>(null);
let dotArray: Dot[][] = [];
let ctx, height, width;
let realHeight, realWidth;
let size = 10;
let count = 10;

setInterval(() => {
    for(let i = 0; i < Math.floor(Math.random() * count); i++) {
        const dot = randomDot();
        if (dot.enable) i--;
        else dot.enable = true;
    }
}, 100)


function randomDot() {
    const ranX = Math.floor(Math.random() * width ) ;
    const ranY = Math.floor(Math.random() * height) ;

    return dotArray[ranY][ranX];
}

onMounted(() => {
    if (canvas.value) {
        ctx = canvas.value.getContext("2d");
        
        realWidth = canvas.value.offsetWidth;
        realHeight = canvas.value.offsetHeight;

        width = canvas.value.offsetWidth / size + 1;
        height = canvas.value.offsetHeight / size + 1;

        dotArray = createDotArray(width, height);
      
        draw();
    }

    function createDotArray(width: number, height: number) {
        const grid: Dot[][] = [];

        for (let y = 0; y < height; y++) {
            let dotRow: Dot[] = [];
            for (let x = 0; x < width; x++) {
                dotRow.push(new Dot(x * size, y * size + realHeight, getRandomRgb(), x * size, y * size));
            }
            grid.push(dotRow);
        }

        return grid;
    }

    function getRandomRgb() {
        const r = Math.floor(Math.random() * 16) * 16;
        const g = Math.floor(Math.random() * 16) * 16;
        const b = Math.floor(Math.random() * 16) * 16;
    
        return `rgb(${r}, ${g}, ${b})`;
    }

    function draw() {
        ctx.clearRect(0, 0, realWidth, realHeight);
   
        for (let y = 0; y < height; y++) {
            for (let x = 0; x < width; x++) {
                const dot = dotArray[y][x];
                dot.move();
                ctx.fillStyle = dot.rgb;
                ctx.fillRect(dot.x, dot.y, size, size);
            }
        }

        ctx.lineWidth = 5;
        ctx.strokeStyle = "black";
        ctx.strokeRect(0, 0, 400, 200)

        requestAnimationFrame(draw);
    }
});

class Dot {
    x: number;
    y: number;
    rgb: string;
    tx: number;
    ty: number;
    done: boolean;
    enable: boolean;

    constructor(x: number, y: number, rgb: string, tx: number, ty: number) {
        this.x = x;
        this.y = y;
        this.rgb = rgb;
        // target point
        this.tx = tx;
        this.ty = ty;
        this.done = false;
        this.enable = false;
    }
    move() {
        if (! this.enable) return;

        if (! this.done) {
            if (this.tx <= this.x) this.x -= size;
            if (this.ty <= this.y) this.y -= size;
        }
        
        if (this.tx <= this.x && this.ty <= this.y) {
            this.done = true;
        }
    }
}

</script>

<style lang="scss" scoped>
.sb-logo {
    height: 100%;
    width: 100%;

    > .canvas {
        height: 100%;
        width: 100%;
    }
}
</style>

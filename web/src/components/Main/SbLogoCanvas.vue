<template>
    <div class="sb-logo">
        <canvas
            class="canvas"
            ref="canvas" 
        />
    </div>
</template>

<script setup lang="ts">
import { GreaterDepth } from 'three';
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
let ctx, row, col;

onMounted(() => {
    if (canvas.value) {
        ctx = canvas.value.getContext("2d");

        row = canvas.value.offsetHeight;
        col = canvas.value.offsetWidth;

        dotArray = createDotArray(row, col);
        // array init
      
        draw();
    }

    function createDotArray(row: number, col: number) {
        const grid: Dot[][] = [];

        for (let x = 0; x < row; x++) {
            let dotRow: Dot[] = [];
            for (let y = 0; y < col; y++) {
                dotRow.push(new Dot(x + row, y, getRandomRgb(), x, y));
            }
            grid.push(dotRow);
        }

        return grid;
    }

    function getRandomRgb() {
        const r = Math.floor(Math.random() * 255);
        const g = Math.floor(Math.random() * 255);
        const b = Math.floor(Math.random() * 255);
    
        return `rgb(${r}, ${g}, ${b})`;
    }

    function draw() {
        ctx.clearRect(0, 0, col, row);

        for (let x = 0; x < row; x++) {
            for (let y = 0; y < col; y++) {
                const dot = dotArray[x][y];
                dot.move();
                ctx.fillRect(dot.x, dot.y, 1, 1);
                
            }
        }
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

    constructor(x: number, y: number, rgb: string, tx: number, ty: number) {
        this.x = x;
        this.y = y;
        this.rgb = rgb;
        // target point
        this.tx = tx;
        this.ty = ty;
        this.done = false;
    }
    move() {
        if (! this.done)
            this.x--;

        if (this.tx < this.x) {
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

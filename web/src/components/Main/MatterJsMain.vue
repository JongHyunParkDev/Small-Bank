<template>
    <div
        class="matter-container"
        ref="matterContainer"
    />
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, nextTick } from 'vue';
import { useQuasar } from 'quasar';
import * as Matter from 'matter-js';
import MatterWrap from 'matter-wrap';
import CoinSrc from '@/assets/images/coin.png';
import BillSrc from '@/assets/images/bill.png';

Matter.use(MatterWrap);

// base Mobile
const stackOption = {
    col: 5,
    row: 5,
};
const $q = useQuasar();
if ($q.platform.is.desktop) {
    stackOption.col = 20;
    stackOption.row = 4;
}

const matterContainer = ref<HTMLInputElement | null>(null);

let runner, render;

function init() {
    // create engine
    let engine = Matter.Engine.create(),
        world = engine.world;

    // create renderer
    render = Matter.Render.create({
        element: matterContainer.value,
        engine: engine,
        options: {
            width: window.innerWidth,
            height: window.outerHeight,
            wireframes: false,
            // showDebug: true,
        },
    });

    Matter.Render.run(render);

    // create runner
    runner = Matter.Runner.create();
    Matter.Runner.run(runner, engine);

    // add bodies
    let stack = Matter.Composites.stack(
        20,
        20,
        stackOption.col,
        stackOption.row,
        0,
        50,
        function (x, y, col, row) {
            const angle = Matter.Common.random(10, 20, 30, 40, 50, 60, 70, 80, 90);
            const frictionAir = Matter.Common.random(0.1, 0.09, 0.08, 0.07, 0.06);
            if ((row + col) % 3 > 0) {
                const num = 60;
                return Matter.Bodies.rectangle(x, y, num * 2, num, {
                    render: {
                        sprite: {
                            texture: BillSrc,
                            xScale: num / 50,
                            yScale: num / 50,
                        },
                    },
                    frictionAir: frictionAir,
                    restitution: 0.2,
                    angle: angle,
                });
            } else {
                const num = 20;
                return Matter.Bodies.circle(x, y, num, {
                    render: {
                        sprite: {
                            texture: CoinSrc,
                            xScale: num / 200,
                            yScale: num / 200,
                        },
                    },
                    frictionAir: 0.03,
                    restitution: 0.2,
                });
            }
        }
    );

    Matter.Composite.add(world, stack);

    // Matter.Composite.add(world, [
    //     Matter.Bodies.rectangle(200, 150, 700, 20, { isStatic: true, angle: Math.PI * 0.06, render: { fillStyle: '#060a19' } }),
    //     Matter.Bodies.rectangle(500, 350, 700, 20, { isStatic: true, angle: -Math.PI * 0.06, render: { fillStyle: '#060a19' } }),
    //     Matter.Bodies.rectangle(340, 580, 700, 20, { isStatic: true, angle: Math.PI * 0.04, render: { fillStyle: '#060a19' } })
    // ]);

    // add mouse control
    let mouse = Matter.Mouse.create(render.canvas),
        mouseConstraint = Matter.MouseConstraint.create(engine, {
            mouse: mouse,
            constraint: {
                stiffness: 0.2,
                render: {
                    visible: false,
                },
            },
        });

    Matter.Composite.add(world, mouseConstraint);

    // keep the mouse in sync with rendering
    render.mouse = mouse;

    // fit the render viewport to the scene
    Matter.Render.lookAt(render, Matter.Composite.allBodies(world));

    // wrapping using matter-wrap plugin
    for (let i = 0; i < stack.bodies.length; i += 1) {
        stack.bodies[i].plugin.wrap = {
            min: { x: render.bounds.min.x, y: render.bounds.min.y },
            max: { x: render.bounds.max.x, y: render.bounds.max.y },
        };
    }

    // context for MatterTools.Demo
    return {
        engine: engine,
        runner: runner,
        render: render,
        canvas: render.canvas,
        stop: function () {
            Matter.Render.stop(render);
            Matter.Runner.stop(runner);
        },
    };
}

onMounted(async () => {
    await nextTick();
    init();
});

onUnmounted(() => {
    Matter.Render.stop(render);
    Matter.Runner.stop(runner);
});
</script>

<style lang="scss" scoped>
.matter-container {
    position: absolute;
    overflow: hidden;
    height: 100%;
}
</style>

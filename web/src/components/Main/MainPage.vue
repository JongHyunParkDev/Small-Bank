<template>
    <div class="three-container" ref="threeDiv">
        <div class="fab-area">
            <QBtn
                round
                :disable="isCoinState"
                @click="updateActionCoin"
            >
                <QAvatar size="42px">
                    <img :src=CoinSrc>
                </QAvatar>
            </QBtn>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';
import * as THREE from 'three';
import { FBXLoader } from 'three/addons/loaders/FBXLoader.js';
import CoinSrc from '@/assets/images/coin.png';

const threeDiv = ref<HTMLInputElement | null>(null);

let isCoinState = false;
// three js 에서 typescript 모두 지원하지 않음으로 대부분을 any 로 둔다.
let mixer: any, renderer: any, lastObject: any, animationId = -1;
let scene = new THREE.Scene();
const clock = new THREE.Clock();

scene.background = new THREE.Color( 0xffffff );
scene.fog = new THREE.Fog( 0xa0a0a0, 200, 500);

const camera = new THREE.PerspectiveCamera( 90, window.innerWidth / window.innerHeight, 5,  2000 );
camera.position.set( 75, 50, 75 );
camera.lookAt( 0, 0, 0 );

const ambLight = new THREE.AmbientLight( 0xffffff, 15);
ambLight.position.set( 300, 300, 300 );
scene.add( ambLight );

let pigWalkObject: any, pigCoinObject: any;

async function loadObjects() {
    const loader = new FBXLoader();
    pigWalkObject = await loader.loadAsync('three/pig_walk.fbx');
    pigCoinObject = await loader.loadAsync('three/pig_coin.fbx');
}

function updateActionWalk() {
    isCoinState = false;

    if (lastObject !== undefined) scene.remove( lastObject );
    if (mixer !== undefined) mixer.stopAllAction();

    mixer = new THREE.AnimationMixer( pigWalkObject );

    const action = mixer.clipAction( pigWalkObject.animations[ 0 ] );

    action.setDuration(action.getClip().duration * 0.75);

    action.play();

    scene.add( pigWalkObject );
    lastObject = pigWalkObject;
}

function updateActionCoin() {
    if (isCoinState) return;
    isCoinState = true;

    mixer.stopAllAction();
    scene.remove( lastObject );

    mixer = new THREE.AnimationMixer( pigCoinObject );
    const action = mixer.clipAction(pigCoinObject.animations[0]);

    action.setDuration(action.getClip().duration * 0.75);
    action.play();

    // 기존 애니메이션의 종료 이벤트 리스너 추가
    action.clampWhenFinished = true;
    action.loop = THREE.LoopOnce; // 한 번만 재생
    mixer.addEventListener('finished', () => {
        // 애니메이션 종료 시 다른 애니메이션으로 전환
        updateActionWalk();
    });

    scene.add( pigCoinObject );
    lastObject = pigCoinObject;
}

function animate() {
    animationId = requestAnimationFrame( animate );

    const delta = clock.getDelta();

    if ( mixer ) mixer.update( delta );

    renderer.render( scene, camera );
}

onMounted(async () => {
    await loadObjects();
    updateActionWalk();
    renderer = new THREE.WebGLRenderer( { antialias: true } );
    renderer.setSize( threeDiv.value?.clientWidth, threeDiv.value?.clientHeight );
    threeDiv.value?.appendChild( renderer.domElement );

    animate();
})

onUnmounted(() => {
    mixer = null;
    renderer = null;
    scene = null;
    if (animationId !== -1) cancelAnimationFrame(animationId);
    clock.stop();
})
</script>

<style lang="scss">
.three-container {
    position: absolute;
    top: 0px;
    bottom: 0px;
    left: 0px;
    right: 0px;
    height: 100%;
    width: 100%;

    > .fab-area {
        position: fixed;
        bottom: 20px;
        right: 20px;
        text-align: center;
    }
}
</style>

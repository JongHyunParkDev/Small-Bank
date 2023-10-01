<template>
    <div class="three-container" ref="threeDiv">
        
    </div>
</template>

<script setup lang="ts">
import { defineComponent, PropType, computed, ref, toRef, Ref, onMounted } from 'vue';
import * as THREE from 'three';
import { FBXLoader } from 'three/addons/loaders/FBXLoader.js';

const threeDiv = ref<HTMLInputElement | null>(null);

let mixer, renderer;

const clock = new THREE.Clock();
const scene = new THREE.Scene();
scene.background = new THREE.Color( 0xffffff );
scene.fog = new THREE.Fog( 0xa0a0a0, 200, 500);

const camera = new THREE.PerspectiveCamera( 90, window.innerWidth / window.innerHeight, 5,  2000 );
camera.position.set( 75, 50, 75 );
camera.lookAt( 0, 0, 0 );

const ambLight = new THREE.AmbientLight( 0xffffff, 15);
ambLight.position.set( 300, 300, 300 );
scene.add( ambLight );

let cnt = 0;

function animate() {
    requestAnimationFrame( animate );

    const delta = clock.getDelta();

    if ( mixer ) mixer.update( delta );

    renderer.render( scene, camera );
}

onMounted(() => {
    console.log('mounted');
    const loader = new FBXLoader();
    loader.load( 'src/assets/three/pig_walk.fbx', function ( object ) {
        mixer = new THREE.AnimationMixer( object );

        const action = mixer.clipAction( object.animations[ 0 ] );

        action.setDuration(action.getClip().duration * 0.75);

        action.play();

        object.traverse( function ( child ) {
            // console.log(child);
            // if ( child.isMesh ) {
            //     child.castShadow = true;
            //     child.receiveShadow = true;
            // }
        } );
        scene.add( object );
    });

    renderer = new THREE.WebGLRenderer( { antialias: true } );
    renderer.setPixelRatio( window.devicePixelRatio );
    renderer.setSize( threeDiv.value?.clientWidth, threeDiv.value?.clientHeight );
    renderer.shadowMap.enabled = true;
    threeDiv.value?.appendChild( renderer.domElement );

    animate();
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
}
</style>
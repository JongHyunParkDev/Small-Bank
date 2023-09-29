<template>
    <div class="three-container" ref="threeDiv">
        
    </div>
</template>

<script setup lang="ts">
import { defineComponent, PropType, computed, ref, toRef, Ref, onMounted  } from 'vue';
import * as THREE from 'three';
const threeDiv = ref<HTMLInputElement | null>(null);

const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera( 75, window.innerWidth / window.innerHeight, 0.1, 1000 );

const renderer = new THREE.WebGLRenderer();

function animate() {
    requestAnimationFrame( animate );
    renderer.render( scene, camera );
}

onMounted(() => {
    console.log(threeDiv.value);
    renderer.setSize( threeDiv.value?.width, threeDiv.value?.height );

    threeDiv.value?.appendChild( renderer.domElement );

    console.log(threeDiv.value);

    const geometry = new THREE.BoxGeometry( 1, 1, 1 );
    const material = new THREE.MeshBasicMaterial( { color: 0x00ff00 } );
    const cube = new THREE.Mesh( geometry, material );
    scene.add( cube );

    camera.position.z = 5;


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
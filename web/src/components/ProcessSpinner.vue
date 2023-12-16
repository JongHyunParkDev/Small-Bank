<template>
    <div class="progress-spinner">
        <QSpinnerHourglass
            class="progress-spinner-icon"
            color="grey-5"
            size="5em"
        />
    </div>
</template>

<script setup lang="ts"></script>

<style lang="scss" scoped>
.progress-spinner {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    cursor: progress;
    z-index: 1080;
    background-color: rgba(255, 255, 255, 0.7);

    .progress-spinner-icon {
        position: absolute;
        display: block;
        // 주의: spinner가 커지면 minus 부분을 키워야 한다.
        left: calc(50% - 2rem);
        top: calc(50% - 2rem);
    }
}
</style>

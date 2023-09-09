<template>
    <QItem
        v-for="(route, idx) in routeList"
        :key="idx"
        clickable
        :to="route.to"
    >
      <QItemSection
          v-if="route.icon"
          avatar
      >
          <QIcon :name="route.icon" />
      </QItemSection>

      <QItemSection>
          <QItemLabel>{{ route.title }}</QItemLabel>
          <QItemLabel caption>{{ route.caption }}</QItemLabel>
      </QItemSection>
    </QItem>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useAuthStore } from '@/stores/AuthStore';
import { Route } from '@/types/LinkTypes';

const isAdmin = useAuthStore().isAdmin;
const routeList = ref<Route[]>([
    {
        title: '가계부',
        caption: '간단한 가계부 서비스 (일)',
        icon: 'calendar_today',
        to: '/AccountBook'
    },
    {
        title: '가계부 통계',
        caption: '간단한 가계부 서비스 (달)',
        icon: 'list',
        to: '/AccountBookDetail'
    },
]);

onMounted(() => {
    if (isAdmin) {
        routeList.value.push({
            title: 'Toy',
            caption: 'Arduino Toy',
            icon: 'developer_board',
            to: '/Arduino'
        })
    }
})
</script>

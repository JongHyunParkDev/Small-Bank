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

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { Route } from './models';

export default defineComponent({
    name: 'RouterLink',
    setup () {
        const routeList = ref<Route[]>([
          {
            title: '가계부',
            caption: '간단한 가계부 서비스',
            icon: 'calendar_today',
            to: '/AccountBook'
          },
        ]);


        return {
            routeList,
        };
    }
});
</script>

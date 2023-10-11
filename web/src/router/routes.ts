import { RouteRecordRaw } from 'vue-router';

const routes: RouteRecordRaw[] = [
    {
        path: '/login',
        component: () => import('../LoginApp.vue')
    },
    {
        path: '/',
        component: () => import('layouts/MainLayout.vue'),
        children: [{ path: '', component: () => import('pages/IndexPage.vue') }],
    },
    {
        path: '/AccountBook',
        component: () => import('layouts/MainLayout.vue'),
        children: [{ path: '', component: () => import('pages/AccountBookPage.vue') }],
        meta: { isAuth: true }
    },
    {
        path: '/AccountBookDetail',
        component: () => import('layouts/MainLayout.vue'),
        children: [{ path: '', component: () => import('pages/AccountBookDetailPage.vue') }],
        meta: { isAuth: true }
    },
    {
        path: '/Arduino',
        component: () => import('layouts/MainLayout.vue'),
        children: [{ path: '', component: () => import('pages/ArduinoPage.vue') }],
        meta: { isAuth: true, isAdmin: true }
    },

    // Always leave this as last one,
    // but you can also remove it
    {
        path: '/:catchAll(.*)*',
        component: () => import('pages/ErrorNotFound.vue'),
    },
];

export default routes;

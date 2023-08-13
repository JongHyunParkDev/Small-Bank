import { RouteRecordRaw } from 'vue-router';

const routes: RouteRecordRaw[] = [
    {
        path: '/loginSuccess',
        component: () => import('../Login.vue'),
        props: { isSuccess: true }
    },
    {
        path: '/login',
        component: () => import('../Login.vue'),
        props: { isSuccess: false }
    },
    {
        path: '/',
        component: () => import('layouts/MainLayout.vue'),
        children: [{ path: '', component: () => import('pages/IndexPage.vue') }],
        meta: { requiresAuth: true }
    },
    {
        path: '/AccountBook',
        component: () => import('layouts/MainLayout.vue'),
        children: [{ path: '', component: () => import('pages/AccountBookPage.vue') }],
        meta: { requiresAuth: true }
    },

    // Always leave this as last one,
    // but you can also remove it
    {
        path: '/:catchAll(.*)*',
        component: () => import('pages/ErrorNotFound.vue'),
    },
];

export default routes;

import { RouteRecordRaw } from 'vue-router';

const routes: RouteRecordRaw[] = [
    {
        path: '/login',
        component: () => import('../LoginApp.vue'),
    },
    {
        path: '/oauthCallback',
        component: () => import('../OauthCallback.vue'),
    },
    {
        path: '/survey/:surveyId',
        component: () => import('../SurveyApp.vue'),
    },
    {
        path: '/',
        component: () => import('layouts/MainLayout.vue'),
        children: [{ path: '', component: () => import('pages/IndexPage.vue') }],
        meta: { isAuth: true },
    },
    {
        path: '/accountBook',
        component: () => import('layouts/MainLayout.vue'),
        children: [{ path: '', component: () => import('pages/AccountBookPage.vue') }],
        meta: { isAuth: true },
    },
    {
        path: '/surveyForm',
        component: () => import('layouts/MainLayout.vue'),
        children: [{ path: '', component: () => import('pages/SurveyFormPage.vue') }],
        meta: { isAuth: true },
    },
    {
        path: '/accountBookDetail',
        component: () => import('layouts/MainLayout.vue'),
        children: [{ path: '', component: () => import('pages/AccountBookDetailPage.vue') }],
        meta: { isAuth: true },
    },
    {
        path: '/arduino',
        component: () => import('layouts/MainLayout.vue'),
        children: [{ path: '', component: () => import('pages/ArduinoPage.vue') }],
        meta: { isAuth: true, isAdmin: true },
    },

    // Always leave this as last one,
    // but you can also remove it
    {
        path: '/:catchAll(.*)*',
        component: () => import('pages/ErrorNotFound.vue'),
    },
];

export default routes;

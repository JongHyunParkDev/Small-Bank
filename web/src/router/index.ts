import { route } from 'quasar/wrappers';
import { useAuthStore } from '@/stores/AuthStore';
import {
    createMemoryHistory,
    createRouter,
    createWebHashHistory,
    createWebHistory,
} from 'vue-router';

import routes from './routes';

/*
 * If not building with SSR mode, you can
 * directly export the Router instantiation;
 *
 * The function below can be async too; either use
 * async/await or return a Promise which resolves
 * with the Router instance.
 */



export default route(function (/* { store, ssrContext } */) {
    const createHistory = process.env.SERVER
        ? createMemoryHistory
        : process.env.VUE_ROUTER_MODE === 'history'
        ? createWebHistory
        : createWebHashHistory;

    const Router = createRouter({
        scrollBehavior: () => ({ left: 0, top: 0 }),
        routes,

        // Leave this as is and make changes in quasar.conf.js instead!
        // quasar.conf.js -> build -> vueRouterMode
        // quasar.conf.js -> build -> publicPath
        history: createHistory(process.env.VUE_ROUTER_BASE),
    });

    Router.beforeEach(async (to, from, next) => {
        const authStore = useAuthStore();
        if (! authStore.userInfo) {
            try {
                await authStore.login();
            }
            catch (err) {
                // next({ path: '/login' });
            }
        }

        if (to.matched.some(record => record.meta.isAdmin)) {
            if (authStore.isAdmin) {
                next();
            }
            else if (authStore.isAuth) {
                next({ path: '/' });
            }
            else {
                next({ path: '/Login' });
            }
        }
        else if (to.matched.some(record => record.meta.isAuth)) {
            if (authStore.isAuth) {
                next();
            } else {
                next({ path: '/Login' });
            }
        } else {
            next();
        }
    });

    return Router;
});

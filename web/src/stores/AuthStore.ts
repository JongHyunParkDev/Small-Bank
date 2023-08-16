import { defineStore } from 'pinia';
import { Api } from '@/lib/Api';

export const useAuthStore = defineStore({
    id: 'auth', // 스토어 식별자
    state: () => ({
        isLoggedIn: false,
        user: undefined
    }),
    actions: {
        async login() {
            const userInfo = await Api.get('user/userinfo', undefined);
            // TODO API 에서 http status code, data 등은 Api.ts 에서 처리하자.
            this.user = userInfo;
            this.isLoggedIn = true;
        },
        async logout() {
            await Api.get('user/logout', undefined);
            this.user = undefined;
            this.isLoggedIn = false;
        }
    }
});

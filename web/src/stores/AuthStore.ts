import { defineStore } from 'pinia';
import { Api } from '@/lib/Api';
import { UserInfo } from '@/stores/Models';

export const useAuthStore = defineStore({
    id: 'auth', // 스토어 식별자
    state: () => ({
        isLoggedIn: false,
        userInfo: undefined as UserInfo | undefined
    }),
    actions: {
        async login() {
            // TODO API 에서 http status code, data 등은 Api.ts 에서 처리하자.
            const userInfo: UserInfo = await Api.get('user/userinfo', undefined);
            if (userInfo) {
                this.userInfo = userInfo;
                this.isLoggedIn = true;
            }
        },
        async logout() {
            await Api.get('user/logout', undefined);
            this.userInfo = undefined;
            this.isLoggedIn = false;
        }
    }
});

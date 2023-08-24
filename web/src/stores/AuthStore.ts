import { defineStore } from 'pinia';
import { Api } from '@/lib/Api';
import { UserInfo } from '@/types/UserTypes';

export const useAuthStore = defineStore({
    id: 'auth', // 스토어 식별자
    state: () => ({
        isLoggedIn: false,
        userInfo: undefined as UserInfo | undefined
    }),
    actions: {
        async login() {
            const userInfo: UserInfo = await Api.get('user/userinfo', undefined, undefined);
            if (userInfo) {
                this.userInfo = userInfo;
                this.isLoggedIn = true;
            }
        },
        async logout() {
            await Api.get('user/logout', undefined, undefined);
            this.userInfo = undefined;
            this.isLoggedIn = false;
        }
    }
});

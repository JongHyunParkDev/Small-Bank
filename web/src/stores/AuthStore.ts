import { defineStore } from 'pinia';
import { Api } from '@/lib/Api';
import { UserInfo } from '@/types/UserTypes';

export const useAuthStore = defineStore({
    id: 'auth', // 스토어 식별자
    state: () => ({
        userInfo: undefined as UserInfo | undefined,
    }),
    getters: {
        isAuth: (state) => {
            return state.userInfo !== undefined;
        },
        isAdmin: (state) => {
            return state.userInfo?.role === 'ROLE_ADMIN';
        },
    },
    actions: {
        async login() {
            const userInfo: UserInfo = await Api.get('user/userInfo');
            if (userInfo) {
                this.userInfo = userInfo;
            }
        },
        async logout() {
            await Api.get('user/logout');
            this.userInfo = undefined;
        },
    },
});

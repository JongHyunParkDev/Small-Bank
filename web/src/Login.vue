<template>
    <div class="login">
        <img
            class="logo"
            :src="LogoSvgSrc"
            alt="logo"
        />
        <div class="login-btns">
            <a
                class="login-btn"
                href="/oauth2/authorization/naver"
            >
                <img
                    :src="NaverLoginBtnSrc"
                    alt="Naver Login"
                />
            </a>
        </div>
        <div class="footer">
            GitHub
            <a
                target="_blank"
                href="https://github.com/JongHyunParkDev/SPPD">
                https://github.com/JongHyunParkDev/SPPD
            </a>
        </div>

    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import LogoSvgSrc from '@/assets/logo/logo.svg';
import NaverLoginBtnSrc from '@/assets/images/naver_login_btn.png';
import { useAuthStore } from '@/stores/AuthStore';

export default defineComponent({
    name: 'App',
    setup() {
        return {
            LogoSvgSrc,
            NaverLoginBtnSrc
        };
    },
    async mounted() {
        await useAuthStore().login();
        this.$router.push('/');
    },
});
</script>

<style lang="scss" scoped>
.login {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    margin: auto;
    width: 400px;
    height: 400px;
    text-align: center;

    > .logo {
        filter: drop-shadow(0 0 3px	#4ec525);
        width: 300px;
    }

    > .login-btns {
        margin: 30px 0px;

        > .login-btn {
            width: 200px;

            img {
                width: 200px;
            }

            &:hover {
                cursor: pointer;
            }
        }
    }

    > .footer {

    }

}
</style>

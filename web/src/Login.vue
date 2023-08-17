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
        <ProcessSpinner v-if="processCount > 0"/>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref, Ref } from 'vue';
import LogoSvgSrc from '@/assets/logo/logo.svg';
import NaverLoginBtnSrc from '@/assets/images/naver_login_btn.png';
import ProcessSpinner from '@/components/ProcessSpinner.vue';
// import { useAuthStore } from '@/stores/AuthStore';
// import { process } from '@/lib/Async';

const processCount: Ref<number> = ref(0);

export default defineComponent({
    name: 'App',
    components: {
        ProcessSpinner
    },
    setup() {
        return {
            LogoSvgSrc,
            NaverLoginBtnSrc,

            processCount
        };
    },
    methods: {
        upProcessSpinner() {
            processCount.value = 1;
        },
        downProcessSpinner() {
            processCount.value = 0;
        }
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
        margin: 50px 0px;
    }

    > .login-btns {

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
        margin: 50px 0px;
    }

}
</style>

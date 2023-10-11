<template>
    <div class="login">
        <img
            class="logo"
            :src="LogoSvgSrc"
            alt="logo"
        />
        <QForm
            :autofocus="true"
            class="login-form"
            @submit.stop="submit"
        >
            <div class="input-area">
                <QInput
                    class="input"
                    outlined
                    stack-label
                    label="ID"
                    v-model="id"
                    type="text"
                />
                <QInput
                    outlined
                    stack-label
                    label="Password"
                    v-model="password"
                    type="password"
                />
            </div>
            <QBtn
                class="submit"
                label="Login"
                type="submit"
            />

    </QForm>
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
            JongHyunParkDev
        </div>
        <ProcessSpinner v-if="processCount > 0"/>
    </div>
</template>

<script setup lang="ts">
import { ref, Ref } from 'vue';
import LogoSvgSrc from '@/assets/logo/logo.svg';
import NaverLoginBtnSrc from '@/assets/images/naver_login_btn.png';
import ProcessSpinner from '@/components/ProcessSpinner.vue';
// import { useAuthStore } from '@/stores/AuthStore';
// import { process } from '@/lib/Async';

const processCount: Ref<number> = ref(0);
const id: Ref<string> = ref('');
const password: Ref<string> = ref('');


// function upProcessSpinner() {
//         processCount.value = 1;
// }
// function downProcessSpinner() {
//     processCount.value = 0;
// }555

function submit() {
    console.log('sub');
}

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

    > .login-form {
        display: flex;
        margin-bottom: $spacing-md;

        > .input-area {
            flex: 1;
            margin-right: $spacing-tn;

            > .input {
                margin-bottom: $spacing-tn;
            }
        }
        > .submit {
            background-color: $naver-bs;
            color: white;
        }
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

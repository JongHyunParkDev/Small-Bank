<template>
    <div class="login-app">
        <div class="login">
            <img
                class="logo"
                :src="LogoSvgSrc"
                alt="logo"
            />
            <div class="login-area">
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
                            label="Email"
                            v-model="email"
                            name="email"
                            type="email"
                        />
                        <QInput
                            outlined
                            stack-label
                            label="Password"
                            v-model="password"
                            type="password"
                            name="pw"
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
            </div>
            <div class="footer">
                JongHyunParkDev
            </div>
        </div>
        <ProcessSpinner v-if="processCount > 0"/>
    </div>
</template>

<script setup lang="ts">
import { ref, Ref } from 'vue';
import LogoSvgSrc from '@/assets/logo/logo.svg';
import NaverLoginBtnSrc from '@/assets/images/naver_login_btn.png';
import ProcessSpinner from '@/components/ProcessSpinner.vue';
import { process } from '@/lib/Async';
import { Api } from '@/lib/Api';
import { useRouter } from 'vue-router';

const router = useRouter();

const processCount: Ref<number> = ref(0);
const email: Ref<string> = ref('');
const password: Ref<string> = ref('');


function upProcessSpinner() {
        processCount.value = 1;
}
function downProcessSpinner() {
    processCount.value = 0;
}

function submit() {
    process(upProcessSpinner, downProcessSpinner, async () => {
        const formData = new FormData();
        formData.append("email", email.value);
        formData.append("pw", password.value);

        await Api.post('anon/login', formData);

        router.push('/');
    });
}
</script>

<style lang="scss" scoped>

.login-app {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    display: flex;
    align-items: center;
    justify-content: center;

    > .login {
        width: 350px;
        height: 300px;

        > .logo {
            filter: drop-shadow(0 0 3px	#4ec525);
            width: 100%;
            margin-bottom: 50px;
        }

        > .login-area {
            max-width: 300px;
            margin: 0px 25px;
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
                display: flex;
                > .login-btn {
                    width: 150px;

                    img {
                        width: 150px;
                    }

                    &:hover {
                        cursor: pointer;
                    }
                }
            }
        }

        > .footer {
            margin: 50px 0px;
        }

    }
}

</style>

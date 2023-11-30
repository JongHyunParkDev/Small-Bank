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
                <div class="login-find-area">
                    <a
                        class="find-label"
                        @click="showJoinForm"
                    >
                        회원 가입
                    </a>
                </div>
                <div
                    v-if="enableOauth"
                    class="login-hr-area"
                >
                    <div class="login-hr" />
                    <div class="login-label">OR</div>
                    <div class="login-hr" />
                </div>
                <div
                    v-if="enableOauth"
                    class="login-btns"
                >
                    <a
                        class="login-btn"
                        href="/oauth2/authorization/google"
                    >
                        <img
                            class="google-img"
                            :src="GoogleLoginBtnSrc"
                            alt="Google Login"
                        />
                    </a>
                    <a
                        class="login-btn"
                        href="/oauth2/authorization/kakao"
                    >
                        <img
                            class="kakao-img"
                            :src="KakaoLoginBtnSrc"
                            alt="Kakao Login"
                        />
                    </a>
                </div>
            </div>
            <div class="footer"></div>
        </div>
        <ProcessSpinner v-if="processCount > 0" />
        <QDialog
            class="join-form-dialog"
            v-model="isVisibleJoinFormDialog"
            persistent
        >
            <QCard class="join-form-card">
                <QForm @submit="joinSubmit">
                    <QCardSection class="row items-center">
                        <QToolbar>
                            <QToolbarTitle> 회원 가입 </QToolbarTitle>
                        </QToolbar>
                        <div class="dialog-content">
                            <QInput
                                name="email"
                                outlined
                                stack-label
                                label="이메일"
                                v-model="joinEmailInput"
                                :rules="[
                                    (val) => validateEmail(val) || '유효한 email 이 아닙니다.',
                                ]"
                            />
                            <QInput
                                name="password"
                                type="password"
                                outlined
                                stack-label
                                label="패스워드"
                                v-model="joinPasswordInput"
                                :rules="[
                                    (val) =>
                                        isPasswordValid(val) ||
                                        '영문자, 숫자 필수이며, 6 ~ 12글자로 사용 가능합니다.',
                                ]"
                            />
                            <QInput
                                name="name"
                                outlined
                                stack-label
                                label="이름"
                                v-model="joinNameInput"
                                :rules="[
                                    (val) =>
                                        (val.length >= 2 && val.length <= 6) ||
                                        '이름은 2 ~ 6글자 사용 가능합니다.',
                                ]"
                            />
                            <QInput
                                name="phone"
                                outlined
                                stack-label
                                label="전화번호"
                                v-model="joinPhoneInput"
                                :rules="[
                                    (val) =>
                                        validatePhone(val) ||
                                        '010-0000-0000 형식으로 작성해주세요.',
                                ]"
                            />
                        </div>
                    </QCardSection>
                    <QCardActions align="right">
                        <QBtn
                            flat
                            padding="xs lg"
                            label="취소"
                            color="primary"
                            @click="clearDialogForm"
                            v-close-popup
                        />
                        <QBtn
                            padding="xs lg"
                            type="submit"
                            label="회원 가입"
                            color="primary"
                        />
                    </QCardActions>
                </QForm>
            </QCard>
        </QDialog>
    </div>
</template>

<script setup lang="ts">
import { ref, Ref, watch } from 'vue';
import LogoSvgSrc from '@/assets/logo/logo.svg';
import KakaoLoginBtnSrc from '@/assets/images/kakao_login_btn.png';
import GoogleLoginBtnSrc from '@/assets/images/google_login_btn.svg';
import ProcessSpinner from '@/components/ProcessSpinner.vue';
import { PROCESS } from '@/lib/Async';
import { Api } from '@/lib/Api';
import { useRouter } from 'vue-router';
import { useErrorStore } from '@/stores/ErrorStore';
import { useQuasar } from 'quasar';
import { useAuthStore } from '@/stores/AuthStore';

const enableOauth = ref(true);
if (process.env.DEV) {
    enableOauth.value = false;
}

const authStore = useAuthStore();
const $q = useQuasar();
const errorStore = useErrorStore();
const router = useRouter();

const processCount: Ref<number> = ref(0);
const email: Ref<string> = ref('');
const password: Ref<string> = ref('');

const isVisibleJoinFormDialog = ref(false);
const joinEmailInput: Ref<string> = ref('');
const joinPasswordInput: Ref<string> = ref('');
const joinNameInput: Ref<string> = ref('');
const joinPhoneInput: Ref<string> = ref('');

function upProcessSpinner() {
    processCount.value = 1;
}
function downProcessSpinner() {
    processCount.value = 0;
}

function submit() {
    PROCESS(upProcessSpinner, downProcessSpinner, async () => {
        const formData = new FormData();
        formData.append('email', email.value);
        formData.append('pw', password.value);

        await Api.post('anon/login', formData);
        await authStore.login();
        router.push('/');
    });
}

function joinSubmit() {
    PROCESS(upProcessSpinner, downProcessSpinner, async () => {
        await Api.post('anon/joinUser', {
            email: joinEmailInput.value,
            password: joinPasswordInput.value,
            name: joinNameInput.value,
            phone: joinPhoneInput.value,
        });

        isVisibleJoinFormDialog.value = false;
        clearDialogForm();
        // 성공시 dialog

        $q.notify({
            type: 'positive',
            message: '정상적으로 회원가입 되었습니다.',
        });
    });
}

function clearDialogForm() {
    joinEmailInput.value = '';
    joinPasswordInput.value = '';
    joinNameInput.value = '';
    joinPhoneInput.value = '';
}

function validateEmail(email: string): boolean {
    return /[a-z0-9]+@[a-z]+\.[a-z]{2,3}/.test(email);
}

function isPasswordValid(password: string): boolean {
    if (password.length < 6 || password.length > 12) {
        return false;
    }

    const hasLetter = /[a-zA-Z]/.test(password);
    const hasNumber = /\d/.test(password);

    return hasLetter && hasNumber;
}

function validatePhone(phone: string): boolean {
    const checkPhone = /^\d{2,3}-\d{3,4}-\d{4}$/.test(phone);

    return checkPhone;
}

function showJoinForm() {
    isVisibleJoinFormDialog.value = true;
}

watch(errorStore.errors, async (newError, oldError) => {
    $q.notify({
        type: 'negative',
        message: newError[0],
    });
});
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
            filter: drop-shadow(0 0 3px #4ec525);
            width: 100%;
            margin-bottom: 50px;
        }

        > .login-area {
            max-width: 300px;
            margin: 0px 25px;
            > .login-form {
                display: flex;

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

            > .login-find-area {
                display: flex;
                justify-content: space-around;
                margin: $spacing-md 0px;

                > .find-label {
                    color: $grey-7;

                    &:hover {
                        cursor: pointer;
                    }
                }
            }

            > .login-hr-area {
                display: flex;

                > .login-label {
                    padding: 0px $spacing-sm;
                }
                > .login-hr {
                    flex: 1;
                    height: 0px;
                    margin: auto;
                    border: 1px inset $grey-5;
                }
            }

            > .login-btns {
                display: flex;
                flex-direction: column;
                > .login-btn {
                    margin: $spacing-sm;
                    text-align: center;

                    > .kakao-img {
                        width: 200px;
                        height: 50px;
                    }

                    > .google-img {
                        width: 200px;
                        height: 50px;
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

.join-form-dialog {
    .dialog-content {
        display: flex;
        flex-direction: column;
        width: 100%;
    }
}
</style>

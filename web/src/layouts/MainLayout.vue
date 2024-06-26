<template>
    <QLayout
        class="main-layout"
        view="hHh lpR fFf"
    >
        <QHeader class="header">
            <QToolbar>
                <QBtn
                    flat
                    dense
                    round
                    icon="menu"
                    aria-label="Menu"
                    @click="toggleLeftDrawer"
                />

                <QToolbarTitle>
                    <span
                        class="logo-text"
                        @click="goHome"
                    >
                        <img
                            class="logo"
                            :src="LogoSrc"
                        >    
                    SB
                    </span>
                </QToolbarTitle>

                <div class="avatar-content">
                    <div
                        class="update-area"
                        @click="showUpdateForm"
                    >
                        <QAvatar>
                            <img :src="userInfo?.profileImg" />
                        </QAvatar>
                        <div class="name">
                            {{ userInfo?.name }}
                        </div>
                    </div>
                    <QIcon
                        class="icon"
                        name="logout"
                        @click="logout"
                    />
                </div>
            </QToolbar>
        </QHeader>

        <QDrawer
            v-model="leftDrawerOpen"
            class="drawer"
            bordered
            overlay
        >
            <QList class="sm-list">
                <QItemLabel header> Menu </QItemLabel>
                <RouterList />
                <OptionList class="footer"/>
            </QList>
        </QDrawer>
        <QPageContainer>
            <RouterView />
        </QPageContainer>
        <QDialog
            class="form-dialog"
            v-model="isVisibleUpdateFormDialog"
            persistent
        >
            <QCard class="form-card">
                <QForm @submit="updateSubmit">
                    <QCardSection class="bg-primary">
                        <div class="text-h6 text-white">설문 항목 추가하기</div>
                    </QCardSection>
                    <QCardSection class="q-pa-md content">
                        <QInput
                            name="email"
                            outlined
                            stack-label
                            label="이메일"
                            v-model="updateEmailInput"
                            disable
                            :rules="[(val) => true]"
                        />
                        <QInput
                            name="password"
                            type="password"
                            outlined
                            stack-label
                            label="패스워드 (공백인 경우 변경하지 않습니다.)"
                            v-model="updatePasswordInput"
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
                            v-model="updateNameInput"
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
                            v-model="updatePhoneInput"
                            :rules="[
                                (val) =>
                                    validatePhone(val) || '010-0000-0000 형식으로 작성해주세요.',
                            ]"
                        />
                    </QCardSection>
                    <QCardActions align="right">
                        <QBtn
                            flat
                            padding="xs lg"
                            label="취소"
                            color="primary"
                            @click="updateUserInfoForm"
                            v-close-popup
                        />
                        <QBtn
                            padding="xs lg"
                            type="submit"
                            label="변경"
                            color="primary"
                        />
                    </QCardActions>
                </QForm>
            </QCard>
        </QDialog>
        <ProcessSpinner v-if="processCount > 0" />
    </QLayout>
</template>

<script setup lang="ts">
import { ref, Ref, watch, provide } from 'vue';
import LogoSrc from '@/assets/logo/logo.svg';
import RouterList from 'components/RouterList.vue';
import OptionList from 'components/OptionList.vue';
import ProcessSpinner from '@/components/ProcessSpinner.vue';
import { useAuthStore } from '@/stores/AuthStore';
import { useErrorStore } from '@/stores/ErrorStore';
import { Api } from '@/lib/Api';
import { useQuasar } from 'quasar';
import { PROCESS } from '@/lib/Async';
import { UserInfo } from '@/types/UserTypes';
import { useRouter } from 'vue-router';

const $q = useQuasar();
const errorStore = useErrorStore();

const router = useRouter();
const leftDrawerOpen: Ref<boolean> = ref(false);
const authStore = useAuthStore();
const userInfo: Ref<UserInfo | undefined> = ref(authStore.userInfo);
const processCount: Ref<number> = ref(0);

const isVisibleUpdateFormDialog = ref(false);
const updateEmailInput: Ref<string> = ref('');
const updatePasswordInput: Ref<string> = ref('');
const updateNameInput: Ref<string> = ref('');
const updatePhoneInput: Ref<string> = ref('');

const toggleLeftDrawer = () => {
    leftDrawerOpen.value = !leftDrawerOpen.value;
};

const upProcessSpinner = () => {
    processCount.value = 1;
};

const downProcessSpinner = () => {
    processCount.value = 0;
};

const logout = async () => {
    PROCESS(upProcessSpinner, downProcessSpinner, async () => {
        await authStore.logout();
        router.push('/login');
    });
};

const goHome = () => {
    router.push('/');
};

function updateSubmit() {
    PROCESS(upProcessSpinner, downProcessSpinner, async () => {
        await Api.put('user/userInfo', {
            password: updatePasswordInput.value === '' ? undefined : updatePasswordInput.value,
            name: updateNameInput.value,
            phone: updatePhoneInput.value,
        });

        isVisibleUpdateFormDialog.value = false;
        // 성공시 dialog

        $q.notify({
            type: 'positive',
            message: '정상적으로 사용자 정보가 업데이트 되었습니다.',
        });
    });
}

function updateUserInfoForm() {
    if (userInfo.value) {
        updateEmailInput.value = userInfo.value.email;
        updateNameInput.value = userInfo.value.name;
        updatePhoneInput.value = userInfo.value.phone;
    }
}

function isPasswordValid(password: string): boolean {
    // 비번 변경은 하기 싫은 경우
    if (password === '') return true;

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

async function showUpdateForm() {
    isVisibleUpdateFormDialog.value = true;

    await authStore.login();
    userInfo.value = authStore.userInfo;
    updateUserInfoForm();
}

watch(errorStore.errors, async (newError) => {
    $q.notify({
        type: 'negative',
        message: newError[0],
    });
});

provide('upProcessSpinner', upProcessSpinner);
provide('downProcessSpinner', downProcessSpinner);
</script>

<style lang="scss" scoped>
.main-layout {
    user-select: none;

    .sm-list {
        height: 100%;
        display: flex;
        flex-direction: column;

        .footer {
            margin-top: auto
        }
    }

    .header {
        background-color: $base-bs;
        color: white;

        .logo-text {
            font-weight: bold;
            font-style: italic;
            display: inline-flex;
            vertical-align: middle;
            
            > .logo {
                height: $logo-height;
            }

            &:hover {
                cursor: pointer;
            }
        }

        .avatar-content {
            display: flex;
            flex-direction: row;

            > .update-area {
                display: flex;
                border-radius: 5%;

                &:hover {
                    cursor: pointer;
                    background-color: $base-dk;
                }

                img {
                    border: 1px solid $grey-7;
                }

                > .name {
                    padding: $spacing-md;
                }
            }

            > .icon {
                font-size: 1.4em;
                padding: $spacing-md;
                border-radius: 100%;

                &:hover {
                    cursor: pointer;
                    background-color: $base-dk;
                }
            }
        }
    }

    &:deep(.drawer) {
        color: $grey-10;
    }

    .error-area {
        position: absolute;
        bottom: 0px;
        left: 0px;
        background-color: $red-9;
        color: white;
        border: 1px solid $red-10;
        margin: $spacing-md;
        padding: $spacing-sm;
        max-width: 50%;
        z-index: 999999;

        > .action-btns {
            text-align: end;
            margin-left: $spacing-md;
            margin-top: $spacing-sm;
            > .btn {
                border: 1px solid $grey-4;
                width: 60px;
            }
        }
    }
}
.form-dialog {
    .form-card {
        min-width: 350px;
    }
}
</style>

<template>
    <QLayout view="lHh Lpr lFf">
        <QHeader elevated class="header">
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
                        SPPD
                    </span>
                </QToolbarTitle>

                <div class="avatar-content">
                    <QAvatar>
                        <img :src="userInfo?.profileImg">
                    </QAvatar>
                    <div class="name">
                        {{ userInfo?.name }}
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
            bordered
        >
            <QList>
                <QItemLabel header> Menu </QItemLabel>
                <RouterLink v-if="isDone"> </RouterLink>

                <QItemLabel header> Essential Links </QItemLabel>
                <EssentialLink
                    v-for="link in essentialLinks"
                    :key="link.title"
                    v-bind="link"
                />
            </QList>
        </QDrawer>

        <QBanner v-if="errors.length !== 0" rounded class="error-area">
            <div
                v-for="(error, idx) in errors"
                :key="idx"
                class="error-row"
            >
                {{ error }}
            </div>
            <template v-slot:action>
                <QBtn class="btn" size="sm" flat color="white" label="닫기" @click="hideError"/>
            </template>
        </QBanner>

        <QPageContainer>
            <RouterView />
        </QPageContainer>
        <ProcessSpinner v-if="processCount > 0"/>
    </QLayout>
</template>

<script setup lang="ts">
import { ref, Ref, computed, provide } from 'vue';
import EssentialLink from 'components/EssentialLink.vue';
import RouterLink from 'components/RouterLink.vue';
import ProcessSpinner from '@/components/ProcessSpinner.vue';
import { useAuthStore } from '@/stores/AuthStore';
import { useErrorStore } from '@/stores/ErrorStore';
import { process } from '@/lib/Async';
import { UserInfo } from '@/types/UserTypes';
import { useRouter } from 'vue-router';

const linksList = [
    {
        title: 'Docs',
        caption: 'quasar.dev',
        icon: 'school',
        link: 'https://quasar.dev',
    },
    {
        title: 'Github',
        caption: 'github.com/quasarframework',
        icon: 'code',
        link: 'https://github.com/quasarframework',
    },
    {
        title: 'Discord Chat Channel',
        caption: 'chat.quasar.dev',
        icon: 'chat',
        link: 'https://chat.quasar.dev',
    },
    {
        title: 'Forum',
        caption: 'forum.quasar.dev',
        icon: 'record_voice_over',
        link: 'https://forum.quasar.dev',
    },
    {
        title: 'Twitter',
        caption: '@quasarframework',
        icon: 'rss_feed',
        link: 'https://twitter.quasar.dev',
    },
    {
        title: 'Facebook',
        caption: '@QuasarFramework',
        icon: 'public',
        link: 'https://facebook.quasar.dev',
    },
    {
        title: 'Quasar Awesome',
        caption: 'Community Quasar projects',
        icon: 'favorite',
        link: 'https://awesome.quasar.dev',
    },
];

const router = useRouter();
const leftDrawerOpen: Ref<boolean> = ref(false);
const authStore = useAuthStore();
const userInfo: Ref<UserInfo | undefined> = ref(authStore.userInfo);
const processCount: Ref<number> = ref(0);
const isDone: Ref<boolean> = ref(false);
const errorStore = useErrorStore();

const essentialLinks = linksList;

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
    process(upProcessSpinner, downProcessSpinner, async () => {
        await authStore.logout();
        router.push('/login');
    });
};

const goHome = () => {
    router.push('/');
};

const hideError = () => {
    errorStore.clearError();
};

if (authStore.isAuth) {
    isDone.value = true;
    userInfo.value = authStore.userInfo;
} else {
    process(upProcessSpinner, () => {
        isDone.value = true;
        downProcessSpinner();
    if (!authStore.isAuth) router.push('/login');
    }, async () => {
        await authStore.login();
        userInfo.value = authStore.userInfo;
    });
}

const errors = computed(() => errorStore.errors);

provide('upProcessSpinner', upProcessSpinner);
provide('downProcessSpinner', downProcessSpinner);
// router 이동하면 mounted 타지 않는다.
</script>

<style lang="scss" scoped>
.header {
    background-color: $naver-bs;
    color: white;

    .logo-text:hover {
        cursor: pointer;
    }

    .avatar-content {
        display: flex;
        flex-direction: row;

        img {
            border: 1px solid $grey-7;
        }

        > .name {
            padding: $spacing-md;
        }

        > .icon {
            font-size: 1.4em;
            padding: $spacing-md;
            border-radius: 100%;

            &:hover {
                cursor: pointer;
                background-color: $naver-dk;
            }
        }
    }
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
</style>

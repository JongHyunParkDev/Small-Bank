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

<script lang="ts">
import { defineComponent, ref, Ref } from 'vue';
import EssentialLink from 'components/EssentialLink.vue';
import RouterLink from 'components/RouterLink.vue';
import ProcessSpinner from '@/components/ProcessSpinner.vue';
import { useAuthStore } from '@/stores/AuthStore';
import { useErrorStore } from '@/stores/ErrorStore';
import { process } from '@/lib/Async';
import { UserInfo } from '@/types/UserTypes';

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

export default defineComponent({
    name: 'MainLayout',
    components: {
        EssentialLink,
        RouterLink,
        ProcessSpinner
    },
    provide() {
        return {
            upProcessSpinner: this.upProcessSpinner,
            downProcessSpinner: this.downProcessSpinner
        }
    },
    setup() {
        const leftDrawerOpen = ref(false);
        const userInfo: Ref<UserInfo | undefined> = ref(undefined);
        const processCount: Ref<number> = ref(0);
        const isDone: Ref<boolean> = ref(false);
        const errorList: Ref<Array<string>> = ref([]);
        const errorStore = useErrorStore();

        return {
            essentialLinks: linksList,
            leftDrawerOpen,
            toggleLeftDrawer() {
                leftDrawerOpen.value = !leftDrawerOpen.value;
            },
            userInfo,
            processCount,
            isDone,
            errorList,
            errorStore
        };
    },
    computed: {
        errors() {
            return this.errorStore.errors;
        }
    },
    methods: {
        upProcessSpinner() {
            this.processCount = 1;
        },
        downProcessSpinner() {
            this.processCount = 0;
        },
        logout() {
            process(this.upProcessSpinner, this.downProcessSpinner, async () => {
                await useAuthStore().logout();
                this.$router.push('/login');
            });
        },
        goHome() {
            this.$router.push('/');
        },
        hideError() {
            this.errorStore.clearError();
        }
    },
    mounted() {
        if (useAuthStore().isAuth) {
            this.userInfo = useAuthStore().userInfo;
        }
        else {
            process(this.upProcessSpinner, () => {
                this.isDone = true;
                this.downProcessSpinner();
                if (! useAuthStore().isAuth) this.$router.push('/login');
            }, async () => {
                await useAuthStore().login();
                this.userInfo = useAuthStore().userInfo;
            });
        }
    },
});
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

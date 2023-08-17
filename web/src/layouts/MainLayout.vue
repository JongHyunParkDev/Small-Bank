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

                <QToolbarTitle> SPPD </QToolbarTitle>

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
            show-if-above
            bordered
        >
            <QList>
                <QItemLabel header> Menu </QItemLabel>
                <RouterLink> </RouterLink>

                <QItemLabel header> Essential Links </QItemLabel>
                <EssentialLink
                    v-for="link in essentialLinks"
                    :key="link.title"
                    v-bind="link"
                />
            </QList>
        </QDrawer>

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
import { process } from '@/lib/Async';
import { UserInfo } from '@/stores/Models';

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

        return {
            essentialLinks: linksList,
            leftDrawerOpen,
            toggleLeftDrawer() {
                leftDrawerOpen.value = !leftDrawerOpen.value;
            },
            userInfo,
            processCount,
        };
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
        }
    },
    mounted() {
        if (useAuthStore().isLoggedIn) return;
        process(this.upProcessSpinner, () => {
            this.downProcessSpinner();
            if (! useAuthStore().isLoggedIn) this.$router.push('/login');
        }, async () => {
            await useAuthStore().login();
            this.userInfo = useAuthStore().userInfo;

        });
    },
});
</script>

<style lang="scss" scoped>
.header {
    background-color: white;
    color: black;

    .avatar-content {
        display: flex;
        flex-direction: row;

        > .name {
            padding: 10px;
        }

        > .icon {
            font-size: 1.2em;
            padding: 12px;
            border-radius: 100%;

            &:hover {
                cursor: pointer;
                background-color: green;
            }
        }
    }
}
</style>

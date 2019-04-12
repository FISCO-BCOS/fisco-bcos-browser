import Vue from 'vue'
import Router from 'vue-router'
import main from '@/views/layout/mains'
// import block from '@/views/components/block'
// import home from '@/views/components/home'
// import nodeConfig from '@/views/components/nodeConfig'
// import transaction from '@/views/components/transaction'
// import pendingTransaction from '@/views/components/pendingTransaction'
// import transactionDetail from '@/views/components/transactionDetail'
// import blockDetail from '@/views/components/blockDetail'
// import pendingTransactionDetail from '@/views/components/pendingTransactionDetail'


Vue.use(Router)

// export default new Router({
    // mode: 'history',
    // base: '/COMS',
const routes = [
        // {
        //     path: '/project',
        //     name: 'project',
        //     component: resolve => require(['@/views/components/project'],resolve),
        // },
        // {
        //     path: '/chain',
        //     name: 'chain',
        //     component: resolve => require(['@/views/components/chain'],resolve),
        // },
        {
        path: '/',
        name: 'main',
        component: main,
        redirect: '/home',
        children:[
            {
                path: '/home',
                name: 'home',
                meta: {
                    requireAuth: true, //
                },
                component: resolve => require(['@/views/components/home'],resolve),
            },
            {
                path: '/blankPage',
                name: 'blankPage',
                meta: {
                    requireAuth: true, //
                },
                component: resolve => require(['@/views/components/blankPage'],resolve),
            },
            {
                path: '/block',
                name: 'block',
                meta: {
                    requireAuth: true, //
                },
                component: resolve => require(['@/views/components/block'],resolve),
            },
            {
                path: '/help',
                name: 'help',
                meta: {
                    requireAuth: true, //
                },
                component: resolve => require(['@/views/components/help'],resolve),
            },
            {
                path: '/block/blockDetail',
                name: 'blockDetail',
                meta: {
                    requireAuth: true, //
                },
                component: resolve => require(['@/views/components/blockDetail'],resolve),
            },
            {
                path: '/nodeConfig',
                name: 'nodeConfig',
                meta: {
                    requireAuth: true, //
                },
                component: resolve => require(['@/views/components/nodeConfig'],resolve),
            },
            {
                path: '/groupConfig',
                name: 'groupConfig',
                meta: {
                    requireAuth: true, //
                },
                component: resolve => require(['@/views/components/group'],resolve),
            },
            {
                path: '/contractConfig',
                name: 'contractConfig',
                component: resolve => require(['@/views/components/contractConfig'],resolve),
            },
            {
                path: '/transaction',
                name: 'transaction',
                meta: {
                    requireAuth: true, //
                },
                component: resolve => require(['@/views/components/transaction'],resolve),
            },
            {
                path: '/transaction/transactionDetail',
                name: 'transactionDetail',
                meta: {
                    requireAuth: true, //
                },
                component: resolve => require(['@/views/components/transactionDetail'],resolve),
            },
        ]
        }
    ]
    const router = new Router({
        routes
    });
    router.onError((error) => {
        const pattern = /Loading chunk (\d)+ failed/g;
        const isChunkLoadFailed = error.message.match(pattern);
        const targetPath = router.history.pending.fullPath;
        if (isChunkLoadFailed) {
            router.go(0);
            router.replace(targetPath);
        }
    });

    export default router

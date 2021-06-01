<template>
    <div>

    </div>
</template>
<script>

    import router from '@/router'
    export default {
        name: 'blankPage',
        data: function () {
            return {

            }
        },
        mounted: function () {
            if(!this.$route.query.path){
                router.push({
                    name: 'home',
                });
            }else if(this.$route.query.path == "/transaction/transactionDetail"){
                router.push({
                    path: '/transaction',
                });
            }else if(this.$route.query.path == "/block/blockDetail"){
                router.push({
                    path: 'block',
                });
            }else if(this.$route.query.path == "/chainUser/userTransactionList"){
                router.push({
                    path: 'userTransactionList',
                });                              
            }else if(this.$route.query.path == "/chainContract/contractTransactionList"){
                router.push({
                    path: 'contractTransactionList',
                });
            }else{
                router.push({
                    path: this.$route.query.path,
                });
            }

        },
    }
</script>

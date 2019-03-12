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
            }else{
                router.push({
                    path: this.$route.query.path,
                });
            }

        },
    }
</script>

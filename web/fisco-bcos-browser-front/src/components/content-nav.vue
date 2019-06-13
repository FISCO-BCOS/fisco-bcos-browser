<template>
    <div class="content-nav">
        <div class="title">{{title}} {{content}}</div>
        <div class="content">
            <span v-if="chainShow&&projectShow" @click="linkPage('project')" class="table-link">首页</span>
            <span v-if="!chainShow&&projectShow" @click="linkPage('home')" class="table-link">概览</span>
            <span v-if="subtitle && projectShow" @click="linkPage(router)" class="table-link">/ {{subtitle}}</span>
            <span v-if="projectShow"> / {{contentTitle}}</span>
        </div>
    </div>
</template>
<script>
    import '@/assets/css/public.css'
    import {goPage} from '@/util/util'
    import router from '@/router'

    export default {
        name: 'navs',
        props:  ['hrTitle','navContent','navSubtitle','hrcontent','route','page'],
        data: function () {
            return {
                title: this.hrTitle || '',
                content: this.navContent || '',
                subtitle: this.navSubtitle || '',
                contentTitle: this.hrcontent || '',
                router: this.route || "",
                chainType: this.$route.query.chainType || "01",
                chainShow: false,
                projectShow: true,
                pages: this.page
            }
        },
        mounted: function () {
          if(this.title === "区块链"){
              this.projectShow = true;
              this.chainShow = true
          } else if(this.title === "项目"){
              this.projectShow = false;
              this.chainShow = false;
          }else{
              this.projectShow = true;
              this.chainShow = false;
          }
        },
        methods: {
            linkPage: function (name) {
               router.push({
                   name: name,
                   query: this.pages
               })
            },
            // goBlock: function (value) {
            //     router.push({
            //         name: value,
            //         chainType: this.$route.query.chainType
            //     })
            // },
            // goHome: function () {
            //     router.push({
            //         name: 'home',
            //         chainType: this.$route.query.chainType
            //     })
            // }
        }
    }
</script>
<style>
    .content-nav{
        width: 100%;
        height: 34px;
        overflow: hidden;
        color: #fff;
    }
    .content-nav .title{
        float: left;
        font-size: 16px;
    }
    .content-nav .content{
        float: right;
        font-size: 14px;
    }
</style>

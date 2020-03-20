<template>
    <div>Setting</div>
</template>

<script>
    export default {
        name: "Setting",
        data(){
          return{
            info:''
          }
        },
        methods:{
          // handleUserInfo(data){
          //   this.info = data;
          // }
        },
        created(){
          console.log("setting pages",this.$route.params);
        }
    }
</script>

<style scoped>

</style>

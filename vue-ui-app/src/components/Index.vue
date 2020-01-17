<template>
    <div>{{info}}</div>
</template>

<script>
    export default {
        name: "Index",
        data(){
          return{
            info:''
          }
        },
        methods:{
          handleUserInfo(data){
            this.info = data;
          }
        },
        created(){
          this.$axios.get("api/user-service/api/account/info")
            .then(response => this.handleUserInfo(response))
            .catch(error =>  this.$Message.error(error.toString()))
        }
    }
</script>

<style scoped>

</style>

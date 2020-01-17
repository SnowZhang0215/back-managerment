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
          handleUserInfo(data){
            this.info = data;
            this.$storage.setValue("userMenus",data.data.userMenus);
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

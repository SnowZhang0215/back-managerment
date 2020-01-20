<style scoped>

</style>
<template>
  <div>这里是首页内容{{userInfo}}</div>
</template>
<script>
  export default {
      data(){
        return {
          userInfo:""
        }
      },
      created(){
        this.userInfo = this.$route.params;
      }
  }
</script>


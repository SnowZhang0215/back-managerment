<template>
    <div>{{name}}</div>
</template>

<script>
    export default {
        name: "Index",
        data(){
          return{
            name:'Index'
          }
        },
        methods:{

        },
        created(){
          if (this.$storage.getValue("access_token")) {

          } else {

          }
        }
    }
</script>

<style scoped>

</style>

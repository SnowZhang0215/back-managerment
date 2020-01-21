<template>
  <Modal
    v-model="modal"
    :title="title"
    @on-ok="ok"
    @on-cancel="cancel">
    <p>{{content}}</p>
  </Modal>
</template>

<script>
    export default {
        name: "PermissionEdit",
        data(){
          return{
            modal : false,
            content: '',
            title:''
          }
        },
        methods:{
          ok(){

          },
          init(PermissionEditConfig){
            this.title = PermissionEditConfig.title;
            this.modal = true;
          },
          cancel(){
            this.modal = false;
          }
        },
    }
    export class PermissionEditConfig {
       title;
       model;
    }
</script>

<style scoped>

</style>

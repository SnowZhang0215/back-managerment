<template>
  <div>
    <Layout>
      <Sider>
        <Tree :data="data1"></Tree>
      </Sider>
      <Layout>
        <Content>
          <Card>
            <div style="height: 600px">Content</div>
          </Card>
        </Content>
      </Layout>
    </Layout>
  </div>
</template>

<script>
    export default {
      name: "PermissionList",
      data(){
        return{
          data1:[]
        }
      },
      created(){
        // this.$axios.get("api/user-service/menu/manage/list")
        //   .then(response => this.handleResult(response.data))
        //   .catch(error => this.$Message.error(error.toString()))
      },
      methods:{
        // handleResult(data){
        //   console.log(data)
        //   data.forEach(item =>{
        //
        //   });
        //   this.data1 = data;
        // }
      }
    }
</script>

<style scoped>

</style>

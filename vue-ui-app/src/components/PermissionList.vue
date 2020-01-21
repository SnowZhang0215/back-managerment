<template>
  <div>
    <Layout>
      <Sider>
        <Tree :data="menuData" @on-select-change="onSelectChange"></Tree>
      </Sider>
      <Layout>
        <Content>
          <Card>
            <div style="height: 500px">
              <div style="text-align: right;padding-bottom: 10px">
                  <Button style="margin-right: 10px" :type="btn.type" @click="handleClick(btn.clickMethod)" v-for="btn in btnDatas" :key="btn.id">{{btn.name}}</Button>
              </div>
              <Table stripe :columns="columns" height="430" :data="tableData"></Table>
              <div style="text-align: right;padding: 5px"><Page :total="tableData.length" show-sizer class="ivu-page"></Page></div>
            </div>
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
          menuData:[],
          tableData:[],
          btnMethods:{
            add: this.add,
            edit: this.edit,
            delete: this.delete
          },
          btnDatas:[
            {
              id:'1',
              name: '新建',
              type: 'success',
              clickMethod:'add'
            },
            {
              id:'2',
              name: '修改',
              type: 'primary',
              clickMethod:'edit'
            },
            {
              id:'3',
              name: '删除',
              type: 'error',
              clickMethod:'delete'
            }
            ],
          columns: [
            {
              type: 'selection',
              width: 60,
              align: 'center'
            },
            {
              title: 'ID',
              key: 'id'
            },
            {
              title: '编码',
              key: 'code'
            },
            {
              title: '名称',
              key: 'name'
            },
            {
              title: '父菜单',
              key: 'parentName'
            },
            {
              title: '路由路径',
              key: 'url'
            },
            {
              title: '是否菜单',
              key: 'isMenu'
            },
            {
              title: 'icon',
              key: 'icon'
            },
            {
              title: '是否默认',
              key: 'defaultType'
            },
            {
              title: '对应组件',
              key: 'component'
            }
          ],
        }
      },
      created(){
        this.$axios.get(this.$api.menuManageList)
          .then(response => this.handleResult(response.data))
          .catch(error => this.$Message.error(error.toString()))
      },
      methods:{
        add(){
          this.$Modal.confirm({
            title: '新建',
            content: 'add'
          })
        },
        edit(){
          this.$Modal.confirm({
            title: '修改',
            content: 'edit'
          })
        },
        delete(){
          this.$Modal.confirm({
            title: '删除',
            content: 'delete'
          })
        },
        onSelectChange(data){
          console.log(data[0]);
          if (data[0] && data[0].children)
          this.tableData = data[0].children;
          this.tableData.forEach(item => (item.parentName = data[0].name))
        },
        handleResult(data){
          console.log(data);
          data.forEach(item =>{
              addTitleForData(item)
          });
          function addTitleForData(data){
            if (data.children.length>0){
              data.children.forEach(item => addTitleForData(item))
            }
            data.expand= true;
            data.title = data.name;
          }
          this.menuData = data;
        },
        handleClick(funName){
          const functionName = this.btnMethods[funName];
          if (functionName){
            return functionName();
          }
        }
      }
    }
</script>

<style>
  .ivu-layout-sider {
    transition: all .2s ease-in-out;
    position: relative;
     background: #f5f5ff;
    min-width: 0;
  }
</style>

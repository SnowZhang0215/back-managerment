<template>
  <Modal v-model="modal" :title="title" :closable=false :mask-closable=false :width="70">
    <Form :model="dataModel" ref="permissionEdit" :label-width="150" :rules="rules">
      <FormItem label="ID" hidden>
        <Input v-model="dataModel.id"/>
      </FormItem>
      <Row>
        <FormItem class="ivu-form-item-left" label="权限父级菜单ID" >
          <Input :disabled=true v-model="dataModel.parentId"/>
        </FormItem>
        <FormItem class="ivu-form-item-right" label="权限父级菜单名称">
          <Input :disabled=true v-model="dataModel.parentName"/>
        </FormItem>
      </Row>
      <Row>
        <FormItem class="ivu-form-item-left" prop="code" label="权限编码">
          <Input v-model="dataModel.code" placeholder="请输入权限编码"/>
        </FormItem>
        <FormItem class="ivu-form-item-right" prop="name" label="权限名称">
          <Input v-model="dataModel.name" placeholder="请输入权限名称"/>
        </FormItem>
      </Row>
      <Row>
        <FormItem  class="ivu-form-item-left" prop="icon" label="权限图标">
          <Input v-model="dataModel.icon" placeholder="请输入权限名称"/>
        </FormItem>
        <FormItem class="ivu-form-item-right" prop="defaultType" label="默认显示">
          <Select v-model="dataModel.defaultType">
            <Option value="0">是</Option>
            <Option value="1">否</Option>
          </Select>
        </FormItem>
      </Row>
      <Row>
        <FormItem class="ivu-form-item-left" label="权限类型" prop="isMenu">
          <Select v-model="dataModel.isMenu" placeholder="请选择权限类型" @on-change="onPermissionTypeChange">
            <Option value="1">菜单</Option>
            <Option value="0">按钮</Option>
          </Select>
        </FormItem>
        <FormItem class="ivu-form-item-right"  prop="status" label="启用">
          <i-switch v-model="dataModel.status === '1'" size="large">
            <span slot="open">启用</span>
            <span slot="close">关闭</span>
          </i-switch>
        </FormItem>
      </Row>
      <Row v-show="this.dataModel.isMenu === '1'">
        <FormItem class="ivu-form-item-left"  label="跳转路径">
          <Input v-model="dataModel.url" placeholder="请输入跳转路径"/>
        </FormItem>
        <FormItem  class="ivu-form-item-right" label="对应组件名称">
          <Input v-model="dataModel.component" placeholder="请输入跳转组件"/>
        </FormItem>
      </Row>
      <Row v-show="this.dataModel.isMenu === '0'">
        <FormItem class="ivu-form-item-left"  label="按钮类型">
          <Input v-model="dataModel.btnType" placeholder="请输入按钮类型"/>
        </FormItem>
        <FormItem  class="ivu-form-item-right" label="按钮方法">
          <Input v-model="dataModel.btnMethod" placeholder="请输入按钮方法"/>
        </FormItem>
      </Row>
    </Form>
    <div slot="footer">
        <Button type="primary" @click="handleSubmit">保存</Button>
        <Button type="success" @click="handleReset" style="margin-left: 8px">重置</Button>
        <Button @click="cancel" style="margin-left: 8px">关闭</Button>
    </div>
  </Modal>
</template>

<script>
    export default {
        name: "PermissionEdit",
        data(){
          return{
            modal : false,
            dataModel: {},
            title:'',
            rules:{
              code: [
                { required: true, message: '请输入权限编码', trigger: 'blur' }
              ],
              name: [
                { required: true, message: '请输入权限名称', trigger: 'blur' }
              ],
              isMenu: {
                required: true,message:'请选择权限类型', trigger: 'change'
              },
              defaultType: {
                required: true,message:'请选择权是否默认显示', trigger:'change'
              },
              status: {
                required: true,message:'请选择是否启用', trigger:'blur'
              }
            }
          }
        },
        methods:{
          onPermissionTypeChange(value){
            console.log(value);
            console.log(this.dataModel)
          },
          handleSubmit(){
            this.$refs['permissionEdit'].validate((valid) => {
              if (valid) {
                this.$Message.success('Success!');
                this.request();
              } else {
                this.$Message.error('Fail!');
              }
            })
          },
          request(){
            console.log(this.dataModel);
            this.$axios.post(this.$api.menuManageAdd,this.dataModel)
              .then(response => this.handleUpdateOk(response))
              .catch(error => this.handleUpdateError(error))
          },
          handleUpdateOk(data){
            this.modal = false;
            if (data.errorCode === 200){
              this.$Modal.success({
                title:'提示',
                content:'创建成功'
              })
            } else{
              this.$Modal.error({
                title:'提示',
                content:data.errorMsg
              })
            }
          },
          handleUpdateError(error){
              this.$Message.error(error.toString());
          },
          init(PermissionEditConfig){
            this.title = PermissionEditConfig.title;
            this.dataModel = PermissionEditConfig.model;
            if (this.dataModel.id){
              //edit
            } else {
              //new
              this.dataModel.isMenu = "1";
              this.dataModel.status = "1";
            }
            this.modal = true;
          },
          cancel(){
            this.modal = false;
          },
          handleReset () {
            this.$refs['permissionEdit'].resetFields();
          },
        },
    }
    export class PermissionEditConfig {
       title;
       model;
    }
</script>

<style scoped>
  .ivu-form-item-left {
    float: left;
  }

  .ivu-form-item-right {
    float: left;
    margin-left: 20px;
  }
</style>

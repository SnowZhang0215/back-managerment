<template>
  <el-form :model="dataModel" ref="dataModel" class="form-class" label-width="20%">
    <el-form-item>
      <el-tree
        :data="dataModel.allPermissions"
        show-checkbox
        node-key="id"
        ref="tree"
        :expand-on-click-node="true"
        :highlight-current="true"
        default-expand-all
        :check-strictly="true"
        :getCheckedNodes="getCheckedNodes"
        :default-checked-keys="dataModel.rolePermissions"
      ></el-tree>
    </el-form-item>
    <el-row class="row">
      <el-col :lg="12" :md="12" :sm="24">
        <el-form-item>
          <el-button type="primary" @click="submitForm('dataModel')">提交</el-button>
          <el-button @click="cancel()">关闭</el-button>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>
<script>
import {
  createRole,
  editRole,
  submitAuthRole
} from "../../service/rolemanager.service";
import { noticeMsg } from "../../common/common.service";
export default {
  props: { dataModel: Object, onfinish: Function, onClose: Function },
  name: "AuthRole",
  data() {
    return {};
  },
  created() {
    console.log(this.dataModel);
    // this.setCheckedKeys();
  },
  methods: {
    cancel() {
      console.log("close");
      this.onClose();
    },
    submitForm(formName) {
      var checks = this.$refs.tree.getCheckedNodes();
      this.dataModel.checks = checks;
      let option = {};
      option.onSuccess = this.onSubmitOk;
      option.onFaild = this.onSubmitFaild;
      option.params = this.dataModel;
      submitAuthRole(option);
    },
    setCheckedNodes() {
      this.$refs.tree.setCheckedNodes(this.dataModel.rolePermissions);
    },
    getCheckedNodes(val) {},
    onSubmitOk(data) {
      if (data.errorCode == 200) {
        console.log("提交成功");
        this.onfinish(1);
      } else {
        noticeMsg(data.errorMsg, true);
      }
    },
    onSubmitFaild(error) {
      noticeMsg(error, true);
    }
  }
};
</script>
<style scoped>
.form-class {
  margin: 0 auto;
}
.form-class .row {
  display: flex;
  justify-content: space-around;
}
</style>
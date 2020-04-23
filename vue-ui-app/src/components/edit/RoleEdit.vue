<template>
  <el-form :model="dataModel" :rules="rules" ref="dataModel" class="form-class" label-width="20%">
    <el-row class="row">
      <el-col :lg="18" :md="18" :sm="18">
        <el-form-item label="ID" prop="id">
          <el-input v-model="dataModel.id" disabled="disabled"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row class="row">
      <el-col :lg="18" :md="18" :sm="18">
        <el-form-item label="角色编码" prop="code">
          <el-input v-model="dataModel.code"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row class="row">
      <el-col :lg="18" :md="18" :sm="18">
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="dataModel.name"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row class="row">
      <el-col :lg="12" :md="12" :sm="24">
        <el-form-item>
          <el-button type="primary" @click="submitForm('dataModel')">提交</el-button>
          <el-button v-if="dataModel.id == null " @click="resetForm('dataModel')">重置</el-button>
          <el-button v-if="dataModel.id" @click="cancel()">关闭</el-button>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>
<script>
import { createRole, editRole } from "../../service/rolemanager.service";
import { noticeMsg } from "../../common/common.service";
export default {
  props: { dataModel: Object, onfinish: Function, onClose: Function },
  name: "RoleEdit",
  data() {
    return {
      rules: {
        code: [{ required: true, message: "请输入角色编码", trigger: "blur" }],
        name: [{ required: true, message: "请输入角色名称", trigger: "blur" }]
      }
    };
  },
  created() {
    console.log(this.dataModel);
  },
  methods: {
    cancel() {
      console.log("close");
      this.onClose();
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          let option = {};
          option.onSuccess = this.onSubmitOk;
          option.onFaild = this.onSubmitFaild;
          option.params = this.dataModel;
          if (this.dataModel.id) {
            editRole(option);
          } else {
            createRole(option);
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
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
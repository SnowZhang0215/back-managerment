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
        <el-form-item label="API路径" prop="path">
          <el-input v-model="dataModel.path"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row class="row">
      <el-col :lg="18" :md="18" :sm="18">
        <el-form-item label="API描述" prop="description">
          <el-input v-model="dataModel.description"></el-input>
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
import { createApiEntity, editApiEntity } from "../../service/api.service";
import { noticeMsg } from "../../common/common.service";
export default {
  props: { dataModel: Object, onfinish: Function, onClose: Function },
  name: "ApiEntityEdit",
  data() {
    return {
      rules: {
        path: [
          { required: true, message: "请输入api对应的path", trigger: "blur" }
        ],
        description: [
          { required: true, message: "请输入接口的描述", trigger: "blur" }
        ]
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
            editApiEntity(option);
          } else {
            createApiEntity(option);
          }
          //   console.log(this.dataModel);
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
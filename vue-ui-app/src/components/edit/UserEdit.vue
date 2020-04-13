<template>
  <el-form :model="dataModel" :rules="rules" ref="dataModel" class="form-class" label-width="35%">
    <el-row class="row">
      <el-col :lg="12" :md="12" :sm="24">
        <el-form-item label="ID" prop="id">
          <el-input v-model="dataModel.id" disabled="disabled"></el-input>
        </el-form-item>
      </el-col>
      <el-col :lg="12" :md="12" :sm="24">
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="dataModel.userName"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row class="row">
      <el-col :lg="12" :md="12" :sm="24">
        <el-form-item label="手机" prop="phoneNumber">
          <el-input v-model="dataModel.phoneNumber"></el-input>
        </el-form-item>
      </el-col>
      <el-col :lg="12" :md="12" :sm="24">
        <el-form-item label="性别" prop="gender">
          <el-select v-model="dataModel.gender" placeholder="请选性别">
            <el-option
              v-for="item in genderType"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row class="row">
      <el-col :lg="12" :md="12" :sm="24">
        <el-form-item label="邮箱" prop="emile">
          <el-input v-model="dataModel.emile"></el-input>
        </el-form-item>
      </el-col>
      <el-col :lg="12" :md="12" :sm="24">
        <el-form-item label="用户角色" prop="userHasRoleId">
          <el-select
            v-model="dataModel.userHasRoleId"
            multiple
            filterable
            value-key="id"
            default-first-option
            placeholder="请选择用户角色"
          >
            <el-option
              v-for="item in dataModel.allRoles"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row class="row">
      <el-col :lg="12" :md="12" :sm="24">
        <el-form-item>
          <el-button type="primary" @click="submitForm('dataModel')">提交</el-button>
          <el-button v-if="dataModel.id == null" @click="resetForm('dataModel')">重置</el-button>
          <el-button v-if="dataModel.id" @click="cancel()">关闭</el-button>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>
<script>
import { createUser, updateUser } from "../../service/userinfo.service";
import { noticeMsg } from "../../common/common.service";
export default {
  props: { dataModel: Object, onfinish: Function, onClose: Function },
  name: "UserEdit",
  data() {
    var validateUserRole = (rule, value, callback) => {
      if (this.dataModel.userHasRoleId.length == 0) {
        callback(new Error("请选择用户角色"));
      } else {
        callback();
      }
    };
    return {
      genderType: [
        { label: "男", value: 0 },
        { label: "女", value: 1 }
      ],
      rules: {
        userName: [
          { required: true, message: "用户名不能为空", trigger: "blur" }
        ],
        userHasRoleId: [
          { required: true, validator: validateUserRole, trigger: "change" }
        ]
      }
    };
  },
  methods: {
    cancel() {
      console.log("close");
      this.onClose();
    },
    submitForm(formName) {
      console.log(this.dataModel.userHasRoleId);
      this.$refs[formName].validate(valid => {
        if (valid) {
          let option = {};
          option.onSuccess = this.onSubmitOk;
          option.onFaild = this.onSubmitFaild;
          option.params = this.dataModel;
          if (this.dataModel.id) {
            updateUser(option);
          } else {
            createUser(option);
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
    onPermissionTypeChange(val) {
      console.log("currnt chose val", val);
    },
    onSubmitOk(data) {
      if (data.errorCode == 200) {
        console.log("提交成功");
        this.onfinish(1);
      } else {
        noticeMsg(data.errorMsg);
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
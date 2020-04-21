<template>
  <el-form :model="dataModel" :rules="rules" ref="dataModel" class="form-class" label-width="20%">
    <el-row class="row" v-show="false">
      <el-col :lg="18" :md="18" :sm="18">
        <el-form-item label="ID" prop="id">
          <el-input v-model="dataModel.id" disabled="disabled"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row class="row">
      <el-col :lg="18" :md="18" :sm="18">
        <el-form-item label="组织ID" prop="orgId">
          <el-input v-model="dataModel.orgId"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row class="row">
      <el-col :lg="18" :md="18" :sm="18">
        <el-form-item label="组织名称" prop="orgName">
          <el-input v-model="dataModel.orgName"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row class="row">
      <el-col :lg="18" :md="18" :sm="18">
        <el-form-item label="是否激活" prop="openFlag">
          <el-select
            v-model="dataModel.openFlag"
            placeholder="请选择是否激活组织"
          >
            <el-option
              v-for="item in openFlagOptions"
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
import { createOrg, editOrg } from "../../service/orgmanager.service";
import { queryUserByName } from "../../service/userinfo.service";
import { noticeMsg } from "../../common/common.service";
export default {
  props: { dataModel: Object, onfinish: Function, onClose: Function },
  name: "OrgManagerEdit",
  data() {
    return {
      rules: {
        orgName: [
          { required: true, message: "请输入组织名称", trigger: "blur" }
        ],
        orgId: [
          { required: true, message: "请输入组织ID", trigger: "blur" }
        ],
        openFlag: [
          { required: true, message: "请选择是否激活组织", trigger: "change" }
        ]
      },
      openFlagOptions: [
        { label: "激活", value: "1" },
        { label: "未激活", value: "0" }
      ],
      loading: false
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
            editOrg(option);
          } else {
            createOrg(option);
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
    },
    queryUser(query) {
      if (query !== "") {
        this.loading = true;
        var option = {};
        option.onSuccess = this.queryUserOk;
        option.onFaild = this.queryUserError;
        option.params = {
          userName: query
        };
        queryUserByName(option);
      }
    },
    queryUserOk(data) {
      this.loading = false;
      console.log("query", data);
      if (data && data.errorCode == 200) {
        let remoteData = data.data;
        remoteData.forEach(element => {
          element.label = element.userName;
        });
        this.dataModel.orgMasterOpt = remoteData;
      } else {
        noticeMsg(data.errorMsg);
      }
    },
    queryUserError(data) {
      this.loading = false;
      console.log(data);
      noticeMsg(data, true);
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
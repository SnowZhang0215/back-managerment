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
        <el-form-item label="所属组织" prop="orgId">
          <el-select
            v-model="dataModel.orgId"
            filterable
            :remote="true"
            value-key="id"
            :remote-method="queryOrg"
            :loading="loading"
            default-first-option
            placeholder="请选择所属组织"
          >
            <el-option
              v-for="item in dataModel.orgOpt"
              :key="item.id"
              :label="item.orgName"
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
          <el-button v-if="dataModel.id == null " @click="resetForm('dataModel')">重置</el-button>
          <el-button v-if="dataModel.id" @click="cancel()">关闭</el-button>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>
<script>
import {
  editOrgMember
} from "../../service/orgmember.service";
import {queryOrgByName} from '../../service/orgmanager.service'
import { noticeMsg } from "../../common/common.service";
export default {
  props: { dataModel: Object, onfinish: Function, onClose: Function },
  name: "MemberOrgEdit",
  data() {
    return {
      rules: {
        
        orgId: [
          { required: true, message: "请选择所属组织", trigger: "change" }
        ]
      },
      loading: false,
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
          
          editOrgMember(option);
          
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
     queryOrg(query) {
      if (query !== "") {
        this.loading = true;
        var option = {};
        option.onSuccess = this.queryOrgOk;
        option.onFaild = this.queryOrgError;
        option.params = {
          orgName: query
        };
        queryOrgByName(option);
      }
    },
    queryOrgOk(data) {
      this.loading = false;
      console.log("query", data);
      if (data && data.errorCode == 200) {
        let remoteData = data.data;
        this.dataModel.orgOpt = remoteData;
      } else {
        noticeMsg(data.errorMsg);
      }
    },
    queryOrgError(data) {
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
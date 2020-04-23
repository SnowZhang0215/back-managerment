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
        <el-form-item label="游戏ID" prop="gameId">
          <el-input v-model="dataModel.gameId"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row class="row">
      <el-col :lg="18" :md="18" :sm="18">
        <el-form-item label="游戏名称" prop="gameName">
          <el-input v-model="dataModel.gameName"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row class="row">
      <el-col :lg="18" :md="18" :sm="18">
        <el-form-item label="战斗力" prop="fightCapacity">
          <el-input v-model="dataModel.fightCapacity"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row class="row">
      <el-col :lg="18" :md="18" :sm="18">
        <el-form-item label="成员身份" prop="orgRoleType">
          <!-- <el-input v-model="dataModel.orgMaster"></el-input> -->
          <el-select
            v-model="dataModel.orgRoleType"
            filterable
            default-first-option
            placeholder="请选择成员身份"
          >
            <el-option
              v-for="item in orgRoleTypeOpt"
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
import {
  createOrgMember,
  editOrgMember
} from "../../service/orgmember.service";
import { noticeMsg } from "../../common/common.service";
export default {
  props: { dataModel: Object, onfinish: Function, onClose: Function },
  name: "OrgMemberEdit",
  data() {
    return {
      rules: {
        gameId: [{ required: true, message: "请输入游戏ID", trigger: "blur" }],
        gameName: [
          { required: true, message: "请输入游戏名称", trigger: "blur" }
        ],
        fightCapacity: [
          { required: true, message: "请输入战斗力", trigger: "blur" }
        ],
        orgRoleType: [
          { required: true, message: "请选择成员身份", trigger: "change" }
        ]
      },
      orgRoleTypeOpt: [
        { label: "首领", value: 0 },
        { label: "高层", value: 1 },
        { label: "精英", value: 2 },
        { label: "成员", value: 3 },
        { label: "学员", value: 4 }
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
            editOrgMember(option);
          } else {
            createOrgMember(option);
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
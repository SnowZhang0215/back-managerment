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
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="dataModel.userName"></el-input>
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
        <el-form-item label="用户角色">
          <el-select
            v-model="dataModel.userHasRoleId"
            multiple
            filterable
            value-key="id"
            :loading="loading"
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
import { createUser, updateUser} from "../../service/userinfo.service";
import { noticeMsg } from "../../common/common.service";
export default {
  props: { dataModel: Object, onfinish: Function, onClose: Function },
  name: "UserEdit",
  data() {
    var validateUrl = (rule, value, callback) => {
      if (this.dataModel.permissionType == 1) {
        if (!value) {
          callback(new Error("请输入菜单路由跳转路径"));
        } else {
          callback();
        }
      } else {
        callback();
      }
    };
    let validateComponent = (rule, value, callback) => {
      if (this.dataModel.permissionType == 1) {
        if (!value) {
          callback(new Error("请输入菜单对应的组件"));
        } else {
          callback();
        }
      } else {
        callback();
      }
    };
    let validateBtnMethod = (rule, value, callback) => {
      if (this.dataModel.permissionType == 2) {
        if (!value) {
          callback(new Error("请输入按钮对应的方法"));
        } else {
          callback();
        }
      } else {
        callback();
      }
    };
    let validateBtnType = (rule, value, callback) => {
      if (this.dataModel.permissionType == 2) {
        if (!value) {
          callback(new Error("请选择按钮的类型"));
        } else {
          callback();
        }
      } else {
        callback();
      }
    };
    return {
      defaultTypeOptions: [
        { label: "是", value: "0" },
        { label: "否", value: "1" }
      ],
      permissionTypeOptions: [
        { label: "菜单", value: "1" },
        { label: "按钮", value: "2" }
      ],
      permissionStatusOptions: [
        { label: "启用", value: "1" },
        { label: "未启用", value: "0" }
      ],
      btnTypeOptions: [
        { label: "主要按钮", value: "primary" },
        { label: "成功按钮", value: "success" },
        { label: "信息按钮", value: "info" },
        { label: "警告按钮", value: "warning" },
        { label: "危险按钮", value: "danger" }
      ],
      loading: false,
      apiOptions: [],
      // permissionApiIds: [],
      rules: {
        name: [
          { required: true, message: "权限名称不能为空", trigger: "blur" }
        ],
        defaultType: [
          { required: true, message: "请选择是否默认", trigger: "change" }
        ],
        permissionType: [
          { required: true, message: "请选择权限类型", trigger: "change" }
        ],
        status: [
          { required: true, message: "请选择启用状态", trigger: "change" }
        ],
        sort: [{ required: true, message: "请输入序号", trigger: "blur" }],
        url: [{ validator: validateUrl, trigger: "blur" }],
        component: [
          {
            validator: validateComponent,
            trigger: "blur"
          }
        ],
        btnType: [{ validator: validateBtnType, trigger: "change" }],
        btnMethod: [{ validator: validateBtnMethod, trigger: "blur" }]
      }
    };
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
          // this.dataModel.permissionApiIds = this.permissionApiIds;
          option.params = this.dataModel;
          // console.log(this.permissionApiIds);
          if (this.dataModel.id) {
            updatePermission(option);
          } else {
            createPermission(option);
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
    onPermissionTypeChange(val) {
      console.log("currnt chose val", val);
    },
    onSubmitOk(data) {
      if (data.errorCode == 200) {
        console.log("提交成功");
        this.onfinish(1);
      }else{
        noticeMsg(data.errorMsg);
      }
    },
    onSubmitFaild(error) {
      noticeMsg(error, true);
    },
    queryApi(query) {
      if (query !== "") {
        this.loading = true;
        var option = {};
        option.onSuccess = this.queryApiOk;
        option.onFaild = this.queryApiError;
        option.params = {
          desc: query
        };
        queryApiByDesc(option);
      }
    },
    queryApiOk(data) {
      this.loading = false;
      console.log("query", data);
      if (data && data.errorCode == 200) {
        let remoteData = data.data;
        remoteData.forEach(element => {
          element.label = element.description;
        });
        this.dataModel.apiOptions = remoteData;
      } else {
        noticeMsg(data.errorMsg);
      }
    },
    queryApiError(data) {
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
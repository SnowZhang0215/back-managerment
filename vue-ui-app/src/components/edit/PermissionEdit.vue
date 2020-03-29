<template>
  <el-form :model="dataModel" :rules="rules" ref="dataModel" class="form-class" label-width="35%">
    <el-row class="row">
      <el-col :lg="12" :md="12" :sm="24">
        <el-form-item label="父权限ID" prop="parentId">
          <el-input v-model="dataModel.parentId" disabled="disabled"></el-input>
        </el-form-item>
      </el-col>
      <el-col :lg="12" :md="12" :sm="24">
        <el-form-item label="权限名称" prop="name">
          <el-input v-model="dataModel.name"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row class="row">
      <el-col :lg="12" :md="12" :sm="24">
        <el-form-item label="是否默认" prop="defaultType">
          <el-select v-model="dataModel.defaultType" placeholder="请选择是否默认">
            <el-option
              v-for="item in defaultTypeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :lg="12" :md="12" :sm="24">
        <el-form-item label="权限类型" prop="permissionType">
          <el-select
            v-model="dataModel.permissionType"
            @change="onPermissionTypeChange"
            placeholder="请选择权限类型"
          >
            <el-option
              v-for="item in permissionTypeOptions"
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
        <el-form-item label="是否启用" prop="status">
          <el-select v-model="dataModel.status" placeholder="请选择启用状态">
            <el-option
              v-for="item in permissionStatusOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :lg="12" :md="12" :sm="24">
        <el-form-item label="排序" prop="sort">
          <el-input v-model="dataModel.sort"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row class="row">
      <el-col
        :lg="12"
        :md="12"
        :sm="24"
        v-if="dataModel.permissionType == 1 || dataModel.permissionType == null "
      >
        <el-form-item label="路由路径" prop="url">
          <el-input v-model="dataModel.url"></el-input>
        </el-form-item>
      </el-col>
      <el-col
        :lg="12"
        :md="12"
        :sm="24"
        v-if="dataModel.permissionType == 1 || dataModel.permissionType == null "
      >
        <el-form-item label="权限对应组件" prop="component">
          <el-input v-model="dataModel.component"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row class="row">
      <el-col :lg="12" :md="12" :sm="24" v-if="dataModel.permissionType == 2">
        <el-form-item label="按钮方法" prop="btnMethod">
          <el-input v-model="dataModel.btnMethod"></el-input>
        </el-form-item>
      </el-col>
      <el-col :lg="12" :md="12" :sm="24" v-if="dataModel.permissionType == 2">
        <el-form-item label="按钮类型" prop="btnType">
          <el-select v-model="dataModel.btnType" placeholder="请选择按钮类型">
            <el-option
              v-for="item in btnTypeOptions"
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
          <el-button @click="resetForm('dataModel')">重置</el-button>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>
<script>
import { createPermission } from "../../service/menuService";
import { noticeMsg } from "../../common/common.service";
export default {
  props: { dataModel: Object, onfinish: Function },
  name: "PermissionEdit",
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
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          let option = {};
          option.onSuccess = this.onSubmitOk;
          option.onFaild = this.onSubmitFaild;
          option.params = this.dataModel;
          createPermission(option);
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
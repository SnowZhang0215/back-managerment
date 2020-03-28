<template>
  <el-form :model="dataModel" :rules="rules" ref="dataModel" :inline="true">
    <el-form-item label="父权限ID" prop="parentId">
      <el-input v-model="dataModel.parentId" disabled="disabled"></el-input>
    </el-form-item>
    <el-form-item label="权限名称" prop="name">
      <el-input v-model="dataModel.name"></el-input>
    </el-form-item>
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
    <el-form-item label="权限类型" prop="permissionType">
      <el-select v-model="dataModel.permissionType" placeholder="请选择权限类型">
        <el-option
          v-for="item in permissionTypeOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="路由路径" prop="url">
      <el-input v-model="dataModel.url"></el-input>
    </el-form-item>
    <el-form-item label="权限对应组件" prop="component">
      <el-input v-model="dataModel.component"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm('dataModel')">立即创建</el-button>
      <el-button @click="resetForm('dataModel')">重置</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
export default {
  props: ["dataModel"],
  name: "PermissionEdit",
  data() {
    return {
      defaultTypeOptions: [
        { label: "是", value: "0" },
        { label: "否", value: "1" }
      ],
      permissionTypeOptions: [
        { label: "菜单", value: "1" },
        { label: "按钮", value: "2" }
      ],
      rules: {
        name: [
          { required: true, message: "权限名称不能为空", trigger: "blur" }
        ],
        region: [
          { required: true, message: "请选择活动区域", trigger: "change" }
        ],
        date1: [
          {
            type: "date",
            required: true,
            message: "请选择日期",
            trigger: "change"
          }
        ],
        date2: [
          {
            type: "date",
            required: true,
            message: "请选择时间",
            trigger: "change"
          }
        ],
        type: [
          {
            type: "array",
            required: true,
            message: "请至少选择一个活动性质",
            trigger: "change"
          }
        ],
        resource: [
          { required: true, message: "请选择活动资源", trigger: "change" }
        ],
        desc: [{ required: true, message: "请填写活动形式", trigger: "blur" }]
      }
    };
  },
  created() {
    console.log(this.dataModel);
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          alert("submit!");
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
};
</script>
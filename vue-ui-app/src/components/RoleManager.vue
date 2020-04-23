<template>
  <el-container>
    <el-main class="main-content">
      <el-row class="btn-opt">
        <el-button
          v-for="btn in pageBtns"
          :key="btn.id"
          :type="btn.btnType"
          @click="handleBtnClick(btn.btnMethod)"
        >{{btn.name}}</el-button>
      </el-row>
      <el-table
        :data="tableData"
        :stripe="true"
        :highlight-current-row="true"
        style="width: 100%"
        :header-cell-style="tableHeaderStyle"
        :border="true"
        @select="onRowSelect"
        @select-all="onSelectAll"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="id" label="ID" width="55"></el-table-column>
        <el-table-column prop="code" label="角色编码"></el-table-column>
        <el-table-column prop="name" label="角色名称"></el-table-column>
      </el-table>
      <el-row>
        <el-pagination
          background
          layout="prev, total, pager, sizes, next"
          :total="totalCount"
          :hide-on-single-page="false"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :page-sizes="[2,10, 20, 50, 100]"
          :page-size="pageSize"
          :current-page="currentPage"
        ></el-pagination>
      </el-row>
    </el-main>
    <el-dialog
      :title="dialogTitle"
      :visible.sync="showDialog"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :destroy-on-close="true"
      center
      :append-to-body="true"
      width="50%"
      @open="onDialogOpen"
      @close="onDialogClose"
    >
      <auth-role
        v-if="editView == 'authRole'"
        :data-model="editDataModel"
        :onfinish="onEditFinish"
        :onClose="close"
      ></auth-role>
      <role-edit
        v-if="editView == 'roleEdit'"
        :data-model="editDataModel"
        :onfinish="onEditFinish"
        :onClose="close"
      ></role-edit>
    </el-dialog>
  </el-container>
</template>

<script>
import { getPermissionBtns, getAllPermission } from "../service/menuService";
import { convertTree } from "../service/tree.service";
import {
  loadAllRoles,
  loadRolePermission
} from "../service/rolemanager.service";
import { showMsgBox, noticeMsg } from "../common/common.service";

import RoleEdit from "../components/edit/RoleEdit";
import AuthRole from "../components/authrole/AuthRole";
export default {
  name: "RoleManager",
  components: {
    RoleEdit,
    AuthRole
  },
  data() {
    return {
      info: "",
      editView: "roleEdit", //编辑框默认是role编辑
      tableData: [],
      allPermission: [],
      currentPage: 1,
      pageSize: 10,
      totalCount: 0,
      selection: [],
      pageBtns: [],
      btnMethods: {
        add: this.add,
        edit: this.edit,
        delete: this.delete,
        authRole: this.authRole
      },
      //eidt
      showDialog: false,
      dialogTitle: "",
      editDataModel: {}
    };
  },
  created() {
    // this.lodadAllPermission();
    this.getTableData();
    this.getUserBtns();
    this.lodadAllPermission();
  },
  methods: {
    lodadAllPermission() {
      let opt = {};
      opt.onSuccess = this.onLoadPermission;
      opt.onFaild = this.onFaild;
      getAllPermission(opt);
    },
    onLoadPermission(data) {
      console.log(data);
      if (data.errorCode === 200) {
        this.allPermission = convertTree(data.data);
        console.log("allPermission", this.allPermission);
      }
    },
    onFaild(data) {
      console.log(data);
    },
    getUserBtns() {
      if (this.$store.state.asideActiveCode) {
        console.log("current active code :", this.$store.state.asideActiveCode);
      }

      let btns = getPermissionBtns(
        this.$store.getters.getMenuData,
        this.$store.state.asideActiveCode
      );
      this.pageBtns = btns;
    },
    handleBtnClick(methodName) {
      console.log(methodName);
      const functionName = this.btnMethods[methodName];
      if (functionName) {
        return functionName();
      }
    },
    add() {
      this.editDataModel = {};
      this.showEditDialog("新建", "roleEdit", this.editDataModel);
    },
    edit() {
      if (this.selection.length <= 0) {
        noticeMsg("请选择要编辑的数据", true);
      } else if (this.selection.length > 1) {
        noticeMsg("请选择一条数据", true);
      } else {
        console.log(this.selection[0]);
        this.showEditDialog("编辑", "roleEdit", this.selection[0]);
      }
    },

    delete() {
      if (this.selection.length == 0) {
        noticeMsg("请选择要删除的数据", true);
      } else {
        let option = {};
        option.onSuccess = this.onDeleteOk;
        option.onFaild = this.onDeleteError;
        option.params = this.selection;
        deletePermission(option);
      }
    },
    authRole() {
      if (this.selection.length == 0) {
        noticeMsg("请选择要授权的角色", true);
      } else {
        if (this.allPermission.length == 0) {
          noticeMsg("无法获取权限列表请稍后再试", true);
          return;
        }
        let authModel = {}
        authModel.id = this.selection[0].id;
        this.getRolePermission(authModel);
      }
    },
    getRolePermissionOK(data) {
      console.log(data);
      if (data.errorCode == 200) {
        let authModel = {};
        authModel.roleId = this.selection[0].id;
        authModel.allPermissions = this.allPermission;
        authModel.rolePermissions = data.data;
        this.showEditDialog("角色授权", "authRole", authModel);
      } else {
        noticeMsg(data.errorMsg);
      }
    },
    getRolePermission(authModel) {
      let option = {};
      option.onSuccess = this.getRolePermissionOK;
      option.params = authModel;
      loadRolePermission(option);
    },
    onDeleteOk(data) {
      if (data.errorCode === 200) {
        noticeMsg(data.errorMsg, true);
        this.getTableData();
      } else {
        noticeMsg(data.errorMsg, true);
      }
    },
    onDeleteError(data) {
      noticeMsg(data, true);
    },
    showEditDialog(title, view, data) {
      this.showDialog = true;
      this.dialogTitle = title;
      this.editView = view;
      this.editDataModel = data;
    },
    getTableData() {
      let requestOpt = {};
      let params = {};
      params.pageNum = this.currentPage;
      params.pageSize = this.pageSize;
      requestOpt.onSuccess = this.listRoleOk;
      requestOpt.onFaild = this.listRoleError;
      requestOpt.params = params;
      loadAllRoles(requestOpt);
    },
    listRoleOk(data) {
      console.log(data);
      if (data.errorCode === 200) {
        this.tableData = data.data.content;
        this.pageSize = data.data.pageSize;
        this.currentPage = data.data.pageNum;
        this.totalCount = data.data.totalSize;
        console.log("数据改变了", this.tableData);
        console.log("totalCount", this.totalCount);
      }
    },
    listRoleError(data) {
      console.log(data);
    },
    onRowSelect(selection, row) {
      this.selection = selection;
    },
    onSelectAll(selection) {
      this.selection = selection;
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.getTableData();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val;
      this.getTableData();
    },

    tableHeaderStyle({ row, column, rowIndex, columnIndex }) {
      if (rowIndex === 0) {
        return "background-color: #f5f7fa;";
      } else {
        return "";
      }
    },
    onEditFinish(result) {
      if (result) {
        this.showDialog = false;
        this.getTableData();
        // this.lodadAllPermission();
      }
    },
    close() {
      this.showDialog = false;
    },
    onDialogOpen() {
      this.$nextTick(() => {
        this.$refs;
      });
    },
    onDialogClose(){
        this.editView = "roleEdit"
    }
  }
};
</script>

<style scoped>
.btn-opt {
  margin-bottom: 5px;
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
}
/* .table-header {
  background-color: cornflowerblue;
} */
</style>

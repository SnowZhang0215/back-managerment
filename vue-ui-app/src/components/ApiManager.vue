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
        <el-table-column prop="path" label="API路径"></el-table-column>
        <el-table-column prop="description" label="API接口描述"></el-table-column>
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
    >
      <api-entity-edit :data-model="editDataModel" :onfinish="onEditFinish" :onClose="close"></api-entity-edit>
    </el-dialog>
  </el-container>
</template>

<script>
import { getPermissionBtns } from "../service/menuService";
import { listApiEntity } from "../service/api.service";
import { showMsgBox, noticeMsg } from "../common/common.service";
import ApiEntityEdit from "../components/edit/ApiEntityEdit";
export default {
  name: "ApiManager",
  components: {
    ApiEntityEdit
  },
  data() {
    return {
      info: "",
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      totalCount: 0,
      selection: [],
      pageBtns: [],
      btnMethods: {
        add: this.add,
        edit: this.edit,
        delete: this.delete
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
  },
  methods: {
    getUserBtns() {
      if (this.$store.state.asideActiveCode) {
        console.log("current active code :", this.$store.state.asideActiveCode);
      }

      let btns = getPermissionBtns(
        this.$store.getters.getMenuData,
        this.$store.state.asideActiveCode
      );
      console.log(btns);
      this.pageBtns = btns;
    },
    lodadAllPermission() {
      let opt = {};
      opt.onSuccess = this.onLoadPermission;
      opt.onFaild = this.onFaild;
      getAllPermission(opt);
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
      this.showEditDialog("新建", this.editDataModel);
    },
    edit() {
      if (this.selection.length <= 0) {
        noticeMsg("请选择要编辑的数据", true);
      } else if (this.selection.length > 1) {
        noticeMsg("请选择一条数据", true);
      } else {
        console.log(this.selection[0]);
        this.showEditDialog("编辑", this.selection[0]);
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
    showEditDialog(title, data) {
      this.showDialog = true;
      this.dialogTitle = title;
      this.editDataModel = data;
    },
    getTableData() {
      let requestOpt = {};
      let params = {};
      params.pageNum = this.currentPage;
      params.pageSize = this.pageSize;
      requestOpt.onSuccess = this.listApiEntityOk;
      requestOpt.onFaild = this.listApiEntityError;
      requestOpt.params = params;
      listApiEntity(requestOpt);
    },
    listApiEntityOk(data) {
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
    listApiEntityError(data) {
      console.log(data);
    },
    onRowSelect(selection, row) {
      this.selection = selection;

      console.log("selection:", selection);
      console.log("row:", row);
    },
    onSelectAll(selection) {
      this.selection = selection;
      console.log("selection:", selection);
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
    onLoadPermission(data) {
      console.log(data);
      if (data.errorCode === 200) {
        this.tableData = data.data;
        this.treeData = convertTree(data.data);
        console.log("tree", this.treeData);
      }
    },
    onFaild(data) {
      console.log(data);
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

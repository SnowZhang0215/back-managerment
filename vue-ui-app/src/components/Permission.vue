<template>
  <el-container>
    <el-aside style="width:200px">
      <el-tree
        v-if="treeData.length > 0"
        :data="treeData"
        node-key="id"
        :expand-on-click-node="false"
        :highlight-current="true"
        :default-expanded-keys="[0]"
        :current-node-key="0"
        @node-click="onNodeClick"
      ></el-tree>
    </el-aside>
    <el-main class="main-content">
      <el-row class="btn-opt">
        <el-button
          v-for="btn in pageBtns"
          :key="btn.id"
          :type="btn.type"
          @click="handleBtnClick(btn.method)"
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
        <el-table-column prop="isMenu" label="权限类型"></el-table-column>
        <el-table-column prop="url" label="权限跳转路径"></el-table-column>
        <el-table-column prop="name" label="名称"></el-table-column>
        <el-table-column prop="component" label="组件"></el-table-column>
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
      center
      :append-to-body="true"
      width="50%"
    >
      <permission-edit :data-model="editDataModel"></permission-edit>
    </el-dialog>
  </el-container>
</template>

<script>
import { getAllPermission } from "../service/menuService";
import {
  getSubMenusByParentId,
  getPermissionDetail
} from "../service/menuService";
import { convertTree } from "../service/tree.service";
import { showMsgBox, noticeMsg } from "../common/common.service";
import PermissionEdit from "./edit/PermissionEdit";
export default {
  name: "Permission",
  components: {
    PermissionEdit
  },
  data() {
    return {
      info: "",
      currentKey: "0",
      tableData: [],
      treeData: [],
      currentPage: 1,
      pageSize: 10,
      totalCount: 0,
      selection: [],
      pageBtns: [
        {
          id: 1,
          name: "新增",
          type: "primary",
          method: "add"
        },
        {
          id: 2,
          name: "修改",
          type: "warning",
          method: "edit"
        },
        {
          id: 3,
          name: "删除",
          type: "danger",
          method: "delete"
        }
      ],
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
  reloadTable() {},
  created() {
    let opt = {};
    opt.onSuccess = this.onLoadPermission;
    opt.onFaild = this.onFaild;
    getAllPermission(opt);
    this.getTableData();
  },
  methods: {
    handleBtnClick(methodName) {
      console.log(methodName);
      const functionName = this.btnMethods[methodName];
      if (functionName) {
        return functionName();
      }
    },
    add() {
    this.editDataModel = {};
    this.editDataModel.parentId = this.currentKey;
    this.showEditDialog("新建",this.editDataModel);

    },
    edit() {
      if (this.selection.length <= 0) {
        noticeMsg("请选择要编辑的数据", true);
      } else if (this.selection.length > 1) {
        noticeMsg("请选择一条数据", true);
      } else {
        let opt = {};
        opt.id = this.selection[0].id;
        opt.onSuccess = this.detailOk;
        getPermissionDetail(opt);
      }
    },
    detailOk(data) {
      console.log(data);
      if (data.errorCode === 200) {
        this.showEditDialog("编辑", data.data);
      } else {
        noticeMsg("获取权限详情失败", true);
      }
    },
    delete() {
      noticeMsg("delete", true);
    },
    showEditDialog(title, data) {
      this.showDialog = true;
      this.dialogTitle = title;
      this.editDataModel = data;
    },
    getTableData() {
      this.selection = [];
      let requestOpt = {};
      let params = {};
      requestOpt.params = params;
      params.pageNum = this.currentPage;
      params.pageSize = this.pageSize;
      params.parentId = this.currentKey;
      requestOpt.onSuccess = this.subMenusLoadOk;
      requestOpt.onFaild = this.subMenusLoadFailed;
      getSubMenusByParentId(requestOpt);
    },
    subMenusLoadOk(data) {
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
    subMenusLoadFailed(data) {
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
    onNodeClick(key) {
      if (key.id === this.currentKey) {
        console.log("is opent");
      } else {
        this.currentKey = key.id;
        console.log("根据key 获取child", key);
        this.getTableData();
      }
    },
    tableHeaderStyle({ row, column, rowIndex, columnIndex }) {
      if (rowIndex === 0) {
        return "background-color: #f5f7fa;";
      } else {
        return "";
      }
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

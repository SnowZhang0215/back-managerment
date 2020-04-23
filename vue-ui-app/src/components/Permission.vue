<template>
  <el-container>
    <el-aside style="width:200px">
      <el-tree
        v-if="treeData.length > 0"
        :data="treeData"
        node-key="id"
        :expand-on-click-node="true"
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
        <template v-for="(item,index) in tableHeaderItem">
          <el-table-column :prop="item.code" :label="item.label" :width="item.width" :formatter="item.formatter" :key="index"></el-table-column>
        </template>
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
      <permission-edit :data-model="editDataModel" :onfinish="onEditFinish" :onClose="close"></permission-edit>
    </el-dialog>
  </el-container>
</template>

<script>
import {
  getAllPermission,
  getSubMenusByParentId,
  getPermissionDetail,
  deletePermission,
  getPermissionBtns
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
      pageBtns: [],
      btnMethods: {
        add: this.add,
        edit: this.edit,
        delete: this.delete
      },
      tableHeaderItem: [
        {
          code: "id",
          label: "ID",
          width: 55
        },
        {
          code: "permissionType",
          label: "权限类型",
          formatter: function(row, column, cellValue, index){
              if(cellValue == 1){
                return '菜单'
              }
              if(cellValue == 2){
                return '按钮'
              }
          }
        },
        {
          code: "url",
          label: "菜单跳转路径"
        },
        {
          code: "name",
          label: "权限名称"
        },
        {
          code: "component",
          label: "菜单对应组件"
        },
        {
          code: "btnMethod",
          label: "按钮方法"
        }
      ],
      //eidt
      showDialog: false,
      dialogTitle: "",
      editDataModel: {}
    };
  },
  reloadTable() {},
  created() {
    this.lodadAllPermission();
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
      this.editDataModel.parentId = this.currentKey;
      this.showEditDialog("新建", this.editDataModel);
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
        let dataModel = data.data;

        if (dataModel.permissionHasApi) {
          dataModel.permissionApiIds = [];
          dataModel.permissionHasApi.forEach(element => {
            dataModel.permissionApiIds.push(element.id);
            element.label = element.description;
          });
          dataModel.apiOptions = dataModel.permissionHasApi;
        }
        this.showEditDialog("编辑", dataModel);
      } else {
        noticeMsg("获取权限详情失败", true);
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
        let result = this.getUserBtns();
        console.log(result);
      }
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

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
        <template v-for="(item,index) in tableHeaderItem">
          <el-table-column
            :prop="item.code"
            :label="item.label"
            :width="item.width"
            :formatter="item.formatter"
            :key="index"
          ></el-table-column>
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
      <user-edit :data-model="editDataModel" :onfinish="onEditFinish" :onClose="close"></user-edit>
    </el-dialog>
  </el-container>
</template>

<script>
import { getPermissionBtns } from "../service/menuService";
import { getSelectRole } from "../service/rolemanager.service";
import { listUserPage, getUserDetail ,deleteUser} from "../service/userinfo.service";
import { showMsgBox, noticeMsg } from "../common/common.service";
import UserEdit from "../components/edit/UserEdit";
export default {
  name: "UserManager",
  components: {
    UserEdit
  },
  data() {
    return {
      info: "",
      tableData: [],
      allRoles: [],
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
          code: "userName",
          label: "用户名"
        },
        {
          code: "emile",
          label: "邮箱"
        },
        {
          code: "gender",
          label: "性别",
          formatter: function(row, column, cellValue, index) {
            if (cellValue == 0) {
              return "男";
            }
            if (cellValue == 1) {
              return "女";
            }
          }
        },
        {
          code: "phoneNumber",
          label: "电话号码"
        },
        {
          code: "profile",
          label: "头像"
        }
      ],
      //eidt
      showDialog: false,
      dialogTitle: "",
      editDataModel: {}
    };
  },
  created() {
    this.getTableData();
    this.getUserBtns();
    this.loadAllRole();
  },
  methods: {
    loadAllRole() {
      var opt = {};
      opt.onSuccess = this.onGetAllRole;
      getSelectRole(opt);
    },
    onGetAllRole(data) {
      if (data.errorCode == 200) {
        this.allRoles = data.data;
      } else {
        noticeMsg(data.errorMsg, true);
      }
    },
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
    handleBtnClick(methodName) {
      console.log(methodName);
      const functionName = this.btnMethods[methodName];
      if (functionName) {
        return functionName();
      }
    },
    add() {
    //   this.editDataModel = {};
      var dataModel={};
      let userHasRoleId = [];
      this.allRoles.forEach(element => {
        element.label = element.name;
      });
      if (dataModel.authorities) {
        dataModel.authorities.forEach(element => {
          userHasRoleId.push(element.id);
        });
      }
      dataModel.userHasRoleId = userHasRoleId;
      dataModel.allRoles = this.allRoles;
      this.showEditDialog("新建", dataModel);
    },
    edit() {
      if (this.selection.length <= 0) {
        noticeMsg("请选择要编辑的数据", true);
      } else if (this.selection.length > 1) {
        noticeMsg("请选择一条数据", true);
      } else {
        console.log(this.selection[0]);
        let opt = {};
        opt.id = this.selection[0].id;
        opt.onSuccess = this.detailOk;
        getUserDetail(opt);
      }
    },
    detailOk(data) {
      if (data.errorCode === 200) {
        let dataModel = data.data;
        let userHasRoleId = [];
        this.allRoles.forEach(element => {
          element.label = element.name;
        });
        if (dataModel.authorities) {
          dataModel.authorities.forEach(element => {
            userHasRoleId.push(element.id);
          });
        }
        dataModel.userHasRoleId = userHasRoleId;
        dataModel.allRoles = this.allRoles;
        console.log(dataModel);
        this.showEditDialog("编辑用户", dataModel);
      } else {
        noticeMsg("获取用户详情失败", true);
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
        deleteUser(option);
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
      requestOpt.onSuccess = this.listUserPageOk;
      requestOpt.onFaild = this.listUserPageError;
      requestOpt.params = params;
      listUserPage(requestOpt);
    },
    listUserPageOk(data) {
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
    listUserPageError(data) {
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

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
      <member-org-edit
        v-if="editView == 'memberOrg'"
        :data-model="editDataModel"
        :onfinish="onEditFinish"
        :onClose="close"
      ></member-org-edit>
      <org-member-edit
        v-if="editView == 'memberEdit'"
        :data-model="editDataModel"
        :onfinish="onEditFinish"
        :onClose="close"
      ></org-member-edit>
    </el-dialog>
  </el-container>
</template>

<script>
import { getPermissionBtns } from "../service/menuService";
import { listOrgMemberPage } from "../service/orgmember.service";
import { showMsgBox, noticeMsg } from "../common/common.service";
import OrgMemberEdit from "../components/edit/OrgMemberEdit";
import MemberOrgEdit from "../components/edit/MemberOrgEdit";
export default {
  name: "OrgManager",
  components: {
    OrgMemberEdit,
    MemberOrgEdit
  },
  data() {
    return {
      info: "",
      editView: "memberEdit", //编辑框默认是role编辑
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      totalCount: 0,
      selection: [],
      pageBtns: [],
      btnMethods: {
        add: this.add,
        edit: this.edit,
        delete: this.delete,
        setOrgId: this.setOrgId
      },
      tableHeaderItem: [
        {
          code: "id",
          label: "ID",
          width: 55
        },
        {
          code: "gameId",
          label: "游戏ID"
        },
        {
          code: "gameName",
          label: "游戏名称"
        },
        {
          code: "fightCapacity",
          label: "战斗力(万)"
        },
        {
          code: "orgName",
          label: "组织名称"
        },
        {
          code: "orgRoleType",
          label: "组织身份",
          formatter: function(row, column, cellValue, index) {
            if (cellValue == 0) {
              return "首领";
            }
            if (cellValue == 1) {
              return "高层";
            }
            if (cellValue == 2) {
              return "精英";
            }
            if (cellValue == 3) {
              return "成员";
            }
            if (cellValue == 4) {
              return "学员";
            }
          }
        },
        {
          code: "fightChange",
          label: "近7天战斗力变化(万)"
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
    handleBtnClick(methodName) {
      console.log(methodName);
      const functionName = this.btnMethods[methodName];
      if (functionName) {
        return functionName();
      }
    },
    add() {
      this.editDataModel = {};
      this.editDataModel.orgMasterOpt = [];
      this.showEditDialog("新建成员", "memberEdit", this.editDataModel);
    },
    edit() {
      if (this.selection.length <= 0) {
        noticeMsg("请选择要编辑的数据", true);
      } else if (this.selection.length > 1) {
        noticeMsg("请选择一条数据", true);
      } else {
        console.log(this.selection[0]);
        this.showEditDialog("编辑", "memberEdit", this.selection[0]);
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
    setOrgId() {
      if (this.selection.length == 0) {
        noticeMsg("请选择一条数据", true);
      } else {
        var dataModel = this.selection[0];
        dataModel.orgOpt = [];
        if(dataModel.orgId){
            dataModel.orgOpt.push({
                id:dataModel.orgId,
                orgName:dataModel.orgName
            })
        }
        this.showEditDialog("分配组织", "memberOrg", dataModel);
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
    showEditDialog(title, view, data) {
      this.showDialog = true;
      this.dialogTitle = title;
      this.editDataModel = data;
      this.editView = view;
    },
    getTableData() {
      let requestOpt = {};
      let params = {};
      params.pageNum = this.currentPage;
      params.pageSize = this.pageSize;
      requestOpt.onSuccess = this.listOrgPageOk;
      requestOpt.onFaild = this.listOrgPageError;
      requestOpt.params = params;
      listOrgMemberPage(requestOpt);
    },
    listOrgPageOk(data) {
      console.log(data);
      if (data.errorCode === 200) {
        this.tableData = data.data.content;
        this.pageSize = data.data.pageSize;
        this.currentPage = data.data.pageNum;
        this.totalCount = data.data.totalSize;
        console.log("数据改变了", this.tableData);
        console.log("totalCount", this.totalCount);
      } else {
        noticeMsg(data.errorMsg, true);
      }
    },
    listOrgPageError(data) {
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

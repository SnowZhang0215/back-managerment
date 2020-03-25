<template>
  <el-container>
    <el-aside style="width:250px">
      <el-tree
        v-if="treeData.length > 0"
        :data="treeData"
        node-key="id"
        :highlight-current="true"
        :default-expanded-keys="[0]"
        @node-click="onNodeClick"
      ></el-tree>
    </el-aside>
    <el-main>
      <el-table
        :data="tableData"
        :stripe="true"
        :highlight-current-row="true"
        style="width: 100%"
        height="450"
        :border="true"
        @select="onRowSelect"
        @select-all="onSelectAll"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="id" label="ID" width="180"></el-table-column>
        <el-table-column prop="code" label="编码" width="180"></el-table-column>
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
  </el-container>
</template>

<script>
import { getAllPermission } from "../service/menuService";
import { getSubMenusByParentId } from "../service/menuService";
import { convertTree } from "../service/tree.service";
export default {
  name: "Permission",
  data() {
    return {
      info: "",
      currentKey: "",
      tableData: [],
      treeData: [],
      currentPage: 1,
      pageSize: 10,
      totalCount: 0,
      selection: []
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
    }
  }
};
</script>

<style scoped>
</style>

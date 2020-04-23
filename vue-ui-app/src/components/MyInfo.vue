<template>
  <div class="my-info">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>成员信息</span>
      </div>
      <el-row>
        <el-col :span="10">游戏ID:</el-col>
        <el-col :span="14">{{userInfo.gameId}}</el-col>
      </el-row>
      <el-row>
        <el-col :span="10">游戏名称:</el-col>
        <el-col :span="14">{{userInfo.gameName}}</el-col>
      </el-row>
      <el-row>
        <el-col :span="10">组织:</el-col>
        <el-col :span="14">{{userInfo.orgName}}</el-col>
      </el-row>
      <el-row>
        <el-col :span="10">战斗力:</el-col>
        <el-col :span="14">{{userInfo.fightCapacity}}万</el-col>
      </el-row>
    </el-card>
    <el-card class="box-card-right">
      <div slot="header" class="clearfix">
        <span>战斗力状态</span>
      </div>
      <div>
        <chart :options="options" :auto-resize="true"></chart>
      </div>
    </el-card>
  </div>
</template>

<script>
import { getUserInfo } from "../service/orgmember.service";
import { formatDate } from "../common/common.service";
import ECharts from "vue-echarts/components/ECharts";
// import chart from "echarts/lib/chart/line";
import "echarts/lib/chart/line";

// 提示
import "echarts/lib/component/tooltip";
// 图例
import "echarts/lib/component/legend";
// 标题
import "echarts/lib/component/title";
export default {
  name: "myInfo",
  components: {
    chart: ECharts
  },
  data() {
    return {
      userInfo: {},
      options: {
        tooltip: {
          trigger: "axis"
        },
        legend: {
          data: ["近7天战斗力变化"]
        },
        xAxis: {
          type: "category", // 还有其他的type，可以去官网喵两眼哦
          data: [], // x轴数据
          name: "日期", // x轴名称
          // x轴名称样式
          nameTextStyle: {
            fontWeight: 600,
            fontSize: 18
          },
          axisLabel: {
            interval: 0,
            rotate: 10
          }
        },
        yAxis: {
          type: "value",
          name: "战斗力(万)", // y轴名称
          min: 0,
          max: 0,
          splitNumber: 0.5,
          // y轴名称样式
          nameTextStyle: {
            fontWeight: 600,
            fontSize: 18
          }
        },

        series: [
          {
            name: "近7天战斗力变化",
            data: [],
            type: "line"
          }
        ]
      }
    };
  },
  created() {
    this.loadMyInfo();
  },
  methods: {
    loadMyInfo() {
      var request = {};
      request.onSuccess = this.onLoadMemberInfo;
      getUserInfo(request);
    },
    onLoadMemberInfo(data) {
      if (data.errorCode == 200) {
        this.userInfo = data.data;
        console.log(this.userInfo);
        let xData = [];
        let yDate = [];
        this.userInfo.frightChanges;
        let max = this.userInfo.frightChanges[0].fightVal;
        let min = this.userInfo.frightChanges[0].fightVal;
        this.userInfo.frightChanges.forEach(element => {
          if (max <= element.fightVal) {
            max = element.fightVal;
          }
          if (min >= element.fightVal) {
            min = element.fightVal;
          }
          xData.push(formatDate(element.changeDate));
          yDate.push(element.fightVal);
        });
        // var xData = this.userInfo.
        this.options.xAxis.data = xData;
        console.log(this.options.xAxis.data);
        this.options.yAxis.min = min - 2;
        this.options.yAxis.max = max + 2;
        this.options.series[0].data = yDate;
      }
    }
  }
};
</script>

<style scoped>
.my-info {
  display: flex;
  justify-content: space-around;
}
.box-card {
  width: 50%;
  padding: 10px;
  margin: 10px;
}
.box-card-right {
  width: 100%;
  padding: 10px;
  margin: 10px;
}
</style>

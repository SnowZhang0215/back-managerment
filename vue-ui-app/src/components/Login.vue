<template>
    <el-container>
        <el-row>
            <el-col :xs="0" :sm="6" :md="6" :lg="8" :xl="8">&nbsp;</el-col>
            <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
                <el-card class="login" shadow="hover">
                    <div slot="header" class="clearfix">
                        <span>登录</span>
                    </div>
                    <div>
                        Login
                    </div>
                    <div>
                        <Button>xxx</Button>
                    </div>
                </el-card>
            </el-col>
            <el-col :xs="0" :sm="6" :md="6" :lg="8" :xl="8">&nbsp;</el-col>
        </el-row>
        <el-footer></el-footer>
    </el-container>
</template>
<style>
.login{
    /* width: 40rem; */
    /* margin-left: 33%; */
}
</style>
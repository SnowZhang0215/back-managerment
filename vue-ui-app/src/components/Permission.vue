<template>
    <Layout>
        <Sider hide-trigger :style="{background: '#fff'}" v-if="menuData.length > 0">
            <Menu active-name="1-2" theme="dark" width="auto" :class="menuitemClasses">
                    <MenuItem name="1-1">
                        <Icon type="ios-navigate"></Icon>
                        <span>Option 1</span>
                    </MenuItem>
                    <MenuItem name="1-2">
                        <Icon type="ios-search"></Icon>
                        <span>Option 2</span>
                    </MenuItem>
                    <MenuItem name="1-3">
                        <Icon type="ios-settings"></Icon>
                        <span>Option 3</span>
                    </MenuItem>
                </Menu>
        </Sider>
        <Content :style="{padding: '5px', minHeight: '540px', background: '#fff'}">
            <router-view v-if="menuData.length > 0"/>
            <div>permission content</div>
        </Content>
    </Layout>
</template>

<script>
    export default {
        name: "Permission",
        data(){
            return{
                info:'',
                menuData:[]
            }
        },
        created(){
        console.log("permission page",this.$route.params);
        this.menuData = this.$route.params.menuData;
        if(!this.menuData){
            const rootData = this.$storage.getValue("userMenus");
            const result = []
            this.$commonMethods.getChildFromRootByCode(result,rootData,this.$route.name);
            this.menuData = result;
        }
        console.log(this.menuData);
    }
}
</script>

<style scoped>

</style>

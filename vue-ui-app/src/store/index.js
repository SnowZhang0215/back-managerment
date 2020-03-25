import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
    strict: true,
    state: {
        menuData:[],
        subMenus:[],
        topActiveCode:'',
        asideActiveCode:'',
        userInfo: null
    },
    mutations:{
        menuData(state,data){
            state.menuData = data;
        },
        subMenus(state,data){
            state.subMenus = data;
        },
        topActiveCode(state,code){
            state.topActiveCode = code;
        },
        asideActiveCode(state,code){
            state.asideActiveCode = code
        },
        setUserInfo(state,data){
            state.userInfo = data
        }
    },
    actions:{
        switchMenu(state,data){
            console.log(data);
            state.commit("subMenus",data.subMenu);
            state.commit("topActiveCode",data.topActiveCode);
        },
        changeAsideActive(state,data){
            state.commit("asideActiveCode",data)
        },
        menuData(state,data){
            state.commit("menuData",data)
        },
        asideActiveCode(state,code){
            state.commit("asideActiveCode",code)
        },
        setUserInfo(state,data){
            state.commit('setUserInfo',data)
        }
    },
    getters:{
        subMenu(state){
            return state.subMenus;
        },
        getTopActiveCode(state){
            return state.topActiveCode
        },
        getMenuData(state){
            return state.menuData;
        },
        getUserInfo(state){
            return state.userInfo;
        }
    }
});
export default store;
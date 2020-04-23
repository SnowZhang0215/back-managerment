const api = {

    loginUrl: 'api/auth-server/oauth/token',
    logout: 'api/auth-server/oauth/logout',
    signUpUrl: 'api/user-service/account/register',
    userMenus: 'api/user-service/menu/getUserMenus',
    userInfo: 'api/user-service/account/info',
    userManagerList: 'api/user-service/user/manager/list',//用户管理列表展示
    userManagerDetail: 'api/user-service/user/manager/detail',
    userManagerAdd: 'api/user-service/user/manager/add',
    userManagerEdit: 'api/user-service/user/manager/edit',
    userManagerDelete: 'api/user-service/user/manager/delete',
    userManagerQuery: "api/user-service/user/manager/query",


    menuManageList: 'api/user-service/menu/manage/list',
    menuDetail: 'api/user-service/menu/manage/detail',
    menuManageAdd: 'api/user-service/menu/manage/add',
    menuManageSubMenus: 'api/user-service/menu/manage/subMenu',
    menuManageUpdate: 'api/user-service/menu/manage/update',
    menuManageDelete: 'api/user-service/menu/manage/delete',


    roleManagerList: 'api/user-service/role/manager/list',
    roleGerRoleForUser: 'api/user-service/role/manager/getAllRole',
    roleManagerAdd: 'api/user-service/role/manager/add',
    roleManagerUpdate: 'api/user-service/role/manager/update',
    roleManagerDelete: 'api/user-service/role/manager/delete',
    roleManagerAuthRole: 'api/user-service/role/manager/authrole',
    roleManagerGetRolePermission: 'api/user-service/role/manager/getrolepermission',

    apiManagerQuery: 'api/user-service/apiEntity/query',
    apiManagerList: 'api/user-service/apiEntity/list',
    apiManagerAdd: 'api/user-service/apiEntity/add',
    apiManagerEdit: 'api/user-service/apiEntity/edit',


    orgManagerList: "api/user-service/orgManager/list",
    orgManagerAdd: "api/user-service/orgManager/add",
    orgManagerUpdate: "api/user-service/orgManager/update",
    orgManagerDelete: "api/user-service/orgManager/delete",
    orgManagerQuery:"api/user-service/orgManager/query",
    

    orgMemberList: "api/user-service/orgMember/list",
    orgMemberAdd: "api/user-service/orgMember/add",
    orgMemberUpdate: "api/user-service/orgMember/edit",
    orgMemberDelete: "api/user-service/orgMember/delete",
    orgMemberInfo:"api/user-service/orgMember/member/info"



}
export default api
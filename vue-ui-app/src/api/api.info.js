const api = {
    
    loginUrl:'api/auth-server/oauth/token',
    logout:'api/auth-server/oauth/logout',
    signUpUrl:'api/user-service/account/register',
    userMenus:'api/user-service/menu/getUserMenus',
    userInfo:'api/user-service/account/info',
    userManagerList:'api/user-service/user/manager/list',//用户管理列表展示
    userManagerDetail:'api/user-service/user/manager/detail',


    menuManageList:'api/user-service/menu/manage/list',
    menuDetail:'api/user-service/menu/manage/detail',
    menuManageAdd:'api/user-service/menu/manage/add',
    menuManageSubMenus:'api/user-service/menu/manage/subMenu',
    menuManageUpdate:'api/user-service/menu/manage/update',
    menuManageDelete:'api/user-service/menu/manage/delete',


    roleManagerList:'api/user-service/role/manager/list',
    roleGerRoleForUser:'api/user-service/role/manager/getAllRole',
    roleManagerAdd:'api/user-service/role/manager/add',
    roleManagerUpdate: 'api/user-service/role/manager/update',
    roleManagerDelete:'api/user-service/role/manager/delete',
    roleManagerAuthRole: 'api/user-service/role/manager/authrole',
    roleManagerGetRolePermission: 'api/user-service/role/manager/getrolepermission',

    apiManagerQuery:'api/user-service/apiEntity/query',
    apiManagerList:'api/user-service/apiEntity/list',
    apiManagerAdd:'api/user-service/apiEntity/add',
    apiManagerEdit: 'api/user-service/apiEntity/edit',
    
}
export default api
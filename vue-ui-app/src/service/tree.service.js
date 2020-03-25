export function convertTree(data){
    let topData = {
        id: 0,
        label: '系统菜单',
        children: []
    }
    let result = []
    data.forEach(item => {
        transParent(result,item)
    });
    function transParent(result,data){
        let children = [];
        if(data.children && data.children.length > 0){
            data.children.forEach(e => transParent(children,e))
        }
        result.push({
            id: data.id,
            label: data.name,
            children: children
        })
    }
    topData.children = result;
    let returnData = [];
    returnData.push(topData);
    return returnData;
}
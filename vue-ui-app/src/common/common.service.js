import { MessageBox, Notification } from 'element-ui'
export function noticeMsg(msg, autoClose) {
    let config = {};
    config.title = "提示"
    config.message = msg;
    config.duration = autoClose ? 3000:0
    return Notification(
        config
    )
}
export function showMsgBox(title,msg,onConfirm,onCancel) {
    return MessageBox.confirm(msg, title, {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'confirm'
      }).then(() => {
        onConfirm()
      }).catch(() => {
        if(onCancel){
            onCancel()     
        }  
      });
}
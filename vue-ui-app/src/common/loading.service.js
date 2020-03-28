import { Loading } from 'element-ui'

let needLoadingRequestCount = 0;

export function showLoading() {
    if (needLoadingRequestCount === 0) {
        startLoading()
    }
    needLoadingRequestCount++
}

export function closeLoading() {
    if (needLoadingRequestCount <= 0) {
        return
    }
    needLoadingRequestCount--

    if (needLoadingRequestCount === 0) {
        stopLoading()
    }
}

let loading

function startLoading() {
    loading = Loading.service({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
    })
}

function stopLoading() {
    if(loading){
        loading.close()
    }
}
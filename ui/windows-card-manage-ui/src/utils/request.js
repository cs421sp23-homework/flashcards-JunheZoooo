import axios from "axios"

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// 创建 axios 实例
const service = axios.create({
    baseURL: process.env.VITE_API_URL,
    // 超时
    timeout: 100000
})
// request 拦截器
service.interceptors.request.use(config => {
    // 做鉴权 token
    // 如果需要在header头中传token，使用下边这段代码
    if(localStorage.getItem('token')){
        config.headers['token'] = localStorage.getItem('token');
    }
    // get请求映射params参数
    if (config.method === 'get' && config.params) {
        let url = config.url + '?';
        for (const propName of Object.keys(config.params)) {
            const value = config.params[propName];
            var part = encodeURIComponent(propName) + "=";
            if (value !== null && typeof(value) !== "undefined") {
                if (typeof value === 'object') {
                    for (const key of Object.keys(value)) {
                        let params = propName + '[' + key + ']';
                        var subPart = encodeURIComponent(params) + "=";
                        url += subPart + encodeURIComponent(value[key]) + "&";
                    }
                } else {
                    url += part + encodeURIComponent(value) + "&";
                }
            }
        }
        url = url.slice(0, -1);
        config.params = {};
        config.url = url;
    }
    return config
}, error => {
    return Promise.reject(error)
})
// 响应拦截器
service.interceptors.response.use(res => {
    return res
},error =>{
    return Promise.reject(error)
})

// 暴露这个实例
export default service

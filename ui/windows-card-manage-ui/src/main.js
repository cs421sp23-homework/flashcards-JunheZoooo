import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import Menu3 from 'vue3-menus'
import App from './App.vue'
import router from './router'

import './assets/main.css'
import './assets/global.css'
import './assets/public.css'
import './assets/icon/iconfont.css'

const app = createApp(App)

app.use(ElementPlus)
app.use(router)
app.use(Menu3)

app.config.globalProperties.msgSuccess = function (msg) {
    this.$message({message: msg, type: "success"})
}

app.config.globalProperties.msgError = function (msg) {
    this.$message({message: msg, type: "error"})
}

app.config.globalProperties.refFocus = function (val) {
    this.$nextTick(() => {
        val.focus()
    })
}

app.mount('#app')

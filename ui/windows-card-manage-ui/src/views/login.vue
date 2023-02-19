<template>
  <!-- 这里是登录界面 -- login -->
  <!-- 最外层 -- box -->
  <div :style="{backgroundImage: 'url('+ bgImageUrl +')'}" class="v-h-100">
    <div class="row h-100">
      <div class="col-c-lgg-12 v-flex v-flex-aic h-100">
        <div class="col-c-lgg-8 v-flex v-flex-aic v-flex-jus-cent m-auto">
          <!-- 登录输入框 box -- login input box -->
          <div class="col-c-lgg-4 m-auto bg-black-transparent border-radius-10">
            <div class="p-t-20 p-b-20 p-l-20 p-r-20">
              <!-- 提示部分 -- tips -->
              <div class="text-c p-b-10 color-white">
                <h1>Welcome Login</h1>
              </div>
              <!-- 表单部分 -- form -->
              <el-form status-icon ref='loginForm'>
                <el-form-item prop='phone'>
                  <el-input maxlength='14' autocomplete='off' v-model="form.account" ref='account' placeholder='please input account' clearable></el-input>
                </el-form-item>
                <el-form-item prop='password'>
                  <el-input type='password' autocomplete='off' v-model="form.password" ref='password' placeholder='please input password'></el-input>
                </el-form-item>
              </el-form>
              <!-- 前往部分 -- go router -->
              <div class="v-flex v-flex-aic v-flex-jus-between go-link p-b-10">
                <el-link @click="routerPath('/register')" class="color-active-default">click to register</el-link>
                <el-link @click="routerPath('/forget')" class="color-active-default">forgot password</el-link>
              </div>
              <!-- 按钮部分 -- login button -->
              <div>
                <el-button :loading="loading" type="primary" class="w-100" @click="btnLogin">login</el-button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import bgOne from '/src/assets/img/bg-one.jpg'
import {useRoute,useRouter} from 'vue-router'
import {login} from '@/api/user'
import store from '@/store/store'
export default {
  name: "login",
  setup() {
    const route = useRoute()
    const router = useRouter()
    function routerPath(to,query) {
      if (query) {
        router.push({
          path: to,
          query: query
        })
      } else {
        router.push({
          path: to
        })
      }
    }
    return {
      route,routerPath
    }
  },
  data() {
    return {
      bgImageUrl: bgOne,
      loading: false,
      form: {
        account: "",
        password: ""
      }
    }
  },
  created() {
    // 获取 account 账号数据 -- get router account
    if (this.route.query.account) {
      this.form.account = this.route.query.account
    }
  },
  methods: {
    btnLogin() {
      if (this.form.account === "") {
        this.msgError("please input account !")
        this.refFocus(this.$refs.account)
      } else {
        if (this.form.password === "") {
          this.msgError("please input password !")
          this.refFocus(this.$refs.password)
        } else {
          // login
          login(this.form).then(res => {
            if (res.data.code === 200) {
              this.msgSuccess(res.data.message)
              store.setToken(res.data.data.token)
              setTimeout(() => {
                this.routerPath("/")
              },2000)
            } else {
              this.msgError(res.data.message)
            }
          })
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
<template>
  <!-- 用户注册界面  -- register-->
  <!-- 最外层 -- box -->
  <div :style="{backgroundImage: 'url('+ bgImageUrl +')'}" class="v-h-100">
    <div class="row h-100">
      <div class="col-c-lgg-12 v-flex v-flex-aic h-100">
        <div class="col-c-lgg-8 v-flex v-flex-aic v-flex-jus-cent m-auto">
          <!-- 注册输入框 box -- register box -->
          <div class="col-c-lgg-4 m-auto bg-black-transparent border-radius-10">
            <div class="p-t-20 p-b-20 p-l-20 p-r-20">
              <!-- 提示部分 -- tips -->
              <div class="text-c p-b-10 color-white">
                <h1>Welcome Register</h1>
              </div>
              <!-- 表单部分 -- form -->
              <el-form status-icon ref='loginForm'>
                <el-form-item prop='account'>
                  <el-input maxlength='14' autocomplete='off' v-model="form.account" ref='account' placeholder='please input register account' clearable></el-input>
                </el-form-item>
                <el-form-item prop='password'>
                  <el-input type='password' autocomplete='off' v-model="form.password" ref='password' placeholder='please input password'></el-input>
                </el-form-item>
                <el-form-item prop='checkPass'>
                  <el-input type='password' autocomplete='off' v-model="form.checkPass" ref='checkPass' placeholder='please input confirm password'></el-input>
                </el-form-item>
              </el-form>
              <!-- 前往部分 -- go router -->
              <div class="v-flex v-flex-aic v-flex-jus-between go-link p-b-10">
                <el-link @click="routerPath('/login')" class="color-active-default">click to login</el-link>
                <el-link @click="routerPath('/forget')" class="color-active-default">forgot password</el-link>
              </div>
              <!-- 按钮部分 -- button -->
              <div>
                <el-button :loading="loading" type="primary" class="w-100" @click="btnRegister">register</el-button>
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
import {register} from '@/api/user'
import {useRouter} from 'vue-router'
export default {
  name: "register",
  setup() {
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
      routerPath
    }
  },
  data() {
    return {
      bgImageUrl: bgOne,
      form: {
        account: "",
        password: "",
        checkPass: ""
      },
      loading: false
    }
  },
  methods: {
    btnRegister() {
      if (this.form.account === "") {
        this.msgError("please input register account !")
        this.refFocus(this.$refs.account)
      } else {
        if (this.form.password === "") {
          this.msgError("please input password !")
          this.refFocus(this.$refs.password)
        } else {
          if (this.form.checkPass === "") {
            this.msgError("please input confirm password !")
            this.refFocus(this.$refs.checkPass)
          } else {
            if (this.form.checkPass !== this.form.password) {
              this.msgError("the password and confirmation password are different !")
              this.refFocus(this.$refs.checkPass)
            } else {
              // create register
              register(this.form).then(res => {
                if (res.data.code === 200) {
                  this.msgSuccess(res.data.message)
                  setTimeout(() => {
                    this.routerPath("/login",{account: this.form.account})
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
  }
}
</script>

<style scoped>

</style>
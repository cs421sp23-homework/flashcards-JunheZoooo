<template>
  <!-- 桌面首页 -->
  <!-- 最外层 -->
  <div style="text-align: center;" :style="{backgroundImage: 'url('+ bgImageUrl +')'}" class="v-h-100 v-flex v-flex-flow">
    <!-- 头部标签 -->
    <div class="nav-header v-flex" style="height: 7vh">
      <div class="v-flex-1 v-flex v-flex-aic p-l-40">
        <div class="cursor-p">
          <el-popover placement="right-end" :width="50" trigger="click">
            <template #reference>
              <i class="iconfont icon-tianjia color-white font-size-20-plus"></i>
            </template>
            <div class="v-flex v-flex-aic v-flex-jus-cent v-flex-flow">
              <div class="p-b-10">
                <el-button type="primary" @click="createFolderBtn">create DESKS</el-button>
              </div>
              <div>
                <el-button @click="createFileBtn">create card</el-button>
              </div>
            </div>
          </el-popover>
        </div>
      </div>
      <div v-if="isLogin==='NOT'" class="font-size-20 text-r p-t-10 p-b-10 p-l-30 p-r-30 v-flex-1">
        <el-link href="/register" type="primary" class="m-r-10 color-white">register</el-link>
        <el-link href="/login" type="primary" class="m-l-10 color-white">login</el-link>
      </div>
      <div v-else class="font-size-20 text-r p-t-10 p-b-10 p-l-30 p-r-30 v-flex-1">
        <el-link @click="btnExit" type="primary" class="m-r-10 color-white">quit</el-link>
      </div>
    </div>
    <div class="nav-bottom-content p-l-20 p-r-20" style="height: 93vh">
      <div class="h-100">
        <div v-for="(item,i) in list" :key="i">
          <!-- 文件夹 -->
          <div v-if="item.cardType === 1" class="float-left m-r-10 cursor-d windows-icon" @contextmenu.prevent="clickRight(item,$event)">
            <!-- 文件夹图标 -->
            <div>
              <i class="iconfont icon-kapian1 folder-color font-size-40-plus"></i>
            </div>
            <!-- 文件夹名称 -->
            <div class="font-size-12">
              <span class="color-white">{{item.name}}</span>
            </div>
          </div>
          <!-- 普通文件 -->
          <div v-if="item.cardType === 2" class="float-left m-l-10 cursor-d windows-icon" @contextmenu="fileClickRight(item,$event)">
            <!-- 文件夹图标 -->
            <div>
              <i class="iconfont icon-kapian color-white font-size-40-plus"></i>
            </div>
            <!-- 文件夹名称 -->
            <div class="font-size-12">
              <span class="color-white">{{item.name}}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 文件夹上右键的弹框内容 -->
    <vue3-menus :open="isOpen" :event="eventVal" :menus="menus.menus">
      <template #label="{ menu }">{{ menu.label }}</template>
    </vue3-menus>
    <!-- 文件上右键的弹框内容 -->
    <vue3-menus :open="isFileOpen" :event="fileEventVal" :menus="fileMenus.menus">
      <template #label="{ menu }">{{ menu.label }}</template>
    </vue3-menus>
    <!-- 在 card pakage dialog 中文件上右键的弹框内容 -->
    <vue3-menus :open="isDialogFileOpen" :event="dialogFileEventVal" :menus="dialogFileMenus.menus" :z-index="9999">
      <template #label="{ menu }">{{ menu.label }}</template>
    </vue3-menus>
    <!-- 创建卡包弹框 -->
    <el-dialog v-model="openCreateFolder" width="30%" title="create card DESKS" :before-close="closeCreateFolder">
      <el-form>
        <el-form-item label="DESKS name：">
          <el-input v-model="packageForm.name" ref='packageName' placeholder="please input DESKS name" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="closeCreateFolder">cancel</el-button>
          <el-button type="primary" @click="createCardPackageBtn">yes</el-button>
        </span>
      </template>
    </el-dialog>
    <!-- 打开卡包弹框 -->
    <el-dialog v-model="openFolder" width="60%" title="open card package">
      <div style="min-height: 100px;">
        <div v-for="(item,i) in packageCardList" :key="i">
          <!-- 普通文件 -->
          <div v-if="item.cardType === 2" class="float-left m-l-10 cursor-d windows-icon-dialog" @contextmenu="fileDialogClickRight(item,$event)">
            <!-- 文件夹图标 -->
            <div>
              <i class="iconfont icon-24gl-fileText color-white font-size-35-plus"></i>
            </div>
            <!-- 文件夹名称 -->
            <div class="font-size-12">
              <span class="color-white">{{item.name}}</span>
            </div>
          </div>
        </div>
      </div>
    </el-dialog>
    <!-- 创建卡片弹框 -->
    <el-dialog v-model="openCreateFile" width="30%" title="create card" :before-close="closeCreateFile">
      <el-form>
        <el-form-item label="card name：">
          <el-input v-model="cardForm.name" ref='cardName' placeholder="please input card name" />
        </el-form-item>
        <el-form-item label="card content：">
          <el-input v-model="cardForm.content" ref='cardContent' type="textarea" :rows="10" placeholder="please input card content" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="closeCreateFile">cancel</el-button>
          <el-button type="primary" @click="createCardBtn">yes</el-button>
        </span>
      </template>
    </el-dialog>
    <!-- 重看卡片弹框 -->
    <el-dialog v-model="openSelectFile" width="30%" title="see card">
      <el-form>
        <el-form-item label="card name：">
          <div class="font-size-20 font-size-bold">
            <span>{{selectCardForm.name}}</span>
          </div>
        </el-form-item>
        <el-form-item label="card content：">
          <div class="font-size-15">
            <span>{{selectCardForm.content}}</span>
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="openSelectFile=false">close</el-button>
        </span>
      </template>
    </el-dialog>
    <!-- 移动卡片弹框 -->
    <el-dialog v-model="openMoveFile" width="30%" title="move card" :before-close="closeMoveFile">
      <el-form>
        <el-form-item label="card package">
          <el-select v-model="cardMoveForm.cardOfId" ref="cardMove" placeholder="please select card package">
            <el-option v-for="(item, index) in selectPackageOption" :key="index" :label="item.name" :value="item.id"/>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="closeMoveFile">cancel</el-button>
          <el-button type="primary" @click="moveCardBtn">yes</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import bgTwo from "@/assets/img/bg-two.jpg";
import {nextTick} from "vue";
import {cardList, packageList, cardSave, delCard, getCard,packageCardList} from "@/api/HomeIndex"
import {getUserData} from "@/api/user"
import store from '@/store/store'
import {useRouter} from 'vue-router'
export default {
  name: "HomeIndex",
  setup() {
    // router function
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
      bgImageUrl: bgTwo,
      // 文件开关
      isFileOpen: false,
      // file event
      fileEventVal: {},
      // file data
      fileDataVal: {},
      // file menus
      fileMenus: {
        menus: [
          {
            label: "update",
            click: () => {
              // get card data
              let body = {
                id: this.fileDataVal.id
              }
              getCard(body).then(res => {
                if (res.data.code === 200) {
                  this.cardForm = res.data.data.card
                  this.openCreateFile = true
                }
              })
            }
          },
          {
            label: "delete",
            style: {
              color: "red"
            },
            click: () => {
              let body = {
                id: this.fileDataVal.id
              }
              delCard(body).then(res => {
                if (res.data.code === 200) {
                  this.msgSuccess(res.data.message)
                  this.getCardList()
                } else {
                  this.msgError(res.data.message)
                }
              })
            }
          },
          {
            label: "see",
            click: () => {
              // get card data
              let body = {
                id: this.fileDataVal.id
              }
              getCard(body).then(res => {
                if (res.data.code === 200) {
                  this.selectCardForm = res.data.data.card
                  this.openSelectFile = true
                }
              })
            }
          },
          {
            label: "move",
            click: () => {
              // get card data
              let body = {
                id: this.fileDataVal.id
              }
              getCard(body).then(res => {
                if (res.data.code === 200) {
                  this.cardMoveForm = res.data.data.card
                  this.cardMoveForm.cardOfIs = 1
                  this.cardMoveForm.cardOfId = ""
                  this.openMoveFile = true
                  this.getCardPackageList()
                }
              })
            }
          },
        ]
      },
      // 文件 dialog 开关
      isDialogFileOpen: false,
      // file event
      dialogFileEventVal: {},
      // file data
      dialogFileDataVal: {},
      // file menus
      dialogFileMenus: {
        menus: [
          {
            label: "update",
            click: () => {
              // get card data
              let body = {
                id: this.dialogFileDataVal.id
              }
              getCard(body).then(res => {
                if (res.data.code === 200) {
                  this.cardForm = res.data.data.card
                  this.openCreateFile = true
                }
              })
            }
          },
          {
            label: "delete",
            style: {
              color: "red"
            },
            click: () => {
              let body = {
                id: this.dialogFileDataVal.id
              }
              delCard(body).then(res => {
                if (res.data.code === 200) {
                  this.msgSuccess(res.data.message)
                  this.getPackageCardList(this.eventData.id)
                } else {
                  this.msgError(res.data.message)
                }
              })
            }
          },
          {
            label: "see",
            click: () => {
              // get card data
              let body = {
                id: this.dialogFileDataVal.id
              }
              getCard(body).then(res => {
                if (res.data.code === 200) {
                  this.selectCardForm = res.data.data.card
                  this.openSelectFile = true
                }
              })
            }
          }
        ]
      },
      // 文件夹相关
      isOpen: false,
      // package event
      eventVal: {},
      // package event data
      eventData: {},
      // package menus
      menus: {
        menus: [
          {
            label: "rename",
            click: () => {
              // get card package data
              let body = {
                id: this.eventData.id
              }
              getCard(body).then(res => {
                if (res.data.code === 200) {
                  this.packageForm = res.data.data.card
                  this.openCreateFolder = true
                }
              })
            }
          },
          {
            label: "open",
            click: () => {
              this.getPackageCardList(this.eventData.id)
              this.openFolder = true
            }
          },
          {
            label: "delete",
            style: {
              color: "red"
            },
            click: () => {
              let body = {
                id: this.eventData.id
              }
              delCard(body).then(res => {
                if (res.data.code === 200) {
                  this.msgSuccess(res.data.message)
                  this.getCardList()
                } else {
                  this.msgError(res.data.message)
                }
              })
            }
          }
        ]
      },
      // 卡包弹框开关
      openCreateFolder: false,
      // 卡片弹框开关
      openCreateFile: false,
      // 查看卡片弹框开关
      openSelectFile: false,
      // is login
      isLogin: "NOT",
      // list
      list: [],
      // card package form
      packageForm: {
        cardType: 1,
        cardOfIs: 0,
        name: ""
      },
      // card form
      cardForm: {
        cardType: 2,
        cardOfIs: 0,
        name: "",
        content: ""
      },
      // card move form
      cardMoveForm: {},
      // select card form
      selectCardForm: {},
      // select package option
      selectPackageOption: [],
      // open Move card switch default close
      openMoveFile: false,
      // card list in card package
      packageCardList: [],
      // open card package switch
      openFolder: false
    }
  },
  created() {
    this.getCardList()
    this.getUserData()
  },
  methods: {
    // package card list
    getPackageCardList(id) {
      let body = {
        packageId: id
      }
      packageCardList(body).then(res => {
        if (res.data.code === 200) {
          this.packageCardList = res.data.data.packageCardList
        }
      })
    },
    // move card button
    moveCardBtn() {
      if (this.cardMoveForm.cardOfId === "") {
        this.msgError("please select card package")
        this.refFocus(this.$refs.cardMove)
      } else {
        cardSave(this.cardMoveForm).then(res => {
          if (res.data.code === 200) {
            this.msgSuccess("move success")
            this.openMoveFile = false
            this.cardMoveForm.cardOfId = ""
            this.getCardList()
          } else {
            this.msgError("move error")
          }
        })
      }
    },
    // close move card dialog
    closeMoveFile() {
      this.cardMoveForm.id = ""
      this.cardMoveForm.cardOfId = ""
      this.openMoveFile = false
    },
    // create card
    createCardBtn() {
      if (this.cardForm.name === "") {
        this.msgError("please input card name")
        this.refFocus(this.$refs.cardName)
      } else {
        if (this.cardForm.content === "") {
          this.msgError("please input card content")
          this.refFocus(this.$refs.cardContent)
        } else {
          cardSave(this.cardForm).then(res => {
            if (res.data.code === 200) {
              this.msgSuccess(res.data.message)
              this.openCreateFile = false
              this.cardForm.name = ""
              this.cardForm.content = ""
              this.getCardList()
              if (this.eventData.id) {
                this.getPackageCardList(this.eventData.id)
              }
            } else {
              this.msgError(res.data.message)
            }
          })
        }
      }
    },
    closeCreateFile() {
      this.cardForm.id = ""
      this.cardForm.name = ""
      this.cardForm.content = ""
      this.openCreateFile = false
    },
    // close card package
    closeCreateFolder() {
      this.packageForm.id = ""
      this.packageForm.name = ""
      this.openCreateFolder = false
    },
    // create card package
    createCardPackageBtn() {
      if (this.packageForm.name === "") {
        this.msgError("please input card package name")
        this.refFocus(this.$refs.packageName)
      } else {
        cardSave(this.packageForm).then(res => {
          if (res.data.code === 200) {
            this.msgSuccess(res.data.message)
            this.openCreateFolder = false
            this.packageForm.name = ""
            this.getCardList()
          } else {
            this.msgError(res.data.message)
          }
        })
      }
    },
    // card list
    getCardList() {
      cardList().then(res => {
        if (res.data.code === 200) {
          this.list = res.data.data.list
        }
      })
    },
    // card package list
    getCardPackageList() {
      packageList().then(res => {
        if (res.data.code === 200) {
          this.selectPackageOption = res.data.data.packageList
        }
      })
    },
    // 文件右键
    fileClickRight(row,event) {
      this.isFileOpen = false
      nextTick(() => {
        this.fileEventVal = event
        this.fileDataVal = row
        this.isFileOpen = true
      })
      event.preventDefault()
    },
    // dialog 文件右键
    fileDialogClickRight(row,event) {
      this.isDialogFileOpen = false
      nextTick(() => {
        this.dialogFileEventVal = event
        this.dialogFileDataVal = row
        this.isDialogFileOpen = true
      })
      event.preventDefault()
    },
    // 文件卡包右键
    clickRight(row,event) {
      this.isOpen = false
      nextTick(() => {
        this.eventVal = event
        this.eventData = row
        this.isOpen = true
      })
      event.preventDefault()
    },
    // 创建卡包按钮
    createFolderBtn() {
      this.openCreateFolder = true
    },
    // 创建卡片按钮
    createFileBtn() {
      this.openCreateFile = true
    },
    // exit btn
    btnExit() {
      store.delToken()
      this.msgSuccess("exiting...")
      setTimeout(() => {
        this.routerPath("/login")
      },2000)
    },
    getUserData() {
      getUserData().then(res => {
        if (res.data.code === 200) {
          this.isLogin = "LOGIN"
        } else {
          this.isLogin = "NOT"
          this.msgError("Login expired, Processing...")
          setTimeout(() => {
            this.routerPath("/login")
          },2000)
        }
      })
    }
  }
}
</script>

<style scoped>
.dialog-footer button:first-child {
  margin-right: 10px;
}
</style>

<template>
  <div class="m-content">
    <img class="hlogo" src="../assets/help2.png">
    <el-divider><i class="el-icon-magic-stick"></i></el-divider>
    <div class="block">
        <el-avatar :size="80" :src=user.avatar ></el-avatar>
        <div>{{ user.username }}</div>
    </div>
    <div class="maction">
    <span><el-link href="/blogs">主页</el-link></span>
    <span v-show="hasLogin">
      <el-divider direction="vertical"></el-divider>
      <el-link type="primary" href="/blog/add">发布博客</el-link>
    </span>
    <el-divider direction="vertical"></el-divider>
    <span v-show="!hasLogin"><el-link type="success" href="/login">登录</el-link></span>
    <span v-show="hasLogin"><el-link type="danger" @click="logout">退出</el-link></span>
    </div>
  </div>
</template>

<script>
export default {
    name: "Header",
    data(){
        return{
            user: {
                username: '您还没有登录哦',
                avatar: require('../assets/face.png')
            },
            hasLogin: false
        }
    },
    methods:{
       logout() {
        const _this = this
        _this.$axios.get("/logout", {
          headers: {
            "Authorization": localStorage.getItem("token")
          }
        }).then(res => {
          _this.$store.commit("REMOVE_INFO")
          _this.$router.push("/login")
        })
      }
    },
    created(){
        if(this.$store.getters.getUser.username) {
            this.user.username = this.$store.getters.getUser.username
            this.user.avatar = this.$store.getters.getUser.avatar 
            this.hasLogin = true
      }
    }
}
</script>

<style scoped>
    .m-content{
        text-align: center;
        margin: 0 auto;
    }
    .maction{
        margin: 10px 0;
    }

</style>
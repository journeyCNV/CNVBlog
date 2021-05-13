<template>
  <div class="login_con">
      <el-container>
        <el-header>
            <img class="mlogo" src="../assets/help2.png">
        </el-header>
        <el-divider><i class="el-icon-lock"></i>
        <el-link type="primary" href="/blogs">去看看作者主页</el-link>
        </el-divider>
        <el-main>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
  <el-form-item label="用户名" prop="username">
    <el-input v-model="ruleForm.username"></el-input>
  </el-form-item>
   <el-form-item label="密码" prop="password">
    <el-input type="password" v-model="ruleForm.password"></el-input>
  </el-form-item>
  
  <el-form-item>
    <el-button type="primary" @click="submitForm('ruleForm')">立即登录</el-button>
    <el-button @click="resetForm('ruleForm')">重置</el-button>
  </el-form-item>
</el-form> </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
    name: "Login",
    data() {
      return {
        ruleForm: {
          username: 'cnCodeHub',
          password: '111111',
        },
        rules: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'change' }
          ]
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            const _this = this
            //alert('submit!');
            this.$axios.post('/login',this.ruleForm).then(res =>{
                console.log(res.headers)
                console.log(res.data)
                const jwt = res.headers['authorization']
                const userInfo = res.data.data
                // 把数据共享出去
              _this.$store.commit("SET_TOKEN", jwt)
              _this.$store.commit("SET_USERINFO", userInfo)
              // 获取
              console.log(_this.$store.getters.getUser)
              _this.$router.push("/blogs")
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  
}
</script>

<style>
.el-header, .el-footer {
    background-color: #ffffff;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
  
  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
  }
  
  .el-main {
    /*background-color: #E9EEF3;*/
    color: #333;
    text-align: center;
    line-height: 160px;
  }
  
  body > .el-container {
    margin-bottom: 40px;
  }
  
  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }
  
  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }
  .login_con{
        text-align: center;
        margin: 0 auto;
  }

  .mlogo{
      height:100%;
      margin-top: 0%;
      /**margin-right: 88%;*/
  }

  .demo-ruleForm{
      text-align: center;
      margin: 0 auto;
      max-width: 50%;
     /**  margin: 0 auto;*/
  }

</style>
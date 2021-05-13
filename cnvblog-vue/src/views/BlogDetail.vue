<template>
  <div>
    <Header></Header>
      <h2> 
        <i class="el-icon-reading"></i>
        {{ blog.title }}
      </h2>

      <el-divider>
      <h5>
        <i class="el-icon-time"></i>
        {{blog.created}}
        <el-link v-if="ownBlog" icon="el-icon-edit"> <!-- 编辑按钮只有是自己的博客才会显示 -->
        <router-link :to="{name: 'BlogEdit', params: {blogId: blog.id}}" >
        编辑文章
        </router-link>
        </el-link>
        
        <el-link v-if="ownBlog" type="danger" @click="deleF(blog.id)" icon="el-icon-delete">删除文章</el-link>

      </h5>
      </el-divider>

    <div class="mblog">

      <div class="markdown-body" v-html="blog.content"></div>

    </div>

  </div>
</template>

<script>
import Header from "../components/Header";
export default {
    name: "BlogDetail",
    components: {Header},
    data() {
      return {
        blog: {
          id: "",
          title: "",
          content: "",
          created: ""
        },
        ownBlog: false
      }
    },
    methods:{
       deleF(id) {
        
           //弹窗提示
           this.$confirm('确定要删除本篇文章？','提示',{
             confirmButtonText: '确定',
             cancelButtonText: '取消',
             type: 'waring'
           }).then(()=>{
             //删除指定文章
             this.$axios.get("/blog/delete/"+id,{
               headers: {
                     "Authorization": localStorage.getItem("token")
                }
             }).then(
               res=>{
                 if(res.data.code===200){
                   this.$message({type:'success',message:'删除成功'});
                   this.$router.push("/Blogs") //跳转到主页
                 }else{
                   this.$message({type:'error',message:'删除失败'});
                 }
               })
           }).catch(()=>{
             this.$message({
               type: 'info',
               message: '已经取消删除'
             })
           })
         
       }
        
    },
    created() {
      const blogId = this.$route.params.blogId
      console.log(blogId)
      const _this = this
      this.$axios.get('/blog/' + blogId).then(res => {
        const blog = res.data.data
        _this.blog.id = blog.id
        _this.blog.created = blog.created
        _this.blog.title = blog.title
        //渲染
        var MardownIt = require("markdown-it")
        var md = new MardownIt()
        var result = md.render(blog.content)
        _this.blog.content = result
        _this.ownBlog = (blog.userId === _this.$store.getters.getUser.id) //只有是当前用户才能看到编辑按钮
      })
    }
}
</script>

<style scope>
  .mblog{
    box-shadow: 0 2px 12px 0 rgba(7, 55, 145, 0.171);
    width: 100%;
    min-height: 720px;
    padding: 20px 26px;
  }
</style>
import axios from 'axios'
import ElementUI from 'element-ui'
import router from './router'
import store from './store'

/**前置拦截和后置拦截 */

axios.defaults.baseURL="http://localhost:8086" /**自动作为URL前缀 */
//前置拦截
axios.interceptors.request.use(config =>{
    return config
})

//后置拦截
axios.interceptors.response.use(response =>{
    let res = response.data;
    console.log("========")
    console.log(res)
    console.log(res.msg)
    console.log("========")

    if(res.code === 200){
        /**
        ElementUI.Message({
            showClose: true,
            message: res.msg,
            type: 'success'
          });*/
        return response
    }else{
        ElementUI.Message({
            showClose: true,
            message: res.msg,
            type: 'error'
          });
        //ElementUI.Message.error('错了哦,请重新输入',{duration: 3* 1000});
        return Promise.reject(response.data.msg) //这样就不会进入后面的逻辑
    }
},
    error => {
        console.log(error)


        if(error.response.status === 401){
            store.commit("REMOVE_INFO")
            router.push("/login")
        }
       
        return Promise.reject(error) //这样就不会进入后面的逻辑
        
    }

)
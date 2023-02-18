import request from "@/utils/request"

// user register
export function register(data) {
    return request({
        url: '/user/userRegister',
        method: 'post',
        data: data
    })
}

// user login
export function login(data) {
    return request({
        url: '/user/userLogin',
        method: 'post',
        data: data
    })
}

// get user data
export function getUserData() {
    return request({
        url: '/user/getUserData',
        method: 'post'
    })
}

// forget password
export function forget(data) {
    return request({
        url: '/user/forgetPassword',
        method: 'post',
        data: data
    })
}
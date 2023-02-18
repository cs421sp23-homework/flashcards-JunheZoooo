export default {
    setToken,
    delToken
}

function setToken(token) {
    window.localStorage.setItem('token', token) // 存储 token
}

function delToken() {
    window.localStorage.removeItem('token') // 删除 token
}

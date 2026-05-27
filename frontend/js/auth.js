/**
 * 认证工具 — token/用户信息管理、页面鉴权
 */

const Auth = {
    /** 获取存储的 token */
    getToken() {
        return localStorage.getItem('token');
    },

    /** 保存登录信息 */
    saveLogin(token, userId, username) {
        localStorage.setItem('token', token);
        localStorage.setItem('userId', userId);
        localStorage.setItem('username', username);
    },

    /** 获取当前用户 ID */
    getUserId() {
        return localStorage.getItem('userId');
    },

    /** 获取当前用户名 */
    getUsername() {
        return localStorage.getItem('username');
    },

    /** 是否已登录 */
    isLoggedIn() {
        return !!this.getToken();
    },

    /** 登出 */
    logout() {
        localStorage.removeItem('token');
        localStorage.removeItem('userId');
        localStorage.removeItem('username');
        window.location.href = '/login.html';
    },

    /** 检查登录状态，未登录则跳转 */
    requireLogin() {
        if (!this.isLoggedIn()) {
            window.location.href = '/login.html?redirect=' + encodeURIComponent(location.pathname);
        }
    },

    /** 更新导航栏：显示登录状态/用户名 */
    renderNavbar() {
        const navUser = document.getElementById('nav-user');
        if (!navUser) return;
        if (this.isLoggedIn()) {
            navUser.innerHTML = `
                <span class="nav-user">👤 ${this.getUsername()}</span>
                <span class="btn-logout" onclick="Auth.logout()">退出</span>`;
        } else {
            navUser.innerHTML = '<a href="/login.html">登录</a>';
        }
    }
};

// 页面加载时自动渲染导航栏
document.addEventListener('DOMContentLoaded', () => {
    Auth.renderNavbar();
});

/**
 * API 请求封装 — 所有请求通过 Gateway (8080) 转发
 */
var API_BASE = '';

var api = {
    /**
     * method: GET / POST / PUT / DELETE
     * url:    "/api/xxx"
     * body:   JSON body，仅 POST/PUT 时有效
     * params: 拼接到 URL 后的查询参数 {key:val}
     */
    request: async function(method, url, body, params) {
        var config = { method: method, headers: {} };

        var token = localStorage.getItem('token');
        if (token) config.headers['Authorization'] = 'Bearer ' + token;

        // 查询参数
        if (params) {
            var qs = new URLSearchParams(params).toString();
            url = url + (url.indexOf('?') >= 0 ? '&' : '?') + qs;
        }

        // JSON body
        if (body && (method === 'POST' || method === 'PUT')) {
            config.headers['Content-Type'] = 'application/json';
            config.body = JSON.stringify(body);
        }

        try {
            var res = await fetch(API_BASE + url, config);
            var json = await res.json();
            return json;
        } catch (err) {
            return { code: -1, message: '网络错误：' + err.message, data: null };
        }
    },

    get:    function(url, params)          { return this.request('GET', url, null, params); },
    post:   function(url, body, params)    { return this.request('POST', url, body, params); },
    put:    function(url, body, params)    { return this.request('PUT', url, body, params); },
    delete: function(url, params)          { return this.request('DELETE', url, null, params); },

    /* ---- 用户 ---- */
    register: function(user)               { return this.post('/api/user/register', user); },
    login:    function(username, password)  { return this.post('/api/user/login', null, {username:username, password:password}); },
    getUser:  function(id)                 { return this.get('/api/user/' + id); },

    /* ---- 图书 ---- */
    getBooks:     function()          { return this.get('/api/book'); },
    getBookById:  function(id)        { return this.get('/api/book/' + id); },
    searchBooks:  function(keyword)   { return this.get('/api/book/search', {keyword:keyword}); },
    addBook:      function(book)      { return this.post('/api/book', book); },
    updateBook:   function(book)      { return this.put('/api/book', book); },
    deleteBook:   function(id)        { return this.delete('/api/book/' + id); },
    getBookConfig: function()         { return this.get('/api/book/config'); },

    /* ---- 借阅 ---- */
    borrowBook:       function(userId, bookId) { return this.post('/api/borrow', null, {userId:userId, bookId:bookId}); },
    returnBook:       function(borrowId)       { return this.put('/api/borrow/' + borrowId + '/return'); },
    getBorrowsByUser: function(userId)         { return this.get('/api/borrow/user/' + userId); },
    getAllBorrows:    function()               { return this.get('/api/borrow'); },
};

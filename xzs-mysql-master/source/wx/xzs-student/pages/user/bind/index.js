const app = getApp()
Page({
  data: {
    spinShow: false,
    userName: '',
    password: '',
  },
  formSubmit: function(e) {
       let form = e.detail.value
      console.log(form.userName),
      console.log(form.password)
      if (form.userName == null || form.userName == '') {
        app.message('用户名不能为空', 'error');
        return;
      }
      if (form.password == null || form.password == '') {
        app.message('密码不能为空', 'error');
        return;
      }
  
    let _this = this
    _this.setData({
      spinShow: true
    });
    wx.login({
      success(wxres) {
        if (wxres.code) {
          e.detail.value.code = wxres.code
          app.formPost('/api/wx/student/auth/bind', e.detail.value)
            .then(res => {
              _this.setData({
                spinShow: false
              });
              if (res.code == 1) {
                wx.setStorageSync('token', res.response)
                wx.reLaunch({
                  url: '/pages/index/index',
                });
              } else {
                app.message(res.message, 'error')
              }
            }).catch(e => {
              _this.setData({
                spinShow: false
              });
              app.message(e, 'error')
            })
        } else {
          app.message(res.errMsg, 'error')
        }
      }
    })
  },
  register: function(e) {
    wx.navigateTo({
      url: "../register/index"
    })
  }
})
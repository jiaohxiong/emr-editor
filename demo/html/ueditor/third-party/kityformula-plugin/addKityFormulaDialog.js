 UE.registerUI('kityformula', function(editor, uiname){
    // 创建dialog
    var kfDialog = new UE.ui.Dialog({

        // 指定弹出层路径
        iframeUrl: editor.options.UEDITOR_HOME_URL + 'third-party/kityformula-plugin/kityFormulaDialog.html',
        // 编辑器实例
        editor: editor,
        // dialog 名称
        name: uiname,
        // dialog 标题
        title: '插入公式',
        //className
        className :'edui-for-' + uiname,
        // dialog 外围 css
        cssRules: 'width:783px; height: 386px;',

        //如果给出了buttons就代表dialog有确定和取消
        buttons:[
            {
                className:'edui-okbutton',
                label:'确定',
                onclick:function () {
                    kfDialog.close(true);
                }
            },
            {
                className:'edui-cancelbutton',
                label:'取消',
                onclick:function () {
                    kfDialog.close(false);
                }
            }
        ]});

    editor.ready(function(){
        UE.utils.cssRule('kfformula', 'img.kfformula{vertical-align: middle;}', editor.document);
    });

    var iconUrl = editor.options.UEDITOR_HOME_URL + 'third-party/kityformula-plugin/kf-icon.png';
    var tmpLink = document.createElement('a');
    tmpLink.href = iconUrl;
    tmpLink.href = tmpLink.href;
    iconUrl = tmpLink.href;

    var kfBtn = new UE.ui.Button({
        name: uiname,
        title:'插入公式',
        //需要添加的额外样式，指定icon图标
        cssRules :'background: url("' + iconUrl + '") !important',
        onclick:function () {
            //渲染dialog
            kfDialog.render();
            kfDialog.open();
        }
    });

    //当点到编辑内容上时，按钮要做的状态反射
    editor.addListener('selectionchange', function () {
        var state = editor.queryCommandState(uiname);
        if (state == -1) {
            kfBtn.setDisabled(true);
            kfBtn.setChecked(false);
        } else {
            kfBtn.setDisabled(false);
            kfBtn.setChecked(state);
        }
    });

    return kfBtn;

});
baidu.editor.ui.kityformula = function(editor,list,title){

  // 创建dialog
  var kfDialog = new UE.ui.Dialog({

    // 指定弹出层路径
    iframeUrl: editor.options.UEDITOR_HOME_URL + 'third-party/kityformula-plugin/kityFormulaDialog.html',
    // 编辑器实例
    editor: editor,
    // dialog 名称
    name: 'kityformula',
    // dialog 标题
    title: '插入公式',
    // dialog 外围 css
    cssRules: 'width:783px; height: 386px;',

    //如果给出了buttons就代表dialog有确定和取消
    buttons:[
      {
        className:'edui-okbutton',
        label:'确定',
        onclick:function () {
          kfDialog.close(true);
        }
      },
      {
        className:'edui-cancelbutton',
        label:'取消',
        onclick:function () {
          kfDialog.close(false);
        }
      }
    ]});

  editor.ready(function(){
    UE.utils.cssRule('kfformula', 'img.kfformula{vertical-align: middle;}', editor.document);
  });

  var iconUrl = editor.options.UEDITOR_HOME_URL + 'third-party/kityformula-plugin/kf-icon.png';
  var tmpLink = document.createElement('a');
  tmpLink.href = iconUrl;
  tmpLink.href = tmpLink.href;
  iconUrl = tmpLink.href;

  var kfBtn = new UE.ui.Button({
    name: 'kityformula-1',
    title:'插入公式',
    //需要添加的额外样式，指定icon图标
    className:'edui-for-kityformula-1',
    cssRules :'background: url("' + iconUrl + '") !important',
    onclick:function () {
      //渲染dialog
      kfDialog.render();
      kfDialog.open();
    }
  });

  //当点到编辑内容上时，按钮要做的状态反射
  editor.addListener('selectionchange', function () {
    var state = editor.queryCommandState('kityformula');
    if (state == -1) {
      kfBtn.setDisabled(true);
      kfBtn.setChecked(false);
    } else {
      kfBtn.setDisabled(false);
      kfBtn.setChecked(state);
    }
  });

  return kfBtn;

}

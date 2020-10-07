// 为工具栏添加按钮，以下都是统一的按钮触发命令
// UEDITOR 自带按钮
window.editCmds = [
  "bold",
  "italic",
  "underline",
  "fontborder",
  "strikethrough",
  "subscript",
  "superscript",
  "|",
  "paragraph",
  "fontfamily",
  "fontsize",
  "|",
  "indent",
  "outdent",
  "|",
  "rowspacingtop",
  "rowspacingbottom",
  "lineheight",
  "|",
  "forecolor",
  "backcolor",
  "insertorderedlist",
  "insertunorderedlist",
  "indent",
  "|",
  "justifyleft",
  "justifycenter",
  "justifyright",
  "justifyjustify",
];
// 固定按钮
window.fixedCmds = [
  "undo",
  "redo",
  "formatmatch",
  "removeformat",
  "|",
  "pagenumber",
  "print",
  "preview",
  "searchreplace",
  "|",
  "copy",
  "save",
  "mobile",
  "source",
  "check"
  
  // "allwidgets",
  // "allhtml",
];
// UEDIOR 自带控件
window.insertCmds = [
  "simpleupload",
  "insertimage",
  "scrawl",
  "inserttable",
  "|",
  "pagebreak",
  "spechars",
  "|",
  "kityformula",
  "|",
  "horizontal",
  "snapscreen",
  "|",
  "inserttable",
  "deletetable",
  "insertparagraphbeforetable",
  "insertrow",
  "deleterow",
  "insertcol",
  "deletecol",
  "|",
  "copyplan",
  "|",
  "fullrow",
  "averagedistributecol",
  "mergecells",
  "mergeright",
  "mergedown",
  "splittocells",
  "splittorows",
  "splittocols",
];
/*var insertCmds = [
 'kityformula'
 ]*/
 // 表格操作按钮
window.tableCmds = [
  "deletetable",
  "insertparagraphbeforetable",
  "insertrow",
  "deleterow",
  "insertcol",
  "deletecol",
  "|",
  "copyplan",
  "|",
  "mergecells",
  "mergeright",
  "mergedown",
  "splittocells",
  "splittorows",
  "splittocols",
];
// 电子病历按钮
window.emreCmds = [
  "checkbox",
  "|",
  "radio",
  "|",
  "select",
  "|",
  "input",
  "|",
  "timeinput",
  "|",
  "totalscore",
  "|",
  "score",
  "|",
  "comment"
]; // 'template','controllibrary',
window.modeCmds = ["design", "edit", "readonly", "review"];
//页面布局按钮
window.layoutCmds = ["vertical", "horizontal2"];
// 文件按钮
window.fileCmds = ["open", "new", "datasource", "component"];
window.patientCms = ["newdoc", "deletedoc", "patient"];

window.BASE_URL =
  location.host.indexOf("80") > -1
    ? "http://" + location.hostname + ":8828"
    : (location.host.indexOf("xdawo")>-1?"http://emr.xdawo.com":"");
console.log("default BASE_URL=",window.BASE_URL);

window.title = "博钧电子病历编辑器";
/**
 * 
 * execCommand 命令
 * 
 * 1、setopt 参数设置
 * {
 *  baseUrl: 除去方法名的接口地址
 * }
 * 
 * 
 * 2、getdoc 获取文档内容
 * {
 *  doc:{}, 打开文档对象
 *  html: "", 文档html
 *  structure: "", 文档自定义组件结构
 *  attrs: [], 组件描述
 *  data: [], 组件填入的数据
 * }
 * 
 * 3、opendoc 打开文档
 * {
 *  mode: 0, 模式0-3 只读、编辑、设计、审阅
 *  showToolbar: false, 是否展示保存按钮栏
 *  doc: {} 文档对象
 * }
 */
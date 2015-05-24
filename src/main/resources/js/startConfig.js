/**
 * 配置开始页面JS
 */

$(function(){
    
});

/**
 * 点击“下一步”按钮，完成推荐系统名称的设置
 * */
function startConfig(){
    var recommenderName = $("input[name='recommenderName']").val();
    if(recommenderName.trim() == ""){
    	alert("请填写推荐系统名称！");
    	return false;
    }
    $.cookie("recommenderName", recommenderName);
    window.location.href = "/sr/config/dataconfig";
}

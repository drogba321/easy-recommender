/**
 * 算法评估机制配置页面JS
 */

$(function(){

});

/**
 * 向后端提交推荐系统的配置信息
 * */
function commitConfig(){
    $.ajax({
        async : false,
        type : "POST",
        dataType : "text",
        mode : "block",
        url : "/sr/config/receiveconfig",
        data : $("#configForm").serialize(),
        success : function(data){
            if(data == "success"){
            	window.location.href = "/sr/config/finish";
            } else {
            	alert(data);
            }
        },
        error : function(){
        	alert("发生错误，请重试！");
        }
    });
}

/**
 * 建立算法评估机制参数配置模型的JSON对象
 * */
function buildEvaluator(){
    var evaluator = new Object();
    evaluator.deviation = $("select[name='deviation']").val();
    evaluator.precision = $("select[name='precision']").val();
    evaluator.recall = $("select[name='recall']").val();
    evaluator.runningTime = $("select[name='runningTime']").val();

    $.cookie("evaluator", JSON.stringify(evaluator));
    console.log("evaluator:" + JSON.stringify(evaluator));
}

/**
 * 建立整个推荐系统参数配置模型的JSON对象
 * */
function buildConfig(){
    buildEvaluator();
    var config = new Object();

    var recommenderName = $.cookie("recommenderName");
    var dataList = $.parseJSON($.cookie("dataList"));
    var similarityList = $.parseJSON($.cookie("similarityList"));
    var algorithm = $.parseJSON($.cookie("algorithm"));
    var evaluator = $.parseJSON($.cookie("evaluator"));

    config.recommenderName = recommenderName;
    config.dataList = dataList;
    config.similarityList = similarityList;
    config.algorithm = algorithm;
    config.evaluator = evaluator;
    

    var finalConfig = JSON.stringify(config).replace(/\"/g, "'").replace(/\\\\/g, "/").replace(/\\/g, "/");
    console.log("config:" + JSON.stringify(finalConfig));
    
    $("#configJSON").val(finalConfig);
    
    commitConfig();
}

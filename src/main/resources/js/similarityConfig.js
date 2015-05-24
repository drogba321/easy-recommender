/**
 * 相似度度量方法配置页面JS
 */

similarityCount = 0;
userSimilarityCount = 0;
itemSimilarityCount = 0;

$(function(){

});

/**
 * 添加用户间相似度度量机制
 * */
function addUserSimilarity(){
    var userSimilarityDiv = $("#userSimilarity");
    var newUserSimilarityDiv = $("<div class='box' title='similarity'></div>");
    newUserSimilarityDiv.html(userSimilarityDiv.html());
    $("#content").append(newUserSimilarityDiv);
}

/**
 * 添加项目间相似度度量机制
 * */
function addItemSimilarity(){
    var itemSimilarityDiv = $("#itemSimilarity");
    var newItemSimilarityDiv = $("<div class='box' title='similarity'></div>");
    newItemSimilarityDiv.html(itemSimilarityDiv.html());
    $("#content").append(newItemSimilarityDiv);
}

/**
 * 检查相似度度量方法的个数
 * */
function validateSimilarityCount(){
    var flag = true;
    if($.cookie("preferenceDataCount") != similarityCount){
        alert("用户偏好数据源的个数与相似度度量方法的个数不相等，无法一一匹配，因此无法创建协同过滤推荐器，请刷新页面并重新配置！");
        return false;
    }
}

/**
 * 创建相似度度量方法的列表
 * */
function buildSimilarityList(){
    var similarityList = new Array();
    $("[title='similarity']").each(function(index){
        //跳过前2个隐藏的div
        if($(this).attr("id") == "itemSimilarity" || $(this).attr("id") == "userSimilarity"){
            return true;
        }
        similarityCount++;
        var theSimilarity = new Object();
        var similarityDiv = $(this);
        if(similarityDiv.find("form[name='userSimilarityForm']").length > 0){
            theSimilarity.similarity = "userSimilarity";
            theSimilarity.similarityType = similarityDiv.find("select[name='userSimilarity']").val();
            similarityList[index-2] = theSimilarity;
            userSimilarityCount++;
        }
        if(similarityDiv.find("form[name='itemSimilarityForm']").length > 0){
            theSimilarity.similarity = "itemSimilarity";
            theSimilarity.similarityType = similarityDiv.find("select[name='itemSimilarity']").val();
            similarityList[index-2] = theSimilarity;
            itemSimilarityCount++;
        }
    });
    return similarityList;
}

/**
 * 点击“下一步”按钮，完成相似度度量方法的配置
 * */
function finishSimilarityConfig(){

    var similarityList = buildSimilarityList();

    if(validateSimilarityCount() == false){
        return false;
    }

    $.cookie("similarityList", JSON.stringify(similarityList));
    $.cookie("similarityCount", similarityCount);
    $.cookie("userSimilarityCount", userSimilarityCount);
    $.cookie("itemSimilarityCount", itemSimilarityCount);
    console.log("similarityList:" + JSON.stringify(similarityList));
    
    window.location.href = "/sr/config/algorithmconfig";
}

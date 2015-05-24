/**
 * 算法处理流程配置页面JS
 */

$(function(){

});

/**
 * 生成基于用户的协同过滤推荐算法的配置表单
 * */
function renderUserBasedCF(obj){
    var thresholdInput = $("<span title='thresholdSpan'>&nbsp;阈值：</span><input type='text' class='field size2' name='thresholdInput' />");
    var nearestNInput = $("<span title='nearestNSpan'>&nbsp;邻域大小：</span><input type='text' class='field size2' name='nearestNInput' />");
    if($(obj).val()==""){
        $(obj).parents("[title='userBasedCFFields']").find("[title='thresholdSpan']").remove();
        $(obj).parents("[title='userBasedCFFields']").find("[name='thresholdInput']").remove();
        $(obj).parents("[title='userBasedCFFields']").find("[title='nearestNSpan']").remove();
        $(obj).parents("[title='userBasedCFFields']").find("[name='nearestNInput']").remove();
    }
    if($(obj).val()=="threshold"){
        $(obj).parents("[title='userBasedCFFields']").find("[title='thresholdSpan']").remove();
        $(obj).parents("[title='userBasedCFFields']").find("[name='thresholdInput']").remove();
        $(obj).parents("[title='userBasedCFFields']").find("[title='nearestNSpan']").remove();
        $(obj).parents("[title='userBasedCFFields']").find("[name='nearestNInput']").remove();
        $(obj).parents("[title='userBasedCFFields']").append(thresholdInput);
    }
    if($(obj).val()=="nearestN"){
        $(obj).parents("[title='userBasedCFFields']").find("[title='thresholdSpan']").remove();
        $(obj).parents("[title='userBasedCFFields']").find("[name='thresholdInput']").remove();
        $(obj).parents("[title='userBasedCFFields']").find("[title='nearestNSpan']").remove();
        $(obj).parents("[title='userBasedCFFields']").find("[name='nearestNInput']").remove();
        $(obj).parents("[title='userBasedCFFields']").append(nearestNInput);
    }
}

/**
 * 在加权型混合推荐算法中，生成基于用户的协同过滤推荐算法的配置表单
 * */
function renderUserBasedCFinWeightedMixed(obj){
    var thresholdInput = $("<span title='thresholdSpan'>&nbsp;阈值：</span><input type='text' class='field size2' name='thresholdInput' />");
    var nearestNInput = $("<span title='nearestNSpan'>&nbsp;邻域大小：</span><input type='text' class='field size2' name='nearestNInput' />");
    if($(obj).val()==""){
        $(obj).parents("[title='weightedMixedFields']").find("[title='thresholdSpan']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[name='thresholdInput']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[title='nearestNSpan']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[name='nearestNInput']").remove();
    }
    if($(obj).val()=="threshold"){
        $(obj).parents("[title='weightedMixedFields']").find("[title='thresholdSpan']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[name='thresholdInput']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[title='nearestNSpan']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[name='nearestNInput']").remove();
        $(obj).parents("[title='weightedMixedFields']").append(thresholdInput);
    }
    if($(obj).val()=="nearestN"){
        $(obj).parents("[title='weightedMixedFields']").find("[title='thresholdSpan']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[name='thresholdInput']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[title='nearestNSpan']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[name='nearestNInput']").remove();
        $(obj).parents("[title='weightedMixedFields']").append(nearestNInput);
    }
}

/**
 * 在瀑布型混合推荐算法中，生成基于用户的协同过滤推荐算法的配置表单
 * */
function renderUserBasedCFinWaterfallMixed(obj){
    var thresholdInput = $("<span title='thresholdSpan'>&nbsp;阈值：</span><input type='text' class='field size2' name='thresholdInput' />");
    var nearestNInput = $("<span title='nearestNSpan'>&nbsp;邻域大小：</span><input type='text' class='field size2' name='nearestNInput' />");
    if($(obj).val()==""){
        $(obj).parents("[title='waterfallMixedFields']").find("[title='thresholdSpan']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[name='thresholdInput']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[title='nearestNSpan']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[name='nearestNInput']").remove();
    }
    if($(obj).val()=="threshold"){
        $(obj).parents("[title='waterfallMixedFields']").find("[title='thresholdSpan']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[name='thresholdInput']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[title='nearestNSpan']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[name='nearestNInput']").remove();
        $(obj).parents("[title='waterfallMixedFields']").append(thresholdInput);
    }
    if($(obj).val()=="nearestN"){
        $(obj).parents("[title='waterfallMixedFields']").find("[title='thresholdSpan']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[name='thresholdInput']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[title='nearestNSpan']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[name='nearestNInput']").remove();
        $(obj).parents("[title='waterfallMixedFields']").append(nearestNInput);
    }
}

/**
 * 生成加权型混合推荐算法的配置表单
 * */
function renderWeightedMixed(obj){
    if($(obj).val() ==  ""){
        $(obj).parents("[title='weightedMixedFields']").find("[name='weightInput']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[title='weightSpan']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[name='userNeighborhoodSelect']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[title='userNeighborhoodSpan']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[title='thresholdSpan']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[name='thresholdInput']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[title='nearestNSpan']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[name='nearestNInput']").remove();
    }
    if($(obj).val() ==  "contentBased"){
        $(obj).parents("[title='weightedMixedFields']").find("[name='weightInput']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[title='weightSpan']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[name='userNeighborhoodSelect']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[title='userNeighborhoodSpan']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[title='thresholdSpan']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[name='thresholdInput']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[title='nearestNSpan']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[name='nearestNInput']").remove();

        var weightInput = $("<input type='text' class='field size2' name='weightInput' />");
        $(obj).parents("[title='weightedMixedFields']").append("<span title='weightSpan'>&nbsp;算法权重：</span>");
        $(obj).parents("[title='weightedMixedFields']").append(weightInput);

    }
    if($(obj).val() ==  "userBasedCF"){
        $(obj).parents("[title='weightedMixedFields']").find("[name='weightInput']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[title='weightSpan']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[name='userNeighborhoodSelect']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[title='userNeighborhoodSpan']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[title='thresholdSpan']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[name='thresholdInput']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[title='nearestNSpan']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[name='nearestNInput']").remove();

        var userBasedFields = $("<select name='userNeighborhoodSelect' class='field size4' onchange='renderUserBasedCFinWeightedMixed(this)'></select>");
        userBasedFields.append("<option value=''>请选择</option>");
        userBasedFields.append("<option value='nearestN'>固定大小的用户邻域</option>");
        userBasedFields.append("<option value='threshold'>基于阈值的用户邻域</option>");

        var weightInput = $("<input type='text' class='field size2' name='weightInput' />");
        $(obj).parents("[title='weightedMixedFields']").append("<span title='weightSpan'>&nbsp;算法权重：</span>");
        $(obj).parents("[title='weightedMixedFields']").append(weightInput);
        $(obj).parents("[title='weightedMixedFields']").append("<span title='userNeighborhoodSpan'>&nbsp;用户邻域：</span>");
        $(obj).parents("[title='weightedMixedFields']").append(userBasedFields);

    }
    if($(obj).val() ==  "itemBasedCF"){
        $(obj).parents("[title='weightedMixedFields']").find("[name='weightInput']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[title='weightSpan']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[name='userNeighborhoodSelect']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[title='userNeighborhoodSpan']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[title='thresholdSpan']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[name='thresholdInput']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[title='nearestNSpan']").remove();
        $(obj).parents("[title='weightedMixedFields']").find("[name='nearestNInput']").remove();

        var weightInput = $("<input type='text' class='field size2' name='weightInput' />");
        $(obj).parents("[title='weightedMixedFields']").append("<span title='weightSpan'>&nbsp;算法权重：</span>");
        $(obj).parents("[title='weightedMixedFields']").append(weightInput);

    }
}

/**
 * 生成瀑布型混合推荐算法的配置表单
 * */
function renderWaterfallMixed(obj){
    if($(obj).val() ==  ""){
        $(obj).parents("[title='waterfallMixedFields']").find("[name='waterfallInput']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[title='waterfallSpan']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[name='userNeighborhoodSelect']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[title='userNeighborhoodSpan']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[title='thresholdSpan']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[name='thresholdInput']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[title='nearestNSpan']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[name='nearestNInput']").remove();
    }
    if($(obj).val() ==  "contentBased"){
        $(obj).parents("[title='waterfallMixedFields']").find("[name='waterfallInput']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[title='waterfallSpan']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[name='userNeighborhoodSelect']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[title='userNeighborhoodSpan']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[title='thresholdSpan']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[name='thresholdInput']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[title='nearestNSpan']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[name='nearestNInput']").remove();

        var waterfallInput = $("<input type='text' class='field size2' name='waterfallInput' />");
        $(obj).parents("[title='waterfallMixedFields']").append("<span title='waterfallSpan'>&nbsp;推荐数目：</span>");
        $(obj).parents("[title='waterfallMixedFields']").append(waterfallInput);

    }
    if($(obj).val() ==  "userBasedCF"){
        $(obj).parents("[title='waterfallMixedFields']").find("[name='waterfallInput']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[title='waterfallSpan']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[name='userNeighborhoodSelect']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[title='userNeighborhoodSpan']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[title='thresholdSpan']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[name='thresholdInput']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[title='nearestNSpan']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[name='nearestNInput']").remove();

        var userBasedFields = $("<select name='userNeighborhoodSelect' class='field size4' onchange='renderUserBasedCFinWaterfallMixed(this)'></select>");
        userBasedFields.append("<option value=''>请选择</option>");
        userBasedFields.append("<option value='nearestN'>固定大小的用户邻域</option>");
        userBasedFields.append("<option value='threshold'>基于阈值的用户邻域</option>");

        var waterfallInput = $("<input type='text' class='field size2' name='waterfallInput' />");
        $(obj).parents("[title='waterfallMixedFields']").append("<span title='waterfallSpan'>&nbsp;推荐数目：</span>");
        $(obj).parents("[title='waterfallMixedFields']").append(waterfallInput);
        $(obj).parents("[title='waterfallMixedFields']").append("<span title='userNeighborhoodSpan'>&nbsp;用户邻域：</span>");
        $(obj).parents("[title='waterfallMixedFields']").append(userBasedFields);

    }
    if($(obj).val() ==  "itemBasedCF"){
        $(obj).parents("[title='waterfallMixedFields']").find("[name='waterfallInput']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[title='waterfallSpan']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[name='userNeighborhoodSelect']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[title='userNeighborhoodSpan']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[title='thresholdSpan']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[name='thresholdInput']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[title='nearestNSpan']").remove();
        $(obj).parents("[title='waterfallMixedFields']").find("[name='nearestNInput']").remove();

        var waterfallInput = $("<input type='text' class='field size2' name='waterfallInput' />");
        $(obj).parents("[title='waterfallMixedFields']").append("<span title='waterfallSpan'>&nbsp;推荐数目：</span>");
        $(obj).parents("[title='waterfallMixedFields']").append(waterfallInput);

    }
}

/**
 * 在配置加权型混合推荐算法时，添加单个基本推荐算法
 * */
function addSingleAlgorithmInWeightedMix(obj){
    var weightedMixedFields = $("<p class='inline-field' title='weightedMixedFields'></p>");
    var singleAlgorithmTypeSelect = $("<select name='singleAlgorithmNameSelect' class='field size4' onchange='renderWeightedMixed(this)'></select>");
    singleAlgorithmTypeSelect.append("<option value=''>请选择</option>");
    singleAlgorithmTypeSelect.append("<option value='contentBased'>基于内容的推荐</option>");
    singleAlgorithmTypeSelect.append("<option value='userBasedCF'>基于用户的协同过滤推荐</option>");
    singleAlgorithmTypeSelect.append("<option value='itemBasedCF'>基于项目的协同过滤推荐</option>");

    weightedMixedFields.append(singleAlgorithmTypeSelect);
    $(obj).parents("[title='algorithm']").find("[title='singleAlgorithmList']").append(weightedMixedFields);
}

/**
 * 在配置瀑布型混合推荐算法时，添加单个基本推荐算法
 * */
function addSingleAlgorithmInWaterfallMix(obj){
    var waterfallMixedFields = $("<p class='inline-field' title='waterfallMixedFields'></p>");
    var singleAlgorithmTypeSelect = $("<select name='singleAlgorithmNameSelect' class='field size4' onchange='renderWaterfallMixed(this)'></select>");
    singleAlgorithmTypeSelect.append("<option value=''>请选择</option>");
    singleAlgorithmTypeSelect.append("<option value='contentBased'>基于内容的推荐</option>");
    singleAlgorithmTypeSelect.append("<option value='userBasedCF'>基于用户的协同过滤推荐</option>");
    singleAlgorithmTypeSelect.append("<option value='itemBasedCF'>基于项目的协同过滤推荐</option>");

    waterfallMixedFields.append(singleAlgorithmTypeSelect);
    $(obj).parents("[title='algorithm']").find("[title='singleAlgorithmList']").append(waterfallMixedFields);
}

/**
 * 添加基于内容的推荐
 * */
function addContentBasedAlgorithm(){
    //考虑到有5个隐藏的div，
    if($("[title='algorithm']").length > 5){
        alert("一个推荐系统中仅能采用一种推荐算法");
        return false;
    }
    var contentBasedDiv = $("#contentBased");
    var newContentBasedDiv = $("<div class='box' title='algorithm'></div>");
    newContentBasedDiv.html(contentBasedDiv.html());
    $("#content").append(newContentBasedDiv);
}

/**
 * 添加基于用户的协同过滤推荐
 * */
function addUserBasedCFAlgorithm(){
    if($("[title='algorithm']").length > 5){
        alert("一个推荐系统中仅能采用一种推荐算法");
        return false;
    }
    var userBasedCFDiv = $("#userBasedCF");
    var newUserBasedCFDiv = $("<div class='box' title='algorithm'></div>");
    newUserBasedCFDiv.html(userBasedCFDiv.html());
    $("#content").append(newUserBasedCFDiv);
}

/**
 * 添加基于项目的协同过滤推荐
 * */
function addItemBasedCFAlgorithm(){
    if($("[title='algorithm']").length > 5){
        alert("一个推荐系统中仅能采用一种推荐算法");
        return false;
    }
    var itemBasedCFDiv = $("#itemBasedCF");
    var newItemBasedCFDiv = $("<div class='box' title='algorithm'></div>");
    newItemBasedCFDiv.html(itemBasedCFDiv.html());
    $("#content").append(newItemBasedCFDiv);
}

/**
 * 添加加权型混合推荐
 * */
function addWeightedMixedAlgorithm(){
    if($("[title='algorithm']").length > 5){
        alert("一个推荐系统中仅能采用一种推荐算法");
        return false;
    }
    var weightedMixedDiv = $("#weightedMixed");
    var newWeightedMixedDiv = $("<div class='box' title='algorithm'></div>");
    newWeightedMixedDiv.html(weightedMixedDiv.html());
    $("#content").append(newWeightedMixedDiv);
}

/**
 * 添加瀑布型混合推荐
 * */
function addWaterfallMixedAlgorithm(){
    if($("[title='algorithm']").length > 5){
        alert("一个推荐系统中仅能采用一种推荐算法");
        return false;
    }
    var waterfallMixedDiv = $("#waterfallMixed");
    var newWaterfallMixedDiv = $("<div class='box' title='algorithm'></div>");
    newWaterfallMixedDiv.html(waterfallMixedDiv.html());
    $("#content").append(newWaterfallMixedDiv);
}

/**
 * 建立推荐算法处理流程参数配置模型的JSON对象
 * */
function buildAlgorithm(){
    var algorithm = new Object();
    $("div[title='algorithm']").each(function(index){
        //跳过前5个隐藏的div
        if($(this).attr("id") == "contentBased" || $(this).attr("id") == "userBasedCF" || $(this).attr("id") == "itemBasedCF" || $(this).attr("id") == "weightedMixed" || $(this).attr("id") == "waterfallMixed"){
            return true;
        }
        //1、基于内容的推荐
        if($(this).find("[title='contentBasedFields']").length > 0){
            algorithm.algorithmType = "single";

            var singleAlgorithmList = new Array();
            var singleAlgorithm = new Object();

            singleAlgorithm.name = "contentBased";
            singleAlgorithmList.push(singleAlgorithm);
            algorithm.list = singleAlgorithmList;
        }
        //2、基于用户的协同过滤
        if($(this).find("[title='userBasedCFFields']").length > 0){
            algorithm.algorithmType = "single";

            var singleAlgorithmList = new Array();
            var singleAlgorithm = new Object();

            singleAlgorithm.name = "userBasedCF";
            singleAlgorithm.userNeighborhood = $(this).find("select[name='userNeighborhoodSelect']").val();
            singleAlgorithm.nearestN = $(this).find("input[name='nearestNInput']").val();
            singleAlgorithm.threshold = $(this).find("input[name='thresholdInput']").val();
            singleAlgorithmList.push(singleAlgorithm);
            algorithm.list = singleAlgorithmList;
        }
        //3、基于项目的协同过滤
        if($(this).find("[title='itemBasedCFFields']").length > 0){
            algorithm.algorithmType = "single";

            var singleAlgorithmList = new Array();
            var singleAlgorithm = new Object();

            singleAlgorithm.name = "itemBasedCF";
            singleAlgorithmList.push(singleAlgorithm);
            algorithm.list = singleAlgorithmList;
        }
        //4、加权型混合推荐
        if($(this).find("[title='weightedMixedFields']").length > 0){
            var singleAlgorithmList = new Array();
            $(this).find("[title='weightedMixedFields']").each(function(index){
                var singleAlgorithm = new Object();
                singleAlgorithm.name = $(this).find("[name='singleAlgorithmNameSelect']").val();
                singleAlgorithm.weight = $(this).find("[name='weightInput']").val();
                if($(this).find("[name='userNeighborhoodSelect']").length > 0){
                    singleAlgorithm.userNeighborhoodType = $(this).find("[name='userNeighborhoodSelect']").val();
                    singleAlgorithm.nearestN = $(this).find("[name='nearestNInput']").val();
                    singleAlgorithm.threshold = $(this).find("[name='thresholdInput']").val();
                }
                singleAlgorithmList.push(singleAlgorithm);
            });
            algorithm.algorithmType = "weighted";
            algorithm.list = singleAlgorithmList;
        }
        //5、瀑布型混合推荐
        if($(this).find("[title='waterfallMixedFields']").length > 0){
            var singleAlgorithmList = new Array();
            $(this).find("[title='waterfallMixedFields']").each(function(index){
                var singleAlgorithm = new Object();
                singleAlgorithm.name = $(this).find("[name='singleAlgorithmNameSelect']").val();
                singleAlgorithm.resultNum = $(this).find("[name='waterfallInput']").val();
                if($(this).find("[name='userNeighborhoodSelect']").length > 0){
                    singleAlgorithm.userNeighborhoodType = $(this).find("[name='userNeighborhoodSelect']").val();
                    singleAlgorithm.nearestN = $(this).find("[name='nearestNInput']").val();
                    singleAlgorithm.threshold = $(this).find("[name='thresholdInput']").val();
                }
                singleAlgorithmList.push(singleAlgorithm);
            });
            algorithm.algorithmType = "waterfall";
            algorithm.list = singleAlgorithmList;
        }
    });
    return algorithm;

}

/**
 * 验证表单项是否正确填写
 * */
function validateFormBefore() {
    var flag = true;
    if ($("[title='algorithm']").length <= 5) {
        alert("请配置算法处理流程参数！");
        flag = false;
    }

    $("select").each(function (index) {
        if($(this).parents("#contentBased").length > 0 || $(this).parents("#userBasedCF").length > 0 || $(this).parents("#itemBasedCF").length > 0 || $(this).parents("#weightedMixed").length > 0 || $(this).parents("#waterfallMixed").length > 0 ){
            return true;
        }
        if ($(this).val() == "") {
            alert("请选择下拉列表项");
            flag = false;
            return false;
        }
    });

    $("input").each(function (index) {
        if($(this).parents("#contentBased").length > 0 || $(this).parents("#userBasedCF").length > 0 || $(this).parents("#itemBasedCF").length > 0 || $(this).parents("#weightedMixed").length > 0 || $(this).parents("#waterfallMixed").length > 0 ){
            return true;
        }
        if ($(this).val() == "") {
            alert("请填写表单项");
            flag = false;
            return false;
        }
    });

    var integer = /^[0-9]*[1-9][0-9]*$/;
    var float = /^\d+(\.\d+)?$/;
    if ($("input[name='weightInput']").length > 0) {
        $("input[name='weightInput']").each(function (index) {
            if (!float.test($(this).val())) {
                alert("算法权重应为非负浮点数！");
                flag = false;
                return false;
            }
        });
    }
    if ($("input[name='waterfallInput']").length > 0) {
        $("input[name='waterfallInput']").each(function (index) {
            if (!integer.test($(this).val())) {
                alert("推荐数目应为正整数！");
                flag = false;
                return false;
            }
        });
    }
    if ($("input[name='nearestNInput']").length > 0) {
        $("input[name='nearestNInput']").each(function (index) {
            if (!integer.test($(this).val())) {
                alert("用户邻域大小应为正整数！");
                flag = false;
                return false;
            }
        });
    }

    if ($("input[name='thresholdInput']").length > 0) {
        $("input[name='thresholdInput']").each(function (index) {
            if (!float.test($(this).val())) {
                alert("阈值应为非负浮点数！");
                flag = false;
                return false;
            }
        });
    }

    if ($("input[name='waterfallInput']").length > 0) {
        var resultNumList = new Array();
        $("input[name='waterfallInput']").each(function (index) {
            resultNumList.push($(this).val());
        });
        for (var k = 0; k < resultNumList.length - 1; k++) {
            if (parseInt(resultNumList[k]) < parseInt(resultNumList[k+1])) {
                alert("在瀑布型推荐器中，前一个推荐算法的推荐数目应当不小于下一个推荐算法的推荐数目！");
                flag = false;
                break;
            }
        }
    }
    return flag;
}

/**
 * 验证配置的推荐算法是否合法（是否能与先前的配置对应上）
 * */
function validateFormAfter(algorithm){
    var flag = true;
    var list = algorithm.list;
    var similarityList = $.parseJSON($.cookie("similarityList"));
    var cbCount = 0;
    var cfCount = 0;
    var cfList = new Array();
    for(var i=0; i<list.length; i++){
        if(list[i].name == "userBasedCF" || list[i].name == "itemBasedCF"){
            cfCount++;
            cfList.push(list[i]);
        }
        if(list[i].name == "contentBased"){
            cbCount++;
        }
    }
    if(parseInt($.cookie("contentDataCount")) != cbCount){
    	alert("特征数据源个数与基于内容的推荐算法个数不相等，无法一一匹配，因此无法创建基于内容的推荐器，请刷新页面并重新配置！");
    	flag = false;
    }
    if(cfCount != similarityList.length){
        alert("相似度度量方法的个数与协同过滤推荐算法的个数不相等，无法一一匹配，因此无法创建协同过滤推荐器，请刷新页面并重新配置！");
        flag = false;
    }
    for(var j=0; j<cfList.length; j++){
        if(cfList[j].name == "userBasedCF" && similarityList[j].similarity == "itemSimilarity"){
            alert("第" + (j+1) + "个协同过滤算法为基于用户的协同过滤，但对应的相似度度量方法为项目间的相似度度量，无法匹配，请刷新页面并重新配置！");
            flag = false;
            break;
        }
        if(cfList[j].name == "itemBasedCF" && similarityList[j].similarity == "userSimilarity"){
            alert("第" + (j+1) + "个协同过滤算法为基于项目的协同过滤，但对应的相似度度量方法为用户间的相似度度量，无法匹配，请刷新页面并重新配置！");
            flag = false;
            break;
        }
    }
    return flag;
}

/**
 * 点击“下一步”按钮，完成推荐算法处理流程的配置
 * */
function finishAlgorithmConfig(){
    if(validateFormBefore() == false){
        return false;
    }

    var algorithm = buildAlgorithm();

    if(validateFormAfter(algorithm) == false){
        return false;
    }

    $.cookie("algorithm", JSON.stringify(algorithm));
    console.log("algorithm:" + JSON.stringify(algorithm));
    
    window.location.href = "/sr/config/evaluatorconfig";
}

/**
 * 输入数据源配置页面JS
 */

dataCount = 0;
contentDataCount = 0;
preferenceDataCount = 0;

$(function(){

});

/**
 * 添加单个查询条件
 * */
function addQueryUnit(obj){
    var queryUnitList = $(obj).parents("[title='data']").find("[title='queryUnitList']");
    queryUnitList.append("&nbsp;用户字段：");
    queryUnitList.append("<input type='text' class='field size4' name='userColumn' />");
    queryUnitList.append("&nbsp;项目字段：");
    queryUnitList.append("<input type='text' class='field size4' name='itemColumn' />");
    queryUnitList.append("&nbsp;匹配方式：");
    var occurSelect = $("<select class='field size3' name='occur' id='occur'>");
    occurSelect.append("<option value='MUST'>严格匹配</option>");
    occurSelect.append("<option value='MUST_NOT'>排除匹配</option>");
    occurSelect.append("<option value='SHOULD'>模糊匹配</option>");
    queryUnitList.append(occurSelect);
    queryUnitList.append("<br/>");
}

/**
 * 建立多个查询条件的列表
 * */
function buildQueryList(dataDiv){
    var queryList = new Array();
    var count = 0;
    dataDiv.find("input[name='userColumn']").each(function(index){
        count++;
    });
    var queryList = new Array();
    for(var i=0; i<count; i++){
        var queryUnit = new Object();
        queryList[i] = queryUnit;
    }
    dataDiv.find("input[name='userColumn']").each(function(index){
        queryList[index].userColumn = $(this).val();
    });
    dataDiv.find("input[name='itemColumn']").each(function(index){
        queryList[index].itemColumn = $(this).val();
    });
    dataDiv.find("select[name='occur']").each(function(index){
        queryList[index].occur = $(this).val();
    });

    return queryList;
}

/**
 * 建立特征数据参数配置模型的JSON对象
 * */
function buildContentData(dataDiv){
    var contentData = new Object();
    contentData.dbServerName = dataDiv.find("input[name='dbServerName']").val();
    contentData.dbUser = dataDiv.find("input[name='dbUser']").val();
    contentData.dbPassword = dataDiv.find("input[name='dbPassword']").val();
    contentData.dbDatabaseName = dataDiv.find("input[name='dbDatabaseName']").val();
    contentData.userTable = dataDiv.find("input[name='userTable']").val();
    contentData.itemTable = dataDiv.find("input[name='itemTable']").val();
    contentData.queryList = buildQueryList(dataDiv);

    return contentData;
}

/**
 * 建立MySQL用户偏好数据参数配置模型的JSON对象
 * */
function buildMySQLPreferenceData(dataDiv){
    var mysqlPreferenceData = new Object();
    mysqlPreferenceData.dbServerName = dataDiv.find("input[name='dbServerName']").val();
    mysqlPreferenceData.dbUser = dataDiv.find("input[name='dbUser']").val();
    mysqlPreferenceData.dbPassword = dataDiv.find("input[name='dbPassword']").val();
    mysqlPreferenceData.dbDatabaseName = dataDiv.find("input[name='dbDatabaseName']").val();
    mysqlPreferenceData.preferenceTable = dataDiv.find("input[name='preferenceTable']").val();
    mysqlPreferenceData.userIDColumn = dataDiv.find("input[name='userIDColumn']").val();
    mysqlPreferenceData.itemIDColumn = dataDiv.find("input[name='itemIDColumn']").val();
    mysqlPreferenceData.preferenceColumn = dataDiv.find("input[name='preferenceColumn']").val();
    mysqlPreferenceData.timestampColumn = dataDiv.find("input[name='timestampColumn']").val();

    return mysqlPreferenceData;
}

/**
 * 建立文件用户偏好数据参数配置模型的JSON对象
 * */
function buildFilePreferenceData(dataDiv){
    var filePreferenceData = new Object();
    filePreferenceData.filePath = dataDiv.find("input[name='filePath']").val();

    return filePreferenceData;
}

/**
 * 添加特征数据源
 * */
function addContentData(){
    var contentDataDiv = $("#contentData");
    var newContentDataDiv = $("<div class='box' title='data'></div>");
    newContentDataDiv.html(contentDataDiv.html());
    $("#content").append(newContentDataDiv);
}

/**
 * 添加来自MySQL的用户偏好数据源
 * */
function addMySQLPreferenceData(){
    var mysqlPreferenceDiv = $("#mysqlPreferenceData");
    var newMysqlPreferenceDiv = $("<div class='box' title='data'></div>");
    newMysqlPreferenceDiv.html(mysqlPreferenceDiv.html());
    $("#content").append(newMysqlPreferenceDiv);
}

/**
 * 添加来自文件的用户偏好数据源
 * */
function addFilePreferenceData(){
    var filePreferenceDiv = $("#filePreferenceData");
    var newFilePreferenceDiv = $("<div class='box' title='data'></div>");
    newFilePreferenceDiv.html(filePreferenceDiv.html());
    $("#content").append(newFilePreferenceDiv);
}

/**
 * 检查输入数据源列表是否为空
 * */
function validateData(){
    //考虑有3个隐藏的div
    if($("div[title='data']").length <= 3){
        alert("请至少配置一个数据源");
        return false;
    }
}

/**
 * 检查表单项是否为空
 * */
function validateForm(){
    var flag = true;
    $("input").each(function () {
        //对于前3个隐藏的div不做检查
        if($(this).parents("#contentData").length == 0 && $(this).parents("#mysqlPreferenceData").length == 0 && $(this).parents("#filePreferenceData").length == 0){
            if($(this).val() == null || $(this).val() == undefined || $(this).val().trim() == ""){
                //用户偏好值字段和时间戳字段可以为空
                if($(this).attr("name") != "preferenceColumn" && $(this).attr("name") != "timestampColumn"){
                    alert("请完整填写配置信息");
                    flag = false;
                    return false;
                }
            }
        }
    });
    return flag;
}

/**
 * 建立输入数据源的列表
 * */
function buildDataList(){
    var dataList = new Array();
    $("[title='data']").each(function(index){
        //跳过前3个隐藏的div
        if($(this).attr("id") == "contentData" || $(this).attr("id") == "mysqlPreferenceData" || $(this).attr("id") == "filePreferenceData"){
            return true;
        }
        dataCount++;
        var integralData = new Object();
        var dataDiv = $(this);
        if(dataDiv.find("form[name='contentDataForm']").length > 0){
            var contentData = buildContentData(dataDiv);
            integralData.dataType = "content";
            integralData.sourceType = "mysql";
            integralData.data = contentData;
            dataList[index-3] = integralData;
            contentDataCount++;
        }
        if(dataDiv.find("form[name='mysqlPreferenceDataForm']").length > 0){
            var mysqlPreferenceData = buildMySQLPreferenceData(dataDiv);
            integralData.dataType = "preference";
            integralData.sourceType = "mysql";
            integralData.data = mysqlPreferenceData;
            dataList[index-3] = integralData;
            preferenceDataCount++;
        }
        if(dataDiv.find("form[name='filePreferenceDataForm']").length > 0){
            var filePreferenceData = buildFilePreferenceData(dataDiv);
            integralData.dataType = "preference";
            integralData.sourceType = "file";
            integralData.data = filePreferenceData;
            dataList[index-3] = integralData;
            preferenceDataCount++;
        }
    });
    return dataList;
}

/**
 * 点击“下一步”按钮，完成输入数据源的配置
 * */
function finishDataConfig(){
    if(validateData() == false)
        return false;

    if(validateForm() == false){
        return false;
    }

    var dataList = buildDataList();

    $.cookie("dataList", JSON.stringify(dataList));
    $.cookie("dataCount", dataCount);
    $.cookie("contentDataCount", contentDataCount);
    $.cookie("preferenceDataCount", preferenceDataCount);
    console.log("dataList:" + JSON.stringify(dataList));
    
    window.location.href = "/sr/config/similarityconfig";
}

/**
 * 配置完成页面JS
 */

$(function(){
    renderDataConfig();
    renderSimilarityConfig();
    renderAlgorithmConfig();
    renderEvaluatorConfig();
});

/**
 * 渲染输入数据源的配置信息
 * */
function renderDataConfig(){
    var dataList = $.parseJSON($.cookie("dataList"));
    for(var i=0; i<dataList.length; i++){
        var theData = dataList[i];
        //1、用户与项目特征数据源
        if(theData.dataType == "content" && theData.sourceType == "mysql"){
            var contentDataConfig = $("<div id='data" + i +"'></div>");
            contentDataConfig.html($("#content-data").html());
            $("#data-config").append(contentDataConfig);
            $("#data"+i).find("[title='dataType']").html("(用户与项目特征数据库)");
            $("#data"+i).find("[title='dbServerName']").html(theData.data.dbServerName);
            $("#data"+i).find("[title='dbUser']").html(theData.data.dbUser);
            $("#data"+i).find("[title='dbPassword']").html(theData.data.dbPassword);
            $("#data"+i).find("[title='dbDatabaseName']").html(theData.data.dbDatabaseName);
            $("#data"+i).find("[title='userTable']").html(theData.data.userTable);
            $("#data"+i).find("[title='itemTable']").html(theData.data.itemTable);

            var queryList = theData.data.queryList;
            for(var j=0; j<queryList.length; j++){
                var occur;
                if(queryList[j].occur == "MUST"){
                    occur = "严格匹配";
                }
                if(queryList[j].occur == "MUST_NOT"){
                    occur = "排除匹配";
                }
                if(queryList[j].occur == "SHOULD"){
                    occur = "模糊匹配";
                }

                $("#data"+i).find("[title='queryListOl']").append("<li>用户字段：<span title='userColumn'>" + queryList[j].userColumn + "</span>项目字段：<span title='itemColumn'>" + queryList[j].itemColumn + "</span>匹配方式：<span title='occur'>" + occur +"</span></li>");
            }
        }
        //2、来源于MySQL的用户偏好数据
        if(theData.dataType == "preference" && theData.sourceType == "mysql"){
            var mysqlPreferenceDataConfig = $("<div id='data" + i +"'></div>");
            mysqlPreferenceDataConfig.html($("#mysql-preference").html());
            $("#data-config").append(mysqlPreferenceDataConfig);
            $("#data"+i).find("[title='dataType']").html("(用户偏好数据库)");
            $("#data"+i).find("[title='dbServerName']").html(theData.data.dbServerName);
            $("#data"+i).find("[title='dbUser']").html(theData.data.dbUser);
            $("#data"+i).find("[title='dbPassword']").html(theData.data.dbPassword);
            $("#data"+i).find("[title='dbDatabaseName']").html(theData.data.dbDatabaseName);
            $("#data"+i).find("[title='preferenceTable']").html(theData.data.preferenceTable);
            $("#data"+i).find("[title='userIDColumn']").html(theData.data.userIDColumn);
            $("#data"+i).find("[title='itemIDColumn']").html(theData.data.itemIDColumn);
            $("#data"+i).find("[title='preferenceColumn']").html(theData.data.preferenceColumn);
            $("#data"+i).find("[title='timestampColumn']").html(theData.data.timestampColumn);
        }
        //3、来源于文件的用户偏好数据
        if(theData.dataType == "preference" && theData.sourceType == "file"){
            var filePreferenceDataConfig = $("<div id='data" + i +"'></div>");
            filePreferenceDataConfig.html($("#file-preference").html());
            $("#data-config").append(filePreferenceDataConfig);
            $("#data"+i).find("[title='dataType']").html("(用户偏好数据文件)");
            $("#data"+i).find("[title='filePath']").html(theData.data.filePath);
        }
    }
}

/**
 * 渲染相似度度量方法的配置信息
 * */
function renderSimilarityConfig(){
    var similarityList = $.parseJSON($.cookie("similarityList"));
    for(var i=0; i<similarityList.length; i++){
        var similarityType;
        if(similarityList[i].similarity == "userSimilarity"){
            similarityType = "用户间相似度";
        }
        if(similarityList[i].similarity == "itemSimilarity"){
            similarityType = "项目间相似度";
        }
        var similarityName;
        if(similarityList[i].similarityType == "pearsonCorrelationSimilarity"){
            similarityName = "皮尔逊相关系数";
        }
        if(similarityList[i].similarityType == "euclideanDistanceSimilarity"){
            similarityName = "欧几里得距离";
        }
        if(similarityList[i].similarityType == "tanimotoCoefficientSimilarity"){
            similarityName = "谷本系数";
        }
        if(similarityList[i].similarityType == "spearmanCorrelationSimilarity"){
            similarityName = "斯皮尔曼相关系数";
        }
        if(similarityList[i].similarityType == "logLikelihoodSimilarity"){
            similarityName = "对数似然比";
        }
        if(similarityList[i].similarityType == "cityBlockSimilarity"){
            similarityName = "曼哈顿距离";
        }

        $("#similarity-config").find("[title='similarityListOl']").append("<li>" + similarityType + "：<span>" + similarityName + "</span></li>");
    }

}

/**
 * 渲染算法处理流程的配置信息
 * */
function renderAlgorithmConfig(){
    var algorithm = $.parseJSON($.cookie("algorithm"));
    //1、基本推荐算法
    if(algorithm.algorithmType == "single"){
        var list = algorithm.list;
        var theAlgorithm = list[0];
        //1.1、基于内容的推荐
        if(theAlgorithm.name == "contentBased"){
            var contentBasedConfig = $("<div id='theAlgorithm'></div>");
            contentBasedConfig.html($("#content-based").html());
            $("#algorithm-config").append(contentBasedConfig);
            $("#theAlgorithm").find("[title='algorithmType']").html("基于内容的推荐");
        }
        //1.2、基于用户的协同过滤
        if(theAlgorithm.name == "userBasedCF"){
            var userBasedCFConfig = $("<div id='theAlgorithm'></div>");
            userBasedCFConfig.html($("#user-based-cf").html());
            $("#algorithm-config").append(userBasedCFConfig);
            $("#theAlgorithm").find("[title='algorithmType']").html("基于用户的协同过滤");

            var userNeighborhoodType;
            var threshold;
            var nearestN;
            if(theAlgorithm.userNeighborhood == "nearestN"){
                userNeighborhoodType = "固定大小";
                threshold = "--";
                nearestN = theAlgorithm.nearestN;
            }
            if(theAlgorithm.userNeighborhood == "threshold"){
                userNeighborhoodType = "基于阈值";
                threshold = theAlgorithm.threshold;
                nearestN = "--";
            }
            $("#theAlgorithm").find("[title='userNeighborhoodType']").html(userNeighborhoodType);
            $("#theAlgorithm").find("[title='nearestN']").html(nearestN);
            $("#theAlgorithm").find("[title='threshold']").html(threshold);
        }
        //1.3、基于项目的协同过滤
        if(theAlgorithm.name == "itemBasedCF"){
            var itemBasedCFConfig = $("<div id='theAlgorithm'></div>");
            itemBasedCFConfig.html($("#item-based-cf").html());
            $("#algorithm-config").append(itemBasedCFConfig);
            $("#theAlgorithm").find("[title='algorithmType']").html("基于项目的协同过滤");
        }

    }
    //2、加权型混合推荐算法
    if(algorithm.algorithmType == "weighted"){
        var weightedAlgorithmConfig = $("<div id='theAlgorithm'></div>");
        weightedAlgorithmConfig.html($("#weighted").html());
        $("#algorithm-config").append(weightedAlgorithmConfig);
        $("#theAlgorithm").find("[title='algorithmType']").html("加权型混合推荐");

        var list = algorithm.list;
        for(var i=0; i<list.length; i++){
            var singleAlgorithmName;
            if(list[i].name == "contentBased"){
                $("#theAlgorithm").find("[title='singleAlgorithmListOl']").append("<li><span title='singleAlgorithmName'>基于内容的推荐</span>算法权重：<span title='weight'>" + list[i].weight + "</span></li>");
            }
            if(list[i].name == "userBasedCF"){
                var userNeighborhoodType;
                var nearestN;
                var threshold;
                if(list[i].userNeighborhoodType == "nearestN"){
                    userNeighborhoodType = "固定大小";
                    nearestN = list[i].nearestN;
                    threshold = "--";
                }
                if(list[i].userNeighborhoodType == "threshold"){
                    userNeighborhoodType = "基于阈值";
                    nearestN = "--";
                    threshold = list[i].threshold;
                }
                $("#theAlgorithm").find("[title='singleAlgorithmListOl']").append("<li><span title='singleAlgorithmName'>基于用户的协同过滤</span>算法权重：<span title='weight'>" + list[i].weight + "</span>用户邻域类型：<span title='userNeighborhoodType'> " + userNeighborhoodType + "</span>阈值：<span title='threshold'>" + threshold + "</span>邻域大小：<span title='nearestN'>" + nearestN + "</span></li>");
            }
            if(list[i].name == "itemBasedCF"){
                $("#theAlgorithm").find("[title='singleAlgorithmListOl']").append("<li><span title='singleAlgorithmName'>基于项目的协同过滤</span>算法权重：<span title='weight'>" + list[i].weight + "</span></li>");
            }
        }
    }
    //3、瀑布型混合推荐算法
    if(algorithm.algorithmType == "waterfall"){
        var waterfallAlgorithmConfig = $("<div id='theAlgorithm'></div>");
        waterfallAlgorithmConfig.html($("#waterfall").html());
        $("#algorithm-config").append(waterfallAlgorithmConfig);
        $("#theAlgorithm").find("[title='algorithmType']").html("瀑布型混合推荐");

        var list = algorithm.list;
        for(var i=0; i<list.length; i++){
            var singleAlgorithmName;
            if(list[i].name == "contentBased"){
                $("#theAlgorithm").find("[title='singleAlgorithmListOl']").append("<li><span title='singleAlgorithmName'>基于内容的推荐</span>推荐数目：<span title='resultNum'>" + list[i].resultNum + "</span></li>");
            }
            if(list[i].name == "userBasedCF"){
                var userNeighborhoodType;
                var nearestN;
                var threshold;
                if(list[i].userNeighborhoodType == "nearestN"){
                    userNeighborhoodType = "固定大小";
                    nearestN = list[i].nearestN;
                    threshold = "--";
                }
                if(list[i].userNeighborhoodType == "threshold"){
                    userNeighborhoodType = "基于阈值";
                    nearestN = "--";
                    threshold = list[i].threshold;
                }
                $("#theAlgorithm").find("[title='singleAlgorithmListOl']").append("<li><span title='singleAlgorithmName'>基于用户的协同过滤</span>推荐数目：<span title='resultNum'>" + list[i].resultNum + "</span>用户邻域类型：<span title='userNeighborhoodType'> " + userNeighborhoodType + "</span>阈值：<span title='threshold'>" + threshold + "</span>邻域大小：<span title='nearestN'>" + nearestN + "</span></li>");
            }
            if(list[i].name == "itemBasedCF"){
                $("#theAlgorithm").find("[title='singleAlgorithmListOl']").append("<li><span title='singleAlgorithmName'>基于项目的协同过滤</span>推荐数目：<span title='resultNum'>" + list[i].resultNum + "</span></li>");
            }
        }
    }
}

/**
 * 渲染算法评估机制的配置信息
 * */
function renderEvaluatorConfig(){
    var evaluator = $.parseJSON($.cookie("evaluator"));

    var deviation;
    var precision;
    var recall;
    var runningTime;

    if(evaluator.deviation == "true"){
        deviation = "是";
    }
    if(evaluator.deviation == "false"){
        deviation = "否";
    }
    if(evaluator.precision == "true"){
        precision = "是";
    }
    if(evaluator.precision == "false"){
        precision = "否";
    }
    if(evaluator.recall == "true"){
        recall = "是";
    }
    if(evaluator.recall == "false"){
        recall = "否";
    }
    if(evaluator.runningTime == "true"){
        runningTime = "是";
    }
    if(evaluator.runningTime == "false"){
        runningTime = "否";
    }

    $("#evaluator-config").find("[title='deviation']").html(deviation);
    $("#evaluator-config").find("[title='precision']").html(precision);
    $("#evaluator-config").find("[title='recall']").html(recall);
    $("#evaluator-config").find("[title='runningTime']").html(runningTime);
}

/**
 * 点击“重新配置”按钮，回到开始页面重新配置新的推荐系统
 * */
function restart(){
	window.location.href = "/sr/config/start";
}
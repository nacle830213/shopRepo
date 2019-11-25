


function post() {

    var questionId=$("#question_id").val();
    var content=$("#content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:'application/json',
        data: JSON.stringify({
            "parentId":questionId,
            "content":content,
            "type":1
        }),
        success: function (response) {
            if (response.code == 200 || response.status==200){
                $("#comment").hide();
                window.location.reload();
            }else {
                alert(response.message);
            }
            console.log(response)
        },
        dataType: "json"
    });
}

function slectTag(value) {
    var previou  =$("#tag").val();
    if (previou.indexOf(value)==-1){
        if (previou){
            $("#tag").val(previou+','+value);
        }else {
            $("#tag").val(value);
        }
    } 
}
function  refresh() {
    window.location.reload();
}
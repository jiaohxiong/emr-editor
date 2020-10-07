String.prototype.padLeft = function (len, char) {
  var s = this + '';
  return new Array(len - s.length + 1).join(char || '') + s;
}

var data = [], nameKeys = [],nodeIdVals = [],dateTimeNode = [], doc, plans, plan, planId;


function getQuery(variable) {
  var query = window.location.search.substring(1);
  var vars = query.split("&");
  for (var i = 0; i < vars.length; i++) {
    var pair = vars[i].split("=");
    if (pair[0] == variable) { return pair[1]; }
  }
  return (false);
}

function search(e) {
  let q = document.getElementById('search').value;

  let index = 0;
  if(q && (index = nameKeys.indexOf(q)) > -1) {
    let id = 'box-' + nodeIdVals[index];
    let target = document.getElementById(id)
    if(target) {
      target.style.background = "rgb(255,20,20,0.3)";
      target.scrollIntoView({behavior: "smooth", block: "end", inline: "end"});
      setTimeout(function(){
        target.style.background = "rgb(255,255,255,1)";
      }, 500);
    }
  }
  console.log("do serach ....")
  
}

function getTime(time) {
  
  if (time && time != "" && time.length > 2) {
    console.log('time:'+time)
  } else {
    return null;
    // time = new Date().getTime();
  }
  
  console.log("time", time);
  if(typeof time === "string") {
    let r = time.replace(/[-,:]/g, " ").split(" ");
    console.log(r);
    return r;
  } else if(typeof time === "number") {
    let d = new Date(time);
    var o = {
      "Y": d.getFullYear(),
      "M": ((d.getMonth() + 1) + '').padLeft(2, '0'), //月份
      "d": (d.getDate() + '').padLeft(2, '0'), //日
      "H": (d.getHours() + '').padLeft(2, '0'), //小时
      "m": (d.getMinutes() + '').padLeft(2, '0'), //分
      "s": d.getSeconds(), //秒
      "q": Math.floor((d.getMonth() + 3) / 3), //季度
      "S": d.getMilliseconds(), //毫秒
    };
    return [o.Y, o.M, o.d, o.H, o.m];
  }
  return null;
}

function getInputHtml(d) {

  let totalCss = "";
  if (d.type == "totalscore") {
    let tcss = $.md5(d.groupName);
    if (d.plan) {
      tcss += d.plan;
    }
    totalCss = 'class="' + tcss + '"';
  }
  let html = '<li id="box-'+d.nodeId+'">'+
    '<div class="item-content">'+
      '<div class="item-inner">'+
        '<div class="item-title label">'+d.name+'</div>'+
          '<div class="item-input">'+
            '<input id="'+d.nodeId+'" '+totalCss+' type="text" value="'+d.value+'" placeholder="'+d.name+'" '+(d.sourceId?'disabled':'')+' '+(d.type=="totalscore"?'disabled':'')+'/>'+
          '</div>'+
        '</div>'+
      '</div>'+
    '</li >';
  return html;
}
function getScoreInputHtml(d) {

  let totalCss = "";
  let html = '<li id="box-'+d.nodeId+'">'+
    '<div class="item-content">'+
      '<div class="item-inner">'+
        '<div class="item-title label">'+d.name+'</div>'+
          '<div class="item-input">'+
            '<input id="'+d.nodeId+'" total="'+d.totalGroupName+'" type="text" value="'+d.value+'" placeholder="'+d.name+'" onChange="onScoreChanged(this)"/>'+
          '</div>'+
        '</div>'+
      '</div>'+
    '</li >';
  return html;
}

function getDateTime2Html(d) {
  let html = '<li>'+
    '<div class="item-content">'+
      '<div class="item-inner">'+
        '<div class="item-title label">'+d.name+'</div>'+
          '<div class="item-input" id="box-' + d.nodeId +'">'+
            '<input id="'+d.nodeId+'" type="text" value="'+d.value+'" placeholder="'+d.name+'" '+(d.sourceId?'disabled':'')+' />'+
          '</div>'+
        '</div>'+
      '</div>'+
    '</li >';
  
  return html;
}


function getCheckbox2Html(d) {
  let options = '';
  for(let i=0; i<d.attr.options.length;i++) {
      let p = d.attr.options[i];
    options += '<div class="tui-checkbox-box"><span>'+p.label+'</span><input type="checkbox" class="tui-checkbox" name="'+d.nodeId+'" value="'+p.value+'" '+(d.value == p.value?'checked="chekced"':'')+' /></div>';
  }
  let html = '<li>'+
    '<div class="item-content">' +
      '<div class="item-block">' +
        '<div class="item-title label">'+d.name+'</div>' +
          '<div class="item-input" id="box-' + d.nodeId +'">' +
            options +
          '</div>' +
        '</div>' +
      '</div>'+
    '</li>';

    return html;
}


function getRadio2Html(d) {
  let options = '';
  for(let i=0; i<d.attr.options.length;i++) {
      let p = d.attr.options[i];
    options += '<div class="tui-checkbox-box"><span>'+p.label+'</span><input type="radio" class="tui-checkbox" name="'+d.nodeId+'" value="'+p.value+'" ' + (d.value == p.value ? 'checked="chekced"' : '') +' /></div>';
  }
  let html = '<li>'+
    '<div class="item-content">' +
      '<div class="item-block">' +
        '<div class="item-title label">'+d.name+'</div>' +
        '<div class="item-input" id="box-' + d.nodeId +'">' +
          options +
          '</div>' +
        '</div>' +
      '</div>'+
    '</li>';

    return html;
}

function getDateTimeHtml(d) {
  let html = '<li>'+
    '<div class="item-content">'+
      '<div class="item-inner">'+
        '<div class="item-title label">'+d.name+'</div>'+
          '<div class="item-input">'+
            '<input id="'+d.nodeId+'" type="date" value="'+d.value+'" placeholder="'+d.name+'" '+(d.sourceId?'disabled':'')+' />'+
          '</div>'+
        '</div>'+
      '</div>'+
    '</li >';
  return html;
}

function getScoreHtml(d) {
  let options = '';
  for (let i = 0; i < d.options.length; i++) {
    let p = d.options[i];
    options += '<div class="tui-checkbox-box"><span>' + p.name + '</span><input type="checkbox" id="' + p.nodeId + '" class="tui-checkbox" name="' + d.groupName + '" total="' + p.totalGroupName + '" plan="' + p.plan + '" value="' + p.attr.score + '" ' + (p.value ? 'checked="chekced"' : '') + ' onTouchEnd="onScoreOptionClick(event,this)" /></div>';
    
    // set to detaul
    if (p.value) {
      countTotalScore(p.totalGroupName, parseFloat(p.value), p.plan);
    }
  }
  let html = '<li>' +
      '<div class="item-content">' +
        '<div class="item-block">' +
          '<div class="item-title label">' + d.groupName + '</div>' +
          '<div class="item-input">' +
            options +
          '</div>' +
        '</div>' +
      '</div>' +
    '</li>';
  return html;
}

function countTotalScore(totalGroupName,score,plan) {
  console.log(totalGroupName, score, plan);
  if (totalGroupName) {
    let m_totalGroupName = $.md5(totalGroupName);
    if (plan && plan != 'null' && plan != null) {
      m_totalGroupName = m_totalGroupName + plan;
    }
    let el = document.getElementsByClassName(m_totalGroupName);
    // console.log("class=", m_totalGroupName);
    let totalEl;
    if (el && el.length > 0 && (totalEl = el[0])) {
      let oldVal = parseFloat(totalEl.value);
      let realVal = oldVal + parseInt(score);
      totalEl.value = realVal;
    }
  }
}

function onScoreChanged(t) {
  if(t) {
    countTotalScore(t.getAttribute("total"), t.value, null);
  }
}

function onScoreOptionClick(e, t) {

  if (!t.value) {
    console.log('this input value is null');
    return;
  }

  $("input[name='" + t.getAttribute("name") + "']").each(function () {
    if (t.id != this.id) {
      if (this.checked && this.value) {
        let val = -parseFloat(this.value);
        countTotalScore(this.getAttribute("total"), val, this.getAttribute("plan"));
      }
      this.checked = false;
    }
  });
  let checked = !t.checked;
  if (checked) {
    let val = parseFloat(t.value);
    countTotalScore(t.getAttribute("total"), val, t.getAttribute("plan"));
  } else {
    let val = -parseFloat(t.value)
    countTotalScore(t.getAttribute("total"), val, t.getAttribute("plan"));
  }
}

function parseFormData() {

  let edit = data;
  if (planId) {
    edit = plans[planId];
  }

  edit.map((item, index) => {
    if (item.name && (item.type == "input" || item.type == "timeinput"|| item.type == "totalscore" || item.type == "datetime" || item.type == "text")) {
      item.value = document.getElementById(item.nodeId).value;
    } else if (item.type == "checkbox" || item.type == "select",item.type == "radio") {
      let vals = [];
      $("input[name='" + item.nodeId + "']:checked").each(function () {
        if (this.checked) {
          vals.push(this.value);
        }
      });
      item.value = vals.join(",");
    } else if (item.type == "score") {
      let el = document.getElementById(item.nodeId);
      // console.log(el.checked, el.value);
      if (el.checked) {
        item.value = el.value;
      } else {
        item.value = null;
      }

      
    }
  });

  let r = [].concat(data);
  // console.log(r);
  // 合并 data/plans
  let planIds = Object.keys(plans).sort();
  planIds.map((item, index) => {
    // console.log(plans[item]);
    r = r.concat(plans[item]);
  });
  return r;
}

function toFormData(data) {
  nameKeys = [];
  nodeIdVals = [];
  dateTimeNode = [];

  scoreGroup = {};

  $("#controls").empty();
  data.map((item, index) => {
    item.nodeId = item.nodeId || uuid();

    nameKeys.push(item.name);
    nodeIdVals.push(item.nodeId);

    if (item.name && (item.type == "input" || item.type == "totalscore" || item.type == "text")) {
      $("#controls").append(getInputHtml(item));
    } else if (item.type == "checkbox") {
      $("#controls").append(getCheckbox2Html(item));
    } else if (item.type == "radio" || item.type == "select") {
      $("#controls").append(getRadio2Html(item));
    } else if (item.type == "timeinput" || item.type == "datetime") {
      //$("#controls").append(getDateTimeHtml(item));
      $("#controls").append(getDateTime2Html(item));
      dateTimeNode.push({ nodeId: item.nodeId, value: item.value });
    } else if (item.type == "score") {
      // $("#controls").append(getScoreHtml(item));
      if (item.groupName) {
        if (scoreGroup[item.groupName] && scoreGroup[item.groupName].options) {
          scoreGroup[item.groupName].options.push(item);
        } else {
          scoreGroup[item.groupName] = {
            groupName: item.groupName,
            options: [item],
          };
        }
      } else if (item.name) {
        // 独立记分
        $("#controls").append(getScoreInputHtml(item));
      } else {
        console.log(item);
      }
    }
  });

  if (plans) {
    $("#plans").empty();
    let defualt =
      '<li class="item-content item-link" onclick="openPlan()">' +
        '<div class="item-media"><i class="icon icon-f7"></i></div>' +
        '<div class="item-inner">' +
        '<div class="item-title">默认</div>' +
        // '<div class="item-after">0</div>'+
        "</div>" +
      "</li>";
    $("#plans").append(defualt);

    let planIds = Object.keys(plans).sort();

    planIds.map((item, index) => {
      let l =
        '<li class="item-content item-link" onclick="openPlan(\'' + item + "')\">" +
          '<div class="item-media"><i class="icon icon-f7"></i></div>' +
            '<div class="item-inner">' +
              '<div class="item-title">计划' + (index + 1) + "</div>" +
            // '<div class="item-after">'+(index+1)+'</div>'+
          "</div>" +
        "</li>";
      $("#plans").append(l);
    });
  }

  setTimeout(function () {
    for (let i = 0; i < dateTimeNode.length; i++) {
      let item = dateTimeNode[i];
      // console.log(item);
      let id = "#" + item.nodeId;
      let date = getTime(item.value);
      if(date) {
        $(id).datetimePicker({
          //value: ['1985', '12', '04', '9', '34']
          value: getTime(item.value),
        });
      } else {
        $(id).datetimePicker();
      }
    }
  }, 300);

  setTimeout(function () {
    // console.log(scoreGroup);
    for (let key in scoreGroup) {
      let item = scoreGroup[key];
      $("#controls").append(getScoreHtml(item));
    }
  }, 300);
}
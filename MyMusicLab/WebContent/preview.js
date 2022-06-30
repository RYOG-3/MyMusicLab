var xmlHttpRequest;

var j = 0;

function sendWithGetMethod() {
  var musicselectElement = document.getElementById("music_select");
  var interestselectElement = document.getElementById("interest_select");
  var param = location.search;

 var url = "preview?music_select=" + musicselectElement.value + "&interest_select=" + interestselectElement.value + "&name=" + param;

  xmlHttpRequest = new XMLHttpRequest();
  xmlHttpRequest.onreadystatechange = receive;
  xmlHttpRequest.open("GET", url, true);
  xmlHttpRequest.send(null);
}

function sendWithDeleteMethod(id_value) {
  var url = "preview?music=" + id_value;

  xmlHttpRequest = new XMLHttpRequest();
  xmlHttpRequest.onreadystatechange = receive_delete;
  xmlHttpRequest.open("DELETE", url, true);
  xmlHttpRequest.send(null);

  var tr = document.getElementById(id_value);
  tr = tr.parentNode.parentNode;
  tr.parentNode.deleteRow(tr.sectionRowIndex);
}

function receive() {
  if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
    var response = JSON.parse(xmlHttpRequest.responseText);
    if (response != null) {
      var musicTableElement = document.getElementById("musicTable");

      while (musicTableElement.rows.length > 1) musicTableElement.deleteRow(1);

      
      for (let i = 0; i < response.length; i++) {
        var newRow = musicTableElement.insertRow();

        var newCell = newRow.insertCell();
        var newText = document.createTextNode(response[i].music);
        newCell.appendChild(newText);

        newCell = newRow.insertCell();
        newText = document.createTextNode(response[i].artist);
        newCell.appendChild(newText);

        newCell = newRow.insertCell();
        switch (response[i].music_select) {
          case "j-pop":
          newText = document.createTextNode("J-POP");
          break;

          case "k-pop":
          newText = document.createTextNode("K-POP");
          break;

          case "anime":
          newText = document.createTextNode("アニメ/ゲーム");
          break;

          case "rock":
          newText = document.createTextNode("ロック・ポップス");
          break;

          case "rap":
          newText = document.createTextNode("ラップ・ヒップホップ");
          break;

          case "rb":
          newText = document.createTextNode("R&B・ソウル");
          break;

          case "club":
          newText = document.createTextNode("クラブ・ダンス");
          break;

          case "jazz":
          newText = document.createTextNode("ジャズ");
          break;

          case "classic":
          newText = document.createTextNode("クラシック");
          break;

          case "kayou":
          newText = document.createTextNode("歌謡曲");
          break;

          case "enka":
          newText = document.createTextNode("演歌・民謡");
          break;

          }

        newCell.appendChild(newText);

        newCell = newRow.insertCell();
        if (response[i].interest_select == "concern") {
          newText = document.createTextNode("気になる");
        } else if (response[i].interest_select == "favorite") {
          newText = document.createTextNode("お気に入り");
        } else if (response[i].interest_select == "recommended") {
          newText = document.createTextNode("勧められた");
        } else if (response[i].interest_select == "listened") {
          newText = document.createTextNode("視聴済み");
        }

        newCell.appendChild(newText);

        if (location.search == "") {
          newCell = newRow.insertCell();
          var removeButton = document.createElement("input");
          removeButton.setAttribute("type", "submit");
          removeButton.setAttribute("value", "削除する");
          removeButton.setAttribute("id", response[i].music);
          removeButton.setAttribute("onclick", "sendWithDeleteMethod(this.id)");
          newCell.appendChild(removeButton);
        } else {
	    
	    if(j == 0){
		musicTableElement.rows[0].deleteCell(-1);
		j++;
	    }
        }
      }
    } else {
      alert("該当する曲がありませんでした");
    }
  }
}

function receive_delete() {
  if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
    alert("削除しました");
  }
}

window.addEventListener("load", function() {
	var updateButtonElement = document.getElementById("update");
	updateButtonElement.addEventListener("click", sendWithGetMethod, false);
}, false);

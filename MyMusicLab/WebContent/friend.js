var xmlHttpRequest;

function addWithPostMethod() {
  friendElement = document.getElementById("friend");

  var url = "friend";

  xmlHttpRequest = new XMLHttpRequest();
  xmlHttpRequest.onreadystatechange = receive;
  xmlHttpRequest.open("POST", url, true);
  xmlHttpRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xmlHttpRequest.send("friend=" + friendElement.value);
}

function showWithGetMethod() {
  var url = "friend";

  xmlHttpRequest = new XMLHttpRequest();
  xmlHttpRequest.onreadystatechange = receive_friend;
  xmlHttpRequest.open("GET", url, true);
  xmlHttpRequest.send(null);
}

function sendWithDeleteMethod(id_value) {
  var url = "friend?friend=" + id_value;

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
    if (response.result) {
      alert("追加しました");

      var friendTableElement = document.getElementById("friendTable");
      var newRow = friendTableElement.insertRow();

      var newCell = newRow.insertCell();
      var newText = document.createTextNode(friendElement.value);
      newCell.appendChild(newText);

      newCell = newRow.insertCell();
      var previewButton = document.createElement("input");
      previewButton.setAttribute("type", "submit");
      previewButton.setAttribute("value", "曲を閲覧する");
      previewButton.setAttribute("id", friendElement.value);
      previewButton.setAttribute("onclick", "previewWithGetMethod(this.id)");
      newCell.appendChild(previewButton);

      newCell = newRow.insertCell();
      var previewButton = document.createElement("input");
      previewButton.setAttribute("type", "submit");
      previewButton.setAttribute("value", "削除する");
      previewButton.setAttribute("id", friendElement.value);
      previewButton.setAttribute("onclick", "sendWithDeleteMethod(this.id)");
      newCell.appendChild(previewButton);

    } else {
      alert("追加できませんでした");
    }
  }
}

function receive_friend() {
  if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
    var response = JSON.parse(xmlHttpRequest.responseText);
    if (response != null) {
      var friendTableElement = document.getElementById("friendTable");
      for (let i = 0; i < response.length; i++) {
        var newRow = friendTableElement.insertRow();

        var newCell = newRow.insertCell();
        var newText = document.createTextNode(response[i].friend);
        newCell.appendChild(newText);

        newCell = newRow.insertCell();
        var previewButton = document.createElement("input");
        previewButton.setAttribute("type", "submit");
        previewButton.setAttribute("value", "曲を閲覧する");
        previewButton.setAttribute("id", response[i].friend);
        previewButton.setAttribute("onclick", "previewWithGetMethod(this.id)");
        newCell.appendChild(previewButton);

        newCell = newRow.insertCell();
        var removeButton = document.createElement("input");
        removeButton.setAttribute("type", "submit");
        removeButton.setAttribute("value", "削除する");
        removeButton.setAttribute("id", response[i].friend);
        removeButton.setAttribute("onclick", "sendWithDeleteMethod(this.id)");
        newCell.appendChild(removeButton);
      }
    }
  }
}

function receive_delete() {
  if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
    alert("削除しました");
  }
}

function previewWithGetMethod(id_value) {
  window.location.href = "preview.html?name=" + encodeURIComponent(id_value);
}

window.addEventListener("load", function() {
  showWithGetMethod();
	var addButtonElement = document.getElementById("add_button");
	addButtonElement.addEventListener("click", addWithPostMethod, false);
}, false);

var xmlHttpRequest;

function addWithPostMethod() {

  var url = "main";

  xmlHttpRequest = new XMLHttpRequest();
  xmlHttpRequest.onreadystatechange = receive;
  xmlHttpRequest.open("POST", url, true);
  xmlHttpRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xmlHttpRequest.send(null);
}

function showWithGetMethod() {
  var url = "main";

  xmlHttpRequest = new XMLHttpRequest();
  xmlHttpRequest.onreadystatechange = receive_username;
  xmlHttpRequest.open("GET", url, true);
  xmlHttpRequest.send(null);
}

function receive() {
    if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
      window.location.href = "index.html";
      alert("ログアウトしました");
  }
}

function receive_username() {
  if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
    var response = JSON.parse(xmlHttpRequest.responseText);
    if (response != null) {
      var usernameElement = document.getElementById("username");
      var newText = document.createTextNode(response.username);

      usernameElement.appendChild(newText);
    } else {
      alert("名前を取得できませんでした");
    }
  }
}

function previewWithGetMethod(id_value) {
  window.location.href = "preview.html?name=" + encodeURIComponent(id_value);
}

window.addEventListener("load", function() {
  showWithGetMethod();
	var addButtonElement = document.getElementById("logout_button");
	addButtonElement.addEventListener("click", addWithPostMethod, false);
}, false);

var xmlHttpRequest;

function sendWithPostMethod() {
  var nameElement = document.getElementById("name");
  var passwordElement = document.getElementById("password");

  var url = "login";

  xmlHttpRequest = new XMLHttpRequest();
  xmlHttpRequest.onreadystatechange = receive;
  xmlHttpRequest.open("POST", url, true);
  xmlHttpRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xmlHttpRequest.send("name=" + nameElement.value + "&password=" + passwordElement.value);
}

function receive() {
  if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
    var response = JSON.parse(xmlHttpRequest.responseText);
    if (response.result) {
      window.location.href = "main.html";
      alert("ログインしました");
      response.username;
    } else {
      window.location.href = "index.html";
      alert("ログインできませんでした");
    }
  }
}

window.addEventListener("load", function() {
	var loginButtonElement = document.getElementById("login_button");
	loginButtonElement.addEventListener("click", sendWithPostMethod, false);
}, false);

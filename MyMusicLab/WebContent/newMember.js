var xmlHttpRequest;
function sendWithGetMethod() {
  var nameElement = document.getElementById("name");
  var passwordElement = document.getElementById("password");

  var url = "register";

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
     window.location.href = "index.html";
      alert("登録できました");
    } else {
      alert("登録できませんでした");
    }
  }
}

window.addEventListener("load", function() {
	var calculateButtonElement = document.getElementById("register_button");
	calculateButtonElement.addEventListener("click", sendWithGetMethod, false);
}, false);

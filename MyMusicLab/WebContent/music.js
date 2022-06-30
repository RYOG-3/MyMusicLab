var xmlHttpRequest;

function sendWithPostMethod() {
  var musicElement = document.getElementById("music");
  var artistElement = document.getElementById("artist");
  var music_selectElement = document.getElementById("music_select");
  var interest_selectElement = document.getElementById("interest_select");

  var url = "music";

  xmlHttpRequest = new XMLHttpRequest();
  xmlHttpRequest.onreadystatechange = receive;
  xmlHttpRequest.open("POST", url, true);
  xmlHttpRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xmlHttpRequest.send("music=" + musicElement.value + "&artist=" + artistElement.value +
  "&music_select=" + music_selectElement.value + "&interest_select=" + interest_selectElement.value);
}

function receive() {
  if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
    var response = JSON.parse(xmlHttpRequest.responseText);
    if (response.result) {
      alert("登録しました");
    } else {
      alert("登録できませんでした");
    }
  }
}

window.addEventListener("load", function() {
	var registerButtonElement = document.getElementById("register_button");
	registerButtonElement.addEventListener("click", sendWithPostMethod, false);
}, false);
